//https://github.com/ArtsicleOfficial/switching-trainer/blob/master/src/main/java/com/switching/DropsOverlay.java
package org.makingstan.ui.xpdrops;

import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.util.ImageUtil;
import org.makingstan.ArtisanPlugin;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class XPDropOverlay extends Overlay {

    public final float xpDropSpeed = 1.1f;
    BufferedImage icon;
    public ArrayList<XPDrop> xpDrops = new ArrayList<>();

    public XPDropOverlay(ArtisanPlugin plugin) {
        super(plugin);

        icon = ImageUtil.loadImageResource(getClass(),"/Artisan-25.png");
        setPriority(OverlayPriority.LOW);
        setPreferredPosition(OverlayPosition.TOP_LEFT);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        //arbitrary idk why this offset works
        int yOffset = icon.getHeight();

        for(int i = 0; i < xpDrops.size(); i++) {
            float y = xpDrops.get(i).y;
            xpDrops.get(i).y -= xpDropSpeed;

            float amtBelow = y < 0 ? -y : 0;

            graphics.drawImage(icon,0,(int)y+yOffset,icon.getWidth(),(int)y+icon.getHeight()+yOffset-(int)amtBelow,0,(int)amtBelow,icon.getWidth(),icon.getHeight()-(int)amtBelow,null);
            graphics.setColor(Color.WHITE);
            graphics.drawString((int)xpDrops.get(i).xp + "",icon.getWidth()+1,y+20+yOffset);
        }
        xpDrops.removeIf(i -> i.y + icon.getHeight() < 0);
        return new Dimension(icon.getWidth() + 10,300);
    }
}
/*
 * Copyright (c) 2018, Jasper Ketelaar <Jasper0781@gmail.com>
 * Copyright (c) 2020, Anthony <https://github.com/while-loop>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.makingstan.ui.xpdrops;

import java.awt.*;
import java.awt.image.BufferedImage;
import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.Experience;
import static net.runelite.api.MenuAction.RUNELITE_OVERLAY_CONFIG;
import net.runelite.api.Skill;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.SkillColor;
import static net.runelite.client.ui.overlay.OverlayManager.OPTION_CONFIGURE;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.components.*;
import net.runelite.client.ui.overlay.components.ComponentOrientation;
import org.makingstan.ArtisanConfig;
import org.makingstan.ArtisanPlugin;
import org.makingstan.ui.XPBar;

public class XPOverlay extends OverlayPanel
{
    private static final int BORDER_SIZE = 2;
    private static final int XP_AND_PROGRESS_BAR_GAP = 2;
    private static final int XP_AND_ICON_GAP = 4;
    private static final Rectangle XP_AND_ICON_COMPONENT_BORDER = new Rectangle(2, 1, 4, 0);

    private final PanelComponent iconXpSplitPanel = new PanelComponent();
    private final ArtisanPlugin plugin;
    private final ArtisanConfig config;

    @Getter(AccessLevel.PACKAGE)
    private final Skill skill;
    private final BufferedImage icon;

    public XPOverlay(
            ArtisanPlugin plugin,
            ArtisanConfig config,
            Skill skill,
            BufferedImage icon)
    {
        super(plugin);
        this.plugin = plugin;
        this.config = config;
        this.skill = skill;
        this.icon = icon;
        panelComponent.setBorder(new Rectangle(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        panelComponent.setGap(new Point(0, XP_AND_PROGRESS_BAR_GAP));
        iconXpSplitPanel.setBorder(XP_AND_ICON_COMPONENT_BORDER);
        iconXpSplitPanel.setBackgroundColor(null);
        addMenuEntry(RUNELITE_OVERLAY_CONFIG, OPTION_CONFIGURE, "XP Tracker overlay");
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        iconXpSplitPanel.getChildren().clear();

        //Setting the font to rs small font so that the overlay isn't huge
        graphics.setFont(FontManager.getRunescapeSmallFont());


        final LineComponent xpLine = LineComponent.builder()
                .right("3,151,256")
                .rightFont(FontManager.getRunescapeFont())
                .build();

        final ImageComponent imageComponent = new ImageComponent(icon);
        final SplitComponent iconXpSplit = SplitComponent.builder()
                .first(imageComponent)
                .second(xpLine)
                .orientation(ComponentOrientation.HORIZONTAL)
                .gap(new Point(XP_AND_ICON_GAP, 10))
                .build();

        iconXpSplitPanel.getChildren().add(iconXpSplit);

        final XPBar progressBarComponent = new XPBar();

        progressBarComponent.setValue(65.0);
        progressBarComponent.setLabelDisplayMode(XPBar.LabelDisplayMode.TEXT_ONLY);
        progressBarComponent.setBackgroundColor(new Color(0, 0, 0));
        progressBarComponent.setForegroundColor(getProgressBarColor((long) progressBarComponent.getValue()));

        panelComponent.getChildren().add(iconXpSplitPanel);
        panelComponent.getChildren().add(progressBarComponent);

        return super.render(graphics);
    }

    private Color getProgressBarColor(long progressBarValue)
    {
        int percent = (int) progressBarValue;

        Color returnColor = new Color(150, 0, 0);

        if(progressBarValue < 50)
        {
            int green = (int) (150*((progressBarValue*2)/100.0));

            returnColor = new Color(150, green, 0);
        }
        else {
            int red = 150 - (int) (300 * ((progressBarValue-50) / 100.0));
            returnColor = new Color(red, 150, 0);
        }

        return returnColor;
    }

    @Override
    public String getName()
    {
        return super.getName() + skill.getName();
    }
}
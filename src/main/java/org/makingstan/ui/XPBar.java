package org.makingstan.ui;

import com.google.common.base.Strings;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import lombok.Getter;
import lombok.Setter;
import net.runelite.client.ui.overlay.components.ComponentConstants;
import net.runelite.client.ui.overlay.components.LayoutableRenderableEntity;
import net.runelite.client.ui.overlay.components.TextComponent;

@Setter
public class XPBar implements LayoutableRenderableEntity
{
    public void setMinimum(long minimum) {
        this.minimum = minimum;
    }

    public void setMaximum(long maximum) {
        this.maximum = maximum;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setLabelDisplayMode(LabelDisplayMode labelDisplayMode) {
        this.labelDisplayMode = labelDisplayMode;
    }

    public void setCenterLabel(String centerLabel) {
        this.centerLabel = centerLabel;
    }

    public void setLeftLabel(String leftLabel) {
        this.leftLabel = leftLabel;
    }

    public void setRightLabel(String rightLabel) {
        this.rightLabel = rightLabel;
    }

    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public void setPreferredLocation(Point preferredLocation) {
        this.preferredLocation = preferredLocation;
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        this.preferredSize = preferredSize;
    }

    public enum LabelDisplayMode
    {
        PERCENTAGE,
        FULL,
        TEXT_ONLY,
        BOTH
    }

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.0");
    private static final DecimalFormat DECIMAL_FORMAT_ABS = new DecimalFormat("#0");

    private static final int SIDE_LABEL_OFFSET = 4;

    private long minimum;
    private long maximum = 100;
    private double value;
    private LabelDisplayMode labelDisplayMode = LabelDisplayMode.PERCENTAGE;
    private String centerLabel;
    private String leftLabel;
    private String rightLabel;
    private Color foregroundColor = new Color(82, 161, 82);
    private Color backgroundColor = new Color(255, 255, 255, 127);
    private Color fontColor = Color.WHITE;
    private Point preferredLocation = new Point();
    private Dimension preferredSize = new Dimension(ComponentConstants.STANDARD_WIDTH, 16);

    private final Rectangle bounds = new Rectangle();

    @Override
    public Dimension render(Graphics2D graphics)
    {
        final FontMetrics metrics = graphics.getFontMetrics();

        final int barX = preferredLocation.x;
        final int barY = preferredLocation.y;

        final long span = maximum - minimum;
        final double currentValue = value - minimum;
        final double pc = currentValue / span;
        String textToWrite;

        switch (labelDisplayMode)
        {
            case TEXT_ONLY:
                textToWrite = "";
                break;
            case PERCENTAGE:
                textToWrite = formatPercentageProgress(pc);
                break;
            case BOTH:
                textToWrite = formatFullProgress(currentValue, maximum) + " (" + formatPercentageProgress(pc) + ")";
                break;
            case FULL:
            default:
                textToWrite = formatFullProgress(currentValue, maximum);
        }

        if (!Strings.isNullOrEmpty(centerLabel))
        {
            if (!textToWrite.isEmpty())
            {
                textToWrite += " ";
            }

            textToWrite += centerLabel;
        }

        final int width = preferredSize.width;
        final int height = 8;
        final int progressTextX = barX + (width - metrics.stringWidth(textToWrite)) / 2;
        final int progressTextY = barY + ((height - metrics.getHeight()) / 2) + metrics.getHeight();
        final int progressFill = (int) (width * Math.min(1, pc));

        // Draw bar
        graphics.setColor(backgroundColor);
        graphics.fillRect(barX + progressFill, barY, width - progressFill, height);
        graphics.setColor(foregroundColor);
        graphics.fillRect(barX, barY, progressFill, height);

        //draw black border around the bar
        graphics.setColor(Color.BLACK);
        graphics.drawRect(barX, barY, width-1, height-1);

        final TextComponent textComponent = new TextComponent();
        textComponent.setPosition(new Point(progressTextX, progressTextY));
        textComponent.setColor(fontColor);
        textComponent.setText(textToWrite);
        textComponent.render(graphics);

        if (leftLabel != null)
        {
            final TextComponent leftTextComponent = new TextComponent();
            leftTextComponent.setPosition(new Point(barX + SIDE_LABEL_OFFSET, progressTextY));
            leftTextComponent.setColor(fontColor);
            leftTextComponent.setText(leftLabel);
            leftTextComponent.render(graphics);
        }

        if (rightLabel != null)
        {
            final TextComponent leftTextComponent = new TextComponent();
            leftTextComponent.setPosition(new Point(barX + width - metrics.stringWidth(rightLabel) - SIDE_LABEL_OFFSET, progressTextY));
            leftTextComponent.setColor(fontColor);
            leftTextComponent.setText(rightLabel);
            leftTextComponent.render(graphics);
        }

        final Dimension dimension = new Dimension(width, height);
        bounds.setLocation(preferredLocation);
        bounds.setSize(dimension);
        return dimension;
    }

    private static String formatFullProgress(double current, long maximum)
    {
        return DECIMAL_FORMAT_ABS.format(Math.floor(current)) + "/" + maximum;
    }

    private static String formatPercentageProgress(double ratio)
    {
        return DECIMAL_FORMAT.format(ratio * 100d) + "%";
    }
}
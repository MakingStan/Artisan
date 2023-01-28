package org.makingstan;

import net.runelite.api.Client;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ArtisanPanel extends PluginPanel {

    @Inject
    Client client;

    @Inject
    ArtisanConfig config;

    @Inject
    public ArtisanPanel()
    {
        this.setBackground(ColorScheme.DARK_GRAY_COLOR);
        this.setBorder(new EmptyBorder(8, 8, 8, 8));

        JLabel label = new JLabel("Cool");

        this.add(label);
    }
}

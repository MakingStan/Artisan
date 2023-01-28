package com.example;

import org.makingstan.ArtisanPlugin;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ArtisanLauncher
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ArtisanPlugin.class);
		RuneLite.main(args);
	}
}
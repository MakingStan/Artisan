package org.makingstan;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("Artisan")
public interface ArtisanConfig extends Config
{
	@ConfigItem(
		keyName = "Xp Drops",
		name = "Xp Drops",
		description = "Enable or disable xp drops. "
	)
	default boolean xpDrops()
	{
		return true;
	}
}

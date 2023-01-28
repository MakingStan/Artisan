package org.makingstan;

import com.google.inject.Provides;

import javax.inject.Inject;
import javax.inject.Provider;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.CommandExecuted;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ImageUtil;
import org.makingstan.ui.FakeDialogInput;
import org.makingstan.ui.xpdrops.XPDrop;
import org.makingstan.ui.xpdrops.XPDropOverlay;
import org.makingstan.ui.xpdrops.XPOverlay;
import org.makingstan.vnpcs.Master;
import org.makingstan.vnpcs.MasterID;

import java.awt.image.BufferedImage;

@Slf4j
@PluginDescriptor(
	name = "Artisan"
)
public class ArtisanPlugin extends Plugin
{
	private ArtisanPanel panel;
	private NavigationButton navButton;


	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private Client client;

	@Inject
	private ArtisanConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private Provider<FakeDialogInput> fakeDialogInputProvider;

	XPOverlay xpOverlay;
	XPDropOverlay xpDropOverlay;

	@Override
	protected void startUp() throws Exception
	{
		xpOverlay = new XPOverlay(this, config , Skill.AGILITY, ImageUtil.loadImageResource(getClass(), "/Artisan-25.png"));
		xpDropOverlay = new XPDropOverlay(this);

		/* Add the navigation button to the panel */
		panel = injector.getInstance(ArtisanPanel.class);
		final BufferedImage panelIcon = ImageUtil.loadImageResource(getClass(), "/Artisan-25.png");
		navButton = NavigationButton.builder()
				.tooltip("Artisan")
				.icon(panelIcon)
				.priority(1)
				.panel(panel)
				.build();

		overlayManager.add(xpDropOverlay);
		overlayManager.add(xpOverlay);
		clientToolbar.addNavigation(navButton);
	}

	@Override
	protected void shutDown() throws Exception
	{
		//Remove the xp overlay and the xp drop overlay
		overlayManager.remove(xpOverlay);
		overlayManager.remove(xpDropOverlay);

		//Remove the navigation button on the side of the client
		clientToolbar.removeNavigation(navButton);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{

			Widget container = client.getWidget(WidgetInfo.SKILLS_CONTAINER);
			if (container == null) {
				System.out.println("Container is null");
				return;
			}
		}
	}

	@Subscribe
	public void onGameTick(GameTick e)
	{
		xpDrop(100);
	}

	private void xpDrop(int amount)
	{
		xpDropOverlay.xpDrops.add(new XPDrop(amount));
	}

	@Subscribe
	private void onCommandExecuted(CommandExecuted e)
	{
		if(e.getCommand().equalsIgnoreCase("spawnGenie"))
		{
			Master genie = new Master(MasterID.Genie, fakeDialogInputProvider, client);
		}
		if (e.getCommand().equalsIgnoreCase("genie"))
		{
			Master genie = new Master(MasterID.Genie, fakeDialogInputProvider);
			genie.putTalk("Hello! I'm the head of Artisan I will guide you through your exciting journey in the world of Artisan!");
			genie.putTalk("Artisan is a skill similar to Slayer only in skilling form. ");
			genie.putTalk("In Artisan you have a master, similar to a slayer master, an Artisan master will give you tasks that can earn you points and Artisan xp.");

			Master freakyForester = new Master(MasterID.FreakyForester, fakeDialogInputProvider);
			freakyForester.putTalk("I'm an Artisan master!");
			freakyForester.putTalk("I'm the freaky forester, I like to give afk tasks, like redwoods, willow trees, barbarian fishing etc..");
			genie.concatCharacter(freakyForester);

			Master chefOlivia = new Master(MasterID.ChefOlivia, fakeDialogInputProvider);
			chefOlivia.putTalk("Hey! I'm also an Artisan master!");
			chefOlivia.putTalk("I like to give lower level-chain tasks, chain tasks are tasks that require you to use your resources of your previous task in the next task.");
			chefOlivia.putTalk("An example of this would be catching trout and salmon and then cooking them.");
			genie.concatCharacter(chefOlivia);

			Master alryTheAngler = new Master(MasterID.AlryTheAngler, fakeDialogInputProvider);
			alryTheAngler.putTalk("Hey let me talk please! I'm Alry The Artisan Angler.");
			alryTheAngler.putTalk("Haha I like to call myself that, I'm actually Alry the Angler but you could call me Alry The Artisan Angler.");
			alryTheAngler.putTalk("I like to give long tasks and I give you the possibility to block tasks that have to do something with a certain skill.");
			alryTheAngler.putTalk("An example of this would be that you could spend 1000 Artisan points to block all mining tasks from me. ");
			genie.concatCharacter(alryTheAngler);

			genie.putTalk("Hey hey Alry, your going too fast! Let me clarify what Artisan points are!");
			genie.putTalk("Artisan points are points that you gain after completing an Artisan task. Artisan points (similair to slayer points) can be used to learn new things from Artisan masters.");
			genie.putTalk("Artisan masters have a base knowledge and specialize into things. You can learn specific things only from one master and not from another, but you can learn the base knowledge from all of them.");
			genie.putTalk("For example: Alry The Angler can teach you how to block certain Artisan tasks.");
			genie.putTalk("Now it's time for our wise Artisan masters to talk!");

			Master alecKincade = new Master(MasterID.AlecKincade, fakeDialogInputProvider);
			alecKincade.putTalk("Hey hey, I'm Alec Kincade.");
			alecKincade.putTalk("I like to give profitable tasks, I want to help someone make money and buy new gear. For me to have flexibility, you will have to have a base level of 70 in all gathering and utility skills!");
			alecKincade.putTalk("Gathering skills include: Mining, woodcutting, hunting, fishing and utility skills include thieving and agility.");
			alecKincade.putTalk("Aye, my introduction time is over, now you are going to meet Angelo, angelo is the TOP master of Artisan!");
			genie.concatCharacter(alecKincade);

			Master angelo = new Master(MasterID.Angelo, fakeDialogInputProvider);
			angelo.putTalk("Hey it's the man Angelo here! WOOT!");
			angelo.putTalk("I'm Angelo, some call me the TOP master of Artisan, I usually deny that statement though.");
			angelo.putTalk("I like to give tasks to the liking of the player, at an expense of their points though.");
			angelo.putTalk("This means that you can spend your Artisan points at my rewards shop to customize your tasks!");
			angelo.putTalk("That's about it for my introduction, OH WAIT I almost forgot... If you ever become a master of Artisan, you can get your cape by contacting me!");
			genie.concatMaster(angelo);

			genie.putTalk("That's about it, we all welcome you to the world of Artisan. Feel free to contact me if you have any questions!");
			genie.putTalk("Choose the master to your likings! I wish you the best of luck!");



			genie.startTalk();
		}
	}

	@Provides
	ArtisanConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ArtisanConfig.class);
	}
}

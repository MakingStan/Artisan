package org.makingstan.vnpcs;

import lombok.Getter;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.eventbus.Subscribe;
import org.makingstan.ui.DialogType;
import org.makingstan.ui.FakeDialogInput;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.ArrayList;
import java.util.Arrays;


// RuneliteObject code yoinked from: https://github.com/Zoinkwiz/quest-helper/blob/master/src/main/java/com/questhelper/Cheerer.java
public class Master implements TalkableCharacter {
    private int npcID;
    public RuneLiteObject masterRuneliteObject;
    public String displayName;
    private Client client;
    private LocalPoint localPoint;
    private String examineText;

    @Getter
    public boolean onlyTalkable = false;

    @Inject
    private Provider<FakeDialogInput> fakeDialogInputProvider;

    public ArrayList<FakeDialogInput> dialogStack = new ArrayList<>();

    public Master(MasterID masterID, Provider<FakeDialogInput> fakeDialogInputProvider, Client client)
    {
        this.fakeDialogInputProvider = fakeDialogInputProvider;
        this.npcID = masterID.toNpcID();
        this.displayName = client.getNpcDefinition(this.npcID).getName();
        this.examineText = masterID.getExamineText();
        this.client = client;

        this.masterRuneliteObject = client.createRuneLiteObject();
        this.masterRuneliteObject.setModel(masterID.toModel(client));
        this.masterRuneliteObject.setAnimation(client.loadAnimation(-1));
        this.masterRuneliteObject.setShouldLoop(true);

        this.onlyTalkable = false;
    }
    public Master(MasterID masterID, Provider<FakeDialogInput> fakeDialogInputProvider)
    {
        this.fakeDialogInputProvider = fakeDialogInputProvider;
        this.npcID = masterID.toNpcID();

        this.onlyTalkable = true;
    }



    /* Only callable if you have created a master that is not only talkable */
    public void spawnModel()
    {
        if(!onlyTalkable)
        {
            LocalPoint lp = client.getLocalPlayer().getLocalLocation();
            this.masterRuneliteObject.setLocation(new LocalPoint(lp.getX() + 128, lp.getY()), client.getPlane());
            this.localPoint = this.masterRuneliteObject.getLocation();
            this.masterRuneliteObject.setActive(true);
        }
    }

    public String getExamine()
    {
        return this.examineText;
    }


    /* In this function we handle adding the options to the masters */
    public MenuEntry[] addActions(MenuEntry[] menuEntries, int widgetIndex, int widgetID)
    {
        if (localPoint == null) return menuEntries;

        Point p = Perspective.localToCanvas(client, localPoint, client.getPlane(),
                this.masterRuneliteObject.getModelHeight() / 2);
        if (p == null) return menuEntries;


        if (p.distanceTo(client.getMouseCanvasPosition()) > 100) return menuEntries;

        menuEntries = Arrays.copyOf(menuEntries, menuEntries.length + 1);

        client.createMenuEntry(menuEntries.length-1)
                .setOption("Examine")
                .setTarget("<col=ffff00>"+this.displayName+"</col>")
                .setType(MenuAction.RUNELITE)
                .setParam0(widgetIndex)
                .setParam1(widgetID);

        return menuEntries;
    }




    @Override
    public void putTalk(String messageContent)
    {
        FakeDialogInput dialog = fakeDialogInputProvider.get()
                .type(DialogType.DIALOG_HEAD_LEFT)
                .npc(npcID)
                .message(messageContent );

        this.dialogStack.add(dialog);
    }
    @Override
    public void startTalk()
    {
        for(int i = 0; i < this.dialogStack.size(); i++)
        {
            if(i != this.dialogStack.size()-1)
            {
                this.dialogStack.get(i).next(dialogStack.get(i+1));
            }
        }

        this.dialogStack.get(0).build();
    }

    @Override
    public ArrayList<FakeDialogInput> getDialogStack() {
        return this.dialogStack;
    }

    @Override
    public void concatCharacter(TalkableCharacter character) {
        System.out.println("Char dialogstack: "+ character.getDialogStack());
        this.dialogStack.addAll(character.getDialogStack());
    }
}

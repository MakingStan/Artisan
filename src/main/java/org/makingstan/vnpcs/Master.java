package org.makingstan.vnpcs;

import lombok.Getter;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import org.makingstan.JebScapeActor;
import org.makingstan.ui.DialogType;
import org.makingstan.ui.FakeDialogInput;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.ArrayList;


// RuneliteObject code yoinked from: https://github.com/Zoinkwiz/quest-helper/blob/master/src/main/java/com/questhelper/Cheerer.java
public class Master implements TalkableCharacter {
    private int npcID;
    private RuneLiteObject masterRuneliteObject;
    private Client client;

    @Getter
    public boolean onlyTalkable = false;

    @Inject
    private Provider<FakeDialogInput> fakeDialogInputProvider;

    public ArrayList<FakeDialogInput> dialogStack = new ArrayList<>();

    public Master(MasterID masterID, Provider<FakeDialogInput> fakeDialogInputProvider, Client client)
    {
        this.fakeDialogInputProvider = fakeDialogInputProvider;
        this.npcID = masterID.toNpcID();
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

            this.masterRuneliteObject.setActive(true);
        }
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

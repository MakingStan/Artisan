package org.makingstan.vnpcs;

import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.NpcID;
import net.runelite.api.RuneLiteObject;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.callback.ClientThread;
import org.makingstan.ui.DialogType;
import org.makingstan.ui.FakeDialogInput;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.ArrayList;


// RuneliteObject code yoinked from: https://github.com/Zoinkwiz/quest-helper/blob/master/src/main/java/com/questhelper/Cheerer.java
public class Master implements TalkableCharacter {
    private int npcID;
    private RuneLiteObject masterRLObject;
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
        this.masterRLObject.setModel(masterID.toModel(client));
        this.masterRLObject.setAnimation(client.loadAnimation(-1)); // idle animation

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
            masterRLObject.setLocation(new LocalPoint(lp.getX() + 128, lp.getY()), client.getPlane());

            this.masterRLObject.setActive(true);
        }
    }



    public void concatMaster(Master master)
    {
        dialogStack.addAll(master.dialogStack);
    }

    @Override
    public void putTalk(String messageContent)
    {
        FakeDialogInput dialog = fakeDialogInputProvider.get()
                .type(DialogType.DIALOG_HEAD_LEFT)
                .npc(npcID)
                .message(messageContent );

        dialogStack.add(dialog);
    }
    @Override
    public void startTalk()
    {
        for(int i = 0; i < dialogStack.size(); i++)
        {
            if(i != dialogStack.size()-1)
            {
                dialogStack.get(i).next(dialogStack.get(i+1));
            }
        }

        dialogStack.get(0).build();
    }
}

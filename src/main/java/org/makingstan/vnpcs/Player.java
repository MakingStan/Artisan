package org.makingstan.vnpcs;

import org.makingstan.ui.DialogType;
import org.makingstan.ui.FakeDialogInput;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.ArrayList;

public class Player implements TalkableCharacter {

    @Inject
    private Provider<FakeDialogInput> fakeDialogInputProvider;

    private ArrayList<FakeDialogInput> dialogStack = new ArrayList<>();

    public Player(Provider<FakeDialogInput> fakeDialogInputProvider)
    {
        this.fakeDialogInputProvider = fakeDialogInputProvider;
    }
    @Override
    public void putTalk(String messageContent)
    {
        FakeDialogInput dialog = fakeDialogInputProvider.get()
                .type(DialogType.DIALOG_HEAD_RIGHT)
                .player()
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

    @Override
    public ArrayList<FakeDialogInput> getDialogStack() {
        return this.dialogStack;
    }

    @Override
    public void concatCharacter(TalkableCharacter character) {
        this.dialogStack.addAll(character.getDialogStack());
    }
}

package org.makingstan.vnpcs;

import org.makingstan.ui.FakeDialogInput;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.ArrayList;

public interface TalkableCharacter {

    @Inject
    Provider<FakeDialogInput> fakeDialogInputProvider = null;

    public ArrayList<FakeDialogInput> dialogStack = new ArrayList<>();

    public void putTalk(String messageContent);
    public void startTalk();

    public default ArrayList<FakeDialogInput> getDialogStack()
    {
        return dialogStack;
    }

    public default void concatCharacter(TalkableCharacter character)
    {
        dialogStack.addAll(character.getDialogStack());
    }
}

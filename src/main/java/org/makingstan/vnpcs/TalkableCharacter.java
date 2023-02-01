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

    public ArrayList<FakeDialogInput> getDialogStack();


    public void concatCharacter(TalkableCharacter character);
}

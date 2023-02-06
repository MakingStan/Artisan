package org.makingstan.vnpcs;

import net.runelite.api.Client;
import net.runelite.api.Model;
import net.runelite.api.ModelData;
import net.runelite.api.NpcID;

import java.util.ArrayList;
import java.util.Collection;

public enum MasterID {
    Genie,
    FreakyForester,
    ChefOlivia,
    AlryTheAngler,
    AlecKincade,
    Angelo;

    private String examineText;
    private int npcId;

    // Not the best solution but does the job.
    private void setVariables()
    {
        if(examineText != null && npcId != 0) return;
        switch(this)
        {
            case FreakyForester:
                this.examineText = "Genius but freaky...";
                this.npcId = NpcID.FREAKY_FORESTER;
                break;
            case ChefOlivia:
                this.examineText = "Every day she likes to cook.";
                this.npcId = NpcID.CHEF_OLIVIA;
                break;
            case AlryTheAngler:
                this.examineText = "Not someone to mess with...";
                this.npcId = NpcID.ALRY_THE_ANGLER;
                break;
            case AlecKincade:
                this.examineText = "In his best form at the Myth's Guild.";
                this.npcId = NpcID.ALEC_KINCADE;
                break;
            case Angelo:
                this.examineText = "Every day is a music day.";
                this.npcId = NpcID.ANGELO;
                break;
            default:
                this.examineText = "Why is he blue?";
                this.npcId = NpcID.GENIE;
                break;
        }
    }
    public int toNpcID()
    {
        setVariables();

        return this.npcId;
    }

    public String getExamineText()
    {
        setVariables();
        return this.examineText;
    }

    public Model toModel(Client client)
    {
        //get the model
        int[] ids = client.getNpcDefinition(this.toNpcID()).getModels();
        ModelData[] modelData = new ModelData[ids.length];
        for(int i = 0; i < ids.length; i++)
        {
            modelData[i] = client.loadModelData(ids[i]);
        }

        ModelData combinedModelData = client.mergeModels(modelData, ids.length);
        return combinedModelData.light();
    }
}



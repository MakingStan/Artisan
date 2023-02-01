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

    public int toNpcID()
    {
        int returnID = 0;
        /* Correlate the correct MasterID with the correct NpcID */
        switch(this)
        {
            case Genie:
                returnID = NpcID.GENIE;
                break;
            case FreakyForester:
                returnID = NpcID.FREAKY_FORESTER;
                break;
            case ChefOlivia:
                returnID = NpcID.CHEF_OLIVIA;
                break;
            case AlryTheAngler:
                returnID = NpcID.ALRY_THE_ANGLER;
                break;
            case AlecKincade:
                returnID = NpcID.ALEC_KINCADE;
                break;
            case Angelo:
                returnID = NpcID.ANGELO;
                break;
            default:
                returnID = NpcID.GENIE; // should never happen but we do this to be able to make npcID final.
                break;
        }

        return returnID;
    }

    public Model toModel(Client client)
    {
        //ModelData[] modelData = new ModelData[100]; // This just means a max of 100 modeldata pieces.
        //int modelDataLength = 0;
        //Correlate the correct MasterID with the correct NpcID
        /*switch(this)
        {
            case Genie:
                returnModel = client.loadModel(4738);
                break;
            case FreakyForester:
                returnModel = client.loadModel(4738);
                break;
            case ChefOlivia:
                returnModel = client.loadModel(4738);
                break;
            case AlryTheAngler:
                returnModel = client.loadModel(4738);
                break;
            case AlecKincade:
                returnModel = client.loadModel(4738);
                break;
            case Angelo:
                returnModel = client.loadModel(4738);
                break;
            default:
                returnModel = client.loadModel(4738);
                break;
        }

        modelData[0] = client.loadModelData(231);
        modelData[1] = client.loadModelData(241);
        modelData[2] = client.loadModelData(252);
        modelData[3] = client.loadModelData(315);
        modelData[4] = client.loadModelData(173);
        modelData[5] = client.loadModelData(176);
        modelData[6] = client.loadModelData(264);
        modelData[7] = client.loadModelData(270);



        modelDataLength = 7;
        */


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



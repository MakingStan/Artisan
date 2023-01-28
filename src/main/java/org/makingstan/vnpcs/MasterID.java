package org.makingstan.vnpcs;

import net.runelite.api.Client;
import net.runelite.api.Model;
import net.runelite.api.ModelData;
import net.runelite.api.NpcID;

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
        Model returnModel = client.loadModel(4738);
        //Correlate the correct MasterID with the correct NpcID
        switch(this)
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

        return returnModel;
    }
}



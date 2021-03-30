package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.HashMap;
import java.util.Iterator;

public class CreationCard {

    private int cardLevel;
    private Faction faction;
    private int value;
    private HashMap<Resource, Integer> price;
    private HashMap<Resource, Integer> input;
    private HashMap<Resource, Integer> output;

    public boolean canProduce(HashMap<Resource, Integer> resources){
        for (Resource currentResource : this.input.keySet()) {
            if (resources.get(currentResource) < this.input.get(currentResource)) {
                return false;
            }
        }
        return true;
    }

    public HashMap<Resource, Integer> produce(HashMap<Resource, Integer> resourcesIn, HashMap<Resource, Integer> resourcesOut){
        if(canProduce(resourcesIn)) {
            for (Resource currentResource : this.input.keySet()) {
                resourcesIn.put(currentResource, resourcesIn.get(currentResource) - this.input.get(currentResource));
            }
            for (Resource currentResource : this.output.keySet()) {
                resourcesOut.put(currentResource, resourcesOut.get(currentResource) + this.input.get(currentResource));
            }
        }
        return resourcesOut;
    }

    public boolean canBuyCard(HashMap<Resource, Integer> resources){
        for (Resource currentResource : this.price.keySet()) {
            if (resources.get(currentResource) < this.price.get(currentResource)) {
                return false;
            }
        }
        return true;
    }

}

package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.HashMap;

public class CreationCard implements CreationCardGroup {
    private int cardCode;
    private int cardLevel;
    private Faction faction;
    private int value;
    private HashMap<Resource, Integer> price;
    private HashMap<Resource, Integer> input;
    private HashMap<Resource, Integer> output;

    public CreationCard(int cardCode, int cardLevel, int value, Faction faction, HashMap<Resource, Integer> price,
        HashMap<Resource, Integer> input, HashMap<Resource, Integer> output){
        this.cardCode = cardCode;
        this.cardLevel = cardLevel;
        this.faction = faction;
        this.value = value;
        this.price = price;
        this.output = output;
        this.input = input;
    }

    public boolean canProduce(HashMap<Resource, Integer> resources){
        for (Resource currentResource : this.input.keySet()) {
            if (resources.get(currentResource) == null || resources.get(currentResource) < this.input.get(currentResource)) {
                return false;
            }
        }
        return true;
    }

    public HashMap<Resource, Integer> produce(HashMap<Resource, Integer> resourcesIn, HashMap<Resource, Integer> resourcesOut){
        if(canProduce(resourcesIn)) {
            for (Resource currentResourceIn : this.input.keySet()) {
                resourcesIn.put(currentResourceIn, (resourcesIn.get(currentResourceIn) == null ? 0 : resourcesIn.get(currentResourceIn)) - this.input.get(currentResourceIn));
            }
            for (Resource currentResourceOut : this.output.keySet()) {
                resourcesOut.put(currentResourceOut, (resourcesOut.get(currentResourceOut) == null ? 0 : resourcesOut.get(currentResourceOut)) + this.output.get(currentResourceOut));
            }
        }
        return resourcesOut;
    }

    public boolean canBuyCard(HashMap<Resource, Integer> resources){
        for (Resource currentResource : this.price.keySet()) {
            if ((resources.get(currentResource) == null ? 0 : resources.get(currentResource)) < this.price.get(currentResource)) {
                return false;
            }
        }
        return true;
    }

    public HashMap<Resource, Integer> buyCard(HashMap<Resource, Integer> resourcesIn){
        if(canBuyCard(resourcesIn)) {
            for (Resource currentResourceIn : this.price.keySet()) {
                resourcesIn.put(currentResourceIn, (resourcesIn.get(currentResourceIn) == null ? 0 : resourcesIn.get(currentResourceIn)) - this.price.get(currentResourceIn));
            }
        }
        return resourcesIn;
    }

    public int getValue() {
        return value;
    }
    public Faction getFaction() {
        return faction;
    }
    public int getCardLevel() {
        return cardLevel;
    }
    public int getCardCode() {return cardCode; }
    public HashMap<Resource, Integer> getInput() {
        return new HashMap<>(this.input);
    }
    public HashMap<Resource, Integer> getOutput() {
        return new HashMap<>(this.output);
    }
    public boolean canAdd(int cardLevel){return false;}
    public boolean addCard(CreationCard card){return false;}
}

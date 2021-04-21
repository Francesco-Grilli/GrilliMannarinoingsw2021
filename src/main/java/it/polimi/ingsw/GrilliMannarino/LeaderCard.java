package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.HashMap;

public abstract class LeaderCard {
    private HashMap<Resource, Integer> resourcePrice;
    private HashMap<Faction, HashMap<Integer, Integer>> cardPrice;
    private Resource definedResource;
    private final int points;

    //public abstract void execute(Board board);

    public LeaderCard(HashMap<Resource, Integer> resourcePrice, HashMap<Faction, HashMap<Integer, Integer>> cardPrice, Resource definedResource, int points) {
        this.resourcePrice = resourcePrice;
        this.cardPrice = cardPrice;
        this.definedResource = definedResource;
        this.points = points;
    }

    public HashMap<Faction, HashMap<Integer, Integer>> getCardPrice() {
        return new HashMap<>(cardPrice);
    }

    public HashMap<Resource, Integer> getResourcePrice() {
        return new HashMap<>(resourcePrice);
    }

    public int getPoints() {
        return points;
    }

    public Resource getDefinedResource(){
        return definedResource;
    }

}

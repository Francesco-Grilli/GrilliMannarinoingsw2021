package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.HashMap;

public abstract class LeaderCard {
    private final int cardCode;
    private final HashMap<Resource, Integer> resourcePrice;
    private final HashMap<Faction, HashMap<Integer, Integer>> cardPrice;
    private final Resource definedResource;
    private final int points;

    public abstract void execute(Board board);

    public LeaderCard(HashMap<Resource, Integer> resourcePrice, HashMap<Faction, HashMap<Integer, Integer>> cardPrice, Resource definedResource, int points, int cardCode) {
        this.resourcePrice = resourcePrice;
        this.cardPrice = cardPrice;
        this.definedResource = definedResource;
        this.points = points;
        this.cardCode = cardCode;
    }

    public HashMap<Faction, HashMap<Integer, Integer>> getCardPrice() {
        return new HashMap<>(cardPrice);
    }

    public HashMap<Resource, Integer> getResourcePrice() {
        return new HashMap<>(resourcePrice);
    }

    public int getValue() {
        return points;
    }

    public Resource getDefinedResource(){
        return definedResource;
    }

    public int getCardCode(){ return cardCode; }

}

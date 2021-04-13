package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.HashMap;

public abstract class LeaderCard {
    protected HashMap<Resource, Integer> resourcePrice;
    protected HashMap<Faction, HashMap<Integer, Integer>> cardPrice;
    protected Resource definedResource;
    final protected int points;

    public abstract void execute(Board board);

    public LeaderCard(HashMap<Resource, Integer> resourcePrice, HashMap<Faction, HashMap<Integer, Integer>> cardPrice, Resource definedResource, int points) {
        this.resourcePrice = resourcePrice;
        this.cardPrice = cardPrice;
        this.definedResource = definedResource;
        this.points = points;
    }
}

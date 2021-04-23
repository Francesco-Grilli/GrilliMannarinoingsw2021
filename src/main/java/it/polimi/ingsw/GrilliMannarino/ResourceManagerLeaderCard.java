package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.HashMap;

public abstract class ResourceManagerLeaderCard extends LeaderCard{
    @Override
    public void execute(Board board) {
    }

    public ResourceManagerLeaderCard(HashMap<Resource, Integer> resourcePrice, HashMap<Faction, HashMap<Integer, Integer>> cardPrice, Resource definedResource, int points) {
        super(resourcePrice, cardPrice, definedResource, points);
    }

    @Override
    public HashMap<Faction, HashMap<Integer, Integer>> getCardPrice() {
        return super.getCardPrice();
    }

    @Override
    public HashMap<Resource, Integer> getResourcePrice() {
        return super.getResourcePrice();
    }

    @Override
    public int getPoints() {
        return super.getPoints();
    }

    @Override
    public Resource getDefinedResource() {
        return super.getDefinedResource();
    }
}

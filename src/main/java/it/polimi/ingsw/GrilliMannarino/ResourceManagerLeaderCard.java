package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.HashMap;

public abstract class ResourceManagerLeaderCard extends LeaderCard{
    @Override
    public void execute(Board board) {
    }

    public ResourceManagerLeaderCard(HashMap<Resource, Integer> resourcePrice, HashMap<Faction, HashMap<Integer, Integer>> cardPrice, Resource definedResource, int points,int cardCode) {
        super(resourcePrice, cardPrice, definedResource, points,cardCode);
    }

    @Override
    public HashMap<Faction, HashMap<Integer, Integer>> getCardPrice() {
        return super.getCardPrice();
    }

    @Override
    public HashMap<Resource, Integer> getResourcePrice() {
        return super.getResourcePrice();
    }

    public int getPoints() {
        return super.getValue();
    }

    @Override
    public Resource getDefinedResource() {
        return super.getDefinedResource();
    }
}

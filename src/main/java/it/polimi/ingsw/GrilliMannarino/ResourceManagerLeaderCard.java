package it.polimi.ingsw.GrilliMannarino;


import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;

import java.util.HashMap;

public class ResourceManagerLeaderCard implements ResourceManagerInterface{

    ResourceManagerInterface resourceManager;

    public ResourceManagerLeaderCard(ResourceManagerInterface resourceManager) {
        this.resourceManager = resourceManager;
    }

    @Override
    public HashMap<Resource, Integer> getResources() {
        return null;
    }

    @Override
    public HashMap<Resource, Integer> setResourcesFromMarket(Row line, Resource res, Integer value) {
        return null;
    }

    @Override
    public boolean canSetResourcesFromMarket(Row line, Resource res, Integer value) {
        return false;
    }

    @Override
    public void setResourcesFromProduction(HashMap<Resource, Integer> res) {

    }

    @Override
    public boolean canRemove(HashMap<Resource, Integer> input) {
        return false;
    }

    @Override
    public HashMap<Resource, Integer> remove(HashMap<Resource, Integer> input) {
        return null;
    }
}

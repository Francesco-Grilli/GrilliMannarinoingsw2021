package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;

import java.util.HashMap;

public interface ResourceManagerInterface {

    HashMap<Resource, Integer> getResources();

    HashMap<Resource, Integer> setResourcesFromMarket(Row line, Resource res, Integer value);

    boolean canSetResourcesFromMarket(Row line, Resource res, Integer value);

    void setResourcesFromProduction(HashMap<Resource, Integer> res);

    boolean canRemove(HashMap<Resource, Integer> input);

    HashMap<Resource, Integer> remove(HashMap<Resource, Integer> input);

    Row getRow();

    int getPoints();
}
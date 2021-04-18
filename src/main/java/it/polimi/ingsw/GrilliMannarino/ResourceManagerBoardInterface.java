package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;

import java.util.HashMap;

public interface ResourceManagerBoardInterface {

    HashMap<Resource, Integer> getResources();

    HashMap<Resource, Integer> setResourcesFromMarket(Row line, Resource res, Integer value);

    boolean canSetResourcesFromMarket(Row line, Resource res, Integer value);

    void setResourcesFromProduction(HashMap<Resource, Integer> res);

    boolean canRemove(HashMap<Resource, Integer> input);

    HashMap<Resource, Integer> remove(HashMap<Resource, Integer> input);

    Row getRow();

    int getPoints();

    boolean setLineFromChest(Row line, Resource res, Integer value);

    void removeLineFromChest(Row line);

    void execute(ResourceManagerBoardInterface managerBoardInterface);

    boolean canSwapLine(Row one, Row two);

    void forceSwapLine(Row one, Row two);

    HashMap<Resource, Integer> getResourceLine(Row line);
}

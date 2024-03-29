package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;
import org.json.simple.JSONObject;

import java.util.HashMap;

public interface ResourceManagerBoardInterface {

    HashMap<Resource, Integer> getResources();

    HashMap<Resource, Integer> setResourcesFromMarket(Row line, Resource res, Integer value);

    boolean canSetResourcesFromMarket(Row line, Resource res, Integer value);

    void setResourcesFromProduction(HashMap<Resource, Integer> res);

    boolean canRemove(HashMap<Resource, Integer> input);

    HashMap<Resource, Integer> remove(HashMap<Resource, Integer> input);

    Row getRow();

    int getResourcePoints();

    int getNumberOfResource();

    boolean setLineFromChest(Row line, Resource res, Integer value);

    void removeLineFromChest(Row line);

    void execute(Board board);

    boolean canSwapLine(Row one, Row two);

    void forceSwapLine(Row one, Row two);

    HashMap<Resource, Integer> getResourceLine(Row line);

    HashMap<Resource, Integer> getResourcesFromChest();

    HashMap<Row, HashMap<Resource, Integer>> getEachResourceFromWareHouse();

    JSONObject getStatus();

    void setStatus(JSONObject status);
}

package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;

import java.util.HashMap;

public class Collection {

    private final Chest chest;
    private final WareHouse wareHouse;

    public Collection() {
        chest = new Chest();
        wareHouse = new WareHouse();
    }

    public HashMap<Resource, Integer> getResources(){
        HashMap<Resource, Integer> ret = new HashMap<>(getResourcesFromChest());
        HashMap<Resource, Integer> looping = new HashMap<>(getResourcesFromWareHouse());
        for(Resource res : looping.keySet()){
            if(ret.containsKey(res))
                ret.put(res, (ret.get(res) + looping.get(res)));
            else
                ret.put(res, looping.get(res));
        }

        return ret;

    }

    private HashMap<Resource, Integer> getResourcesFromChest(){
        return new HashMap<>(chest.getResources());
    }

    private HashMap<Resource, Integer> getResourcesFromWareHouse(){
        HashMap<Resource, Integer> ret = new HashMap<>();
        HashMap<Row, HashMap<Resource, Integer>> resourceCopy = new HashMap<>(wareHouse.getResources());
        for(Row line : resourceCopy.keySet()){
            for(Resource res : resourceCopy.get(line).keySet()){
                ret.put(res, resourceCopy.get(line).get(res));
            }
        }

        return ret;
    }

    public HashMap<Resource, Integer> setResourcesFromMarket(Row line, Resource res, Integer value){
        if (wareHouse.canAddResources(line, res, value))
            return wareHouse.addResources(line, res, value);
        else
            return null;
    }

    public void setResourcesFromProduction(HashMap<Resource, Integer> res){
        chest.addResources(res);
    }

    //method to remove the resources from chest or warehouse returned by getResources
}

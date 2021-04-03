package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;

import java.util.HashMap;

public class WareHouse {
    private HashMap<Row, HashMap<Resource, Integer>> resources;

    public WareHouse(){
        resources = new HashMap<>();
        resources.put(Row.FIRST, new HashMap<>());
        resources.put(Row.SECOND, new HashMap<>());
        resources.put(Row.THIRD, new HashMap<>());
    }

    public HashMap<Row, HashMap<Resource, Integer>> getResources(){
        HashMap<Row, HashMap<Resource, Integer>> retResources = new HashMap<>(resources);
        return retResources;
    }

    public HashMap<Resource, Integer> addResources(Row line, Resource res, Integer value) {

        HashMap<Resource, Integer> resourceCopy = new HashMap<>(resources.get(line));

        int checkPresence =0;
        for(Row l : resources.keySet()){
            if(resources.get(l).containsKey(res) && l!=line)
                checkPresence++;
        }

        if (resourceCopy.size() == 0 && checkPresence==0) {
            if (value > line.getValue())
                return false;
            else {
                resourceCopy.put(res, value);
                resources.put(line, resourceCopy);
                return true;
            }
        }
        else if (resourceCopy.containsKey(res) && resourceCopy.size() == 1 && checkPresence==0) {
            if ((resourceCopy.get(res) + value) > line.getValue()) {
                return false;
            } else {
                resourceCopy.put(res, resourceCopy.get(res) + value);
                resources.put(line, resourceCopy);
                return true;
            }
        }
        else
            return false;
    }

    public boolean removeResources(Row line, Resource res, Integer value){
        HashMap<Resource, Integer> resourceCopy = new HashMap<>(resources.get(line));

        if(resourceCopy.size()==1 && resourceCopy.containsKey(res)){
            if(resourceCopy.get(res)>value){
                resourceCopy.put(res, resourceCopy.get(res) - value);
                resources.put(line, resourceCopy);
                return true;
            }
            else if(resourceCopy.get(res)==value){
                resourceCopy.clear();
                resources.put(line, resourceCopy);
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }
}

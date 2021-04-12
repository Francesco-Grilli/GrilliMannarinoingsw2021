package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;

import java.util.HashMap;

public class ResourceManager {

    private final Chest chest;
    private final WareHouse wareHouse;

    public ResourceManager() {
        chest = new Chest();
        wareHouse = new WareHouse();
    }

    /**
     * @return all resources from chest and warehouse
     */
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

    /**
     * @return the resources from chest
     */
    private HashMap<Resource, Integer> getResourcesFromChest(){
        return new HashMap<>(chest.getResources());
    }

    /**
     * @return the resources from warehouse
     */
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

    /**
     * @param line take the line where to put the resource
     * @param res take the resource to put
     * @param value take the number of resources
     * @return the resources that couldn't set inside warehouse
     */
    public HashMap<Resource, Integer> setResourcesFromMarket(Row line, Resource res, Integer value){
        if (wareHouse.canAddResources(line, res, value))
            return wareHouse.addResources(line, res, value);
        else{
            HashMap<Resource, Integer> ret = new HashMap<>();
            ret.put(res, value);
            return ret;
        }

    }

    /**
     * check if can add the resource to warehouse
     * @see #setResourcesFromMarket(Row, Resource, Integer)
     * @return true if can add the resource, false otherwise
     */
    public boolean canSetResourcesFromMarket(Row line, Resource res, Integer value){
        return wareHouse.canAddResources(line, res, value);
    }

    /**
     * put the resources inside chest
     * @param res is an hashmap of resources
     */
    public void setResourcesFromProduction(HashMap<Resource, Integer> res){
        chest.addResources(res);
    }

    /**
     * check if can remove the resources from both chest and warehouse. The priority is first WareHouse and then Chest
     * @param input take the resources to remove
     * @return true if can remove, false otherwise
     */
    public boolean canRemove(HashMap<Resource, Integer> input){
        HashMap<Resource, Integer> resources = getResources();
        boolean check = true;
        for (Resource res : input.keySet()) {
            if(!resources.containsKey(res) || !(resources.get(res)>=input.get(res)))
                check=false;
        }
        return check;
    }

    /**
     * remove the resources from both chest and warehouse. The priority is first WareHouse and then Chest
     * @param input is the resources to remove
     * @return an hashmap of resources that it failed to remove
     */
    public HashMap<Resource, Integer> remove(HashMap<Resource, Integer> input){
        return removeFromChest(removeFromWareHouse(input));
    }

    private HashMap<Resource, Integer> removeFromWareHouse(HashMap<Resource, Integer> input){
        HashMap<Row, HashMap<Resource, Integer>> resources = wareHouse.getResources();

        if(canRemove(input)){
            for(Row line : resources.keySet()){
                for(Resource res : resources.get(line).keySet()){
                    if(input.containsKey(res)){
                        if(input.get(res)>=resources.get(line).get(res)){
                            input.put(res, (input.get(res) - resources.get(line).get(res)));
                            wareHouse.removeResources(line, res, resources.get(line).get(res));
                        }
                        else{
                            wareHouse.removeResources(line, res, input.get(res));
                            input.put(res, 0);
                        }
                    }
                }
            }
        }
        return input;
    }

    private HashMap<Resource, Integer> removeFromChest(HashMap<Resource, Integer> input){
        HashMap<Resource, Integer> resources = chest.getResources();

        for(Resource res : input.keySet()){
            if(resources.containsKey(res)){
                if(input.get(res)>=resources.get(res)){
                    input.put(res, (input.get(res) - resources.get(res)));
                    HashMap<Resource, Integer> temp = new HashMap<>();
                    temp.put(res, resources.get(res));
                    chest.removeResources(temp);
                }
                else{
                    HashMap<Resource, Integer> temp = new HashMap<>();
                    temp.put(res, input.get(res));
                    chest.removeResources(temp);
                    input.put(res, 0);
                }
            }
        }
        return input;
    }
}

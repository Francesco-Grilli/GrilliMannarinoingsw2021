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

    /**
     * getResources get all the resources from WareHouse
     * @return an HashMap of type Row that link to another HasMap of resources
     */
    public HashMap<Row, HashMap<Resource, Integer>> getResources(){
        return new HashMap<>(resources);
    }

    /**
     * @param line is the position you want to add your resources
     * @param res is the type of resources you want to add
     * @param value is the number of resources you want to add
     * @return true if you can add resources because there is enough space on the line or even if there isn't enough space
     * or false if you absolutely can't add resources because there is already one resource of type res on another line
     */
    public boolean canAddResources(Row line, Resource res, Integer value) throws IllegalArgumentException {

        HashMap<Resource, Integer> resourceCopy = new HashMap<>(resources.get(line));

        if(value < 0)
            throw new IllegalArgumentException("Negative value");

        boolean checkPresence =true;
        for(Row l : resources.keySet()){
            if(resources.get(l).containsKey(res) && l!=line)
                checkPresence=false;
        }

        return checkPresence;
    }

    /**
     *
     * @param line is the position you want to add your resources
     * @param res is the type of resources you want to add
     * @param value is the number of resources you want to add
     * @return null if the added resources have enough space inside the line or an HashMap with the remaining resources
     * if the space is not enough or if it can't be added because already present in another row
     */
    public HashMap<Resource, Integer> addResources(Row line, Resource res, Integer value) throws IllegalArgumentException {

        HashMap<Resource, Integer> resourceCopy = new HashMap<>(resources.get(line));

        if(value < 0)
            throw new IllegalArgumentException("Negative value");

        if (canAddResources(line, res, value)) {
            if (resourceCopy.containsKey(res)) {
                if ((resourceCopy.get(res) + value) > line.getValue()) {
                    HashMap<Resource, Integer> ret = new HashMap<>();
                    ret.put(res, ((value + resourceCopy.get(res)) - line.getValue()));
                    resourceCopy.put(res, line.getValue());
                    resources.put(line, resourceCopy);
                    return ret;
                } else {
                    resourceCopy.put(res, resourceCopy.get(res) + value);
                    resources.put(line, resourceCopy);
                    return null;
                }
            } else {
                if(value > line.getValue()){
                    HashMap<Resource, Integer> ret = new HashMap<>();
                    ret.put(res, (value - line.getValue()));
                    resourceCopy.put(res, line.getValue());
                    resources.put(line, resourceCopy);
                    return ret;
                } else {
                    resourceCopy.put(res, value);
                    resources.put(line, resourceCopy);
                    return null;
                }
            }
        } else {
            HashMap<Resource, Integer> ret = new HashMap<>();
            ret.put(res, value);
            return ret;
        }

    }


    /**
     * removeResources remove the resources from WareHouse if there are enough resources
     * @see #canRemoveResources(Row, Resource, Integer)
     * @param line select the line to remove the resources
     * @param res select the type of resource to remove
     * @param value is the number of element you want to remove
     */
    public void removeResources(Row line, Resource res, Integer value){
        HashMap<Resource, Integer> resourceCopy = new HashMap<>(resources.get(line));

        if(value < 0)
            throw new IllegalArgumentException("Negative value");

        if(canRemoveResources(line, res, value)){
            if(resourceCopy.get(res)>value){
                resourceCopy.put(res, resourceCopy.get(res) - value);
                resources.put(line, resourceCopy);
            }
            else if(resourceCopy.get(res).equals(value)){
                resourceCopy.clear();
                resources.put(line, resourceCopy);
            }
        }
    }


    /**
     * canRemoveResources check if the resource passed by parameter can be removed
     * @param line select the line to remove the resources
     * @param res select the type of resource to remove
     * @param value is the number of element you want to remove
     * @return true if the resource can be removed false otherwise
     */
    public boolean canRemoveResources(Row line, Resource res, Integer value){
        HashMap<Resource, Integer> resourceCopy = new HashMap<>(resources.get(line));

        if(value < 0)
            throw new IllegalArgumentException("Negative value");

        if(resourceCopy.size()==1 && resourceCopy.containsKey(res)){
            return resourceCopy.get(res) >= value;
        }
        else
            return false;
    }
}

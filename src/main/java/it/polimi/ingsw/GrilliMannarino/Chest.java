package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class Chest {

    private HashMap<Resource, Integer> resources = new HashMap<>();


    /**
     * getResources get all the resources from the chest
     * @return an HashMap that contain all resources
     */
    public HashMap<Resource, Integer> getResources(){
        return new HashMap<>(resources) ;
    }

    /**
     * addResources add a set of resources to the chest
     * @param input is an HashMap that contain the resources to add to chest
     */
    public void addResources(HashMap<Resource, Integer> input){
        for(Resource current : input.keySet()){
            if(resources.get(current) == null)
                resources.put(current, input.get(current));
            else
                resources.put(current, resources.get(current)+input.get(current));
        }
    }

    /**
     * canRemoveResources check if can remove the resources passed from parameter
     * @param input are the resources that try to remove
     * @return true if the remove can be applied so if there are enough resources
     */
    public boolean canRemoveResources(HashMap<Resource, Integer> input){
        boolean check = true;
        HashMap<Resource, Integer> resourceCopy;
        try {
            resourceCopy = new HashMap<>(resources);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return false;
        }

        for(Resource current : input.keySet()){
            if(resourceCopy.containsKey(current)){
                if(resourceCopy.get(current) < input.get(current))
                    check=false;
            }
            else
                check=false;
        }
        return check;
    }


    /**
     * removeResources remove all the resources passed from parameter. It call canRemoveResources to check if can properly
     * remove the resources
     * @param input is an HashMap that contain the resources to remove
     */
    public void removeResources(HashMap<Resource, Integer> input){
        HashMap<Resource, Integer> resourceCopy;
        try {
            resourceCopy = new HashMap<>(resources);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return;
        }

        if(canRemoveResources(input)){
            for(Resource current : input.keySet()){
                resourceCopy.put(current, resourceCopy.get(current) - input.get(current));
            }
            resources=resourceCopy;
        }
    }

    public JSONObject getStatus(){
        JSONObject status = new JSONObject();
        resources.forEach((key,value)->{
            status.put(key.toString(),value.toString());
        });
        return status;
    }

}

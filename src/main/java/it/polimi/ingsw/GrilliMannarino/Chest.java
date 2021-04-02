package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.HashMap;

public class Chest {
    private HashMap<Resource, Integer> resources = new HashMap<>();


    public HashMap<Resource, Integer> getResources(){
        return new HashMap<Resource, Integer>(resources) ;
    }

    public void addResources(HashMap<Resource, Integer> input){
        for(Resource current : input.keySet()){
            if(resources.get(current) == null)
                resources.put(current, input.get(current));
            else
                resources.put(current, resources.get(current)+input.get(current));
        }
    }

    public boolean removeResources(HashMap<Resource, Integer> input){
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
            if(resourceCopy.get(current)!=null){
                if(resourceCopy.get(current) >= input.get(current))
                    resourceCopy.put(current, resourceCopy.get(current) - input.get(current));
                else
                    check=false;
            }
            else
                check=false;
        }

        if(check)
            resources=resourceCopy;
        return check;
    }



}

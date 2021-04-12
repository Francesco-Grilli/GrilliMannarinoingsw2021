package it.polimi.ingsw.GrilliMannarino;


import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;

import java.util.HashMap;

public class ResourceManagerLeaderCard implements ResourceManagerInterface{

    private final Resource typeOfResource;
    private int numberOfResource;
    private final int MAXNUMBER = 2;

    ResourceManagerInterface resourceManager;

    public ResourceManagerLeaderCard(ResourceManagerInterface resourceManager, Resource typeOfResource) {
        this.typeOfResource = typeOfResource;
        this.resourceManager = resourceManager;
    }

    @Override
    public HashMap<Resource, Integer> getResources() {
        HashMap<Resource, Integer> retResource = resourceManager.getResources();
        if(numberOfResource>0){
            if(retResource.containsKey(typeOfResource))
                retResource.put(typeOfResource, retResource.get(typeOfResource) + numberOfResource);
        }
        return retResource;
    }

    @Override
    public HashMap<Resource, Integer> setResourcesFromMarket(Row line, Resource res, Integer value) {
        HashMap<Resource, Integer> retResource;
        if(line.getValue()<=3)
            return resourceManager.setResourcesFromMarket(line, res, value);
        else{
            if(res==typeOfResource && numberOfResource<2){
                if(value>(MAXNUMBER-numberOfResource)){
                    retResource = new HashMap<>();
                    retResource.put(typeOfResource, value-(MAXNUMBER-numberOfResource));
                    numberOfResource=MAXNUMBER;
                }
                else if(value<=(MAXNUMBER-numberOfResource)){
                    //numberOfResource+=
                }
            }
        }

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

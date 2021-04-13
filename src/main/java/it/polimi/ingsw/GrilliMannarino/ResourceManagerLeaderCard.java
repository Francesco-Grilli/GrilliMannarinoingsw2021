package it.polimi.ingsw.GrilliMannarino;


import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;

import java.util.HashMap;

public class ResourceManagerLeaderCard extends LeaderCard implements ResourceManagerInterface{

    private int numberOfResource =0;
    private final int MAXNUMBER = 2;
    private final Row currentRow;
    ResourceManagerInterface resourceManager;

    public ResourceManagerLeaderCard(HashMap<Resource, Integer> resourcePrice, HashMap<Faction, HashMap<Integer, Integer>> cardPrice, Resource definedResource, int points) {
        super(resourcePrice, cardPrice, definedResource, points);
        currentRow = Row.getNextValue(resourceManager.getRow());
    }


    /**
     * @return all resources from Warehouse, Chest and leadercard
     */
    @Override
    public HashMap<Resource, Integer> getResources() {
        HashMap<Resource, Integer> retResource = resourceManager.getResources();
        if(numberOfResource>0){
            if(retResource.containsKey(definedResource))
                retResource.put(definedResource, retResource.get(definedResource) + numberOfResource);
            else
                retResource.put(definedResource, numberOfResource);
        }
        return retResource;
    }

    /**
     *
     * @param line select the position to place the resource on the warehouse or the leadercard
     * @param res is the type of resources
     * @param value is the number of resources
     * @return null if all resources have been removed correctly otherwise an HashMap with the resources that failed to
     * remove
     */
    @Override
    public HashMap<Resource, Integer> setResourcesFromMarket(Row line, Resource res, Integer value) {
        HashMap<Resource, Integer> retResource = null;
        if(line.getValue()<=3)
            return resourceManager.setResourcesFromMarket(line, res, value);
        else{
            if(res == definedResource && numberOfResource<2){
                if(value>(MAXNUMBER-numberOfResource)){
                    retResource = new HashMap<>();
                    retResource.put(definedResource, value-(MAXNUMBER-numberOfResource));
                    numberOfResource=MAXNUMBER;
                }
                else if(value<=(MAXNUMBER-numberOfResource)){
                    numberOfResource+=value;
                }
            }
            else
            {
                retResource = new HashMap<>();
                retResource.put(res, value);
            }
        }

        return retResource;
    }

    /**
     *
     * @see #setResourcesFromMarket(Row, Resource, Integer)
     * @return true if the line specified has the same resources, false if has different resource or the same resource is
     * present in another line
     */
    @Override
    public boolean canSetResourcesFromMarket(Row line, Resource res, Integer value) {
        if(line.getValue()<=3){
            return resourceManager.canSetResourcesFromMarket(line, res, value);
        }
        else {
            return line == currentRow;
        }
    }

    /**
     * @see #setResourcesFromProduction(HashMap) of #ResourceManager
     */
    @Override
    public void setResourcesFromProduction(HashMap<Resource, Integer> res) {
        resourceManager.setResourcesFromProduction(res);
    }

    /**
     * check if has enough resources
     * @param input the resources to remove
     * @return true if the resources are enough, false otherwise
     */
    @Override
    public boolean canRemove(HashMap<Resource, Integer> input) {
        HashMap<Resource, Integer> resources = getResources();
        boolean check = true;
        for (Resource res : input.keySet()) {
            if(!resources.containsKey(res) || !(resources.get(res)>=input.get(res)))
                check=false;
        }
        return check;
    }

    @Override
    public HashMap<Resource, Integer> remove(HashMap<Resource, Integer> input) {
        HashMap<Resource, Integer> retResource = new HashMap<>(input);

        if(retResource.containsKey(definedResource)){
            if(retResource.get(definedResource)>=numberOfResource){
                retResource.put(definedResource, retResource.get(definedResource)-numberOfResource);
                numberOfResource=0;
            }
            else{
                numberOfResource=numberOfResource-retResource.get(definedResource);
                retResource.put(definedResource, 0);
            }
        }
        retResource.entrySet().removeIf(e -> e.getValue().equals(0));
        return resourceManager.remove(retResource);
    }

    @Override
    public Row getRow() {
        return currentRow;
    }

    @Override
    public int getPoints() {
        int points=0;
        HashMap<Resource, Integer> resourcePoint = new HashMap<>(getResources());
        for(Resource res : resourcePoint.keySet()){
            points+=resourcePoint.get(res);
        }
        return (points/5) + this.points;
    }


    @Override
    public void execute(Board board) {
        resourceManager = board.getCollection();
        board.setCollection(this);
    }
}

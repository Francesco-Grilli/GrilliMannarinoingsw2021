package it.polimi.ingsw.GrilliMannarino;


import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;

import java.util.HashMap;

public class ResourceManagerLeaderCard extends LeaderCard implements ResourceManagerBoardInterface {

    private int numberOfResource =0;
    private final int MAXNUMBER = 2;
    private Row currentRow;
    ResourceManagerBoardInterface resourceManager;


    public ResourceManagerLeaderCard(HashMap<Resource, Integer> resourcePrice, HashMap<Faction, HashMap<Integer, Integer>> cardPrice, Resource definedResource, int points) {
        super(resourcePrice, cardPrice, definedResource, points);
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
        if(!canSetResourcesFromMarket(line, res, value)){
            retResource = new HashMap<>();
            retResource.put(res, value);
            return retResource;
        }
        else if(line.getValue()<currentRow.getValue())
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
        if(line.getValue() < currentRow.getValue()){
            return resourceManager.canSetResourcesFromMarket(line, res, value);
        }
        else {
            return res == definedResource && ((value + numberOfResource) <= MAXNUMBER);
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
        if(input == null)
            return null;
        HashMap<Resource, Integer> retResource = new HashMap<>(input);
        if(canRemove(input)) {
            if (retResource.containsKey(definedResource)) {
                if (retResource.get(definedResource) >= numberOfResource) {
                    retResource.put(definedResource, retResource.get(definedResource) - numberOfResource);
                    numberOfResource = 0;
                } else {
                    numberOfResource = numberOfResource - retResource.get(definedResource);
                    retResource.put(definedResource, 0);
                }
            }
            retResource.entrySet().removeIf(e -> e.getValue().equals(0));
            return resourceManager.remove(retResource);
        }
        else
            return input;
    }

    @Override
    public Row getRow() {
        return currentRow;
    }

    @Override
    public int getPoints() {
        int resources=0;
        HashMap<Resource, Integer> resourcePoint = new HashMap<>(getResources());
        for(Resource res : resourcePoint.keySet()){
            resources+=resourcePoint.get(res);
        }
        return resources/5 + this.points;
    }   //STILL NEED SOME MODIFICATION TO GET POINTS FROM CARD ABOVE



    @Override
    public boolean setLineFromChest(Row line, Resource res, Integer value) {
        if(line == currentRow) {
            if (res == definedResource) {
                if (value > currentRow.getMaxValue())
                    numberOfResource = currentRow.getMaxValue();
                else
                    numberOfResource = value;
                return true;
            }
            else
                return false;
        }
        else
            return resourceManager.setLineFromChest(line, res, value);
    }

    @Override
    public void removeLineFromChest(Row line) {
        if(line == currentRow)
            numberOfResource=0;
        else
            resourceManager.removeLineFromChest(line);
    }

    @Override
    public void execute(ResourceManagerBoardInterface managerBoardInterface) {
        resourceManager = managerBoardInterface;
        currentRow = Row.getNextValue(resourceManager.getRow());
    }

    @Override
    public HashMap<Resource, Integer> getResourceLine(Row line){
        if(line == currentRow){
            HashMap<Resource, Integer> retResource = new HashMap<>();
            retResource.put(definedResource, numberOfResource);
            return retResource;
        }
        else
            return resourceManager.getResourceLine(line);
    }

    @Override
    public boolean canSwapLine(Row one, Row two) {
        boolean check = true;
        if(one.getValue() < currentRow.getValue() && two.getValue() < currentRow.getValue()){
            return resourceManager.canSwapLine(one, two);
        }
        else if(one==currentRow && two!=currentRow){
            HashMap<Resource, Integer> lineTwo = new HashMap<>(resourceManager.getResourceLine(two));
            Resource resTwo = getTheResource(lineTwo);
            if(resTwo!=null){
                if(lineTwo.get(resTwo) > one.getMaxValue() || resTwo!=definedResource)
                    check = false;
                if(numberOfResource > two.getMaxValue())
                    check = false;
            }
            else if(numberOfResource > two.getMaxValue())
                check = false;
        }
        else if(one!=currentRow && two==currentRow){
            HashMap<Resource, Integer> lineOne = new HashMap<>(resourceManager.getResourceLine(one));
            Resource resOne = getTheResource(lineOne);
            if(resOne!=null){
                if(lineOne.get(resOne) > two.getMaxValue() || resOne!=definedResource)
                    check = false;
                if(numberOfResource > one.getMaxValue())
                    check = false;
            }
            else if(numberOfResource > one.getMaxValue())
                check = false;
        }
        return check;
    }

    @Override
    public void forceSwapLine(Row one, Row two) {
        if(one != currentRow && two != currentRow)
            resourceManager.forceSwapLine(one, two);
        else if(one == currentRow && two != currentRow){
            HashMap<Resource, Integer> lineTwo = new HashMap<>(resourceManager.getResourceLine(two));
            Resource resTwo = getTheResource(lineTwo);
            if(resTwo!=null && resTwo == definedResource) {
                if(numberOfResource>0)
                    resourceManager.setLineFromChest(two, definedResource, numberOfResource);
                else
                    resourceManager.removeLineFromChest(two);
                if (lineTwo.get(resTwo) >= currentRow.getMaxValue())
                    numberOfResource = currentRow.getMaxValue();
                else
                    numberOfResource = lineTwo.get(resTwo);
            }
            else if(resTwo==null){
                if(resourceManager.setLineFromChest(two, definedResource, numberOfResource))
                    numberOfResource=0;
            }
        }
        else if(one != currentRow && two == currentRow){
            HashMap<Resource, Integer> lineOne = new HashMap<>(resourceManager.getResourceLine(one));
            Resource resOne = getTheResource(lineOne);
            if(resOne!=null && resOne == definedResource){
                if(numberOfResource>0)
                    resourceManager.setLineFromChest(one, definedResource, numberOfResource);
                else
                    resourceManager.removeLineFromChest(one);
                if(lineOne.get(resOne) >= currentRow.getMaxValue())
                    numberOfResource = currentRow.getMaxValue();
                else
                    numberOfResource = lineOne.get(resOne);
            }
            else if(resOne==null){
                if(resourceManager.setLineFromChest(one, definedResource, numberOfResource))
                    numberOfResource=0;
            }
        }
    }

    private Resource getTheResource(HashMap<Resource, Integer> map) {
        Resource ret = null;
        for (Resource r : map.keySet())
            ret = r;
        return ret;
    }


    /*@Override
    public void execute(Board board) {
        resourceManager = board.getResourceManager();
        board.setResourceManager(this);
    }*/
}

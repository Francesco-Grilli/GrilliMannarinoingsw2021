package it.polimi.ingsw.GrilliMannarino;


import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class ResourceManagerLeaderCardTwoSpace extends ResourceManagerLeaderCard implements ResourceManagerBoardInterface {

    private int numberOfResource =0;
    private final int MAXNUMBER = 2;
    private Row currentRow;
    ResourceManagerBoardInterface resourceManager;


    public ResourceManagerLeaderCardTwoSpace(HashMap<Resource, Integer> resourcePrice, HashMap<Faction, HashMap<Integer, Integer>> cardPrice, Resource definedResource, int points,int cardCode) {
        super(resourcePrice, cardPrice, definedResource, points,cardCode);
    }


    /**
     * @return all resources from Warehouse, Chest and leadercard
     */
    @Override
    public HashMap<Resource, Integer> getResources() {
        HashMap<Resource, Integer> retResource = resourceManager.getResources();
        if(numberOfResource>0){
            if(retResource.containsKey(this.getDefinedResource()))
                retResource.put(this.getDefinedResource(), retResource.get(this.getDefinedResource()) + numberOfResource);
            else
                retResource.put(this.getDefinedResource(), numberOfResource);
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
            if(res == this.getDefinedResource() && numberOfResource<2){
                if(value>(MAXNUMBER-numberOfResource)){
                    retResource = new HashMap<>();
                    retResource.put(this.getDefinedResource(), value-(MAXNUMBER-numberOfResource));
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
            return res == this.getDefinedResource() && ((value + numberOfResource) <= MAXNUMBER);
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

    /**
     * remove the input resources from the leader card
     * @param input the resources to remove
     * @return the resources left from removing
     */
    @Override
    public HashMap<Resource, Integer> remove(HashMap<Resource, Integer> input) {
        if(input == null)
            return null;
        HashMap<Resource, Integer> retResource = new HashMap<>(input);
        if(canRemove(input)) {
            if (retResource.containsKey(this.getDefinedResource())) {
                if (retResource.get(this.getDefinedResource()) >= numberOfResource) {
                    retResource.put(this.getDefinedResource(), retResource.get(this.getDefinedResource()) - numberOfResource);
                    numberOfResource = 0;
                } else {
                    numberOfResource = numberOfResource - retResource.get(this.getDefinedResource());
                    retResource.put(this.getDefinedResource(), 0);
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

    /**
     * return the number of points of all resources and of the leadercard
     * @return the integer representing the number of points
     */
    @Override
    public int getResourcePoints() {
        int resources=0;
        HashMap<Resource, Integer> resourcePoint = new HashMap<>(getResources());
        for(Resource res : resourcePoint.keySet()){
            resources+=resourcePoint.get(res);
        }
        return resources/5;
    }

    /**
     * return the number of resources into resource manager and leadercard
     * @return the integer representing the number of resources
     */
    @Override
    public int getNumberOfResource() {
        int resource=0;
        HashMap<Resource, Integer> resourceMap = getResources();
        for(Resource r : resourceMap.keySet()){
            resource += resourceMap.get(r);
        }
        return resource;
    }

    /**
     * overwrites a line of warehouse or of the leadercard
     * @param line to overwrite
     * @param res to put into the new line
     * @param value the number of resources to add
     * @return true if could overwrite the line
     */
    @Override
    public boolean setLineFromChest(Row line, Resource res, Integer value) {
        if(line == currentRow) {
            if (res == this.getDefinedResource()) {
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

    /**
     * clear a line of warehouse or leadercard
     * @param line the line to remove
     */
    @Override
    public void removeLineFromChest(Row line) {
        if(line == currentRow)
            numberOfResource=0;
        else
            resourceManager.removeLineFromChest(line);
    }

    /**
     * binds the leadercard to the boards
     * @param board where it will set itself
     */
    @Override
    public void execute(Board board) {
        resourceManager = board.getResourceManager();
        board.setResourceManager(this);
        currentRow = Row.getNextValue(resourceManager.getRow());
    }

    /**
     * return the resources and the number of each resource into the warehouse or leadercard
     * @param line you wants to get the resources
     * @return an hashmap representing the resources into the line
     */
    @Override
    public HashMap<Resource, Integer> getResourceLine(Row line){
        if(line == currentRow){
            HashMap<Resource, Integer> retResource = new HashMap<>();
            retResource.put(this.getDefinedResource(), numberOfResource);
            return retResource;
        }
        else
            return resourceManager.getResourceLine(line);
    }

    /**
     * get all resources inside the chest
     * @return an hashmap representing all resources and the number inside chest
     */
    @Override
    public HashMap<Resource, Integer> getResourcesFromChest() {
        return resourceManager.getResourcesFromChest();
    }

    /**
     * return all resources inside warehouse and leadercard divided by row
     * @return an hashmap with all resources inside warehouse divided bu row
     */
    @Override
    public HashMap<Row, HashMap<Resource, Integer>> getEachResourceFromWareHouse() {
        HashMap<Row, HashMap<Resource, Integer>> resourceCopy = new HashMap<>(resourceManager.getEachResourceFromWareHouse());
        if(numberOfResource>0){
            HashMap<Resource, Integer> ri = new HashMap<>();
            ri.put(this.getDefinedResource(), numberOfResource);
            resourceCopy.put(currentRow, ri);
        }

        return resourceCopy;
    }


    @Override
    public JSONObject getStatus() {
        JSONObject status = resourceManager.getStatus();
        JSONObject leadercard = new JSONObject();
        leadercard.put("card_code",getCardCode());
        leadercard.put("card_resource_type", getDefinedResource().toString());
        leadercard.put("resources_in_card", numberOfResource);
        leadercard.put("row", currentRow.toString());
        ((JSONArray) status.get("leader_cards")).add(leadercard);
        return status;
    }

    public void setStatus(JSONObject status){}

    /**
     * used to swap line between warehouse
     * @param one the first row to swap
     * @param two the second row to swap
     * @return a boolean true if the swap was correct
     */
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
                if(lineTwo.get(resTwo) > one.getMaxValue() || resTwo!=this.getDefinedResource())
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
                if(lineOne.get(resOne) > two.getMaxValue() || resOne!=this.getDefinedResource())
                    check = false;
                if(numberOfResource > one.getMaxValue())
                    check = false;
            }
            else if(numberOfResource > one.getMaxValue())
                check = false;
        }
        return check;
    }

    /**
     * used to force the swapping even if some resources will be lost
     * @param one the first row to swap
     * @param two the second row to swap
     */
    @Override
    public void forceSwapLine(Row one, Row two) {
        if(one != currentRow && two != currentRow)
            resourceManager.forceSwapLine(one, two);
        else if(one == currentRow && two != currentRow){
            HashMap<Resource, Integer> lineTwo = new HashMap<>(resourceManager.getResourceLine(two));
            Resource resTwo = getTheResource(lineTwo);
            if(resTwo!=null && resTwo == this.getDefinedResource()) {
                if(numberOfResource>0)
                    resourceManager.setLineFromChest(two, this.getDefinedResource(), numberOfResource);
                else
                    resourceManager.removeLineFromChest(two);
                if (lineTwo.get(resTwo) >= currentRow.getMaxValue())
                    numberOfResource = currentRow.getMaxValue();
                else
                    numberOfResource = lineTwo.get(resTwo);
            }
            else if(resTwo==null){
                if(resourceManager.setLineFromChest(two, this.getDefinedResource(), numberOfResource))
                    numberOfResource=0;
            }
        }
        else if(one != currentRow && two == currentRow){
            HashMap<Resource, Integer> lineOne = new HashMap<>(resourceManager.getResourceLine(one));
            Resource resOne = getTheResource(lineOne);
            if(resOne!=null && resOne == this.getDefinedResource()){
                if(numberOfResource>0)
                    resourceManager.setLineFromChest(one, this.getDefinedResource(), numberOfResource);
                else
                    resourceManager.removeLineFromChest(one);
                if(lineOne.get(resOne) >= currentRow.getMaxValue())
                    numberOfResource = currentRow.getMaxValue();
                else
                    numberOfResource = lineOne.get(resOne);
            }
            else if(resOne==null){
                if(resourceManager.setLineFromChest(one, this.getDefinedResource(), numberOfResource))
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

}

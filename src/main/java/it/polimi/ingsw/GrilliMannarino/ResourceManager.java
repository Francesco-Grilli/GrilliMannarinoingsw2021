package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;

import java.util.HashMap;

public class ResourceManager implements ResourceManagerBoardInterface {

    private final Chest chest;
    private final WareHouse wareHouse;

    public ResourceManager() {
        chest = new Chest();
        wareHouse = new WareHouse();
    }

    /**
     * @return all resources from chest and warehouse
     */
    public HashMap<Resource, Integer> getResources() {
        HashMap<Resource, Integer> ret = new HashMap<>(getResourcesFromChest());
        HashMap<Resource, Integer> looping = new HashMap<>(getResourcesFromWareHouse());
        for (Resource res : looping.keySet()) {
            if (ret.containsKey(res))
                ret.put(res, (ret.get(res) + looping.get(res)));
            else
                ret.put(res, looping.get(res));
        }

        ret.entrySet().removeIf(e -> e.getValue().equals(0));

        return ret;

    }

    /**
     * @return the resources from chest
     */
    private HashMap<Resource, Integer> getResourcesFromChest() {
        return new HashMap<>(chest.getResources());
    }

    /**
     * @return the resources from warehouse
     */
    private HashMap<Resource, Integer> getResourcesFromWareHouse() {
        HashMap<Resource, Integer> ret = new HashMap<>();
        HashMap<Row, HashMap<Resource, Integer>> resourceCopy = new HashMap<>(wareHouse.getResources());
        for (Row line : resourceCopy.keySet()) {
            for (Resource res : resourceCopy.get(line).keySet()) {
                ret.put(res, resourceCopy.get(line).get(res));
            }
        }

        return ret;
    }

    /**
     * @param line  take the line where to put the resource
     * @param res   take the resource to put
     * @param value take the number of resources
     * @return the resources that couldn't set inside warehouse or null
     */
    public HashMap<Resource, Integer> setResourcesFromMarket(Row line, Resource res, Integer value) {
        if (wareHouse.canAddResources(line, res, value))
            return wareHouse.addResources(line, res, value);
        else {
            HashMap<Resource, Integer> ret = new HashMap<>();
            ret.put(res, value);
            return ret;
        }

    }

    /**
     * check if can add the resource to warehouse
     *
     * @return true if can add the resource, false otherwise
     * @see #setResourcesFromMarket(Row, Resource, Integer)
     */
    public boolean canSetResourcesFromMarket(Row line, Resource res, Integer value) {
        return wareHouse.canAddResources(line, res, value);
    }

    /**
     * put the resources inside chest
     *
     * @param res is an hashmap of resources
     */
    public void setResourcesFromProduction(HashMap<Resource, Integer> res) {
        chest.addResources(res);
    }

    /**
     * check if can remove the resources from both chest and warehouse. The priority is first WareHouse and then Chest
     *
     * @param input take the resources to remove
     * @return true if can remove, false otherwise
     */
    public boolean canRemove(HashMap<Resource, Integer> input) {
        HashMap<Resource, Integer> resources = getResources();
        boolean check = true;
        for (Resource res : input.keySet()) {
            if (!resources.containsKey(res) || !(resources.get(res) >= input.get(res)))
                check = false;
        }
        return check;
    }

    /**
     * remove the resources from both chest and warehouse. The priority is first WareHouse and then Chest
     *
     * @param input is the resources to remove
     * @return an hashmap of resources if it failed to remove
     */
    public HashMap<Resource, Integer> remove(HashMap<Resource, Integer> input) {
        if (input == null)
            return null;
        HashMap<Resource, Integer> pass = new HashMap<>(input);
        return removeFromChest(removeFromWareHouse(pass));
    }

    private HashMap<Resource, Integer> removeFromWareHouse(HashMap<Resource, Integer> input) {
        HashMap<Row, HashMap<Resource, Integer>> resources = wareHouse.getResources();

        if (canRemove(input)) {
            for (Row line : resources.keySet()) {
                for (Resource res : resources.get(line).keySet()) {
                    if (input.containsKey(res)) {
                        if (input.get(res) > resources.get(line).get(res)) {
                            input.put(res, (input.get(res) - resources.get(line).get(res)));
                            wareHouse.removeResources(line, res, resources.get(line).get(res));
                        } else {
                            wareHouse.removeResources(line, res, input.get(res));
                            input.put(res, 0);
                        }
                    }
                }
            }
        }
        input.entrySet().removeIf(e -> e.getValue().equals(0));
        return input;
    }

    private HashMap<Resource, Integer> removeFromChest(HashMap<Resource, Integer> input) {
        HashMap<Resource, Integer> resources = chest.getResources();

        for (Resource res : input.keySet()) {
            if (resources.containsKey(res)) {
                if (input.get(res) >= resources.get(res)) {
                    input.put(res, (input.get(res) - resources.get(res)));
                    HashMap<Resource, Integer> temp = new HashMap<>();
                    temp.put(res, resources.get(res));
                    chest.removeResources(temp);
                } else {
                    HashMap<Resource, Integer> temp = new HashMap<>();
                    temp.put(res, input.get(res));
                    chest.removeResources(temp);
                    input.put(res, 0);
                }
            }
        }
        input.entrySet().removeIf(e -> e.getValue().equals(0));
        return input;
    }

    /**
     * calculate the points from the resources in chest and warehouse. Every 5 resources give 1 point
     *
     * @return the points of the resources
     */
    public int getResourcePoints() {
        int points = 0;
        HashMap<Resource, Integer> resourcePoint = new HashMap<>(getResources());
        for (Resource res : resourcePoint.keySet()) {
            points += resourcePoint.get(res);
        }
        return points / 5;
    }

    public Row getRow() {
        return Row.THIRD;
    }

    /**
     * check if can swap the line
     *
     * @return true if the line can be swapped without loosing resources, false otherwise
     */
    public boolean canSwapLine(Row one, Row two) {
        HashMap<Resource, Integer> lineOne = wareHouse.getResources().get(one);
        HashMap<Resource, Integer> lineTwo = wareHouse.getResources().get(two);
        Resource resourceOne, resourceTwo;
        resourceOne = getTheResource(lineOne);
        resourceTwo = getTheResource(lineTwo);
        if (resourceOne == null && resourceTwo == null) {
            return true;
        } else if (resourceOne == null) {
            if (lineTwo.get(resourceTwo) > one.getValue())
                return false;
            else
                return true;
        } else if (resourceTwo == null) {
            if (lineOne.get(resourceOne) > two.getValue())
                return false;
            else
                return true;
        } else if (lineOne.get(resourceOne) > two.getValue() || lineTwo.get(resourceTwo) > one.getValue())
            return false;
        else
            return true;
    }

    /**
     * force the swap of the line even if resources are removed
     */
    public void forceSwapLine(Row one, Row two) {
        HashMap<Resource, Integer> lineOne = new HashMap<>(wareHouse.getResources().get(one));
        HashMap<Resource, Integer> lineTwo = new HashMap<>(wareHouse.getResources().get(two));
        Resource resourceOne, resourceTwo;
        resourceOne = getTheResource(lineOne);
        resourceTwo = getTheResource(lineTwo);

        if (resourceOne == null && resourceTwo != null) {
            wareHouse.setLine(one, resourceTwo, lineTwo.get(resourceTwo));
            wareHouse.removeLine(two);
        } else if (resourceOne != null && resourceTwo == null) {
            wareHouse.setLine(two, resourceOne, lineOne.get(resourceOne));
            wareHouse.removeLine(one);
        } else if (resourceOne != null && resourceTwo != null) {
            wareHouse.setLine(one, resourceTwo, lineTwo.get(resourceTwo));
            wareHouse.setLine(two, resourceOne, lineOne.get(resourceOne));
        }
    }

    private Resource getTheResource(HashMap<Resource, Integer> map) {
        Resource ret = null;
        for (Resource r : map.keySet())
            ret = r;
        return ret;
    }

    public boolean setLineFromChest(Row line, Resource res, Integer value) {
        return wareHouse.setLine(line, res, value);
    }

    public void removeLineFromChest(Row line) {
        wareHouse.removeLine(line);
    }

    public HashMap<Resource, Integer> getResourceLine(Row line) {
        return wareHouse.getResources().get(line);
    }

    @Override
    public void execute(ResourceManagerBoardInterface managerBoardInterface) {

    }
}
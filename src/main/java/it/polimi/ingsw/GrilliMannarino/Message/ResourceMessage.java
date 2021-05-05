package it.polimi.ingsw.GrilliMannarino.Message;

import java.util.HashMap;

public class ResourceMessage extends Message implements MessageInterface{

    private HashMap<String, HashMap<String, Integer>> wareHouseResources;
    private HashMap<String, Integer> chestResources;

    public ResourceMessage(Integer gameId, Integer playerId) {
        super(gameId, playerId);
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeResource(this);
    }


    public HashMap<String, HashMap<String, Integer>> getWareHouseResources() {
        return wareHouseResources;
    }

    public void setWareHouseResources(HashMap<String, HashMap<String, Integer>> wareHouseResources) {
        this.wareHouseResources = wareHouseResources;
    }

    public HashMap<String, Integer> getChestResources() {
        return chestResources;
    }

    public void setChestResources(HashMap<String, Integer> chestResources) {
        this.chestResources = chestResources;
    }
}

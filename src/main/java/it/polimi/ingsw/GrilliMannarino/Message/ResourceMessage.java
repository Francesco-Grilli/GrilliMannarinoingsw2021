package it.polimi.ingsw.GrilliMannarino.Message;

import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;

import java.util.HashMap;

public class ResourceMessage extends Message implements MessageInterface{

    private HashMap<Row, HashMap<Resource, Integer>> wareHouseResources;
    private HashMap<Resource, Integer> chestResources;

    public ResourceMessage(Integer gameId, Integer playerId) {
        super(gameId, playerId);
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeResource(this);
    }


    public HashMap<Row, HashMap<Resource, Integer>> getWareHouseResources() {
        return wareHouseResources;
    }

    public void setWareHouseResources(HashMap<Row, HashMap<Resource, Integer>> wareHouseResources) {
        this.wareHouseResources = wareHouseResources;
    }

    public HashMap<Resource, Integer> getChestResources() {
        return chestResources;
    }

    public void setChestResources(HashMap<Resource, Integer> chestResources) {
        this.chestResources = chestResources;
    }
}

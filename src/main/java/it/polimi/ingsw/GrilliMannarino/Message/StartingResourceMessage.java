package it.polimi.ingsw.GrilliMannarino.Message;

import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;

import java.io.Serializable;
import java.util.ArrayList;

public class StartingResourceMessage extends Message implements MessageInterface, Serializable {

    private boolean resource = false;
    private boolean placeResource = false;
    private boolean faith = false;
    private Integer faithToAdd;
    private ArrayList<ArrayList<Marble>> marblesToSelect;
    private ArrayList<Resource> resourcesLeft;
    private Row rowToPlace;
    private Resource resourceToPlace;
    private String messageToShow;
    private boolean addResourceCorrect = false;

    public StartingResourceMessage(Integer gameId, Integer playerId) {
        super(gameId, playerId);
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeStartingResource(this);
    }

    public boolean isResource() {
        return resource;
    }

    public void setResource(boolean resource) {
        this.resource = resource;
    }

    public boolean isFaith() {
        return faith;
    }

    public void setFaith(boolean faith) {
        this.faith = faith;
    }

    public ArrayList<ArrayList<Marble>> getMarblesToSelect() {
        return marblesToSelect;
    }

    public void setMarblesToSelect(ArrayList<ArrayList<Marble>> marblesToSelect) {
        this.marblesToSelect = marblesToSelect;
    }

    public ArrayList<Resource> getResourcesLeft() {
        return resourcesLeft;
    }

    public void setResourcesLeft(ArrayList<Resource> resourcesLeft) {
        this.resourcesLeft = resourcesLeft;
    }

    public Row getRowToPlace() {
        return rowToPlace;
    }

    public void setRowToPlace(Row rowToPlace) {
        this.rowToPlace = rowToPlace;
    }

    public Resource getResourceToPlace() {
        return resourceToPlace;
    }

    public void setResourceToPlace(Resource resourceToPlace) {
        this.resourceToPlace = resourceToPlace;
    }

    public String getMessageToShow() {
        return messageToShow;
    }

    public void setMessageToShow(String messageToShow) {
        this.messageToShow = messageToShow;
    }

    public Integer getFaithToAdd() {
        return faithToAdd;
    }

    public void setFaithToAdd(Integer faithToAdd) {
        this.faithToAdd = faithToAdd;
    }

    public boolean isAddResourceCorrect() {
        return addResourceCorrect;
    }

    public void setAddResourceCorrect(boolean addResourceCorrect) {
        this.addResourceCorrect = addResourceCorrect;
    }

    public boolean isPlaceResource() {
        return placeResource;
    }

    public void setPlaceResource(boolean placeResource) {
        this.placeResource = placeResource;
    }
}

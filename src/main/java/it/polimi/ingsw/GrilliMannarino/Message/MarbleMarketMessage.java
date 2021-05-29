package it.polimi.ingsw.GrilliMannarino.Message;

import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;

import java.io.Serializable;
import java.util.ArrayList;

public class MarbleMarketMessage extends Message implements MessageInterface, Serializable {

    /**
     * displayMarbleMarket is set true by client to ask the server to see the configuration of marbleMarket
     * server set displayMarbleMarket true to let client know that marbleList contains the configuration asked
     */
    private ArrayList<ArrayList<Marble>> marbleList;
    private Marble marbleOut;
    private boolean displayMarbleMarket = false; //client ask to see the MarbleMarket

    /**
     * client set selectColumnRow to get a line or a row from the marbleMarket
     * Server set displayMarblesReturned to let client know that in returnedMarble there are the marbles
     */
    private String columnRow;
    private Integer columnRowValue;
    private boolean selectColumnRow = false;
    private boolean displayMarblesReturned = false;
    private ArrayList<ArrayList<Marble>> returnedMarble;

    /**
     * addedResource is used by the client to tell the server that want put a resource into warehouse
     * addedResource is set true by the server to let client check if the resource war correctly added to warehouse or
     * not by checking addResourceCorrect
     * returnedResource is passed between client and server. Server delete the resource placed into warehouse from
     * returnedResource
     */
    private boolean addedResource = false;
    private Resource resourceType;
    private Row insertRow;
    private boolean addResourceCorrect = false;
    private ArrayList<Resource> returnedResource;
    private boolean checkReturnedResource = false;

    /**
     * destroyRemaining is set to true by the client if want to destroy the marble remained
     */
    private boolean destroyRemaining = false;

    private boolean swapRow = false;
    private Row rowOne;
    private Row rowTwo;
    private boolean canMove = false;
    private boolean forceSwap = false;



    public MarbleMarketMessage(Integer gameId, Integer playerId) {
        super(gameId, playerId);
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeMarbleMarket(this);
    }

    public boolean isDisplayMarbleMarket() {
        return displayMarbleMarket;
    }

    public boolean isSelectColumnRow() {
        return selectColumnRow;
    }

    public boolean isDisplayMarblesReturned() {
        return displayMarblesReturned;
    }

    public boolean isAddedResource() {
        return addedResource;
    }

    public ArrayList<ArrayList<Marble>> getMarbleList() {
        return marbleList;
    }

    public void setMarbleList(ArrayList<ArrayList<Marble>> marbleList) {
        this.marbleList = marbleList;
    }

    public void setDisplayMarbleMarket(boolean displayMarbleMarket) {
        this.displayMarbleMarket = displayMarbleMarket;
    }

    public void setSelectColumnRow(boolean selectColumnRow) {
        this.selectColumnRow = selectColumnRow;
    }

    public void setDisplayMarblesReturned(boolean displayMarblesReturned) {
        this.displayMarblesReturned = displayMarblesReturned;
    }

    public void setAddedResource(boolean addedResource) {
        this.addedResource = addedResource;
    }

    public Marble getMarbleOut() {
        return marbleOut;
    }

    public void setMarbleOut(Marble marbleOut) {
        this.marbleOut = marbleOut;
    }

    public String getColumnRow() {
        return columnRow;
    }

    public void setColumnRow(String columnRow) {
        this.columnRow = columnRow;
    }

    public ArrayList<ArrayList<Marble>> getReturnedMarble() {
        return returnedMarble;
    }

    public void setReturnedMarble(ArrayList<ArrayList<Marble>> returnedMarble) {
        this.returnedMarble = returnedMarble;
    }

    public ArrayList<Resource> getReturnedResource() {
        return returnedResource;
    }

    public void setReturnedResource(ArrayList<Resource> returnedResource) {
        this.returnedResource = returnedResource;
    }

    public Integer getColumnRowValue() {
        return columnRowValue;
    }

    public void setColumnRowValue(Integer columnRowValue) {
        this.columnRowValue = columnRowValue;
    }

    public Resource getResourceType() {
        return resourceType;
    }

    public void setResourceType(Resource resourceType) {
        this.resourceType = resourceType;
    }

    public Row getInsertRow() {
        return insertRow;
    }

    public void setInsertRow(Row insertRow) {
        this.insertRow = insertRow;
    }

    public boolean isAddResourceCorrect() {
        return addResourceCorrect;
    }

    public void setAddResourceCorrect(boolean addResourceCorrect) {
        this.addResourceCorrect = addResourceCorrect;
    }

    public boolean isDestroyRemaining() {
        return destroyRemaining;
    }

    public void setDestroyRemaining(boolean destroyRemaining) {
        this.destroyRemaining = destroyRemaining;
    }

    public boolean isCheckReturnedResource() {
        return checkReturnedResource;
    }

    public void setCheckReturnedResource(boolean checkReturnedResource) {
        this.checkReturnedResource = checkReturnedResource;
    }

    public boolean isSwapRow() {
        return swapRow;
    }

    public void setSwapRow(boolean swapRow) {
        this.swapRow = swapRow;
    }

    public Row getRowOne() {
        return rowOne;
    }

    public void setRowOne(Row rowOne) {
        this.rowOne = rowOne;
    }

    public Row getRowTwo() {
        return rowTwo;
    }

    public void setRowTwo(Row rowTwo) {
        this.rowTwo = rowTwo;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean isForceSwap() {
        return forceSwap;
    }

    public void setForceSwap(boolean forceSwap) {
        this.forceSwap = forceSwap;
    }
}

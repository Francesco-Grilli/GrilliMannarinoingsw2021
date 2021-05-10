package it.polimi.ingsw.GrilliMannarino.Message;

import it.polimi.ingsw.GrilliMannarino.GameData.Marble;

import java.io.Serializable;
import java.util.ArrayList;

public class MarbleMarketMessage extends Message implements MessageInterface, Serializable {

    /**
     * displayMarbleMarket is set true by client to ask the server to see the configuration of marbleMarket
     * server set displayMarbleMarket true to let client know that marbleList contains the configuration asked
     */
    private Marble[][] marbleList;
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
    private ArrayList<ArrayList<String>> returnedMarble;

    /**
     * addedResource is used by the client to tell the server that want put a resource into warehouse
     * addedResource is set true by the server to let client check if the resource war correctly added to warehouse or
     * not by checking addResourceCorrect
     */
    private boolean addedResource = false;
    private String marbleType;
    private String insertRow;
    private boolean addResourceCorrect = false;

    /**
     * destroyRemaining is set to true by the client if want to destroy the marble remained
     */
    private boolean destroyRemaining = false;
    private ArrayList<String> returnedResource;


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

    public Marble[][] getMarbleList() {
        return marbleList;
    }

    public void setMarbleList(Marble[][] marbleList) {
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

    public ArrayList<ArrayList<String>> getReturnedMarble() {
        return returnedMarble;
    }

    public void setReturnedMarble(ArrayList<ArrayList<String>> returnedMarble) {
        this.returnedMarble = returnedMarble;
    }

    public ArrayList<String> getReturnedResource() {
        return returnedResource;
    }

    public void setReturnedResource(ArrayList<String> returnedResource) {
        this.returnedResource = returnedResource;
    }

    public Integer getColumnRowValue() {
        return columnRowValue;
    }

    public void setColumnRowValue(Integer columnRowValue) {
        this.columnRowValue = columnRowValue;
    }

    public String getMarbleType() {
        return marbleType;
    }

    public void setMarbleType(String marbleType) {
        this.marbleType = marbleType;
    }

    public String getInsertRow() {
        return insertRow;
    }

    public void setInsertRow(String insertRow) {
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
}

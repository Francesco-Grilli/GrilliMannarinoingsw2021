package it.polimi.ingsw.GrilliMannarino.Message;

import java.io.Serializable;
import java.util.ArrayList;

public class MarbleMarketMessage implements MessageInterface, Serializable {

    private String[][] marbleList;
    private String marbleOut;
    private boolean displayMarbleMarket = false; //client ask to see the MarbleMarket

    private String columnRow;
    private Integer columnRowValue;
    private boolean selectColumnRow = false;    //client ask to select a column or a row to get resource
    private boolean displayMarblesReturned = false; //server set the returnedMarble, client must check this
    private ArrayList<ArrayList<String>> returnedMarble;

    private boolean addedResource = false;  //client ask to add a resource into warehouse and set MarbleType and Row
    private String[] returnedResource;
    private String marbleType;
    private String insertRow;
    private boolean addResourceCorrect = false; //server inform client if the added resource was correctly inserted

    private Integer palyerID;

    public MarbleMarketMessage(Integer palyerID) {
        this.palyerID = palyerID;
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeBuyMarket(this);
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

    public Integer getPalyerID() {
        return palyerID;
    }

    public String[][] getMarbleList() {
        return marbleList;
    }

    public void setMarbleList(String[][] marbleList) {
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

    public String getMarbleOut() {
        return marbleOut;
    }

    public void setMarbleOut(String marbleOut) {
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

    public String[] getReturnedResource() {
        return returnedResource;
    }

    public void setReturnedResource(String[] returnedResource) {
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
}

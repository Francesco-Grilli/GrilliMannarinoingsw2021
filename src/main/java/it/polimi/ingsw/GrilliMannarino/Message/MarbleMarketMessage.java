package it.polimi.ingsw.GrilliMannarino.Message;

public class MarbleMarketMessage implements MessageInterface{

    private String[][] marbleList;
    private String marbleOut;
    private boolean displayMarbleMarket = false;
    private String columnRow;
    private boolean selectColumnRow = false;
    private boolean displayMarbles = false;
    private String[] returnedMarble;
    private boolean addedResource = false;
    private String[] returnedResource;

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeBuyMarket(this);
    }
}

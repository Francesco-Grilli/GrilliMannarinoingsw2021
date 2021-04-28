package it.polimi.ingsw.GrilliMannarino.Message;

import java.util.ArrayList;

public class ProductionMessage implements MessageInterface{

    private ArrayList<String> productionCard;
    private boolean displayCard = false;
    private ArrayList<String> selectedCard;
    private boolean selectCard = false;
    private ArrayList<String> returnedResource;
    private Integer returnedFaith;
    private boolean returningResources = false;

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeProduction(this);
    }
}

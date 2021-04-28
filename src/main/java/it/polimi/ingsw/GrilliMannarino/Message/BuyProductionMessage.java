package it.polimi.ingsw.GrilliMannarino.Message;

import java.util.ArrayList;
import java.util.HashMap;

public class BuyProductionMessage implements MessageInterface{

    private boolean displayCard = false;
    private HashMap<String, Boolean> buyableCard;
    private boolean selectCard = false;
    private ArrayList<String> selectedCard;
    private boolean placeCard = false;
    private ArrayList<String> placeableCard;

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeBuyProduction(this);
    }
}

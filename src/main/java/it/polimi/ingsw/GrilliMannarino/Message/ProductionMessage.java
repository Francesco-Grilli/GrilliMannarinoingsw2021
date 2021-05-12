package it.polimi.ingsw.GrilliMannarino.Message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductionMessage extends Message implements MessageInterface, Serializable {

    /**
     * displayCard is set to true by the client if it wants to see the card in the productionLine to produce
     * is set true by the server to let the client understand that the field productionCard contains all the
     * creation card that can be selected
     * productionCard is <CardCode, Position>
     */
    private HashMap<Integer, Integer> productionCard;
    private boolean displayCard = false;

    /**
     * selectCard is set true by the client to let the server understand that the field selectedCard contains the
     * card that has been selected by the client to produce
     * selectCard is set to true by the server and check if the configuration in selectedCard can produce, if it is
     * correct put productionCorrect to true
     */
    private ArrayList<Integer> selectedCard;
    private boolean selectCard = false;
    private boolean productionCorrect = false;

    public ProductionMessage(Integer gameId, Integer playerId) {
        super(gameId, playerId);
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeProduction(this);
    }

    public boolean isDisplayCard() {
        return displayCard;
    }

    public void setDisplayCard(boolean displayCard) {
        this.displayCard = displayCard;
    }

    public HashMap<Integer, Integer> getProductionCard() {
        return productionCard;
    }

    public void setProductionCard(HashMap<Integer, Integer> productionCard) {
        this.productionCard = productionCard;
    }

    public ArrayList<Integer> getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(ArrayList<Integer> selectedCard) {
        this.selectedCard = selectedCard;
    }

    public boolean isSelectCard() {
        return selectCard;
    }

    public void setSelectCard(boolean selectCard) {
        this.selectCard = selectCard;
    }

    public boolean isProductionCorrect() {
        return productionCorrect;
    }

    public void setProductionCorrect(boolean productionCorrect) {
        this.productionCorrect = productionCorrect;
    }
}

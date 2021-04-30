package it.polimi.ingsw.GrilliMannarino.Message;

import java.util.ArrayList;

public class ProductionMessage implements MessageInterface{

    private ArrayList<Integer> productionCard;
    private boolean displayCard = false;

    private ArrayList<Integer> selectedCard;
    private boolean selectCard = false;
    private boolean productionCorrect = false;

    private Integer playerId;

    public ProductionMessage(Integer playerId) {
        this.playerId = playerId;
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeProduction(this);
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public boolean isDisplayCard() {
        return displayCard;
    }

    public void setDisplayCard(boolean displayCard) {
        this.displayCard = displayCard;
    }

    public ArrayList<Integer> getProductionCard() {
        return productionCard;
    }

    public void setProductionCard(ArrayList<Integer> productionCard) {
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

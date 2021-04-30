package it.polimi.ingsw.GrilliMannarino.Message;

import java.util.HashMap;

public class BuyProductionMessage implements MessageInterface{

    private boolean displayCard = false;    //client set true if want to see card
    private HashMap<Integer, Boolean> buyableCard;

    private boolean selectCard = false;     //client set true if selected a card to buy
    private Integer selectedCard;
    private boolean placeCardCorrect = false;  //server set true if card is added correcty to productionLine
    private Integer positionCard;

    private Integer playerId;

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeBuyProduction(this);
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public BuyProductionMessage(Integer playerId) {
        this.playerId = playerId;
    }

    public boolean isDisplayCard() {
        return displayCard;
    }

    public void setDisplayCard(boolean displayCard) {
        this.displayCard = displayCard;
    }

    public HashMap<Integer, Boolean> getBuyableCard() {
        return buyableCard;
    }

    public void setBuyableCard(HashMap<Integer, Boolean> buyableCard) {
        this.buyableCard = buyableCard;
    }

    public boolean isSelectCard() {
        return selectCard;
    }

    public void setSelectCard(boolean selectCard) {
        this.selectCard = selectCard;
    }

    public Integer getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Integer selectedCard) {
        this.selectedCard = selectedCard;
    }

    public boolean isPlaceCardCorrect() {
        return placeCardCorrect;
    }

    public void setPlaceCardCorrect(boolean placeCardCorrect) {
        this.placeCardCorrect = placeCardCorrect;
    }


    public Integer getPositionCard() {
        return positionCard;
    }

    public void setPositionCard(Integer positionCard) {
        this.positionCard = positionCard;
    }
}

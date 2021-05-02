package it.polimi.ingsw.GrilliMannarino.Message;

import java.util.HashMap;

public class BuyProductionMessage implements MessageInterface{

    /**
     *displayCard is set to true by the client if he wants to see the creation card that can buy,
     * the server set it to true to let the client know that the field buyableCard there are the creationCard
     */
    private boolean displayCard = false;
    private HashMap<Integer, Boolean> buyableCard;

    /**
     * selectCard is set to true by the client if want to buy a creationCard. In selectedCard there is the code of a
     * creationCard and in the positionCard there is the position he wants to put the card into Productionline
     * the server set selectCard to true to let the client check if placeCardCorrect is true, the buy was correct, or
     * false if it failed
     */
    private boolean selectCard = false;
    private Integer selectedCard;
    private boolean placeCardCorrect = false;
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

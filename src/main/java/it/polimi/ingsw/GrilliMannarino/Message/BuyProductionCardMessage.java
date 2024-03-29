package it.polimi.ingsw.GrilliMannarino.Message;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BuyProductionCardMessage extends Message implements MessageInterface, Serializable {

    /**
     *displayCard is set to true by the client if he wants to see the creation card that can buy,
     * the server set it to true to let the client know that the field buyableCard there are the creationCard
     */
    private boolean displayCard = false;
    private HashMap<Faction, HashMap<Integer, Map.Entry<Integer, Boolean>>> buyableCard;

    /**
     * selectCard is set to true by the client if want to buy a creationCard. In selectedCard there is the code of a
     * creationCard and in the positionCard there is the position he wants to put the card into Productionline
     * the server set selectCard to true to let the client check if placeCardCorrect is true, the buy was correct, or
     * false if it failed
     */
    private boolean selectCard = false;
    private Integer selectedCard;
    private Integer positionCard;
    private boolean placeCardCorrect = false;
    private HashMap<Integer, Integer> cardInProductionline;



    public BuyProductionCardMessage(Integer gameId, Integer playerId) {
        super(gameId, playerId);
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeBuyProduction(this);
    }

    public boolean isDisplayCard() {
        return displayCard;
    }

    public void setDisplayCard(boolean displayCard) {
        this.displayCard = displayCard;
    }

    public HashMap<Faction, HashMap<Integer, Map.Entry<Integer, Boolean>>> getBuyableCard() {
        return buyableCard;
    }

    public void setBuyableCard(HashMap<Faction, HashMap<Integer, Map.Entry<Integer, Boolean>>> buyableCard) {
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

    public HashMap<Integer, Integer> getCardInProductionline() {
        return cardInProductionline;
    }

    public void setCardInProductionline(HashMap<Integer, Integer> cardInProductionline) {
        this.cardInProductionline = cardInProductionline;
    }
}

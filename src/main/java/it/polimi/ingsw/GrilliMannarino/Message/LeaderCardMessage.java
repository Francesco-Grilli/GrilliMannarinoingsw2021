package it.polimi.ingsw.GrilliMannarino.Message;

import java.io.Serializable;
import java.util.ArrayList;

public class LeaderCardMessage extends Message implements MessageInterface, Serializable {

    private boolean showLeaderCard = false;
    private ArrayList<Integer> cards;
    private boolean selectLeaderCard = false;

    /**
     * activateCard and sellingCard is used by the client and the server to activate that action
     * the server also put true or false the activationSellingCorrect boolean to let the client know if
     * the action was a success
     * In the cardCode both server and client put the code of the card witch they operated
     */
    private boolean activateCard = false;
    private boolean sellingCard = false;
    private boolean activationSellingCorrect = false;
    private Integer cardCode;

    public LeaderCardMessage(Integer gameId, Integer playerId) {
        super(gameId, playerId);
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeLeaderCard(this);
    }

    public boolean isActivateCard() {
        return activateCard;
    }

    public void setActivateCard(boolean activateCard) {
        this.activateCard = activateCard;
    }

    public boolean isSellingCard() {
        return sellingCard;
    }

    public void setSellingCard(boolean sellingCard) {
        this.sellingCard = sellingCard;
    }

    public Integer getCardCode() {
        return cardCode;
    }

    public void setCardCode(Integer cardCode) {
        this.cardCode = cardCode;
    }

    public boolean isActivationSellingCorrect() {
        return activationSellingCorrect;
    }

    public void setActivationSellingCorrect(boolean activationSellingCorrect) {
        this.activationSellingCorrect = activationSellingCorrect;
    }

    public boolean isShowLeaderCard() {
        return showLeaderCard;
    }

    public void setShowLeaderCard(boolean showLeaderCard) {
        this.showLeaderCard = showLeaderCard;
    }

    public ArrayList<Integer> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Integer> cards) {
        this.cards = cards;
    }

    public boolean isSelectLeaderCard() {
        return selectLeaderCard;
    }

    public void setSelectLeaderCard(boolean selectLeaderCard) {
        this.selectLeaderCard = selectLeaderCard;
    }
}

package it.polimi.ingsw.GrilliMannarino.Message;

import java.io.Serializable;

public class LeaderCardMessage extends Message implements MessageInterface, Serializable {

    private boolean canActivate = false;
    private boolean sellingCard = false;
    private boolean activationSellingCorrect = false;
    private Integer cardCode;

    public LeaderCardMessage(Integer playerId, Integer gameId) {
        super(gameId, playerId);
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeLeaderCard(this);
    }

    public boolean isCanActivate() {
        return canActivate;
    }

    public void setCanActivate(boolean canActivate) {
        this.canActivate = canActivate;
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
}

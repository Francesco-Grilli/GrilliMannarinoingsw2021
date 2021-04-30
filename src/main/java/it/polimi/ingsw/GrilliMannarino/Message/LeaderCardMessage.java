package it.polimi.ingsw.GrilliMannarino.Message;

public class LeaderCardMessage implements MessageInterface{

    private boolean canActivate = false;
    private boolean sellingCard = false;
    private boolean activationSellingCorrect = false;
    private Integer cardCode;

    private Integer playerId;

    public LeaderCardMessage(Integer playerId) {
        this.playerId = playerId;
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

    public Integer getPlayerId() {
        return playerId;
    }
}

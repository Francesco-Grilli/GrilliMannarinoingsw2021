package it.polimi.ingsw.GrilliMannarino.Message;

public class TurnMessage implements MessageInterface{

    private String playerNickName;
    private Integer playerId;
    private boolean isMyTurn = false;

    public TurnMessage(Integer playerId) {
        this.playerId = playerId;
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeTurnPlayer(this);
    }

    public boolean isMyTurn() {
        return isMyTurn;
    }

    public void setMyTurn(boolean myTurn) {
        isMyTurn = myTurn;
    }
}

package it.polimi.ingsw.GrilliMannarino.Message;

import java.io.Serializable;

public class TurnMessage extends Message implements MessageInterface, Serializable {

    private String playerNickName;
    private boolean isMyTurn = false;

    public TurnMessage(Integer gameId, Integer playerId) {
        super(gameId, playerId);
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

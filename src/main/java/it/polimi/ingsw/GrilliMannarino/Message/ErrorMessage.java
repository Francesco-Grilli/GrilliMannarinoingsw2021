package it.polimi.ingsw.GrilliMannarino.Message;

import java.io.Serializable;

public class ErrorMessage extends Message implements MessageInterface, Serializable {

    private String error;
    private boolean yourTurn = false;

    public ErrorMessage(Integer gameId, Integer playerId) {
        super(gameId, playerId);
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeErrorMessage(this);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isYourTurn() {
        return yourTurn;
    }

    public void setYourTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }
}

package it.polimi.ingsw.GrilliMannarino.Message;

import it.polimi.ingsw.GrilliMannarino.GameData.LorenzoToken;

import java.io.Serializable;

public class LorenzoTokenMessage extends Message implements MessageInterface, Serializable {

    private LorenzoToken token;

    public LorenzoTokenMessage(Integer gameId, Integer playerId) {
        super(gameId, playerId);
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeLorenzoAction(this);
    }

    public LorenzoToken getToken() {
        return token;
    }

    public void setToken(LorenzoToken token) {
        this.token = token;
    }
}

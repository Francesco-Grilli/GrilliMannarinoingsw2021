package it.polimi.ingsw.GrilliMannarino.Message;

import java.io.Serializable;

public class SaveStatusMessage extends Message implements MessageInterface, Serializable {


    public SaveStatusMessage(Integer gameId, Integer playerId) {
        super(gameId, playerId);
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeSaveStatus(this);
    }
}

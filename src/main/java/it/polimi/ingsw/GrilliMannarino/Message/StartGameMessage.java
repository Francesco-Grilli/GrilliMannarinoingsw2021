package it.polimi.ingsw.GrilliMannarino.Message;

import java.io.Serializable;

public class StartGameMessage extends Message implements MessageInterface, Serializable {


    public StartGameMessage(Integer gameId, Integer playerId){
        super(gameId, playerId);
    }




    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeStartGame(this);
    }

}

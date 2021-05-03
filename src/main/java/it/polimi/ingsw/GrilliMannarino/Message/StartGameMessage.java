package it.polimi.ingsw.GrilliMannarino.Message;

import java.io.Serializable;

public class StartGameMessage extends Message implements MessageInterface, Serializable {

    private Integer numberOfPlayer;

    public StartGameMessage(Integer gameId, Integer playerId, Integer numberOfPlayer){
        super(gameId, playerId);
        this.numberOfPlayer=numberOfPlayer;
    }




    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeStartGame(this);
    }


    public Integer getNumberOfPlayer() {
        return numberOfPlayer;
    }
}

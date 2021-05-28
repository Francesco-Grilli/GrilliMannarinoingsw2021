package it.polimi.ingsw.GrilliMannarino.Message;

import java.io.Serializable;

public class StartGameMessage extends Message implements MessageInterface, Serializable {

    private boolean start = false;
    private Integer numberPlayer;

    public StartGameMessage(Integer gameId, Integer playerId, boolean start){
        super(gameId, playerId);
        this.start = start;
    }




    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeStartGame(this);
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public Integer getNumberPlayer() {
        return numberPlayer;
    }

    public void setNumberPlayer(Integer numberPlayer) {
        this.numberPlayer = numberPlayer;
    }
}

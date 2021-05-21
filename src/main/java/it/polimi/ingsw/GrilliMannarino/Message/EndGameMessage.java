package it.polimi.ingsw.GrilliMannarino.Message;

import java.util.ArrayList;
import java.util.HashMap;

public class EndGameMessage extends Message implements MessageInterface{

    private HashMap<String, Integer> playerRanking;

    public EndGameMessage(Integer gameId, Integer playerId) {
        super(gameId, playerId);
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeEndGame(this);
    }

    public HashMap<String, Integer> getPlayerRanking() {
        return playerRanking;
    }

    public void setPlayerRanking(HashMap<String, Integer> playerRanking) {
        this.playerRanking = playerRanking;
    }
}

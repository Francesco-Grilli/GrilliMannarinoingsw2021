package it.polimi.ingsw.GrilliMannarino.Message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EndGameMessage extends Message implements MessageInterface, Serializable {

    private HashMap<Integer, Map.Entry<String, Integer>> playerRanking;
    private boolean win = false;

    public EndGameMessage(Integer gameId, Integer playerId) {
        super(gameId, playerId);
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeEndGame(this);
    }

    public HashMap<Integer, Map.Entry<String, Integer>> getPlayerRanking() {
        return playerRanking;
    }

    public void setPlayerRanking(HashMap<Integer, Map.Entry<String, Integer>> playerRanking) {
        this.playerRanking = playerRanking;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }
}

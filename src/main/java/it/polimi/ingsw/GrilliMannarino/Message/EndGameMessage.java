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
}

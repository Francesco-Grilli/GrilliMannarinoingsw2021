package it.polimi.ingsw.GrilliMannarino.Message;

import java.util.ArrayList;
import java.util.HashMap;

public class EndGameMessage implements MessageInterface{

    private HashMap<String, Integer> playerRanking;

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeEndGame(this);
    }
}

package it.polimi.ingsw.GrilliMannarino.Message;

public class EndGameMessage implements MessageInterface{
    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeEndGame(this);
    }
}

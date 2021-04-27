package it.polimi.ingsw.GrilliMannarino.Message;

public class StartGameMessage implements MessageInterface{
    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeStartGame(this);
    }
}

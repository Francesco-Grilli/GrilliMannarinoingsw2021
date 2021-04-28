package it.polimi.ingsw.GrilliMannarino.Message;

public class TurnMessage implements MessageInterface{

    private String playerNickName;

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeTurn(this);
    }
}

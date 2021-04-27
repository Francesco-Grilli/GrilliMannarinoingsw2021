package it.polimi.ingsw.GrilliMannarino.Message;

public class AccountMessage implements MessageInterface{
    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeAccount(this);
    }
}

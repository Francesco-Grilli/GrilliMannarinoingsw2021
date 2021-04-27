package it.polimi.ingsw.GrilliMannarino.Message;

public class LoginMessage implements MessageInterface{
    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeLogin(this);
    }
}

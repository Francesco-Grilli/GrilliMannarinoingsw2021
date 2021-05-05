package it.polimi.ingsw.GrilliMannarino.Message;

import it.polimi.ingsw.GrilliMannarino.Message.MessageInterface;
import it.polimi.ingsw.GrilliMannarino.Message.VisitorInterface;

public class CreateAccountMessage implements MessageInterface {
    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeCreateAccount(this);
    }
}

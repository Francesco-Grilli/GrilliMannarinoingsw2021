package it.polimi.ingsw.GrilliMannarino.Message;

public class LoginMessage implements MessageInterface{

    private String nickName;
    private String typeOfClient; //this can be CLI or GUI String

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeLogin(this);
    }
}

package it.polimi.ingsw.GrilliMannarino.Message;

public class GuestMessage implements MessageInterface{

    private String nickName;
    private String typeOfClient; //CLI or GUI

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeAccount(this);
    }
}

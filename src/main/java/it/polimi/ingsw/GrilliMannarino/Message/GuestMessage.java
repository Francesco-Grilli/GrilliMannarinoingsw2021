package it.polimi.ingsw.GrilliMannarino.Message;

public class GuestMessage implements MessageInterface{


    private final String nickName;

    public GuestMessage(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }


    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeAccount(this);
    }
}

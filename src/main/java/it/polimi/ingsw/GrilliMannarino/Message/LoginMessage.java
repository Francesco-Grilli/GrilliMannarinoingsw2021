package it.polimi.ingsw.GrilliMannarino.Message;

public class LoginMessage implements MessageInterface{

    private String nickName;

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeLogin(this);
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}

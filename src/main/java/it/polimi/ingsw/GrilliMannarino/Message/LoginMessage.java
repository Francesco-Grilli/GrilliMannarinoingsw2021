package it.polimi.ingsw.GrilliMannarino.Message;

public class LoginMessage extends Message implements MessageInterface{

    private String nickName;

    public LoginMessage(Integer gameId, Integer playerId) {
        super(gameId, playerId);
    }

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

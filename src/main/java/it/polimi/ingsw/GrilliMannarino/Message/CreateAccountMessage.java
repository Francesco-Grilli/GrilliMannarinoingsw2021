package it.polimi.ingsw.GrilliMannarino.Message;

import it.polimi.ingsw.GrilliMannarino.Message.MessageInterface;
import it.polimi.ingsw.GrilliMannarino.Message.VisitorInterface;

public class CreateAccountMessage extends Message implements MessageInterface {

    private String nickname;

    public CreateAccountMessage(Integer gameId, Integer playerId) {
        super(gameId, playerId);
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeCreateAccount(this);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}

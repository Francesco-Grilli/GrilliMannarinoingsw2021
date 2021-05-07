package it.polimi.ingsw.GrilliMannarino.Message;

import java.io.Serializable;

public class EnterGameMessage extends Message implements MessageInterface, Serializable {

    private boolean enterGame = false;
    private final String nickname;
    private String messageString;

    public EnterGameMessage(Integer gameId, Integer playerId, String nickname) {
        super(gameId, playerId);
        this.nickname = nickname;
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeEnterGame(this);
    }

    public boolean isEnterGame() {
        return enterGame;
    }

    public void setEnterGame(boolean enterGame) {
        this.enterGame = enterGame;
    }

    public String getNickname() {
        return nickname;
    }

    public String getMessageString() {
        return messageString;
    }

    public void setMessageString(String messageString) {
        this.messageString = messageString;
    }
}

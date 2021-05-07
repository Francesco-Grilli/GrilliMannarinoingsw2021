package it.polimi.ingsw.GrilliMannarino.Message;

import java.io.Serializable;

public class LoginMessage implements Serializable {

    /**
     * if the newAccount boolean is false, by default it will try to log into the server with the nickname and the server
     * will check if there already is that nickname and will return the playerId associated
     * if the newAccount is true then the client wants to create a new account with the nickname string. Server check
     * if it is possible add that nickname and will return the playerId
     */
    private String nickname;
    private Integer playerId;
    private boolean newAccount = false;
    private boolean correctLogin = false;
    private String message;

    public LoginMessage(String nickname){
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public boolean isNewAccount() {
        return newAccount;
    }

    public void setNewAccount(boolean newAccount) {
        this.newAccount = newAccount;
    }

    public boolean isCorrectLogin() {
        return correctLogin;
    }

    public void setCorrectLogin(boolean correctLogin) {
        this.correctLogin = correctLogin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

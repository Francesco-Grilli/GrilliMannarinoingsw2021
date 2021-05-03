package it.polimi.ingsw.GrilliMannarino.Message;

public abstract class Message {

    private final Integer gameId;
    private final Integer playerId;

    public Message(Integer gameId, Integer playerId){
        this.gameId = gameId;
        this.playerId = playerId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public Integer getPlayerId() {
        return playerId;
    }
}

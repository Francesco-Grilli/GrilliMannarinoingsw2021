package it.polimi.ingsw.GrilliMannarino.Message;

public class StartGameMessage implements MessageInterface{

    private Integer numberOfPlayer;
    private Integer gameID;
    private Integer playerId;

    public StartGameMessage(Integer playerId, Integer numberOfPlayer, Integer gameID) {
        this.numberOfPlayer = numberOfPlayer;
        this.gameID = gameID;
        this.playerId = playerId;
    }




    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeStartGame(this);
    }

    public Integer getPlayerId() {
        return playerId;
    }

}

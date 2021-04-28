package it.polimi.ingsw.GrilliMannarino.Message;

public class StartGameMessage implements MessageInterface{

    private Integer numberOfPlayer;
    private Integer gameID;


    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeStartGame(this);
    }
}

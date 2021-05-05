package it.polimi.ingsw.GrilliMannarino.Message;

public class NewGameMessage extends Message implements MessageInterface{


    public NewGameMessage(Integer gameId, Integer playerId) {
        super(gameId, playerId);
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeNewGame(this);
    }
}

package it.polimi.ingsw.GrilliMannarino.Message;

public class ErrorMessage extends Message implements MessageInterface{

    public ErrorMessage(Integer playerId, Integer gameId) {
        super(gameId, playerId);
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeErrorMessage(this);
    }
}

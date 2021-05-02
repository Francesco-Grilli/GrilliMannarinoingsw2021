package it.polimi.ingsw.GrilliMannarino.Message;

public class ErrorMessage extends Message implements MessageInterface{

    private String error;

    public ErrorMessage(Integer playerId, Integer gameId) {
        super(gameId, playerId);
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeErrorMessage(this);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

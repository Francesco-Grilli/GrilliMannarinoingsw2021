package it.polimi.ingsw.GrilliMannarino.Message;

public class PopeLineMessage extends Message implements MessageInterface{

    private boolean checkPopeLine = false;
    private Integer checkPosition;

    public PopeLineMessage(Integer gameId, Integer playerId) {
        super(gameId, playerId);
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executePopeLine(this);
    }

    public boolean isCheckPopeLine() {
        return checkPopeLine;
    }

    public void setCheckPopeLine(boolean checkPopeLine) {
        this.checkPopeLine = checkPopeLine;
    }

    public Integer getCheckPosition() {
        return checkPosition;
    }

    public void setCheckPosition(Integer checkPosition) {
        this.checkPosition = checkPosition;
    }
}

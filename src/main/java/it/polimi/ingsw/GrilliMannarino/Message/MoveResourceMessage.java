package it.polimi.ingsw.GrilliMannarino.Message;

public class MoveResourceMessage extends Message implements MessageInterface{

    /**
     * canMove is used by the server to let client know if while moving lines some resources will be lost
     * forceSwap is used by the server to force the swap of the lines, server can set it to true to let client
     * know that the swap was correct
     */
    private String rowOne;
    private String rowTwo;
    private boolean canMove = false;
    private boolean forceSwap = false;

    public MoveResourceMessage(Integer gameId, Integer playerId) {
        super(gameId, playerId);
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeMoveResource(this);
    }

    public String getRowOne() {
        return rowOne;
    }

    public void setRowOne(String rowOne) {
        this.rowOne = rowOne;
    }

    public String getRowTwo() {
        return rowTwo;
    }

    public void setRowTwo(String rowTwo) {
        this.rowTwo = rowTwo;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean isForceSwap() {
        return forceSwap;
    }

    public void setForceSwap(boolean forceSwap) {
        this.forceSwap = forceSwap;
    }
}

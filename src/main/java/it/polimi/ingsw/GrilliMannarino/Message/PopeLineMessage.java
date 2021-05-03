package it.polimi.ingsw.GrilliMannarino.Message;

import java.io.Serial;
import java.io.Serializable;

public class PopeLineMessage extends Message implements MessageInterface, Serializable {

    /**
     * checkPopeLine is used to indicate that someone has activated a favor and ask all client to update the view
     * if checkPopeLine is true then client should also check it's faithPosition
     * favorActive tell the client if the favor has been activated for him or not
     */
    private boolean checkPopeLine = false;
    private boolean favorActive = false;
    private Integer checkPosition;

    /**
     * if updatePosition is true means that the faith has been updated but didn't activate a new favor client should
     * check his faithPosition
     */
    private boolean updatedPosition = false;
    private Integer faithPosition;

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

    public boolean isUpdatedPosition() {
        return updatedPosition;
    }

    public void setUpdatedPosition(boolean updatedPosition) {
        this.updatedPosition = updatedPosition;
    }

    public Integer getFaithPosition() {
        return faithPosition;
    }

    public void setFaithPosition(Integer faithPosition) {
        this.faithPosition = faithPosition;
    }

    public boolean isFavorActive() {
        return favorActive;
    }

    public void setFavorActive(boolean favorActive) {
        this.favorActive = favorActive;
    }
}

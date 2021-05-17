package it.polimi.ingsw.GrilliMannarino.Message;

import java.io.Serializable;

public class PopeLineSingleMessage extends PopeLineMessage implements Serializable, MessageInterface {

    private Integer lorenzoFaith;
    private boolean lorenzoFavorActive = false;
    private Integer lorenzoCheckPosition;

    public PopeLineSingleMessage(Integer gameId, Integer playerId) {
        super(gameId, playerId);
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executePopeLineSingle(this);
    }

    public Integer getLorenzoFaith() {
        return lorenzoFaith;
    }

    public void setLorenzoFaith(Integer lorenzoFaith) {
        this.lorenzoFaith = lorenzoFaith;
    }

    public boolean isLorenzoFavorActive() {
        return lorenzoFavorActive;
    }

    public void setLorenzoFavorActive(boolean lorenzoFavorActive) {
        this.lorenzoFavorActive = lorenzoFavorActive;
    }

    public Integer getLorenzoCheckPosition() {
        return lorenzoCheckPosition;
    }

    public void setLorenzoCheckPosition(Integer lorenzoCheckPosition) {
        this.lorenzoCheckPosition = lorenzoCheckPosition;
    }
}

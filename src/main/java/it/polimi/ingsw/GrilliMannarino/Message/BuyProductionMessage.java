package it.polimi.ingsw.GrilliMannarino.Message;

public class BuyProductionMessage implements MessageInterface{
    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeBuyProduction(this);
    }
}

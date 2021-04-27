package it.polimi.ingsw.GrilliMannarino.Message;

public class BuyMarketMessage implements MessageInterface{
    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeBuyMarket(this);
    }
}

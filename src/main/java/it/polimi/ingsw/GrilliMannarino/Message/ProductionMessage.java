package it.polimi.ingsw.GrilliMannarino.Message;

public class ProductionMessage implements MessageInterface{
    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeProduction(this);
    }
}

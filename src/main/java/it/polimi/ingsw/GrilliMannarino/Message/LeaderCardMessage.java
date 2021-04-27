package it.polimi.ingsw.GrilliMannarino.Message;

public class LeaderCardMessage implements MessageInterface{
    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeLeaderCard(this);
    }
}

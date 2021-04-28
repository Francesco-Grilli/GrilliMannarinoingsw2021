package it.polimi.ingsw.GrilliMannarino.Message;

public class LeaderCardMessage implements MessageInterface{

    private String actionOnLeaderCard; //can be Sell or Activate
    private boolean canActivate = false;
    private boolean sellingCard = false;
    private Integer faithPoint;

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeLeaderCard(this);
    }
}

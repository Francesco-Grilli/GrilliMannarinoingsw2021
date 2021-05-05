package it.polimi.ingsw.GrilliMannarino.Message;

public class GuestMessage extends Message implements MessageInterface{


    private final String nickName;

    public GuestMessage(Integer gameId, Integer playerId, String nickName) {
        super(gameId, playerId);
        this.nickName = nickName;
    }


    public String getNickName() {
        return nickName;
    }


    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeGuest(this);
    }
}

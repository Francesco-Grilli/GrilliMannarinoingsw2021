package it.polimi.ingsw.GrilliMannarino.Message;

public interface MessageInterface {

    Integer gameId = null;


    void execute(VisitorInterface visitor);

}

package it.polimi.ingsw.GrilliMannarino.GameData;

public enum Resource {
    SHIELD(Marble.BLUE),STONE(Marble.GREY),SERVANT(Marble.PURPLE),COIN(Marble.YELLOW),FAITH(Marble.RED),UNKNOWN(Marble.BLACK),NONE(Marble.WHITE) ;

    private final Marble marble;

    Resource(Marble marble){ this.marble = marble; }

    public  Marble getMarble(Resource m){
        return marble;
    }

}

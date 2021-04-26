package it.polimi.ingsw.GrilliMannarino.GameData;

public enum Resource {
    SHIELD(Marble.BLUE),STONE(Marble.GREY),SERVANT(Marble.PURPLE),COIN(Marble.YELLOW),FAITH(Marble.RED),UNKNOWN(Marble.BLACK),NONE(Marble.WHITE) ;

    private final Marble marble;

    Resource(Marble marble){ this.marble = marble; }

    public static Marble getMarble(Resource m){
        switch(m){
            case COIN:
                return Marble.YELLOW;
            case SHIELD:
                return Marble.BLUE;
            case SERVANT:
                return Marble.PURPLE;
            case STONE:
                return Marble.GREY;
            case FAITH:
                return Marble.RED;
            case UNKNOWN:
                return Marble.BLACK;
            default:
                return Marble.WHITE;
        }
    }

}

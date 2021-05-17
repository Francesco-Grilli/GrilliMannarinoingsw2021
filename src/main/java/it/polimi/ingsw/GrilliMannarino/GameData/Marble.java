package it.polimi.ingsw.GrilliMannarino.GameData;

import java.util.ArrayList;

public enum Marble {
  YELLOW(Resource.COIN), RED(Resource.FAITH), PURPLE(Resource.SERVANT), BLUE(Resource.SHIELD), WHITE(Resource.NONE), GREY(Resource.STONE), BLACK(Resource.UNKNOWN);

  private final Resource resource;

  Marble(Resource resource) { this.resource = resource; }

  public static Resource getResource(Marble m){
    switch(m){
      case YELLOW:
        return Resource.COIN;
      case BLUE:
        return Resource.SHIELD;
      case PURPLE:
        return Resource.SERVANT;
      case GREY:
        return Resource.STONE;
      case RED:
        return Resource.FAITH;
      case BLACK:
        return Resource.UNKNOWN;
      default:
        return Resource.NONE;
    }
  }

  public static ArrayList<Marble> getOrderedMarble(){
    ArrayList<Marble> m = new ArrayList<>();
    m.add(Marble.YELLOW);
    m.add(Marble.PURPLE);
    m.add(Marble.BLUE);
    m.add(Marble.GREY);
    return m;
  }

}

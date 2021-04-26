package it.polimi.ingsw.GrilliMannarino.GameData;

public enum Marble {
  YELLOW(Resource.COIN), RED(Resource.FAITH), PURPLE(Resource.SERVANT), BLUE(Resource.SHIELD), WHITE(Resource.UNKNOWN), GREY(Resource.STONE);

  private final Resource resource;

  Marble(Resource resource){
    this.resource = resource;
  }

  public Resource getResource(){return resource;}
  }

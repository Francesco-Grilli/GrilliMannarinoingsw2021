package it.polimi.ingsw.GrilliMannarino;

public class Player {
  private String name;
  private final int ID;

  public Player(String name, int ID) {
    this.name = name;
    this.ID = ID;
  }

  public String getName() {
    return name;
  }

  public int getID() {
    return ID;
  }
}

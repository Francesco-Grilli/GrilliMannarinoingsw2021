package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Marble;

import java.util.ArrayList;

public class MarbleOption {
  private ArrayList<Marble> marbles;

  public MarbleOption(Marble marble){
    this.marbles = new ArrayList<>();
    marbles.add(marble);
  }

  public void addOption(Marble marble){
    this.marbles.add(marble);
  }
}

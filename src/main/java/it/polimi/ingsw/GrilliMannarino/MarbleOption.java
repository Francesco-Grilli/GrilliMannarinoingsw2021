package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Marble;

import java.util.ArrayList;

public class MarbleOption {
  private ArrayList<Marble> marbles;

  public MarbleOption(Marble marble){
    this.marbles = new ArrayList<>();
    marbles.add(marble);
  }

  /**
   * adds an option marble if it is not already contained in the options
   * @param marble the marble to add
   */
  public void addOption(Marble marble){
    if(!(marbles.contains(marble))) {
      this.marbles.add(marble);
    }
  }

  /**
   * specifies if among the options there is the white marble
   * @return
   */
  public boolean hasWhite(){
    return marbles.contains(Marble.WHITE);
  }

  /**
   * returns all the options
   * @return
   */
  public ArrayList<Marble> getMarbles() {
    return marbles;
  }
}

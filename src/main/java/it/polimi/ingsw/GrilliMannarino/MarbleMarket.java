package it.polimi.ingsw.GrilliMannarino;


import it.polimi.ingsw.GrilliMannarino.GameData.Marble;

import java.util.ArrayList;
import java.util.HashMap;

public class MarbleMarket implements MarbleMarketBoardInterface {
  private Marble[][] marbleBoard;
  private Marble marbleOut;
  private int sizeX;
  private int sizeY;

  public MarbleMarket(int sizeX, int sizeY, HashMap<Marble, Integer> marbles){
    marbleBoard = new Marble[sizeX][sizeY];

    //methods to build the random marble configuration
  }

  public Marble[][] getMarbleBoard() {
    return marbleBoard;
  }

  public Marble getMarbleOut() {
    return marbleOut;
  }

  public ArrayList<MarbleOption> getColumn(int column){
    column = column % this.sizeX;
    ArrayList<MarbleOption> toReturn = new ArrayList<>(this.sizeY);
    for(int pos = 0; pos<this.sizeY; pos++){
      toReturn.add(pos,new MarbleOption(this.marbleBoard[column][pos]));
    }
    shiftColumn(column);
    return toReturn;
  }

  private void shiftColumn(int column){
    Marble temporaryMarble;
    for(int pos = 0; pos<this.sizeY; pos++){
      temporaryMarble = this.marbleBoard[column][pos];
      this.marbleBoard[column][pos] = this.marbleOut;
      this.marbleOut = temporaryMarble;
    }
  }

  public ArrayList<MarbleOption> getRow(int row){
    row = row % this.sizeY;
    ArrayList<MarbleOption> toReturn = new ArrayList<>(this.sizeX);
    for(int pos = 0; pos<this.sizeX; pos++){
      toReturn.add(pos,new MarbleOption(this.marbleBoard[pos][row]));
    }
    shiftRow(row);
    return toReturn;
  }

  private void shiftRow(int row){
    Marble temporaryMarble;
    for(int pos = 0; pos<this.sizeY; pos++){
      temporaryMarble = this.marbleBoard[pos][row];
      this.marbleBoard[pos][row] = this.marbleOut;
      this.marbleOut = temporaryMarble;
    }
  }
}

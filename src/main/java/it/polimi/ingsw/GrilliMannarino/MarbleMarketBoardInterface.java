package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Marble;

import java.util.ArrayList;

public interface MarbleMarketBoardInterface {
  Marble[][] getMarbleBoard();
  Marble getMarbleOut();
  ArrayList<MarbleOption> getColumn(int column);
  ArrayList<MarbleOption> getRow(int row);
}

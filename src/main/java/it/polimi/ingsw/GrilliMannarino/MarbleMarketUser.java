package it.polimi.ingsw.GrilliMannarino;

import java.util.ArrayList;

public interface MarbleMarketUser {
  ArrayList<MarbleOption> getColumn(int column);
  ArrayList<MarbleOption> getRow(int row);
}

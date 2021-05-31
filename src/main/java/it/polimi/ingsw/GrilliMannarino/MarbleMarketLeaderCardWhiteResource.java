package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.ArrayList;
import java.util.HashMap;

public class MarbleMarketLeaderCardWhiteResource extends MarbleMarketLeaderCard implements MarbleMarketBoardInterface{

  public MarbleMarketLeaderCardWhiteResource(HashMap<Resource, Integer> resourcePrice, HashMap<Faction, HashMap<Integer, Integer>> cardPrice, Resource definedResource, int points,int cardCode) {
    super(resourcePrice, cardPrice, definedResource, points,cardCode);
  }

  /**
   * returns the marbles on the grid of the market as an Array of Arrays
   * @return the marbles
   */
  @Override
  public Marble[][] getMarbleBoard() {
    return getMarbleMarket().getMarbleBoard();
  }

  /**
   * returns the marble to joggle into the market
   * @return is the marble
   */
  @Override
  public Marble getMarbleOut() {
    return getMarbleMarket().getMarbleOut();
  }

  /**
   * performs the action of selecting a column in the market
   * adding the optional resource instead of the white marble
   * @param column the column
   * @return the marbles earned from the action
   */
  @Override
  public ArrayList<MarbleOption> getColumn(int column) {
    ArrayList<MarbleOption> temp = getMarbleMarket().getColumn(column);
    temp.forEach(t -> {
      if(t.hasWhite()){
        t.addOption(Resource.UNKNOWN.getMarble(getDefinedResource()));
      }
    });
    return temp;
  }

  /**
   * performs the action of selecting a row in the market
   * adding the optional resource instead of the white marble
   * @param row the row
   * @return the marbles earned from the action
   */
  @Override
  public ArrayList<MarbleOption> getRow(int row) {
    ArrayList<MarbleOption> temp = getMarbleMarket().getColumn(row);
    temp.forEach(t -> {
      if(t.hasWhite()){
        t.addOption(Resource.UNKNOWN.getMarble(getDefinedResource()));
      }
    });
    return temp;
  }

  @Override
  public void execute(Board board) {
    setMarbleMarket(board.getMarbleMarket());
    board.setMarbleMarket(this);
  }
}

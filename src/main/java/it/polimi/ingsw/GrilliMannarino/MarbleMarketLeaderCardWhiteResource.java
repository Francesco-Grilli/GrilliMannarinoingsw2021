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

  @Override
  public Marble[][] getMarbleBoard() {
    return getMarbleMarket().getMarbleBoard();
  }

  @Override
  public Marble getMarbleOut() {
    return getMarbleMarket().getMarbleOut();
  }

  @Override
  public ArrayList<MarbleOption> getColumn(int column) {
    ArrayList<MarbleOption> temp = getMarbleMarket().getColumn(column);
    temp.forEach(t -> {
      if(t.hasWhite()){
        //t.addOption(Resource.getMarble(getDefinedResource()));
      }
    });
    return temp;
  }

  @Override
  public ArrayList<MarbleOption> getRow(int row) {
    ArrayList<MarbleOption> temp = getMarbleMarket().getColumn(row);
    temp.forEach(t -> {
      if(t.hasWhite()){
        //t.addOption(Resource.getMarble(getDefinedResource()));
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

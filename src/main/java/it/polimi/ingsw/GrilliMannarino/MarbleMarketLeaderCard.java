package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.HashMap;

public abstract class MarbleMarketLeaderCard extends LeaderCard{
  private MarbleMarketBoardInterface marbleMarket;

  public MarbleMarketLeaderCard(HashMap<Resource, Integer> resourcePrice, HashMap<Faction, HashMap<Integer, Integer>> cardPrice, Resource definedResource, int points, int cardCode) {
    super(resourcePrice, cardPrice, definedResource, points,cardCode);
  }

  public abstract void execute(Board board);

  public MarbleMarketBoardInterface getMarbleMarket() {
    return marbleMarket;
  }

  public void setMarbleMarket(MarbleMarketBoardInterface marbleMarket) {
    this.marbleMarket = marbleMarket;
  }
}

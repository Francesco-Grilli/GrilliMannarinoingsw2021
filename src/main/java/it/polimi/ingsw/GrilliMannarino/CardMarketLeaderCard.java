package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.HashMap;

public abstract class CardMarketLeaderCard extends LeaderCard{
  protected CardMarketBoardInterface cardMarket;

  public CardMarketLeaderCard(HashMap<Resource, Integer> resourcePrice, HashMap<Faction, HashMap<Integer, Integer>> cardPrice, Resource definedResource, int points) {
    super(resourcePrice, cardPrice, definedResource, points);
  }

  public abstract void execute(Board board);

  public CardMarketBoardInterface getCardMarket() {
    return cardMarket;
  }

  public void setCardMarket(CardMarketBoardInterface cardMarket) {
    this.cardMarket = cardMarket;
  }
}

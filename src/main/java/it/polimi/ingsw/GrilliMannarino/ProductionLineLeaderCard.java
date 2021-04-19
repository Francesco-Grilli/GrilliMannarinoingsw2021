package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.HashMap;

public abstract class ProductionLineLeaderCard extends LeaderCard{
  private ProductionLineBoardInterface productionLine;

  public ProductionLineLeaderCard(HashMap<Resource, Integer> resourcePrice, HashMap<Faction, HashMap<Integer, Integer>> cardPrice, Resource definedResource, int points) {
    super(resourcePrice, cardPrice, definedResource, points);
  }

  public abstract void execute(Board board);

  public ProductionLineBoardInterface getProductionLine() {
    return productionLine;
  }

  public void setProductionLine(ProductionLineBoardInterface productionLine) {
    this.productionLine = productionLine;
  }
}

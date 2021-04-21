package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {

  private Player player;
  private PopeLine popeLine;
  private ResourceManagerInterface resourceManager;
  private ProductionLineBoardInterface productionLine;
  private CardMarketBoardInterface cardMarket;
  private MarbleMarketBoardInterface marbleMarket;

  public Board(Player player, CardMarketBoardInterface cardMarket, MarbleMarketBoardInterface marbleMarket){
    this.player = player;
    this.cardMarket = cardMarket;
    this.marbleMarket = marbleMarket;

    this.popeLine = new PopeLine();
    this.resourceManager = new ResourceManager();
    this.productionLine = new ProductionLine();
  }

  public CardMarketBoardInterface getCardMarket() { return cardMarket; }

  public void setCardMarket(CardMarketBoardInterface cardMarket) { this.cardMarket = cardMarket; }

  public MarbleMarketBoardInterface getMarbleMarket() {
    return marbleMarket;
  }

  public void setMarbleMarket(MarbleMarketBoardInterface marbleMarket){
    this.marbleMarket = marbleMarket;
  }

  public ProductionLineBoardInterface getProductionLine() { return productionLine; }

  public void setProductionLine(ProductionLineBoardInterface productionLine) { this.productionLine = productionLine; }

  public boolean canProduceWithConfiguration(ArrayList<CreationCard> cards){
    return resourceManager.canRemove(getInputOfConfiguration(cards));
  }

  public void produce(ArrayList<CreationCard> cards){
    if(canProduceWithConfiguration(cards)) {
      resourceManager.remove(getInputOfConfiguration(cards));
      resourceManager.setResourcesFromProduction(getOutputOfConfiguration(cards));
    }
  }

  public Boolean canBuyCard(CreationCard card){
    return resourceManager.canRemove(card.getPrice());
  }

  public CreationCard getCardFromMarket(CreationCard card){
    if(canBuyCard(card)) {
      return cardMarket.buyCard(card.getFaction(), card.getCardLevel());
    }
    return null;
  }

  public ResourceManagerInterface getResourceManager(){
    return this.resourceManager;
  }

  public void setResourceManager(ResourceManagerInterface resourceManager) {
    this.resourceManager = resourceManager;
  }

  public void setResourcesFromMarket(HashMap<Resource,Integer> resources){
    resourceManager.getResources();
  }

  public HashMap<Resource, Integer> getInputOfConfiguration(ArrayList<CreationCard> cardsToProduceWith){
    HashMap<Resource, Integer> inputOfConfiguration = new HashMap<>();
    cardsToProduceWith.forEach((t) -> {
      for(Resource key:t.getInput().keySet()){
        inputOfConfiguration.put(key, (inputOfConfiguration.get(key) == null ? 0 : inputOfConfiguration.get(key))+t.getInput().get(key));
      }
    });
    return inputOfConfiguration;
  }

  public HashMap<Resource, Integer> getOutputOfConfiguration(ArrayList<CreationCard> cardsToProduceWith){
    HashMap<Resource, Integer> outputOfConfiguration = new HashMap<>();
    cardsToProduceWith.forEach((t) -> {
      for(Resource key:t.getOutput().keySet()){
        outputOfConfiguration.put(key, (outputOfConfiguration.get(key) == null ? 0 : outputOfConfiguration.get(key))+t.getOutput().get(key));
      }
    });
    return outputOfConfiguration;
  }
}

package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class Board {

  private Player player;
  private PopeLine popeLine;
  private ResourceManager resourceManager;
  private ProductionLine productionLine;
  private CardMarket cardMarket;
  //private MarbleMarketUserInterface marbleMarket;




  /*



  public ArrayList<Integer> checkProduction(){
    return productionLine.showCards();
  }

  public boolean canProduceWithConfiguration(ArrayList<Integer> cardCodes){
    return resourceManager.canRemove(productionLine.getCostOfConfiguration(cardCodes));//productionLine.canProduce(cardCodes, resourceManager.getResources());
  }

  public void produce(ArrayList<Integer> cardCodes){
    if(resourceManager.remove(productionLine.getCostOfConfiguration(cardCodes)).values().stream().reduce(0, Integer::sum).equals(0)){
      productionLine.produce(cardCodes, productionLine.getCostOfConfiguration(cardCodes));
    }
  }

  public ArrayList<Integer> canBuy(){
    return cardMarket.purchaseableCards(resourceManager.getResources());
  }

  public CreationCard buy(Faction faction, Integer level){
    return cardMarket.buyCard(faction,level, resourceManager.getResources());
  }

  public void placingCard(int position, CreationCard card){
    if (!(productionLine.addCard(position,card))) {
      productionLine.forceAddCard(card);
    }
  }

  public HashMap<Resource, Integer> setResourcesFromMarket(HashMap<Resource,Integer> resources){

  }

          */



}

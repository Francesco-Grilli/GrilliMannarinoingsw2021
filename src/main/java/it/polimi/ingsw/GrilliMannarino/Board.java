package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {

  private final Player player;
  private PopeLine popeLine;
  private ResourceManagerBoardInterface resourceManager;
  private ProductionLineBoardInterface productionLine;
  private CardMarketBoardInterface cardMarket;
  private MarbleMarketBoardInterface marbleMarket;

  public Board(Player player, CardMarketBoardInterface cardMarket, MarbleMarketBoardInterface marbleMarket){
    this.player = player;
    this.marbleMarket = marbleMarket;
    this.cardMarket = cardMarket;
    this.productionLine = new ProductionLine();
    this.resourceManager = new ResourceManager();
    this.popeLine = new PopeLine();
  }

  //METHOD TO GET/BUY CARD
  public HashMap<Faction, HashMap<Integer, CreationCard>> getBuyableCard(){
    return cardMarket.getCards();
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

  public boolean canProduceWithConfiguration(ArrayList<CreationCard> cards){
    return resourceManager.canRemove(getInputOfConfiguration(cards));
  }

  public void produce(ArrayList<CreationCard> cards){
    if(canProduceWithConfiguration(cards)) {
      resourceManager.remove(getInputOfConfiguration(cards));
      resourceManager.setResourcesFromProduction(getOutputOfConfiguration(cards));
    }
  }

  public Marble[][] getMarblesFromMarket(){
    return this.marbleMarket.getMarbleBoard();
  }

  public Marble getMarbleOut(){
    return this.marbleMarket.getMarbleOut();
  }

  public ArrayList<MarbleOption> getRow(int i){
    return this.marbleMarket.getRow(i);
  }

  public ArrayList<MarbleOption> getColumn(int i){
    return this.marbleMarket.getColumn(i);
  }


  //METHOD TO WORK WITH RESOURCE MANAGER

  public boolean canSetResourcesFromMarket(Row line, Resource resource, Integer value){
    return resourceManager.canSetResourcesFromMarket(line, resource, value);
  }

  public HashMap<Resource, Integer> setResourcesFromMarket(Row line, Resource resource, Integer value){
    return resourceManager.setResourcesFromMarket(line, resource, value);
  }

  public void setResourcesFromProduction(HashMap<Resource, Integer> resources){
    resourceManager.setResourcesFromProduction(resources);
  }

  public boolean canSwapLineFromWareHouse(Row one, Row two){
    return resourceManager.canSwapLine(one, two);
  }

  public void forceSwapLineFromWareHouse(Row one, Row two){
    resourceManager.forceSwapLine(one, two);
  }

  public int getResourcePoints(){
    return resourceManager.getResourcePoints();
  }

  //METHOD TO ACCESS THE POPELINE

  public boolean addPopeFaith(){
    return popeLine.addFaith();
  }

  public boolean checkPopeFaith(){
    return popeLine.checkPopeFaith();
  }

  //should remove?
  public void updateChecks(){
    PopeLine.updateChecks();
  }

  public int getFaithTrackPoints(){
    return popeLine.getPoints();
  }

  public int getFatith(){
    return popeLine.getFaith();
  }

  public boolean[] getFaithSteps(){
    return popeLine.getFaithSteps();
  }

  //should remove?
  public boolean[] getFaithChecks(){
    return PopeLine.getFaithChecks();
  }

  public void addLorenzoFaith(){
    ((PopeLineSingle) popeLine).addLorenzoFaith();
  }

  public void doubleAddLorenzoFaith(){
    ((PopeLineSingle) popeLine).doubleAddLorenzoFaith();
  }


  public int getLorenzoFaith(){
    return ((PopeLineSingle) popeLine).getLorenzoFaith();
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

  //GETTERS AND SETTERS


  public PopeLine getPopeLine() {
    return popeLine;
  }

  public ResourceManagerBoardInterface getResourceManager() {
    return resourceManager;
  }

  public ProductionLineBoardInterface getProductionLine() {
    return productionLine;
  }

  public CardMarketBoardInterface getCardMarket() {
    return cardMarket;
  }

  public MarbleMarketBoardInterface getMarbleMarket() {
    return marbleMarket;
  }

  public void setPopeLine(PopeLine popeLine) {
    this.popeLine = popeLine;
  }

  public void setResourceManager(ResourceManagerBoardInterface resourceManager) {
    this.resourceManager = resourceManager;
  }

  public void setProductionLine(ProductionLineBoardInterface productionLine) {
    this.productionLine = productionLine;
  }

  public void setCardMarket(CardMarketBoardInterface cardMarket) {
    this.cardMarket = cardMarket;
  }

  public void setMarbleMarket(MarbleMarketBoardInterface marbleMarket) {
    this.marbleMarket = marbleMarket;
  }
}

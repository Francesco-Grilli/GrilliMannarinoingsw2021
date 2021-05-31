package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Board {

  protected final Player player;
  protected PopeLine popeLine;
  protected ResourceManagerBoardInterface resourceManager;
  protected ProductionLineBoardInterface productionLine;
  protected CardMarketBoardInterface cardMarket;
  protected MarbleMarketBoardInterface marbleMarket;
  protected HashMap<Integer, LeaderCard> boardLeaderCards;
  protected HashMap<Integer, LeaderCard> activeLeaderCards;
  private boolean startingResource = false;

  public Board(Player player, CardMarketBoardInterface cardMarket, MarbleMarketBoardInterface marbleMarket){
    this.player = player;
    this.marbleMarket = marbleMarket;
    this.cardMarket = cardMarket;
    this.productionLine = new ProductionLine();
    this.resourceManager = new ResourceManager();
    this.popeLine = new PopeLine();
    this.boardLeaderCards = new HashMap<>();
    this.activeLeaderCards = new HashMap<>();
  }


  /**
   * show the card inside
   * @return
   */
  public HashMap<Integer, CreationCard> showCardInProductionLine(){
    return productionLine.getCards();
  }

  //METHOD TO GET/BUY CARD
  public HashMap<Faction, HashMap<Integer, Map.Entry<CreationCard,Boolean>>> getBuyableCard(){
    HashMap<Faction, HashMap<Integer, CreationCard>> t = cardMarket.getCards();
    HashMap<Faction, HashMap<Integer, Map.Entry<CreationCard,Boolean>>> ret = new HashMap<>();
    t.forEach((fac,levels)->{
      ret.put(fac,new HashMap<>());
      levels.forEach((lev,pair)->{
        if(pair!=null)
          ret.get(fac).put(lev, new AbstractMap.SimpleEntry<>(pair.getCard(), canBuyCard(pair)));
      });
    });
    return ret;
  }

  private Boolean canBuyCard(CreationCard card){
    return resourceManager.canRemove(card.getPrice());
  }


  public CreationCard getCardFromMarket(CreationCard card){
    if(canBuyCard(card)) {
      resourceManager.remove(card.getPrice());
      return cardMarket.buyCard(card.getFaction(), card.getCardLevel());
    }
    return null;
  }


  public boolean canProduceWithConfiguration(ArrayList<CreationCard> cards){
    return resourceManager.canRemove(getInputOfConfiguration(cards));
  }

  public boolean produce(ArrayList<CreationCard> cards){
    boolean check = false;
    if(canProduceWithConfiguration(cards)) {
      resourceManager.remove(getInputOfConfiguration(cards));
      HashMap<Resource, Integer> resourceProduct = getOutputOfConfiguration(cards);
      int numberOfFaith=0;
      if(resourceProduct.containsKey(Resource.FAITH)){
        numberOfFaith = resourceProduct.get(Resource.FAITH);
        resourceProduct.remove(Resource.FAITH);
      }
      resourceManager.setResourcesFromProduction(resourceProduct);
      if(numberOfFaith>0){
        for(int i=0; i<numberOfFaith; i++){
          if(addPopeFaith())
            check=true;
        }
      }
    }
    return check;
  }

  public int getNumberOfCards(){
    return productionLine.getNumberOfCards();
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

  public void setLeaderCards(ArrayList<LeaderCard> leaderCards){
    leaderCards.forEach(t-> this.boardLeaderCards.put(t.getCardCode(),t));
  }

  public boolean activateLeaderCard(int cardCode){
    if(boardLeaderCards.containsKey(cardCode)){
      if(canActivateLeaderCard(boardLeaderCards.get(cardCode))) {
        boardLeaderCards.get(cardCode).execute(this);
        return true;
      }
    }
    return false;
  }

  public LeaderCard getLeaderCard(int cardCode){
    if(activeLeaderCards.containsKey(cardCode))
      return activeLeaderCards.get(cardCode);
    else
      return null;
  }

  private boolean canActivateLeaderCard(LeaderCard leaderCard){
    boolean condition = resourceManager.canRemove(leaderCard.getResourcePrice());
    if(!(checkOnCardsFactionsAndLevels(leaderCard.getCardPrice()))){
      condition = false;
    }
    return condition;
  }

  private boolean checkOnCardsFactionsAndLevels(HashMap<Faction,HashMap<Integer,Integer>> price){
    ArrayList<CreationCard> cardsToTest = productionLine.allUsedCards();
    for(Faction fac: price.keySet()){
      if(price.get(fac) != null){
        for(Integer lev: price.get(fac).keySet()){
          if(price.get(fac).get(lev) != null){
            Stream<CreationCard> test = cardsToTest.stream();
            if(lev > 0){
              test = test.filter(t->t.getCardLevel()==lev);
            }
            int respectableCards = (int) test.filter(t->t.getFaction().equals(fac)).count();
            if(respectableCards < price.get(fac).get(lev)){
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  public CreationCard getCardFromCode(int cardCode){
    return cardMarket.getCardFromCode(cardCode);
  }

  public boolean canAddCard(int pos, CreationCard card){
    return productionLine.canAddCard(pos, card);
  }

  public boolean addCard(int pos, CreationCard card){
    if(canAddCard(pos,card)){
      return productionLine.addCard(pos,card);
    }
    return false;
  }

  private int getLeaderCardsPoints(){
    return this.activeLeaderCards.values().stream().map(l -> l.getValue()).reduce(0, Integer::sum);
  }

  public int getPoints(){
    int points = this.getLeaderCardsPoints();
    points += getResourcePoints();
    points += productionLine.getPoints();
    points += popeLine.getPoints();
    return points;
  }

  public boolean sellLeaderCard(int cardCode){
    if(boardLeaderCards.containsKey(cardCode)){
      boardLeaderCards.remove(cardCode);
      return this.popeLine.addFaith();
    }
    return false;
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

  public HashMap<Resource, Integer> getResources(){
    return resourceManager.getResources();
  }

  public HashMap<Row, HashMap<Resource, Integer>> getResourcesFromWareHouse(){
    return resourceManager.getEachResourceFromWareHouse();
  }

  public HashMap<Resource, Integer> getResourcesFromChest(){
    return resourceManager.getResourcesFromChest();
  }

  public boolean canSwapLineFromWareHouse(Row one, Row two){
    return resourceManager.canSwapLine(one, two);
  }

  public void forceSwapLineFromWareHouse(Row one, Row two){
    resourceManager.forceSwapLine(one, two);
  }

  private int getResourcePoints(){
    return resourceManager.getResourcePoints();
  }

  public int getNumberOfResources(){
    return resourceManager.getNumberOfResource();
  }

  //METHOD TO ACCESS THE POPELINE

  public boolean addPopeFaith(){
    return popeLine.addFaith();
  }

  public boolean checkPopeFaith(){
    return popeLine.checkPopeFaith();
  }

  private int getFaithTrackPoints(){
    return popeLine.getPoints();
  }

  public int getFaith(){
    return popeLine.getFaith();
  }

  public boolean[] getFaithSteps(){
    return popeLine.getFaithSteps();
  }


  public boolean[] getFaithChecks(){
    return popeLine.getFaithChecks();
  }

  public boolean addLorenzoFaith(){
    try{
      return ((PopeLineSingle) popeLine).addLorenzoFaith();
    }catch(ClassCastException e){
      e.printStackTrace();
    }
    return false;
  }

  public boolean doubleAddLorenzoFaith(){
    try{
      return ((PopeLineSingle) popeLine).doubleAddLorenzoFaith();
    }catch (ClassCastException e){
      e.printStackTrace();
    }
    return false;
  }

  public int getLorenzoFaith(){
    try{
      return ((PopeLineSingle) popeLine).getLorenzoFaith();
    }catch(ClassCastException e){
      e.printStackTrace();
    }
    return -1;
  }

  public HashMap<Resource, Integer> getInputOfConfiguration(ArrayList<CreationCard> cardsToProduceWith){
    HashMap<Resource, Integer> inputOfConfiguration = new HashMap<>();
    cardsToProduceWith.forEach((t) -> {
      t.getInput().forEach((key,value) ->{
        inputOfConfiguration.merge(key,value,Integer::sum);
      });
    });
    return inputOfConfiguration;
  }

  public HashMap<Resource, Integer> getOutputOfConfiguration(ArrayList<CreationCard> cardsToProduceWith){
    HashMap<Resource, Integer> outputOfConfiguration = new HashMap<>();
    cardsToProduceWith.forEach((t) -> {
      t.getOutput().forEach((key,value) ->{
        outputOfConfiguration.merge(key,value,Integer::sum);
      });
    });
    return outputOfConfiguration;
  }

  //METHOD TO GET MARBLE

  public ArrayList<MarbleOption> getMarbleRow(int row){
    return marbleMarket.getRow(row);
  }

  public ArrayList<MarbleOption> getMarbleColumn(int column){
    return marbleMarket.getColumn(column);
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

  public ArrayList<Integer> getActiveLeaderCards() {
    return new ArrayList<>(activeLeaderCards.keySet());
  }

  public ArrayList<Integer> getLeaderCards(){
    return new ArrayList<>(this.boardLeaderCards.keySet());
  }

  public JSONObject getStatus(){
    JSONObject status = new JSONObject();
    JSONArray leadercards = new JSONArray();
    boardLeaderCards.forEach((key,value)->{
      JSONObject leadercard = new JSONObject();
      leadercard.put("card",key);
    });
    JSONArray activeleadercards = new JSONArray();
    activeLeaderCards.forEach((key, value)->{
      JSONObject leadercard = new JSONObject();
      leadercard.put("card",key);
    });
    status.put("resource_manager",resourceManager.getStatus());
    status.put("popeline",popeLine.getStatus());
    status.put("production_line",productionLine.getStatus());
    status.put("player_id",player.getID());
    status.put("player_name",player.getName());
    status.put("leader_cards",leadercards);
    status.put("active_leader_cards",activeleadercards);
    return status;
  }

  public void setStatus(JSONObject status){}


  public boolean isStartingResource() {
    return startingResource;
  }

  public void setStartingResource(boolean startingResource) {
    this.startingResource = startingResource;
  }

  public void updateChecks() {
    popeLine.updateChecks();
  }
}

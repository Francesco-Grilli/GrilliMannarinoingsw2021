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
   * show the card inside the production line
   * @return an hashmap with the position of the creation card
   */
  public HashMap<Integer, CreationCard> showCardInProductionLine(){
    return productionLine.getCards();
  }

  /**
   * return all cards into the card market
   * @return an hashmap with the all card into card market
   */
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

  /**
   * check if can buy a creation card
   * @param card the creation card that want to buy
   * @return a boolean equals true if you can buy that card
   */
  private Boolean canBuyCard(CreationCard card){
    return resourceManager.canRemove(card.getPrice());
  }

  /**
   * return a card from market
   * @param card the card you want to get
   * @return the creation card
   */
  public CreationCard getCardFromMarket(CreationCard card){
    if(canBuyCard(card)) {
      resourceManager.remove(card.getPrice());
      return cardMarket.buyCard(card.getFaction(), card.getCardLevel());
    }
    return null;
  }

  /**
   * check if the production line can produce with the configuration passed
   * @param cards the configuration of the cards
   * @return a boolean equals true if you can produce
   */
  public boolean canProduceWithConfiguration(ArrayList<CreationCard> cards){
    return resourceManager.canRemove(getInputOfConfiguration(cards));
  }

  /**
   * produce the resources using the card passed
   * @param cards the configuration of cards to produce
   * @return true if configuration was correct
   */
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

  /**
   * get the number of card bought
   * @return an integer as the number of cards bought
   */
  public int getNumberOfCards(){
    return productionLine.getNumberOfCards();
  }

  /**
   * return the marbles inside the marble market
   * @return a 2d array of the marble inside marble market
   */
  public Marble[][] getMarblesFromMarket(){
    return this.marbleMarket.getMarbleBoard();
  }

  /**
   * return the marble out of the market
   * @return a Marble enum of the marble out
   */
  public Marble getMarbleOut(){
    return this.marbleMarket.getMarbleOut();
  }

  /**
   * return the marble from the marble market selection
   * @param i an integer representing the position of the row to select
   * @return an array of marble option of the marble selected
   */
  public ArrayList<MarbleOption> getRow(int i){
    return this.marbleMarket.getRow(i);
  }

  /**
   * return the marble from the marble market selection
   * @param i an integer representing the position of the column to select
   * @return an array of marble option of the marble selected
   */
  public ArrayList<MarbleOption> getColumn(int i){
    return this.marbleMarket.getColumn(i);
  }

  /**
   * set the leadercards inside board after selected by the player
   * @param leaderCards the leadercard to add, selected by the player
   */
  public void setLeaderCards(ArrayList<LeaderCard> leaderCards){
    leaderCards.forEach(t-> this.boardLeaderCards.put(t.getCardCode(),t));
  }

  /**
   * used to activate the leadercard inside the board
   * @param cardCode of the leadercard to activate
   * @return true if the leadercard was correctly activated
   */
  public boolean activateLeaderCard(int cardCode){
    if(boardLeaderCards.containsKey(cardCode)){
      if(canActivateLeaderCard(boardLeaderCards.get(cardCode))) {
        boardLeaderCards.get(cardCode).execute(this);
        activeLeaderCards.put(cardCode, boardLeaderCards.get(cardCode));
        boardLeaderCards.remove(cardCode);
        return true;
      }
    }
    return false;
  }

  /**
   * return the leadercard inside board
   * @param cardCode the code of the leadercard to return
   * @return the leadercard you want to get
   */
  public LeaderCard getLeaderCard(int cardCode){
    if(activeLeaderCards.containsKey(cardCode))
      return activeLeaderCards.get(cardCode);
    else
      return null;
  }

  /**
   * check if youc an activate the leadercard
   * @param leaderCard the leadercard to activate
   * @return true if the leadercard was correctly activated
   */
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

  /**
   * return the creation from the cardCode parameter
   * @param cardCode the card code of the card to get
   * @return the creation card
   */
  public CreationCard getCardFromCode(int cardCode){
    return cardMarket.getCardFromCode(cardCode);
  }

  /**
   * check if can add a new card inside the production line
   * @param pos the position you want to set the creation card
   * @param card the card you want to set
   * @return true if the card was correctly placed
   */
  public boolean canAddCard(int pos, CreationCard card){
    return productionLine.canAddCard(pos, card);
  }

  /**
   * add the card to the production line
   * @param pos the position you want to set the creation card
   * @param card the card you want to set
   * @return true if the card was correctly placed
   */
  public boolean addCard(int pos, CreationCard card){
    if(canAddCard(pos,card)){
      return productionLine.addCard(pos,card);
    }
    return false;
  }

  private int getLeaderCardsPoints(){
    return this.activeLeaderCards.values().stream().map(l -> l.getValue()).reduce(0, Integer::sum);
  }

  /**
   * return all points of the leadercard, cards, resources inside board
   * @return ad integer representing the number of points inside board
   */
  public int getPoints(){
    int points = this.getLeaderCardsPoints();
    points += getResourcePoints();
    points += productionLine.getPoints();
    points += popeLine.getPoints();
    return points;
  }

  /**
   * sell a leadercard inside board
   * @param cardCode the card code of the leader card you want to sell
   * @return true if the leader card was correctly sold
   */
  public boolean sellLeaderCard(int cardCode){
    if(boardLeaderCards.containsKey(cardCode)){
      boardLeaderCards.remove(cardCode);
      return this.popeLine.addFaith();
    }
    return false;
  }


  /**
   * check if can set the resources from market
   * @param line the line you want to set the resources
   * @param resource the resource you want to set
   * @param value the number of resource to set
   * @return true if can set the resources inside market
   */
  public boolean canSetResourcesFromMarket(Row line, Resource resource, Integer value){
    return resourceManager.canSetResourcesFromMarket(line, resource, value);
  }

  /**
   * set the resources inside market
   * @param line the line you want to set the resources
   * @param resource the resource you want to set
   * @param value the number of resource to set
   * @return an hashmap with the remaining resource
   */
  public HashMap<Resource, Integer> setResourcesFromMarket(Row line, Resource resource, Integer value){
    return resourceManager.setResourcesFromMarket(line, resource, value);
  }

  /**
   * set the resources produce by production line inside the chest
   * @param resources the resources you want to add
   */
  public void setResourcesFromProduction(HashMap<Resource, Integer> resources){
    resourceManager.setResourcesFromProduction(resources);
  }

  /**
   * return all resources inside resource manager
   * @return an hashmap with all resources inside resource manager
   */
  public HashMap<Resource, Integer> getResources(){
    return resourceManager.getResources();
  }

  /**
   * return all resources inside warehouse
   * @return an hashmap with all resources inside warehouse
   */
  public HashMap<Row, HashMap<Resource, Integer>> getResourcesFromWareHouse(){
    return resourceManager.getEachResourceFromWareHouse();
  }

  /**
   * return all resources inside chest
   * @return an hashmap with all resources inside chest
   */
  public HashMap<Resource, Integer> getResourcesFromChest(){
    return resourceManager.getResourcesFromChest();
  }

  /**
   * check if can swap line inside warehouse
   * @param one the first line to reverse
   * @param two the second line to reverse
   * @return true if can swap line between row
   */
  public boolean canSwapLineFromWareHouse(Row one, Row two){
    return resourceManager.canSwapLine(one, two);
  }

  /**
   * force the swapping of the line into warehouse even if some resources will be lost
   * @param one the first line to reverse
   * @param two the second line to reverse
   */
  public void forceSwapLineFromWareHouse(Row one, Row two){
    resourceManager.forceSwapLine(one, two);
  }

  private int getResourcePoints(){
    return resourceManager.getResourcePoints();
  }

  /**
   * return the number of resources inside resource manager
   * @return an integer with the number of resources
   */
  public int getNumberOfResources(){
    return resourceManager.getNumberOfResource();
  }

  /**
   * add one pope faith to the player
   * @return true if while adding a pope faith the player has activated a pope's favor
   */
  public boolean addPopeFaith(){
    return popeLine.addFaith();
  }

  /**
   * check if the player has activate or discharged a pope's favor
   * @return true if the player has activated the favor
   */
  public boolean checkPopeFaith(){
    return popeLine.checkPopeFaith();
  }

  private int getFaithTrackPoints(){
    return popeLine.getPoints();
  }

  /**
   * get the faith of the player
   * @return an integer with the faith of the player
   */
  public int getFaith(){
    return popeLine.getFaith();
  }

  /**
   * get the favor activated or not by the player
   * @return an array with the favor
   */
  public boolean[] getFaithSteps(){
    return popeLine.getFaithSteps();
  }

  public boolean[] getFaithChecks(){
    return popeLine.getFaithChecks();
  }

  /**
   * add to lorenzo one point of faith
   * @return true if lorenzo has activated a pope's favor
   */
  public boolean addLorenzoFaith(){
    try{
      return ((PopeLineSingle) popeLine).addLorenzoFaith();
    }catch(ClassCastException e){
      e.printStackTrace();
    }
    return false;
  }

  /**
   * add two point of faith to lorenzo
   * @return true if lorenzo as activated a pope's favor
   */
  public boolean doubleAddLorenzoFaith(){
    try{
      return ((PopeLineSingle) popeLine).doubleAddLorenzoFaith();
    }catch (ClassCastException e){
      e.printStackTrace();
    }
    return false;
  }

  /**
   * get the faith of lorenzo
   * @return an integer with the faith of lorenzo
   */
  public int getLorenzoFaith(){
    try{
      return ((PopeLineSingle) popeLine).getLorenzoFaith();
    }catch(ClassCastException e){
      e.printStackTrace();
    }
    return -1;
  }

  /**
   * get the resource in input to produce
   * @param cardsToProduceWith the cards used to produce new resources
   * @return an hashmap with the resources needed to produce
   */
  public HashMap<Resource, Integer> getInputOfConfiguration(ArrayList<CreationCard> cardsToProduceWith){
    HashMap<Resource, Integer> inputOfConfiguration = new HashMap<>();
    cardsToProduceWith.forEach((t) -> {
      t.getInput().forEach((key,value) ->{
        inputOfConfiguration.merge(key,value,Integer::sum);
      });
    });
    return inputOfConfiguration;
  }

  /**
   * get the resources produces
   * @param cardsToProduceWith the cards used to produce new resources
   * @return an hashmap with the resources produced by the production line
   */
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

  /**
   * return the selected row of the marble market
   * @param row the selected row of marble market
   * @return an array list of marble to place
   */
  public ArrayList<MarbleOption> getMarbleRow(int row){
    return marbleMarket.getRow(row);
  }

  /**
   * return the selected column of the marble market
   * @param column the selected column of marble market
   * @return an array list of marble to place
   */
  public ArrayList<MarbleOption> getMarbleColumn(int column){
    return marbleMarket.getColumn(column);
  }



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

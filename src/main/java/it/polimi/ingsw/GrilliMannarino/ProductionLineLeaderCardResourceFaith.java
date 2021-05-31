package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductionLineLeaderCardResourceFaith extends ProductionLineLeaderCard implements ProductionLineBoardInterface{

  public ProductionLineLeaderCardResourceFaith(HashMap<Resource, Integer> resourcePrice, HashMap<Faction, HashMap<Integer, Integer>> cardPrice, Resource definedResource, int points,int cardCode) {
    super(resourcePrice, cardPrice, definedResource, points,cardCode);
  }

  /**
   * adds the CreationCard to the specified production slot
   * @param pos the production slot
   * @param card the card to add
   * @return the boolean representing if the adding procedure ended correctly
   */
  @Override
  public boolean addCard(int pos, CreationCard card) {
    return getProductionLine().addCard(pos,card);
  }

  /**
   * checks if the CreationCard can be added to the specified production slot
   * @param pos the production slot
   * @param card the card to add
   * @return the boolean representing if the card can be added
   */
  @Override
  public boolean canAddCard(int pos, CreationCard card) {
    return getProductionLine().canAddCard(pos, card);
  }

  /**
   * returns the Hashmap containing the relation Slot-CreationCard represented in the productionLine
   * adding the creationCard added by the leaderCard
   * @return is the Hashmap
   */
  @Override
  public HashMap<Integer, CreationCard> getCards() {
    HashMap<Integer, CreationCard> temp = getProductionLine().getCards();
    if(temp.get(getProductionLine().getNextFreeSlot()) == null){
      HashMap<Resource,Integer> inputOfLC = new HashMap<>(), outputOfLC = new HashMap<>();
      inputOfLC.put(getDefinedResource(),1);
      outputOfLC.put(Resource.UNKNOWN,1);
      outputOfLC.put(Resource.FAITH,1);
      temp.put(getProductionLine().getNextFreeSlot(),new CreationCard(0,1,this.getPoints(),Faction.NONE,new HashMap<>(),inputOfLC, outputOfLC));
    }
    return temp;
  }

  /**
   * returns the internal number of the next slot to add cards to
   * eg. a productionLine with 3 production slot will return "4" standing for the fourth production slot
   * @return the next free slot
   */
  @Override
  public int getNextFreeSlot() {
    return getProductionLine().getNextFreeSlot()+1;
  }

  /**
   * returns an ArrayList containing all creation card possessed by the production Line enceforth by the
   * player, this collection can be operated upon without modifying the production line
   * @return the Arraylist of cards
   */
  @Override
  public ArrayList<CreationCard> allUsedCards() {
    return getProductionLine().allUsedCards();
  }

  /**
   * returns the points that are due to the possession of CreationCards
   * @return is the points
   */
  @Override
  public int getPoints() {
    return getProductionLine().getPoints();
  }

  @Override
  public JSONObject getStatus() {
    return getProductionLine().getStatus();
  }

  public void setStatus(JSONObject status){
    getProductionLine().setStatus(status);
  }

  /**
   * returns the number of cards currently possessed by the player
   * @return is the number of cards
   */
  @Override
  public int getNumberOfCards() {
    return getProductionLine().getNumberOfCards();
  }

  @Override
  public void execute(Board board) {
    this.setProductionLine(board.getProductionLine());
    board.setProductionLine(this);
  }
}

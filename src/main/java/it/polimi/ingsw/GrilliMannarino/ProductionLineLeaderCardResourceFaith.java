package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductionLineLeaderCardResourceFaith extends ProductionLineLeaderCard implements ProductionLineBoardInterface{

  public ProductionLineLeaderCardResourceFaith(HashMap<Resource, Integer> resourcePrice, HashMap<Faction, HashMap<Integer, Integer>> cardPrice, Resource definedResource, int points,int cardCode) {
    super(resourcePrice, cardPrice, definedResource, points,cardCode);
  }

  @Override
  public boolean addCard(int pos, CreationCard card) {
    return getProductionLine().addCard(pos,card);
  }

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

  @Override
  public int getNextFreeSlot() {
    return getProductionLine().getNextFreeSlot()+1;
  }

  @Override
  public void execute(Board board) {
    this.setProductionLine(board.getProductionLine());
    board.setProductionLine(this);
  }
}

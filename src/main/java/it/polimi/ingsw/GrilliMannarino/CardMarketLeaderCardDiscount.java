package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.HashMap;

public class CardMarketLeaderCardDiscount extends CardMarketLeaderCard implements CardMarketBoardInterface{

  public CardMarketLeaderCardDiscount(HashMap<Resource, Integer> resourcePrice, HashMap<Faction, HashMap<Integer, Integer>> cardPrice, Resource definedResource, int points, int cardCode) {
    super(resourcePrice, cardPrice, definedResource, points,cardCode);
  }

  /**
   * returns the cards of the market applying the said discount to their prices
   * @return the discount
   */
  @Override
  public HashMap<Faction, HashMap<Integer, CreationCard>> getCards() {
    HashMap<Faction, HashMap<Integer, CreationCard>> temp = getCardMarket().getCards();
    for(Faction faction:temp.keySet()){
      for(Integer level:temp.get(faction).keySet()){
        CreationCard card = temp.get(faction).get(level).getCard();
        HashMap<Resource,Integer> priceOfCard = card.getPrice();
        if(priceOfCard.containsKey(getDefinedResource())){
          if(priceOfCard.get(getDefinedResource()) != null){
            priceOfCard.put(getDefinedResource(),priceOfCard.get(getDefinedResource())-1);
          }
        }
        temp.get(faction).put(level, new CreationCard(card.getCardCode(),card.getCardLevel(),card.getValue(),card.getFaction(),priceOfCard,card.getInput(),card.getOutput()));
      }
    }
    return temp;
  }

  /**
   * buys card that is found at the coordinates faction-level on the grid
   * @param faction the faction coordinate
   * @param level the level coordinate
   * @return the card
   */
  @Override
  public CreationCard buyCard(Faction faction, Integer level) {
    return this.cardMarket.buyCard(faction,level);
  }

  /**
   * returns the card corresponding to the specified card code
   * @param cardCode the card code of the card
   * @return the card
   */
  @Override
  public CreationCard getCardFromCode(int cardCode) {
    return getCardMarket().getCardFromCode(cardCode);
  }

  @Override
  public void execute(Board board) {
    setCardMarket(board.getCardMarket());
    board.setCardMarket(this);
  }
}

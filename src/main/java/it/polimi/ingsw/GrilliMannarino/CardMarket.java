package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.HashMap;
import java.util.Stack;

public class CardMarket {
  HashMap<Integer, CreationCard> cardsInGame;
  HashMap<Faction, HashMap<Integer, CardStack>> layedCards;
  HashMap<Integer, CreationCard> playedCards;

  public CardMarket(){
    cardsInGame = new HashMap<>();
    //to implement metod to read cards from file

    //to implement shuffle method for the stacks
  }

  public HashMap<Faction, HashMap<Integer, CreationCard>> getCards(){
    HashMap<Faction, HashMap<Integer, CreationCard>> cardCodes = new HashMap<>();
    for(Faction fac: layedCards.keySet()){
      if(layedCards.get(fac) != null) {
        cardCodes.put(fac, new HashMap<>());
        for (Integer lev : layedCards.get(fac).keySet()) {
          if (layedCards.get(fac).get(lev) != null){
            cardCodes.get(fac).put(lev, layedCards.get(fac).get(lev).getCard());
          }
        }
      }
    }
    return cardCodes;
  }

  public CreationCard buyCard(Faction faction, Integer level){
    if(!layedCards.get(faction).get(level).emptyStack()){
      CreationCard cardToReturn = layedCards.get(faction).get(level).popCard();
      playedCards.put(cardToReturn.getCardCode(),cardToReturn);
      return cardToReturn;
    }else{
       return null;
    }
  }

}

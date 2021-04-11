package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.HashMap;
import java.util.Stack;

public class CardMarket {
  HashMap<Integer, CreationCard> cardsInGame;
  HashMap<Faction, HashMap<Integer, Stack<CreationCard>>> layedCards;
  HashMap<Integer, CreationCard> playedCards;

  public CardMarket(){
    cardsInGame = new HashMap<>();
    //to implement metod to read cards from file

    //to implement shuffle method for the stacks
  }

  public HashMap<Faction, HashMap<Integer, Integer>> getCardView(){
    HashMap<Faction, HashMap<Integer, Integer>> cardCodes = new HashMap<>();
    for(Faction fac: layedCards.keySet()){
      if(layedCards.get(fac) != null) {
        cardCodes.put(fac, new HashMap<>());
        for (Integer lev : layedCards.get(fac).keySet()) {
          if (layedCards.get(fac).get(lev) != null){
            cardCodes.get(fac).put(lev, layedCards.get(fac).get(lev).peek().getCardCode());
          }
        }
      }
    }
    return cardCodes;
  }

  public boolean canCard(Faction faction, Integer level, HashMap<Resource, Integer> resources){
    if(layedCards.get(faction) != null){
      if(layedCards.get(faction).get(level) != null){
        if(layedCards.get(faction).get(level).isEmpty()){
          return false;
        }else{
          return layedCards.get(faction).get(level).peek().canBuyCard(resources);
        }
      }
    }
    return false;
  }

  public CreationCard buyCard(Faction faction, Integer level, HashMap<Resource, Integer> resources){
    if (canCard(faction,level,resources)){
      CreationCard cardToReturn = layedCards.get(faction).get(level).pop();
      cardToReturn.buyCard(resources);
      playedCards.put(cardToReturn.getCardCode(),cardToReturn);
      return cardToReturn;
    }else{
       return null;
    }
  }

}

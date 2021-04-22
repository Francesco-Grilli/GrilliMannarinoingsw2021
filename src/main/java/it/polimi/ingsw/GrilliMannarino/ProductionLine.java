package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.HashMap;

public class ProductionLine implements ProductionLineBoardInterface{

    private final HashMap<Integer, CreationCardGroup> activeCards;
    private final int maxProductionCards;

    public ProductionLine(int numberOfActiveCards){
        this.maxProductionCards = numberOfActiveCards;
        activeCards = new HashMap<>();
        for(int i = 1; i<=numberOfActiveCards;i++){
            activeCards.put(i,new CardStack());
        }
    }

    public ProductionLine(){
        this(3);
    }

    public boolean addCard(int pos, CreationCard card){
        int position = ((pos % this.maxProductionCards)+1);
        if(activeCards.get(position).canAdd(card)){
            return activeCards.get(position).addCard(card);
        }else{
            return false;
        }
    }

    public HashMap<Integer, CreationCard> getCards(){
        HashMap<Integer, CreationCard> cards = new HashMap<>();
        for(Integer key: activeCards.keySet()){
            cards.put(key, activeCards.get(key).getCard());
        }
        HashMap<Resource,Integer> voidInput = new HashMap<>(), voidOutput = new HashMap<>();
        voidInput.put(Resource.UNKNOWN,2);
        voidOutput.put(Resource.UNKNOWN,1);
        cards.put(0, new CreationCard(0,0,0,Faction.NONE,new HashMap<>(),voidInput,voidOutput));
        return cards;
    }

    public int getNextFreeSlot() {
        return maxProductionCards+1;
    }

}

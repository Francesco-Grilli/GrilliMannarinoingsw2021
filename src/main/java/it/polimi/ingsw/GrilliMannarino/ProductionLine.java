package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductionLine {

    private final ArrayList<CreationCardGroup> activeCards;
    private final int maxProductionCards;

    public ProductionLine(int numberOfActiveCards){
        this.maxProductionCards = numberOfActiveCards;
        activeCards = new ArrayList<>();
        for(int i = 0; i<numberOfActiveCards;i++){
            activeCards.add(new CardStack());
        }
    }

    public ProductionLine(){
        this(3);
    }

    public boolean addCard(int pos, CreationCard card){
        int position = ((pos % this.maxProductionCards));
        if(activeCards.get(position).canAdd(card.getCardLevel())){
            return activeCards.get(position).addCard(card);
        }else{
            return false;
        }
    }

    public ArrayList<CreationCard> getCards(){
        ArrayList<CreationCard> cards = new ArrayList<>();
        activeCards.forEach((t) -> {
            if(t.getCard() != null){
                cards.add(t.getCard());
            }/*else{
                method to add blank card
            }*/
        });
        HashMap<Resource,Integer> voidInput = new HashMap<>(), voidOutput = new HashMap<>();
        voidInput.put(Resource.UNKNOWN,2);
        voidOutput.put(Resource.UNKNOWN,1);
        cards.add(new CreationCard(0,0,0,Faction.NONE,new HashMap<>(),voidInput,voidOutput));
        return cards;
    }

}

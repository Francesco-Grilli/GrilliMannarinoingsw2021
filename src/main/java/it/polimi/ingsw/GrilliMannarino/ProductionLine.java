package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductionLine {

    private ArrayList<CreationCardGroup> activeCards;
    private int maxProductionCards;

    public ProductionLine(int numberOfActiveCards){
        this.maxProductionCards = numberOfActiveCards;
        activeCards = new ArrayList<>();
        HashMap<Resource, Integer> unknownInput = new HashMap<>();
        unknownInput.put(Resource.UNKNOWN, 2);
        HashMap<Resource, Integer> unknownOutput = new HashMap<>();
        unknownOutput.put(Resource.UNKNOWN, 1);
        activeCards.add(0, new CreationCard(0,0, 0, Faction.NONE, new HashMap<>(), unknownInput, unknownOutput));
        for(int i = 0; i<numberOfActiveCards;i++){
            activeCards.add(1,new CardStack());
        }
    }

    public ProductionLine(){
        this(3);
    }

    public boolean addCard(int pos, CreationCard card){
        int position = ((pos % this.maxProductionCards)+1);
        if(activeCards.get(position).canAdd(card.getCardLevel())){
            return activeCards.get(position).addCard(card);
        }else{
            return false;
        }
    }

    public boolean canProduce(ArrayList<CreationCardGroup> cardsToProduceWith, HashMap<Resource, Integer> resources){
        HashMap<Resource, Integer> instancedResources = new HashMap<>(resources);
        return cardsToProduceWith.stream().map((t) -> {
            if (t.canProduce(instancedResources)) {
                t.produce(instancedResources, new HashMap<>());
                return true;
            }else{
                return false;
            }
        }).reduce(true, (a,b) -> a && b);
    }

    public ArrayList<Integer> showCards(){
        ArrayList<Integer> cardCodes = new ArrayList<>();
        activeCards.forEach((t) -> cardCodes.add(t.getCardCode()));
        return cardCodes;
    }

    public HashMap<Resource,Integer> produce(ArrayList<CreationCardGroup> cardsToProduceWith, HashMap<Resource, Integer> resources){
        if (canProduce(cardsToProduceWith,resources)) {
            HashMap<Resource, Integer> resourcesToReturn = new HashMap<>(resources);
            cardsToProduceWith.forEach((t) -> t.produce(resources, resourcesToReturn));
            return resourcesToReturn;
        }
        return null;
    }

    public HashMap<Resource,Integer> getProductionPrice(ArrayList<CreationCardGroup> cardsToProduceWith){
        HashMap<Resource,Integer> requiredResources = new HashMap<>();
        for(CreationCardGroup card:cardsToProduceWith){
            HashMap<Resource,Integer> cardResources = new HashMap<>(card.getInput());
            for(Resource key:cardResources.keySet()){
                requiredResources.put(key, (requiredResources.get(key)!=null? requiredResources.get(key):0)+cardResources.get(key));
            }
        }
        return requiredResources;
    }

}

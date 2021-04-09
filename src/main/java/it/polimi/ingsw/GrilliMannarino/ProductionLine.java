package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductionLine {

    private ArrayList<CreationCardGroup> activeCards;

    public ProductionLine(){
        activeCards = new ArrayList<>();
        HashMap<Resource, Integer> unknownInput = new HashMap<>();
        unknownInput.put(Resource.UNKNOWN, 2);
        HashMap<Resource, Integer> unknownOutput = new HashMap<>();
        unknownOutput.put(Resource.UNKNOWN, 1);
        activeCards.add(new CreationCard(0,0, 0, Faction.NONE, new HashMap<>(), unknownInput, unknownOutput));
    }

    public boolean addCard(int position, CreationCard card){
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
        HashMap<Resource, Integer> resourcesToReturn = new HashMap<>(resources);
        cardsToProduceWith.forEach((t) -> t.produce(resources,resourcesToReturn));
        return resourcesToReturn;
    }

}

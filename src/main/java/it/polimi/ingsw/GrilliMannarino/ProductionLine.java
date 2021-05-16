package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductionLine implements ProductionLineBoardInterface{

    private HashMap<Integer,CreationCard> productionCards;
    private final HashMap<Integer, CreationCardGroup> activeCards;
    private final int maxProductionSlots;

    public ProductionLine(int numberOfActiveCards){
        this.maxProductionSlots = numberOfActiveCards;
        activeCards = new HashMap<>();
        productionCards = new HashMap<>();
        for(int i = 1; i<=numberOfActiveCards;i++){
            activeCards.put(i,new CardStack());
        }
    }

    public ProductionLine(){
        this(3);
    }

    public boolean addCard(int pos, CreationCard card){
        int position = ((pos % this.maxProductionSlots)+1);
        if(canAddCard(pos,card)){
            productionCards.put(card.getCardCode(), card);
            return activeCards.get(position).addCard(card);
        }else{
            return false;
        }
    }

    @Override
    public boolean canAddCard(int pos, CreationCard card) {
        int position = ((pos % this.maxProductionSlots)+1);
        return activeCards.get(position).canAdd(card);
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
        return maxProductionSlots +1;
    }

    public ArrayList<CreationCard> allUsedCards(){
        ArrayList<CreationCard> cardsToReturn = new ArrayList<>();
        this.productionCards.values().forEach(t-> cardsToReturn.add(t.getCard()));
        return cardsToReturn;
    }

    public int getPoints() {
        return allUsedCards().stream().map(CreationCard::getValue).reduce(0, Integer::sum);
    }

    public JSONObject getStatus(){
        JSONObject status = new JSONObject();
        JSONArray stacks = new JSONArray();
        this.activeCards.forEach((key,value)->{
            JSONObject stack = new JSONObject();
            stack.put("stack_number",key.toString());
            stack.put("stack_value", value.getStatus());
            stacks.add(stack);
        });
        status.put("stacks", stacks);
        status.put("number_of_slots",maxProductionSlots);
        return status;
    }

    public void setStatus(JSONObject status, GetCarder carder){
        JSONArray stacks = (JSONArray) status.get("stacks");
        stacks.forEach((value)->{
            JSONObject stack = (JSONObject) value;
            int i = Integer.parseInt((String) stack.get("stack_number"));
            activeCards.get(i).setStatus((JSONObject) stack.get("stack_value"));
        });
    }

    public void setStatus(JSONObject status){}

    public int getNumberOfCards(){
        return productionCards.size();
    }
}

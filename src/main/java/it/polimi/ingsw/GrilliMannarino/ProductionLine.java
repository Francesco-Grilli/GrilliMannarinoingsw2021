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

    /**
     * adds the CreationCard to the specified production slot
     * @param pos the production slot
     * @param card the card to add
     * @return the boolean representing if the adding procedure ended correctly
     */
    public boolean addCard(int pos, CreationCard card){
        int position;
        if(pos<1){
            position = 1;
        }
        else if(pos >3){
            position = 3;
        }
        else{
            position = pos;
        }
        if(canAddCard(pos,card)){
            productionCards.put(card.getCardCode(), card);
            return activeCards.get(position).addCard(card);
        }else{
            return false;
        }
    }

    /**
     * checks if the CreationCard can be added to the specified production slot
     * @param pos the production slot
     * @param card the card to add
     * @return the boolean representing if the card can be added
     */
    @Override
    public boolean canAddCard(int pos, CreationCard card) {
        int position;
        if(pos<1){
            position = 1;
        }
        else if(pos >3){
            position = 3;
        }
        else{
            position = pos;
        }
        return activeCards.get(position).canAdd(card);
    }

    /**
     * returns the Hashmap containing the relation Slot-CreationCard represented in the productionLine
     * @return is the Hashmap
     */
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

    /**
     * returns the internal number of the next slot to add cards to
     * eg. a productionLine with 3 production slot will return "4" standing for the fourth production slot
     * @return the next free slot
     */
    public int getNextFreeSlot() {
        return maxProductionSlots +1;
    }

    /**
     * returns an ArrayList containing all creation card possessed by the production Line enceforth by the
     * player, this collection can be operated upon without modifying the production line
     * @return the Arraylist of cards
     */
    public ArrayList<CreationCard> allUsedCards(){
        ArrayList<CreationCard> cardsToReturn = new ArrayList<>();
        this.productionCards.values().forEach(t-> cardsToReturn.add(t.getCard()));
        return cardsToReturn;
    }

    /**
     * returns the points that are due to the possession of CreationCards
     * @return is the points
     */
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

    /**
     * returns the number of cards currently possessed by the player
     * @return is the number of cards
     */
    public int getNumberOfCards(){
        return productionCards.size();
    }
}

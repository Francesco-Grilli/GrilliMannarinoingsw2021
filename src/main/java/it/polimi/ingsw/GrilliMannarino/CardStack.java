package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

public class CardStack implements CreationCardGroup{

    private ArrayList<CreationCard> cards;
    private int size;

    public CardStack(){
        this.cards = new ArrayList<>();
        this.size = -1;
    }

    /**
     * adds forcefully a card to the stack, not caring about the level, to the top
     * @param card is the CreationCard to add to the stack
     */
    public void pushCard(CreationCard card){
        this.cards.add(card);
        size++;
    }

    /**
     *  retrieve forcefully the top card from the stack
     *  @return is the CreationCard returned not present in the stack anymore
     */
    public CreationCard popCard(){
        if(emptyStack() ||this.size==-1){
            return null;
        }
        this.size--;
        return this.cards.remove(size+1);

    }

    /**
     * checks if there are any more cards to the market
     * @return is the answer to the question "is the stack empity"
     */
    public boolean emptyStack(){
        return cards.isEmpty();
    }

    /**
     * return a copy of the card on top of the stack
     * @return is the copy of the card
     */
    public CreationCard getCard(){
        if(this.cards.isEmpty()||this.size==-1){
            return null;
        }
        return cards.get(size).getCard();
    }

    /**
     * shuffles the cards inside the stack
     */
    public void shuffle(){
        Collections.shuffle(cards);
    }


    @Override
    public JSONObject getStatus() {
        JSONObject status = new JSONObject();
        JSONArray cards = new JSONArray();
        for(int i =0; i<=size;i++){
            JSONObject card = new JSONObject();
            card.put("position", i );
            card.put("card_code", this.cards.get(i).getCardCode());
            cards.add(card);
        }
        status.put("cards", cards);
        status.put("top_card", getCardCode());
        return status;
    }

    @Override
    public void setStatus(JSONObject status) {

    }

    /**
     * returns the value in points of the card on top of the stack
     * @return is the int value of the card
     */
    public int getValue() {
        if(this.cards.isEmpty()||this.size==-1){
            return 0;
        }
        return cards.get(size).getValue();
    }

    /**
     * returns the faction of the card on top of the stack
     * @return is the faction
     */
    public Faction getFaction() {
        if(this.cards.isEmpty()||this.size==-1){
            return Faction.NONE;
        }
        return cards.get(size).getFaction();
    }

    /**
     * returns the level of the card on top of the stack
     * @return is the level
     */
    public int getCardLevel() {
        if(this.cards.isEmpty()||this.size==-1){
            return 0;
        }
        return cards.get(size).getCardLevel();
    }

    /**
     * returns the in-game card code of the card on top of the stack
     * @return is the card code
     */
    public int getCardCode() {
        if(this.cards.isEmpty()||this.size==-1){
            return 0;
        }
        return cards.get(size).getCardCode();
    }

    /**
     * returns an HashMap containing the relation Resource-Amount that defines the price of the card on top of
     * the stack
     * @return is said HashMap
     */
    public HashMap<Resource, Integer> getPrice() {
        if(this.cards.isEmpty()||this.size==-1){
            return new HashMap<>();
        }
        return cards.get(size).getPrice();
    }

    /**
     * returns an HashMap containing the relation Resource-Amount that defines the input required by the card on
     * top of the stack to produce resources
     * @return is said HashMap
     */
    public HashMap<Resource, Integer> getInput() {
        if(this.cards.isEmpty()||this.size==-1){
            return new HashMap<>();
        }
        return cards.get(size).getInput();
    }

    /**
     * returns an HashMap containing the relation Resource-Amount that defines the output produced by the card on
     * top of the stack
     * @return is said HashMap
     */
    public HashMap<Resource, Integer> getOutput() {
        if(this.cards.isEmpty()||this.size==-1){
            return new HashMap<>();
        }
        return cards.get(size).getOutput();
    }

    /**
     * checks if the card can be added on top of the stack regarding the ingame rules (the level of the card added must
     * be the level above of the current top card)
     * @param card is the CreationCard to be added
     * @return is the answer
     */
    public boolean canAdd(CreationCard card){
        return this.getCardLevel() == card.getCardLevel()-1;
    }

    /**
     * adds the card on top of the stack if it can be added regarding the ingame rules (the level of the card added must
     * be the level above of the current top card)
     * @param card is the CreationCard that will be added
     * @return is the boolean stating if the card has been added
     */
    public boolean addCard(CreationCard card) {
        if(this.canAdd(card)){
            this.pushCard(card);
            return true;
        }
        return false;
    }
}

package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class CardStack implements CreationCardGroup{

    private ArrayList<CreationCard> cards;
    private int size;

    public CardStack(){
        this.cards = new ArrayList<>();
        this.size = -1;
    }

    public void pushCard(CreationCard card){
        this.cards.add(card);
        size++;
    }

    public CreationCard popCard(){
        if(emptyStack() ||this.size==-1){
            return null;
        }
        this.size--;
        return this.cards.remove(size+1);

    }

    public boolean emptyStack(){
        return cards.isEmpty();
    }

    public CreationCard getCard(){
        if(this.cards.isEmpty()||this.size==-1){
            return null;
        }
        return cards.get(size).getCard();
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

    public int getValue() {
        if(this.cards.isEmpty()||this.size==-1){
            return 0;
        }
        return cards.get(size).getValue();
    }

    public Faction getFaction() {
        if(this.cards.isEmpty()||this.size==-1){
            return Faction.NONE;
        }
        return cards.get(size).getFaction();
    }

    public int getCardLevel() {
        if(this.cards.isEmpty()||this.size==-1){
            return 0;
        }
        return cards.get(size).getCardLevel();
    }

    public int getCardCode() {
        if(this.cards.isEmpty()||this.size==-1){
            return 0;
        }
        return cards.get(size).getCardCode();
    }

    public HashMap<Resource, Integer> getPrice() {
        if(this.cards.isEmpty()||this.size==-1){
            return new HashMap<>();
        }
        return cards.get(size).getPrice();
    }

    public HashMap<Resource, Integer> getInput() {
        if(this.cards.isEmpty()||this.size==-1){
            return new HashMap<>();
        }
        return cards.get(size).getInput();
    }

    public HashMap<Resource, Integer> getOutput() {
        if(this.cards.isEmpty()||this.size==-1){
            return new HashMap<>();
        }
        return cards.get(size).getOutput();
    }

    public boolean canAdd(CreationCard card){
        return this.getCardLevel() == card.getCardLevel()-1;
    }

    public boolean addCard(CreationCard card) {
        if(this.canAdd(card)){
            this.pushCard(card);
            return true;
        }
        return false;
    }
}

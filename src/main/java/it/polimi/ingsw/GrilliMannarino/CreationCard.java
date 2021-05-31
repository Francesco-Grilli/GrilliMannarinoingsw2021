package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class CreationCard implements CreationCardGroup {
    private final int cardCode;
    private final int cardLevel;
    private final Faction faction;
    private final int value;
    private final HashMap<Resource, Integer> price;
    private final HashMap<Resource, Integer> input;
    private final HashMap<Resource, Integer> output;

    public CreationCard(int cardCode, int cardLevel, int value, Faction faction, HashMap<Resource, Integer> price,
        HashMap<Resource, Integer> input, HashMap<Resource, Integer> output){
        this.cardCode = cardCode;
        this.cardLevel = cardLevel;
        this.faction = faction;
        this.value = value;
        this.price = price;
        this.output = output;
        this.input = input;
    }

    /**
     * returns the point value of the current instance of CreationCard
     * @return is the said point value
     */
    public int getValue() {
        return value;
    }

    /**
     * returns the faction of the current instance of CreationCard
     * @return is the faction
     */
    public Faction getFaction() {
        return faction;
    }

    /**
     * returns the level of the current instance of CreationCard
     * @return is the level
     */
    public int getCardLevel() {
        return cardLevel;
    }

    /**
     * returns the in game cardcode of the current instance of CreationCard
     * @return is the card code
     */
    public int getCardCode() {return cardCode; }

    /**
     * returns the HashMap containing the relation Resource-Amount that defines the price of the current instance
     * of CreationCard
     * @return is the price
     */
    public HashMap<Resource, Integer> getPrice() {
        if(this.price == null){
            return null;
        }
        return new HashMap<>(this.price);
    }

    /**
     * returns the HashMap containing the relation Resource-Amount that defines the input required by the execution of
     * the production of the current instance of CreationCard
     * @return is the price
     */
    public HashMap<Resource, Integer> getInput() {
        if(this.input == null){
            return null;
        }
        return new HashMap<>(this.input);
    }

    /**
     * returns the HashMap containing the relation Resource-Amount that defines the output produced the current instance of CreationCard
     * @return is the price
     */
    public HashMap<Resource, Integer> getOutput() {
        if(this.output == null){
            return null;
        }
        return new HashMap<>(this.output);
    }

    public boolean canAdd(CreationCard card){return false;}
    public boolean addCard(CreationCard card){return false;}

    /**
     * returns a copy of the current instance of CreationCard, this instance can be safely modified applying discounts
     * or editing input and output of production
     * @return is the copy of "this"
     */
    public CreationCard getCard() {return new CreationCard(this.getCardCode(),this.getCardLevel(),this.getValue(),this.getFaction(),this.getPrice(),this.getInput(),this.getOutput());}

    @Override
    public JSONObject getStatus() {
        JSONObject status = new JSONObject();
        JSONArray cards = new JSONArray();
        JSONObject card = new JSONObject();
        card.put("position", 0);
        card.put("card_code", cardCode);
        cards.add(card);
        status.put("cards", cards);
        status.put("top_card", cardCode);
        return status;
    }

    @Override
    public void setStatus(JSONObject status) {

    }
}

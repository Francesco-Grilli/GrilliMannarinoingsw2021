package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

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

    public int getValue() {
        return value;
    }
    public Faction getFaction() {
        return faction;
    }
    public int getCardLevel() {
        return cardLevel;
    }
    public int getCardCode() {return cardCode; }
    public HashMap<Resource, Integer> getPrice() {
        return new HashMap<>(this.price);
    }
    public HashMap<Resource, Integer> getInput() {
        return new HashMap<>(this.input);
    }
    public HashMap<Resource, Integer> getOutput() {
        return new HashMap<>(this.output);
    }
    public boolean canAdd(int cardLevel){return false;}
    public boolean addCard(CreationCard card){return false;}
    public CreationCard getCard() {return new CreationCard(this.getCardCode(),this.getCardLevel(),this.getValue(),this.getFaction(),this.getPrice(),this.getInput(),this.getOutput());}
}

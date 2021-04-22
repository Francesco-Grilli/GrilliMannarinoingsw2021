package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.HashMap;
import java.util.Stack;

public class CardStack implements CreationCardGroup{

    private Stack<CreationCard> cards;

    public CardStack(){
        this.cards = new Stack<>();
    }

    public void pushCard(CreationCard card){
        this.cards.push(card);
    }

    public CreationCard popCard(){
        if(emptyStack()){
            return null;
        }
        return this.cards.pop();
    }

    public boolean emptyStack(){
        return cards.empty();
    }

    public CreationCard getCard(){
        if(this.cards.empty()){
            return null;
        }
        return cards.peek().getCard();
    }

    public int getValue() {
        if(this.cards.empty()){
            return 0;
        }
        return cards.peek().getValue();
    }

    public Faction getFaction() {
        if(this.cards.empty()){
            return Faction.NONE;
        }
        return cards.peek().getFaction();
    }

    public int getCardLevel() {
        if(this.cards.empty()){
            return 0;
        }
        return cards.peek().getCardLevel();
    }

    public int getCardCode() {
        if(this.cards.empty()){
            return 0;
        }
        return cards.peek().getCardCode();
    }

    public HashMap<Resource, Integer> getPrice() {
        if(this.cards.empty()){
            return null;
        }
        return cards.peek().getPrice();
    }

    public HashMap<Resource, Integer> getInput() {
        if(this.cards.empty()){
            return null;
        }
        return cards.peek().getInput();
    }

    public HashMap<Resource, Integer> getOutput() {
        if(this.cards.empty()){
            return null;
        }
        return cards.peek().getOutput();
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

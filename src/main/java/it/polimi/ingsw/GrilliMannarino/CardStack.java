package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.HashMap;
import java.util.Stack;

public class CardStack implements CreationCardGroup{

    private Stack<CreationCard> cards;

    public void putCard(CreationCard card){
        cards.push(card);
    }

    public CreationCard getCard(){
        return  cards.pop();
    }

    public boolean canProduce(HashMap<Resource, Integer> resources){
        if(this.cards.empty()){
            return false;
        }
        return cards.peek().canProduce(resources);
    }

    public HashMap<Resource, Integer> produce(HashMap<Resource, Integer> resourcesIn, HashMap<Resource, Integer> resourcesOut){
        if(this.cards.empty()){
            return resourcesOut;
        }
        return cards.peek().produce(resourcesIn, resourcesOut);
    }

    public boolean canBuyCard(HashMap<Resource, Integer> resources){
        if(this.cards.empty()){
            return false;
        }
        return cards.peek().canBuyCard(resources);
    }

    public HashMap<Resource, Integer> buyCard(HashMap<Resource, Integer> resourcesIn){
        if(this.cards.empty()){
            return resourcesIn;
        }
        return cards.peek().buyCard(resourcesIn);
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

    public HashMap<Resource, Integer> getInput() {
        if(this.cards.empty()){
            return new HashMap<>();
        }
        return cards.peek().getInput();
    }

    public HashMap<Resource, Integer> getOutput() {
        if(this.cards.empty()){
            return new HashMap<>();
        }
        return cards.peek().getOutput();
    }

    public boolean canAdd(int cardLevel){
        return this.getCardLevel() < cardLevel;
    }

    public boolean addCard(CreationCard card) {
        if(this.canAdd(card.getCardLevel())){
            this.putCard(card);
            return true;
        }
        return false;
    }
}

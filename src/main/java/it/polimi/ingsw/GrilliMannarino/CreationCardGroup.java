package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.HashMap;

public interface CreationCardGroup {
    boolean canProduce(HashMap<Resource, Integer> resources);
    HashMap<Resource, Integer> produce(HashMap<Resource, Integer> resourcesIn, HashMap<Resource, Integer> resourcesOut);
    boolean canBuyCard(HashMap<Resource, Integer> resources);
    HashMap<Resource, Integer> buyCard(HashMap<Resource, Integer> resourcesIn);
    int getValue();
    Faction getFaction();
    int getCardLevel();
    int getCardCode();
    HashMap<Resource, Integer> getPrice();
    HashMap<Resource, Integer> getInput();
    HashMap<Resource, Integer> getOutput();
    boolean canAdd(int cardLevel);
    boolean addCard(CreationCard card);
}

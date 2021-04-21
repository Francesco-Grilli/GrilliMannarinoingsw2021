package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.HashMap;

public interface CreationCardGroup {
    int getValue();
    Faction getFaction();
    int getCardLevel();
    int getCardCode();
    HashMap<Resource, Integer> getPrice();
    HashMap<Resource, Integer> getInput();
    HashMap<Resource, Integer> getOutput();
    boolean canAdd(CreationCard card);
    boolean addCard(CreationCard card);
    CreationCard getCard();
}

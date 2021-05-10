package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;

import java.util.ArrayList;
import java.util.HashMap;

public interface ClientViewInterface {


    void activateLeaderCard(Integer activatedCard);

    void sellingLeaderCard(Integer cardCode);

    void viewError(String errorMessage);

    void showMarbleMarket(Marble[][] marbleList, Marble marbleOut);

    void selectMarble(ArrayList<ArrayList<String>> returnedMarble);

    void addedResource(String marbleType, String insertRow);

    void showProductionCard(HashMap<Integer, Boolean> buyableCard);

    void setCardIntoProductionLine(Integer selectedCard, Integer positionCard);

    void isYourTurn();

    void checkPopeLine(boolean favorActive, Integer checkPosition, Integer faithPosition);

    void updateFaith(Integer faithPosition);

    void startGame();

    void updateResources(HashMap<Resource, Integer> chestResources, HashMap<Row, HashMap<Resource, Integer>> wareHouseResources);

    void moveApplied();

    void looseResource();

    void createdNewGame(String messageString, Integer gameId);

    void enteredNewGame(String messageString, Integer gameId);
}

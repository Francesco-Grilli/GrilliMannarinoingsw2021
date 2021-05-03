package it.polimi.ingsw.GrilliMannarino;

import java.util.ArrayList;
import java.util.HashMap;

public interface ClientViewInterface {


    void activateLeaderCard(Integer activatedCard);

    void sellingLeaderCard(Integer cardCode);

    void sendError(String errorMessage);

    void showMarbleMarket(String[][] marbleList, String marbleOut);

    void selectMarble(ArrayList<ArrayList<String>> returnedMarble);

    void addedResource(String marbleType, String insertRow);

    void showProductionCard(HashMap<Integer, Boolean> buyableCard);

    void setCardIntoProductionLine(Integer selectedCard, Integer positionCard);

    void isYourTurn();

    void checkPopeLine(boolean favorActive, Integer checkPosition, Integer faithPosition);

    void updateFaith(Integer faithPosition);
}

package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;

import java.util.*;

public abstract class ClientView {

    protected String nickname;
    protected Integer playerId;
    protected Integer gameId;
    protected Map<Row, HashMap<Resource, Integer>> warehouse = new TreeMap<>((o1, o2) -> {
        if(o1.getValue()>o2.getValue()){
            return 1;
        }
        else
            return 0;
    });
    protected HashMap<Marble, Integer> chest;
    protected Integer faith;
    protected boolean[] faithMark = new boolean[3];
    protected final int[] faithValue = {2, 3, 4};

    abstract void activateLeaderCard(Integer activatedCard);

    abstract void sellingLeaderCard(Integer cardCode);

    abstract void viewError(String errorMessage);

    abstract void showMarbleMarket(Marble[][] marbleList, Marble marbleOut);

    abstract void selectMarble(ArrayList<ArrayList<Marble>> returnedMarble);

    abstract void addedResource(Resource resourceType, Row insertRow, ArrayList<Resource> remainingResource, boolean addResourceCorrect);

    abstract void showProductionMarket(HashMap<Integer, Boolean> buyableCard);

    abstract void setCardIntoProductionLine(Integer selectedCard, Integer positionCard);

    abstract void isYourTurn();

    abstract void checkPopeLine(boolean favorActive, Integer checkPosition, Integer faithPosition);

    abstract void updateFaith(Integer faithPosition);

    abstract void startGame();

    abstract void updateResources(HashMap<Resource, Integer> chestResources, HashMap<Row, HashMap<Resource, Integer>> wareHouseResources);

    abstract void moveApplied();

    abstract void looseResource();

    abstract void createdNewGame(String messageString, Integer gameId);

    abstract void enteredNewGame(String messageString, Integer gameId);

    public void setWarehouse(Map<Row, HashMap<Resource, Integer>> warehouse) {
        this.warehouse = warehouse;
    }

    public abstract void finishedNormalAction();

    public abstract void showLeaderCard(ArrayList<Integer> cards);

    public abstract void showProductionCard(ArrayList<Integer> productionCard);
}

package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;
import it.polimi.ingsw.GrilliMannarino.Message.LoginMessage;

import java.util.*;

public abstract class ClientView {

    protected ClientController controller;
    protected String nickname;
    protected Integer playerId;
    protected Integer gameId;
    protected Map<Row, HashMap<Resource, Integer>> warehouse = new HashMap<>();
    protected HashMap<Resource, Integer> chest;
    protected Integer faith;
    protected boolean[] faithMark = new boolean[3];
    protected Integer lorenzoFaith;
    protected boolean[] lorenzoFaithMark = new boolean[3];
    protected final int[] faithValue = {2, 3, 4};
    protected HashMap<Integer, Integer> productionLine = new HashMap<>();
    protected boolean normalAction = false;
    protected boolean leaderAction = false;

    abstract void viewError(String errorMessage);

    abstract void showMarbleMarket(ArrayList<ArrayList<Marble>> marbleList, Marble marbleOut);

    abstract void selectMarble(ArrayList<ArrayList<Marble>> returnedMarble);

    abstract void addedResource(Resource resourceType, Row insertRow, ArrayList<Resource> remainingResource, boolean addResourceCorrect);

    abstract void showCardMarket(HashMap<Faction, HashMap<Integer, Map.Entry<Integer, Boolean>>> buyableCard);

    abstract void setCardIntoProductionLine(Integer selectedCard, Integer positionCard, HashMap<Integer, Integer> cardInProductionline);

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

    public abstract void finishedNormalAction(String message);

    public abstract void showLeaderCard(ArrayList<Integer> cards);

    public abstract void showProductionCard(HashMap<Integer, Integer> productionCard);

    public abstract void setUpInformation();

    public abstract void getUpInformation(LoginMessage message);

    public abstract void setUpGame();

    public void setController(ClientController controller) {
        this.controller = controller;
    }

    public abstract void printInformation(String message);

    abstract void finishedLeaderAction(String s);

    public abstract void selectMarbleStarting(ArrayList<ArrayList<Marble>> marblesToSelect);

    public abstract void placeResourceStarting(ArrayList<Resource> resourcesLeft);

    public abstract void checkReturnedResource(ArrayList<Resource> returnedResource);

    public abstract void selectLeaderCard(ArrayList<Integer> cards);

    public abstract void updateFaithSingle(Integer faithPosition, Integer lorenzoFaith);

    public abstract void checkPopeLineSingle(boolean favorActive, Integer checkPosition, Integer faithPosition, Integer lorenzoFaith, boolean lorenzoFavorActive);

    public abstract void endGame(HashMap<String, Integer> playerRanking);

    public String getNickname() {
        return nickname;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public boolean isNormalAction() {
        return normalAction;
    }

    public boolean isLeaderAction() {
        return leaderAction;
    }

    public abstract void resolveUnknown(HashMap<Integer, HashMap<Resource, Integer>> inputCard, HashMap<Integer, HashMap<Resource, Integer>> outputCard, ArrayList<Integer> selectedCard);
}

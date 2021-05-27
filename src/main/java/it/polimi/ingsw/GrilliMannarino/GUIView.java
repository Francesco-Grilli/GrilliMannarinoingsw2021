package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GUIControllers.*;
import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;
import it.polimi.ingsw.GrilliMannarino.Message.LoginMessage;
import it.polimi.ingsw.GrilliMannarino.Message.MessageInterface;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GUIView extends ClientView{

  private GUIControllerInterface screenHandler;

  @Override
  void viewError(String errorMessage) {

  }

  @Override
  void showMarbleMarket(ArrayList<ArrayList<Marble>> marbleList, Marble marbleOut) {

  }

  @Override
  void selectMarble(ArrayList<ArrayList<Marble>> returnedMarble) {
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("resources", that);
        ResourcesController rc = (ResourcesController) screenHandler.getActiveController();
        rc.setViewMarble(true);
        rc.setReturnedMarble(returnedMarble);
      }
    });

  }

  @Override
  void addedResource(Resource resourceType, Row insertRow, ArrayList<Resource> remainingResource, boolean addResourceCorrect) {

  }

  @Override
  void showCardMarket(HashMap<Faction, HashMap<Integer, Map.Entry<Integer, Boolean>>> buyableCard) {
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("cardMarket", that);
        CardMarketController cdm = (CardMarketController) screenHandler.getActiveController();
        cdm.setCardSlots(buyableCard);
      }
    });
  }

  @Override
  void setCardIntoProductionLine(Integer selectedCard, Integer positionCard, HashMap<Integer, Integer> cardInProductionline) {

  }

  @Override
  void isYourTurn() {
    GUIView that = this;
    this.normalAction = true;
    this.leaderAction = true;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("action", that);
      }
    });
  }

  @Override
  void checkPopeLine(boolean favorActive, Integer checkPosition, Integer faithPosition) {

  }

  @Override
  void updateFaith(Integer faithPosition) {

  }

  @Override
  void startGame() {
    System.out.println("game has started");
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("board", that);
      }
    });
  }

  @Override
  void updateResources(HashMap<Resource, Integer> chestResources, HashMap<Row, HashMap<Resource, Integer>> wareHouseResources) {

  }

  @Override
  void moveApplied() {

  }

  @Override
  void looseResource() {

  }

  @Override
  void createdNewGame(String messageString, Integer gameId) {
    this.gameId = gameId;
    GUIView gv = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("createGame", gv);
        CreateGameController gameController = (CreateGameController) screenHandler.getActiveController();
        gameController.showStartButton();
        screenHandler.errorMessage("GamdeID: " + gameId.toString(), messageString);
      }
    });
  }

  @Override
  void enteredNewGame(String messageString, Integer gameId) {
    this.gameId = gameId;
    GUIView gv = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("createGame", gv);
        CreateGameController gameController = (CreateGameController) screenHandler.getActiveController();
        gameController.disableAllButton();
        screenHandler.errorMessage("GamdID: " + gameId.toString(), messageString + " Wait for the game to start");
      }
    });
  }

  @Override
  public void finishedNormalAction(String message) {

  }

  @Override
  public void showLeaderCard(ArrayList<Integer> cards) {
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("cardDisplay", that);
        CardDisplayController cdc = (CardDisplayController) screenHandler.getActiveController();
        cdc.setLeaderCard(true);
        cdc.setCards(cards);
      }
    });
  }

  @Override
  public void showProductionCard(HashMap<Integer, Integer> productionCard) {
    GUIView that = this;
    this.productionLine = new HashMap<>(productionCard);
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("cardDisplay", that);
        CardDisplayController cdc = (CardDisplayController) screenHandler.getActiveController();
        cdc.setCards(productionCard);
      }
    });
  }

  @Override
  public void setUpInformation() {
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("account", that);
      }
    });
  }

  public void sendInformationToServer(String nickname, String password, boolean newAccount){
    controller.sendInformationToServer(nickname, password, newAccount);
  }

  @Override
  public void getUpInformation(LoginMessage message) {
    if(message.isCorrectLogin()){
      this.playerId = message.getPlayerId();
      this.nickname = message.getNickname();
      //do nothing
    }
    else{
      Platform.runLater(new Runnable() {
        @Override
        public void run() {
          screenHandler.errorMessage("Login Error", message.getMessage());
        }
      });
    }
  }

  @Override
  public void setUpGame() {
    System.out.println("Arrivato a settare il gioco");
    GUIView gv = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("createGame", gv);
      }
    });
  }

  @Override
  public void printInformation(String message) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.errorMessage("Info", message);
      }
    });
  }

  @Override
  public void finishedLeaderAction(String s) {

  }

  @Override
  public void selectMarbleStarting(ArrayList<ArrayList<Marble>> marblesToSelect) {

  }

  @Override
  public void placeResourceStarting(ArrayList<Resource> resourcesLeft) {

  }

  @Override
  public void checkReturnedResource(ArrayList<Resource> returnedResource) {

  }

  @Override
  public void selectLeaderCard(ArrayList<Integer> cards) {
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("cardDisplay", that);
        CardDisplayController cdc = (CardDisplayController) screenHandler.getActiveController();
        cdc.setLeaderCard(true);
        cdc.setSelectStartingLeaderCard(true);
        cdc.setCards(cards);
      }
    });
  }

  @Override
  public void updateFaithSingle(Integer faithPosition, Integer lorenzoFaith) {

  }

  @Override
  public void checkPopeLineSingle(boolean favorActive, Integer checkPosition, Integer faithPosition, Integer lorenzoFaith, boolean lorenzoFavorActive) {

  }

  @Override
  public void endGame(HashMap<Integer, Map.Entry<String, Integer>> playerRanking) {

  }

  @Override
  public void resolveUnknown(HashMap<Integer, HashMap<Resource, Integer>> inputCard, HashMap<Integer, HashMap<Resource, Integer>> outputCard, ArrayList<Integer> selectedCard) {
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("resolveUnknown", that);
        ResolveUnknownController ruc = (ResolveUnknownController) screenHandler.getActiveController();
        ruc.setInputCard(inputCard);
        ruc.setOutputCard(outputCard);
        ruc.setSelectedCard(selectedCard);
        ruc.resolve();
      }
    });
  }

  public void moveResource(){

  }

  public void setScreenHandler(GUIControllerInterface controller){
    this.screenHandler = controller;
  }


  public void sendMessageToServer(MessageInterface message){
    controller.sendMessageToServer(message);
  }
}

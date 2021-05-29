package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GUIControllers.*;
import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;
import it.polimi.ingsw.GrilliMannarino.Message.LoginMessage;
import it.polimi.ingsw.GrilliMannarino.Message.MessageInterface;
import it.polimi.ingsw.GrilliMannarino.Message.TurnMessage;
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
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("marbleMarket", that);
        MarbleMarketController mmc = (MarbleMarketController) screenHandler.getActiveController();
        mmc.setMarbles(marbleList);
        mmc.setMarbleOut(marbleOut);
      }
    });
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
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("warehouse", that);
        WarehouseController wc = (WarehouseController) screenHandler.getActiveController();
        if(addResourceCorrect)
          wc.errorMessage("Correct!", resourceType.toString() + " has been added correctly");
        else
          wc.errorMessage("Incorrect!", resourceType.toString() + " was not place!");
        wc.setResourcesToPlace(remainingResource);
        wc.setWareHouse(that.warehouse);
      }
    });
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
        cdm.setProductionLine(that.productionLine);
      }
    });
  }

  @Override
  void setCardIntoProductionLine(Integer selectedCard, Integer positionCard, HashMap<Integer, Integer> cardInProductionline) {
    this.productionLine = new HashMap<>(cardInProductionline);
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        if(that.numberOfPlayer==1){
          screenHandler.setScene("boardSingle", that);
          BoardSingleController bsc = (BoardSingleController) screenHandler.getActiveController();
          bsc.setLorenzoSteps(that.lorenzoFaith);
          bsc.setPopelineSteps(that.faith);
          bsc.setProductionCards(that.productionLine);
          bsc.setChest(that.chest);
          bsc.showFaithChecks(that.faithMark);
        }
        else {
          screenHandler.setScene("board", that);
          BoardController bc = (BoardController) screenHandler.getActiveController();
          bc.setPopelineSteps(that.faith);
          bc.setProductionCards(that.productionLine);
          bc.setChest(that.chest);
          bc.showFaithChecks(that.faithMark);
        }
      }
    });
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
    if(favorActive)
      this.faithMark[checkPosition]=true;
    this.faith = faithPosition;
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("board", that);
        BoardController bc = (BoardController) screenHandler.getActiveController();
        bc.setPopelineSteps(that.faith);
        bc.setProductionCards(that.productionLine);
        bc.setChest(that.chest);
        bc.showFaithChecks(that.faithMark);
      }
    });
    sleepLong(3);
  }

  @Override
  void updateFaith(Integer faithPosition) {
    this.faith = faithPosition;
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("board", that);
        BoardController bc = (BoardController) screenHandler.getActiveController();
        bc.setPopelineSteps(that.faith);
        bc.setProductionCards(that.productionLine);
        bc.setChest(that.chest);
        bc.showFaithChecks(that.faithMark);
      }
    });
    sleepLong(3);
  }

  @Override
  void startGame(Integer numberPlayer) {
    this.numberOfPlayer = numberPlayer;
    System.out.println("game has started");
    this.warehouse.put(Row.FIRST, new HashMap<>());
    this.warehouse.put(Row.SECOND, new HashMap<>());
    this.warehouse.put(Row.THIRD, new HashMap<>());
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        if(that.numberOfPlayer==1){
          screenHandler.setScene("boardSingle", that);
          BoardSingleController bsc = (BoardSingleController) screenHandler.getActiveController();
          bsc.setLorenzoSteps(that.lorenzoFaith);
          bsc.setPopelineSteps(that.faith);
          bsc.setProductionCards(that.productionLine);
          bsc.setChest(that.chest);
          bsc.showFaithChecks(that.faithMark);
        }
        else {
          screenHandler.setScene("board", that);
          BoardController bc = (BoardController) screenHandler.getActiveController();
          bc.setPopelineSteps(that.faith);
          bc.setProductionCards(that.productionLine);
          bc.setChest(that.chest);
          bc.showFaithChecks(that.faithMark);
        }
      }
    });
    sleepLong(3);
  }

  @Override
  void updateResources(HashMap<Resource, Integer> chestResources, HashMap<Row, HashMap<Resource, Integer>> wareHouseResources) {
    this.chest = chestResources;
    this.warehouse = wareHouseResources;
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        if(that.numberOfPlayer==1) {
          screenHandler.setScene("boardSingle", that);
          BoardSingleController bsc = (BoardSingleController) screenHandler.getActiveController();
          bsc.setPopelineSteps(that.faith);
          bsc.setLorenzoSteps(that.lorenzoFaith);
          bsc.setProductionCards(that.productionLine);
          bsc.setChest(that.chest);
          bsc.showFaithChecks(that.faithMark);
        }
        else{
          screenHandler.setScene("board", that);
          BoardController bc = (BoardController) screenHandler.getActiveController();
          bc.setPopelineSteps(that.faith);
          bc.setProductionCards(that.productionLine);
          bc.setChest(that.chest);
          bc.showFaithChecks(that.faithMark);
        }
      }
    });
    sleepLong(3);
  }

  @Override
  void moveApplied() {
    //TODO need to swap lines and print resource correctly
  }

  @Override
  void looseResource() {
    //TODO need to swap lines and print message of attention
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
    this.normalAction = false;
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("action", that);
        SmallController sm = screenHandler.getActiveController();
        sm.errorMessage("Info", message);
      }
    });
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
    this.leaderAction = false;
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("action", that);
        SmallController sm = screenHandler.getActiveController();
        sm.errorMessage("Info", s);
      }
    });
  }

  @Override
  public void selectMarbleStarting(ArrayList<ArrayList<Marble>> marblesToSelect) {
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("resources", that);
        ResourcesController rc = (ResourcesController) screenHandler.getActiveController();
        rc.setSelectStartingResource(true);
        rc.setReturnedMarble(marblesToSelect);
      }
    });
  }

  @Override
  public void placeResourceStarting(ArrayList<Resource> resourcesLeft) {
    //TODO call fxml to set the resources into warehouse
  }

  @Override
  public void checkReturnedResource(ArrayList<Resource> returnedResource) {
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("warehouse", that);
        WarehouseController wc = (WarehouseController) screenHandler.getActiveController();
        wc.setResourcesToPlace(returnedResource);
        wc.setWareHouse(that.warehouse);
      }
    });
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
    this.faith = faithPosition;
    this.lorenzoFaith = lorenzoFaith;
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("boardSingle", that);
        BoardSingleController bsc = (BoardSingleController) screenHandler.getActiveController();
        bsc.setPopelineSteps(that.faith);
        bsc.setLorenzoSteps(that.lorenzoFaith);
        bsc.setProductionCards(that.productionLine);
        bsc.setChest(that.chest);
        bsc.showFaithChecks(that.faithMark);
      }
    });
    sleepLong(3);
  }

  @Override
  public void checkPopeLineSingle(boolean favorActive, Integer checkPosition, Integer faithPosition, Integer lorenzoFaith, boolean lorenzoFavorActive) {
    if(favorActive)
      this.faithMark[checkPosition] = true;
    this.faith = faithPosition;
    if(lorenzoFavorActive)
      this.lorenzoFaithMark[checkPosition] = true;
    this.lorenzoFaith = lorenzoFaith;
    GUIView that = this;

    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("boardSingle", that);
        BoardSingleController bsc = (BoardSingleController) screenHandler.getActiveController();
        bsc.setPopelineSteps(that.faith);
        bsc.setLorenzoSteps(that.lorenzoFaith);
        bsc.showFaithChecks(that.faithMark);
        bsc.setProductionCards(that.productionLine);
        bsc.setChest(that.chest);
      }
    });
    sleepLong(3);
  }

  @Override
  public void endGame(HashMap<Integer, Map.Entry<String, Integer>> playerRanking) {
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("endGame", that);
        EndGameController egc = (EndGameController) screenHandler.getActiveController();
        egc.setPlayerRanking(playerRanking);
      }
    });
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

  @Override
  public void looseResourceIntoMarbleMarket(ArrayList<Resource> returnedResource) {

  }

  @Override
  public void moveAppliedIntoMarbleMarket(ArrayList<Resource> returnedResource) {

  }

  public void moveResource(){

  }

  public void setScreenHandler(GUIControllerInterface controller){
    this.screenHandler = controller;
  }


  public void sendMessageToServer(MessageInterface message){
    controller.sendMessageToServer(message);
  }

  public void skipTurn(){
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        if(that.numberOfPlayer==1){
          screenHandler.setScene("boardSingle", that);
          BoardSingleController bsc = (BoardSingleController) screenHandler.getActiveController();
          bsc.setLorenzoSteps(that.lorenzoFaith);
          bsc.setPopelineSteps(that.faith);
          bsc.setProductionCards(that.productionLine);
          bsc.setChest(that.chest);
          bsc.showFaithChecks(that.faithMark);
        }
        else {
          screenHandler.setScene("board", that);
          BoardController bc = (BoardController) screenHandler.getActiveController();
          bc.setPopelineSteps(that.faith);
          bc.setProductionCards(that.productionLine);
          bc.setChest(that.chest);
          bc.showFaithChecks(that.faithMark);
        }
      }
    });
    controller.sendMessageToServer(new TurnMessage(this.gameId, this.playerId));
  }

  private void sleepLong(int second){
    try {
      Thread.sleep(second*1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void showPlaceResourceIntoWareHouse(ArrayList<Resource> resources){
    //TODO call fxml to place the resources into warehouse
  }
}

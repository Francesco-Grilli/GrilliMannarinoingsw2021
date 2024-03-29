package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GUIControllers.*;
import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;
import it.polimi.ingsw.GrilliMannarino.Message.LoginMessage;
import it.polimi.ingsw.GrilliMannarino.Message.MarbleMarketMessage;
import it.polimi.ingsw.GrilliMannarino.Message.MessageInterface;
import it.polimi.ingsw.GrilliMannarino.Message.TurnMessage;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GUIView extends ClientView{

  private final int TimeToBoard = 1;

  private GUIControllerInterface screenHandler;

  @Override
  void viewError(String errorMessage) {
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        SmallController sc = screenHandler.getActiveController();
        sc.errorMessage("Info", errorMessage);
      }
    });
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("action", that);
      }
    });
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
    if(remainingResource.isEmpty()){
      MarbleMarketMessage message = new MarbleMarketMessage(this.gameId, this.playerId);
      message.setReturnedResource(remainingResource);
      message.setDestroyRemaining(true);
      controller.sendMessageToServer(message);
    }
    else {
      Platform.runLater(new Runnable() {
        @Override
        public void run() {
          screenHandler.setScene("warehouse", that);
          WarehouseController wc = (WarehouseController) screenHandler.getActiveController();
          if (addResourceCorrect)
            wc.errorMessage("Correct!", resourceType.toString() + " has been added correctly");
          else
            wc.errorMessage("Incorrect!", resourceType.toString() + " was not place!");
          wc.setResourcesToPlace(remainingResource);
          wc.setWareHouse(that.warehouse);
        }
      });
    }
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
          bsc.setWareHouse(that.warehouse);
        }
        else {
          screenHandler.setScene("board", that);
          BoardController bc = (BoardController) screenHandler.getActiveController();
          bc.setPopelineSteps(that.faith);
          bc.setProductionCards(that.productionLine);
          bc.setChest(that.chest);
          bc.showFaithChecks(that.faithMark);
          bc.setWareHouse(that.warehouse);
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
        bc.setWareHouse(that.warehouse);
      }
    });
    sleepLong(that.TimeToBoard);
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
        bc.setWareHouse(that.warehouse);
      }
    });
    sleepLong(that.TimeToBoard);
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
          bsc.setWareHouse(that.warehouse);
        }
        else {
          screenHandler.setScene("board", that);
          BoardController bc = (BoardController) screenHandler.getActiveController();
          bc.setPopelineSteps(that.faith);
          bc.setProductionCards(that.productionLine);
          bc.setChest(that.chest);
          bc.showFaithChecks(that.faithMark);
          bc.setWareHouse(that.warehouse);
        }
      }
    });
    sleepLong(that.TimeToBoard);
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
          bsc.setWareHouse(that.warehouse);
        }
        else{
          screenHandler.setScene("board", that);
          BoardController bc = (BoardController) screenHandler.getActiveController();
          bc.setPopelineSteps(that.faith);
          bc.setProductionCards(that.productionLine);
          bc.setChest(that.chest);
          bc.showFaithChecks(that.faithMark);
          bc.setWareHouse(that.warehouse);
        }
      }
    });
    sleepLong(that.TimeToBoard);
  }

  @Override
  void moveApplied() {
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("action", that);
        SmallController sc = screenHandler.getActiveController();
        sc.errorMessage("Correct", "Resource has been moved correctly");
      }
    });
  }

  @Override
  void looseResource() {
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("action", that);
        SmallController sc = screenHandler.getActiveController();
      }
    });
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
    GUIView that = this;
    if(resourcesLeft.isEmpty()){
      new Thread(new Runnable() {
        @Override
        public void run() {
          controller.receiveMessageFromServer();
        }
      }).start();
    }
    else{
      Platform.runLater(new Runnable() {
        @Override
        public void run() {
          screenHandler.setScene("warehouse", that);
          WarehouseController wc = (WarehouseController) screenHandler.getActiveController();
          wc.setResourcesToPlace(resourcesLeft);
          wc.setStartingResource(true);
          wc.setWareHouse(that.warehouse);
        }
      });
    }

  }

  @Override
  public void checkReturnedResource(ArrayList<Resource> returnedResource) {
    GUIView that = this;
    if(returnedResource.isEmpty()){
      MarbleMarketMessage message = new MarbleMarketMessage(this.gameId, this.playerId);
      message.setReturnedResource(returnedResource);
      message.setDestroyRemaining(true);
      controller.sendMessageToServer(message);
    }
    else {
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
        bsc.setWareHouse(that.warehouse);
      }
    });
    sleepLong(that.TimeToBoard);
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
        bsc.setWareHouse(that.warehouse);
      }
    });
    sleepLong(that.TimeToBoard);
  }

  @Override
  public void endGame(HashMap<Integer, Map.Entry<String, Integer>> playerRanking, boolean win) {
    GUIView that = this;
    if(this.getNumberOfPlayer()>1) {
      Platform.runLater(new Runnable() {
        @Override
        public void run() {
          screenHandler.setScene("endGame", that);
          EndGameController egc = (EndGameController) screenHandler.getActiveController();
          egc.setPlayerRanking(playerRanking);
        }
      });
    }
    else{
      Platform.runLater(new Runnable() {
        @Override
        public void run() {
          screenHandler.setScene("victory", that);
          VictoryScreenController vsc = (VictoryScreenController) screenHandler.getActiveController();
          ArrayList<Integer> score = new ArrayList<>();
          playerRanking.forEach((pos, map) -> score.add(map.getValue()));
          vsc.setResult(win, score.get(0));
        }
      });
    }
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
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("warehouse", that);
        WarehouseController wc = (WarehouseController) screenHandler.getActiveController();
        wc.errorMessage("", "Some resource may be lost!");
        wc.setResourcesToPlace(returnedResource);
        wc.setWareHouse(that.warehouse);
      }
    });
  }

  @Override
  public void moveAppliedIntoMarbleMarket(ArrayList<Resource> returnedResource) {
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("warehouse", that);
        WarehouseController wc = (WarehouseController) screenHandler.getActiveController();
        wc.errorMessage("", "Warehouse moved correctly!");
        wc.setResourcesToPlace(returnedResource);
        wc.setWareHouse(that.warehouse);
      }
    });
  }

  @Override
  public void activateResourceLeaderCard(Integer cardCode, Resource res, Row row) {
    HashMap<Resource, Integer> add = new HashMap<>();
    add.put(res, 1);
    this.warehouse.put(row, add);
  }

  public void moveResource(){
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("warehouse", that);
        WarehouseController wc = (WarehouseController) screenHandler.getActiveController();
        wc.setJustSwitching(true);
        wc.setWareHouse(that.warehouse);
      }
    });
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
          bsc.setWareHouse(that.warehouse);
        }
        else {
          screenHandler.setScene("board", that);
          BoardController bc = (BoardController) screenHandler.getActiveController();
          bc.setPopelineSteps(that.faith);
          bc.setProductionCards(that.productionLine);
          bc.setChest(that.chest);
          bc.showFaithChecks(that.faithMark);
          bc.setWareHouse(that.warehouse);
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
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("warehouse", that);
        WarehouseController wc = (WarehouseController) screenHandler.getActiveController();
        wc.setResourcesToPlace(resources);
        wc.setStartingResource(true);
        wc.setWareHouse(that.warehouse);
      }
    });
  }

  public void setLogin(boolean newAccount){
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("account", that);
        AccountManagingController amc = (AccountManagingController) screenHandler.getActiveController();
        if(newAccount)
          amc.newAccountConfig();
        else
          amc.loginConfig();
      }
    });
  }

  public void backToActionSelect(){
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("action", that);
      }
    });
  }

  public void backToWelcome(){
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("welcome", that);
      }
    });
  }

  public void backToNewGame() {
    this.numberOfPlayer=0;
    this.warehouse=new HashMap<>();
    this.chest = new HashMap<>();
    this.faith=0;
    this.gameId =0;
    this.faithMark = new boolean[3];
    this.lorenzoFaith=0;
    this.lorenzoFaithMark = new boolean[3];
    this.productionLine = new HashMap<>();
    GUIView that = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("createGame", that);
      }
    });
  }
}

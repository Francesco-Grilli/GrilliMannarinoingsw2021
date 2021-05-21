package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GUIControllers.AccountManagingController;
import it.polimi.ingsw.GrilliMannarino.GUIControllers.BoardController;
import it.polimi.ingsw.GrilliMannarino.GUIControllers.GUIControllerInterface;
import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;
import it.polimi.ingsw.GrilliMannarino.Message.LoginMessage;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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

  }

  @Override
  void addedResource(Resource resourceType, Row insertRow, ArrayList<Resource> remainingResource, boolean addResourceCorrect) {

  }

  @Override
  void showCardMarket(HashMap<Integer, Boolean> buyableCard) {

  }

  @Override
  void setCardIntoProductionLine(Integer selectedCard, Integer positionCard, HashMap<Integer, Integer> cardInProductionline) {

  }

  @Override
  void isYourTurn() {

  }

  @Override
  void checkPopeLine(boolean favorActive, Integer checkPosition, Integer faithPosition) {

  }

  @Override
  void updateFaith(Integer faithPosition) {

  }

  @Override
  void startGame() {

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

  }

  @Override
  void enteredNewGame(String messageString, Integer gameId) {

  }

  @Override
  public void finishedNormalAction(String message) {

  }

  @Override
  public void showLeaderCard(ArrayList<Integer> cards) {

  }

  @Override
  public void showProductionCard(HashMap<Integer, Integer> productionCard) {

  }

  @Override
  public void setUpInformation() {
    GUIView con = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        screenHandler.setScene("account", con);
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
        screenHandler.setScene("board", gv);
      }
    });
  }

  @Override
  public void printInformation(String message) {

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

  }

  @Override
  public void updateFaithSingle(Integer faithPosition, Integer lorenzoFaith) {

  }

  @Override
  public void checkPopeLineSingle(boolean favorActive, Integer checkPosition, Integer faithPosition, Integer lorenzoFaith, boolean lorenzoFavorActive) {

  }

  @Override
  public void endGame(HashMap<String, Integer> playerRanking) {

  }

  public void setScreenHandler(GUIControllerInterface controller){
    this.screenHandler = controller;
  }

}

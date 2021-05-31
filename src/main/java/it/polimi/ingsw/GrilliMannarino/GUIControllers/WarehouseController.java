package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;
import it.polimi.ingsw.GrilliMannarino.MarbleMarket;
import it.polimi.ingsw.GrilliMannarino.Message.MarbleMarketMessage;
import it.polimi.ingsw.GrilliMannarino.Message.MoveResourceMessage;
import it.polimi.ingsw.GrilliMannarino.Message.StartingResourceMessage;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;

public class WarehouseController implements SmallController {
  public ImageView res1;
  public ImageView res2;
  public ImageView res3;
  public ImageView res4;
  public ImageView row1_1;
  public ImageView row2_1;
  public ImageView row2_2;
  public ImageView row3_1;
  public ImageView row3_2;
  public ImageView row3_3;
  public Label text;
  public ImageView leaderCard1;
  public ImageView leaderCard2;
  public AnchorPane shoot;
  public ImageView trashcan;
  public ImageView leaderCard2_1;
  public ImageView leaderCard2_2;
  public ImageView leaderCard1_2;
  public ImageView leaderCard1_1;
  public ImageView backArrow;
  private enum Status{
    SWAP,NOTHING,PLACE
  }
  private Status status = Status.NOTHING;
  private Resource resourceToPlace;
  private HashMap<Integer,Resource> resourcesToPlace = new HashMap<>();
  private HashMap<Integer, ImageView> resourceMap = new HashMap<>();
  private ArrayList<Resource> resourcesList;
  private Row selectedRow;
  private boolean startingResource = false;
  private boolean justSwitching = false;
  private GUIView view;

  @Override
  public void setView(GUIView view) {
    this.view = view;
    initializeMap();
  }

  private void initializeMap() {
    resourceMap.put(1, res1);
    resourceMap.put(2, res2);
    resourceMap.put(3, res3);
    resourceMap.put(4, res4);
    resourceMap.forEach((pos, img) -> img.setDisable(true));
    backArrow.setDisable(true);
    backArrow.setVisible(false);
  }

  @Override
  public void errorMessage(String header, String context) {
    text.setText(header + " " + context);
    fade(text);
  }

  private void fade(Label lbl){
    SequentialTransition sequentialTransition = new SequentialTransition();
    Transition t = new PauseTransition(Duration.millis(1500));
    FadeTransition fadeOut = new FadeTransition(Duration.millis(1500), lbl);
    fadeOut.setFromValue(1);
    fadeOut.setToValue(0);
    sequentialTransition.getChildren().addAll(t, fadeOut);
    sequentialTransition.play();
  }

  public void resourceClick(int i){
    if(status==Status.SWAP || resourceToPlace!=resourcesToPlace.get(i)) {
      removeGlow();
      resourceMap.get(i).setEffect(new Glow());
    }
    status = Status.PLACE;
    resourceToPlace = resourcesToPlace.get(i);
  }

  public void warehouseClick(Row r){
    if(status==Status.PLACE){
      removeGlow();
      if(startingResource){
        StartingResourceMessage message = new StartingResourceMessage(view.getGameId(), view.getPlayerId());
        message.setPlaceResource(true);
        message.setRowToPlace(r);
        message.setResourceToPlace(resourceToPlace);
        message.setResourcesLeft(resourcesList);
        status = Status.NOTHING;
        new Thread(new Runnable() {
          @Override
          public void run() {
            view.sendMessageToServer(message);
          }
        }).start();
      }
      else {
        MarbleMarketMessage message = new MarbleMarketMessage(view.getGameId(), view.getPlayerId());
        message.setAddedResource(true);
        message.setResourceType(resourceToPlace);
        message.setInsertRow(r);
        message.setReturnedResource(resourcesList);
        status = Status.NOTHING;
        new Thread(new Runnable() {
          @Override
          public void run() {
            view.sendMessageToServer(message);
          }
        }).start();
      }

    }else if(status == Status.SWAP){
      removeGlow();
      if(justSwitching){
        MoveResourceMessage moveMessage = new MoveResourceMessage(view.getGameId(), view.getPlayerId());
        moveMessage.setRowOne(selectedRow);
        moveMessage.setRowTwo(r);
        moveMessage.setForceSwap(true);
        new Thread(new Runnable() {
          @Override
          public void run() {
            view.sendMessageToServer(moveMessage);
          }
        }).start();
      }
      else {
        MarbleMarketMessage message = new MarbleMarketMessage(view.getGameId(), view.getPlayerId());
        message.setSwapRow(true);
        message.setRowOne(selectedRow);
        message.setRowTwo(r);
        message.setReturnedResource(resourcesList);
        message.setForceSwap(true);
        status = Status.NOTHING;
        new Thread(new Runnable() {
          @Override
          public void run() {
            view.sendMessageToServer(message);
          }
        }).start();
      }
    }else{
      status = Status.SWAP;
      selectedRow = r;
    }
  }

  public void resource1(){
    res1.setEffect(new Glow());
    resourceClick(1);}
  public void resource2(){
    res2.setEffect(new Glow());
    resourceClick(2);}
  public void resource3(){
    res3.setEffect(new Glow());
    resourceClick(3);}
  public void resource4(){
    res4.setEffect(new Glow());
    resourceClick(4);}
  public void warehouse1(){
    row1_1.setEffect(new Glow());
    warehouseClick(Row.FIRST);}
  public void warehouse2(){
    row2_1.setEffect(new Glow());
    row2_2.setEffect(new Glow());
    warehouseClick(Row.SECOND);}
  public void warehouse3(){
    row3_1.setEffect(new Glow());
    row3_2.setEffect(new Glow());
    row3_3.setEffect(new Glow());
    warehouseClick(Row.THIRD);}

  public void leaderCard1(){
    leaderCard1_1.setEffect(new Glow());
    leaderCard1_2.setEffect(new Glow());
    warehouseClick(Row.FOURTH);}
  public void leaderCard2(){
    leaderCard2_1.setEffect(new Glow());
    leaderCard2_2.setEffect(new Glow());
    warehouseClick(Row.FIFTH);}

  private void removeGlow(){
    row3_1.setEffect(null);
    row3_2.setEffect(null);
    row3_3.setEffect(null);
    row2_1.setEffect(null);
    row2_2.setEffect(null);
    row1_1.setEffect(null);
    res4.setEffect(null);
    res3.setEffect(null);
    res2.setEffect(null);
    res1.setEffect(null);
    leaderCard2_1.setEffect(null);
    leaderCard2_2.setEffect(null);
    leaderCard1_1.setEffect(null);
    leaderCard1_2.setEffect(null);
  }
  public void areThereLeaderCards(boolean areThereLeaderCards){
    if(areThereLeaderCards){
      shoot.setPrefHeight(269.0);
    }else{
      shoot.setPrefHeight(225.0);
    }
  }

  private void isThereLeaderCard1(boolean thereIs){
    leaderCard1_1.setDisable(!thereIs);
    leaderCard1_2.setDisable(!thereIs);
  }

  private void isThereLeaderCard2(boolean thereIs){
    leaderCard2_1.setDisable(!thereIs);
    leaderCard2_2.setDisable(!thereIs);
  }


  private void setFirstLine(Resource resource, int amount){
    String p = resource.toString().toUpperCase();
    Image im = new Image("image/" + p + ".png");
    if (amount>0){
      row1_1.setImage(im);
    }
  }

  private void setSecondLine(Resource resource, int amount){
    String p = resource.toString().toUpperCase();
    Image im = new Image("image/" + p + ".png");
    if (amount>0) {
      row2_1.setImage(im);
    }
    if(amount>1){
      row2_2.setImage(im);
    }
  }
  private void setThirdLine(Resource resource, int amount){
    String p = resource.toString().toUpperCase();
    Image im = new Image("image/" + p + ".png");
    if (amount>0) {
      row3_1.setImage(im);
    }
    if(amount>1){
      row3_2.setImage(im);
    }
    if(amount>2){
      row3_3.setImage(im);
    }
  }
  private void setFourthLine(Resource resource, int amount){
    String p = resource.toString().toUpperCase();
    Image logo = new Image("image/LC" + p + ".png");
    leaderCard1.setImage(logo);
    Image im = new Image("image/" + p + ".png");
    if (amount>0) {
      leaderCard1_1.setImage(im);
    }
    if(amount>1){
      leaderCard1_2.setImage(im);
    }
  }
  private void setFifthLine(Resource resource, int amount){
    String p = resource.toString().toUpperCase();
    Image logo = new Image("image/LC" + p + ".png");
    leaderCard2.setImage(logo);
    Image im = new Image("image/" + p + ".png");
    if (amount>0) {
      leaderCard2_1.setImage(im);
    }
    if(amount>1){
      leaderCard2_2.setImage(im);
    }
  }

  public void setResourcesToPlace(ArrayList<Resource> resourcesToPlace) {
    this.resourcesList = resourcesToPlace;
    int k=1;
    for(Resource r : resourcesToPlace) {
      this.resourcesToPlace.put(k, r);
      k++;
    }
    showResources();
  }

  private void showResources() {
    resourcesToPlace.forEach((pos, res) -> {
      resourceMap.get(pos).setImage(new Image("image/" + res.toString() + ".png"));
      resourceMap.get(pos).setDisable(false);
    });
  }

  public void setWareHouse(HashMap<Row, HashMap<Resource, Integer>> wareHouse){
    if(wareHouse.containsKey(Row.FIRST)){
      for(Resource r : wareHouse.get(Row.FIRST).keySet())
        setFirstLine(r, wareHouse.get(Row.FIRST).get(r));
    }

    if(wareHouse.containsKey(Row.SECOND)){
      for(Resource r : wareHouse.get(Row.SECOND).keySet())
        setSecondLine(r, wareHouse.get(Row.SECOND).get(r));
    }

    if(wareHouse.containsKey(Row.THIRD)){
      for(Resource r : wareHouse.get(Row.THIRD).keySet())
        setThirdLine(r, wareHouse.get(Row.THIRD).get(r));
    }
    if(wareHouse.containsKey(Row.FOURTH)||wareHouse.containsKey(Row.FIFTH)){
      areThereLeaderCards(true);
      if(wareHouse.containsKey(Row.FOURTH)){
        isThereLeaderCard1(true);
        for(Resource r : wareHouse.get(Row.FOURTH).keySet())
          setFourthLine(r, wareHouse.get(Row.FOURTH).get(r));
      }else{
        isThereLeaderCard1(false);
      }
      if(wareHouse.containsKey(Row.FIFTH)){
        isThereLeaderCard2(true);
        for(Resource r : wareHouse.get(Row.FIFTH).keySet())
          setFifthLine(r, wareHouse.get(Row.FIFTH).get(r));
      }else{
        isThereLeaderCard2(false);
      }
    }else{
      areThereLeaderCards(false);
    }

  }

  public void setStartingResource(boolean startingResource) {
    this.startingResource = startingResource;
    trashcan.setDisable(true);
    trashcan.setOpacity(0.5);
  }

  public void setJustSwitching(boolean justSwitching) {
    this.justSwitching = justSwitching;
    if(justSwitching){
      shoot.setPrefWidth(200.0);
      res1.setDisable(true);
      res2.setDisable(true);
      res3.setDisable(true);
      res4.setDisable(true);
      trashcan.setDisable(true);
      trashcan.setOpacity(0.5);
      backArrow.setVisible(true);
      backArrow.setDisable(false);
    }else{
      shoot.setPrefHeight(440.0);
      res1.setDisable(false);
      res2.setDisable(false);
      res3.setDisable(false);
      res4.setDisable(false);
      trashcan.setDisable(false);
    }
  }

  public void dumpAllResources(){
    //messaggio per dumpare le risorse
    MarbleMarketMessage message = new MarbleMarketMessage(view.getGameId(), view.getPlayerId());
    message.setReturnedResource(resourcesList);
    message.setDestroyRemaining(true);
    new Thread(new Runnable() {
      @Override
      public void run() {
        view.sendMessageToServer(message);
      }
    }).start();
  }

  public void goBack(MouseEvent mouseEvent) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        view.backToActionSelect();
      }
    }).start();
  }
}

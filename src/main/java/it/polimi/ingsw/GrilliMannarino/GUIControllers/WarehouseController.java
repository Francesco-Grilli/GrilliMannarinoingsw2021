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
  }

  @Override
  public void errorMessage(String header, String context) {
    text.setText(header + " " + context);
    fade(text);
  }

  private void fade(Label lbl){
    SequentialTransition sequentialTransition = new SequentialTransition();
    Transition t = new PauseTransition(Duration.millis(3000));
    FadeTransition fadeOut = new FadeTransition(Duration.millis(2000), lbl);
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
  }

  public void setStartingResource(boolean startingResource) {
    this.startingResource = startingResource;
  }

  public void setJustSwitching(boolean justSwitching) {
    this.justSwitching = justSwitching;
  }
}

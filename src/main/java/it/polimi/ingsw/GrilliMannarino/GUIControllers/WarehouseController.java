package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;
import it.polimi.ingsw.GrilliMannarino.MarbleMarket;
import it.polimi.ingsw.GrilliMannarino.Message.MarbleMarketMessage;
import it.polimi.ingsw.GrilliMannarino.Message.MoveResourceMessage;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.scene.control.Label;
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
  private ArrayList<Resource> resourcesList;
  private Row selectedRow;
  private GUIView view;

  @Override
  public void setView(GUIView view) {
    this.view = view;
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
    status = Status.PLACE;
    resourceToPlace = resourcesToPlace.get(i);
  }

  public void warehouseClick(Row r){
    if(status==Status.PLACE){

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

    }else if(status == Status.SWAP){
      MoveResourceMessage moveMessage = new MoveResourceMessage(view.getGameId(), view.getPlayerId());
      moveMessage.setForceSwap(true);
      moveMessage.setRowOne(selectedRow);
      moveMessage.setRowTwo(r);
      status = Status.NOTHING;
      new Thread(new Runnable() {
        @Override
        public void run() {
          view.sendMessageToServer(moveMessage);
        }
      }).start();
    }else{
      status = Status.SWAP;
      selectedRow = r;
    }
  }

  public void resource1(){resourceClick(1);}
  public void resource2(){resourceClick(2);}
  public void resource3(){resourceClick(3);}
  public void resource4(){resourceClick(4);}
  public void warehouse1(){warehouseClick(Row.FIRST);}
  public void warehouse2(){warehouseClick(Row.SECOND);}
  public void warehouse3(){warehouseClick(Row.THIRD);}

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
}

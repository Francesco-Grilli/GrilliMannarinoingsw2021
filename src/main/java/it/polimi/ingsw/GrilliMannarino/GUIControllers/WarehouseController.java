package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

public class WarehouseController {
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

  private enum Status{
    SWAP,NOTHING,PLACE
  }
  private Status status = Status.NOTHING;
  private Resource resourceToPlace;
  private HashMap<Integer,Resource> resourcesToPlace;
  private Row selectedRow;

  public void resourceClick(int i){
    status = Status.PLACE;
    resourceToPlace = resourcesToPlace.get(i);
  }

  public void warehouseClick(Row r){
    if(status==Status.PLACE){
      //messaggio per mandare risorse e settare status a nothing
    }else if(status == Status.SWAP){
      //messaggio da mandare con selected row e settare status a nothing
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
    Image im = new Image("@/image/" + p + ".png");
    if (amount>0){
      row1_1.setImage(im);
    }
  }

  private void setSecondLine(Resource resource, int amount){
    String p = resource.toString().toUpperCase();
    Image im = new Image("@/image/" + p + ".png");
    if (amount>0) {
      row2_1.setImage(im);
    }
    if(amount>1){
      row2_2.setImage(im);
    }
  }
  private void setThirdLine(Resource resource, int amount){
    String p = resource.toString().toUpperCase();
    Image im = new Image("@/image/" + p + ".png");
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
}

package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BoardSingleController implements SmallController{
  public Pane faithStep0;
  public Pane faithStep1;
  public Pane faithStep2;
  public Pane faithStep3;
  public Pane faithStep4;
  public Pane faithStep5;
  public Pane faithStep6;
  public Pane faithStep7;
  public Pane faithStep8;
  public Pane faithStep9;
  public Pane faithStep10;
  public Pane faithStep11;
  public Pane faithStep12;
  public Pane faithStep13;
  public Pane faithStep14;
  public Pane faithStep15;
  public Pane faithStep16;
  public Pane faithStep17;
  public Pane faithStep18;
  public Pane faithStep19;
  public Pane faithStep20;
  public Pane faithStep21;
  public Pane faithStep22;
  public Pane faithStep23;
  public Pane faithStep24;
  public Pane faithStepLorenzo0;
  public Pane faithStepLorenzo1;
  public Pane faithStepLorenzo2;
  public Pane faithStepLorenzo3;
  public Pane faithStepLorenzo4;
  public Pane faithStepLorenzo5;
  public Pane faithStepLorenzo6;
  public Pane faithStepLorenzo7;
  public Pane faithStepLorenzo8;
  public Pane faithStepLorenzo9;
  public Pane faithStepLorenzo10;
  public Pane faithStepLorenzo11;
  public Pane faithStepLorenzo12;
  public Pane faithStepLorenzo13;
  public Pane faithStepLorenzo14;
  public Pane faithStepLorenzo15;
  public Pane faithStepLorenzo16;
  public Pane faithStepLorenzo17;
  public Pane faithStepLorenzo18;
  public Pane faithStepLorenzo19;
  public Pane faithStepLorenzo20;
  public Pane faithStepLorenzo21;
  public Pane faithStepLorenzo22;
  public Pane faithStepLorenzo23;
  public Pane faithStepLorenzo24;
  public ImageView productionCard1;
  public ImageView productionCard2;
  public ImageView productionCard3;
  public ImageView row1_1;
  public ImageView row2_1;
  public ImageView row2_2;
  public ImageView row3_1;
  public ImageView row3_2;
  public ImageView row3_3;
  public ImageView faithCheck3;
  public ImageView faithCheck2;
  public ImageView faithCheck1;
  public Label shield;
  public Label servant;
  public Label stone;
  public Label coin;
  private GUIView view;

  private HashMap<Integer,Pane> getPopelineHashMap(){
    HashMap<Integer, Pane> valuesByName = new HashMap<>();
    valuesByName.put(0, faithStep0);
    valuesByName.put(1, faithStep1);
    valuesByName.put(2, faithStep2);
    valuesByName.put(3, faithStep3);
    valuesByName.put(4, faithStep4);
    valuesByName.put(5, faithStep5);
    valuesByName.put(6, faithStep6);
    valuesByName.put(7, faithStep7);
    valuesByName.put(8, faithStep8);
    valuesByName.put(9, faithStep9);
    valuesByName.put(10, faithStep10);
    valuesByName.put(11, faithStep11);
    valuesByName.put(12, faithStep12);
    valuesByName.put(13, faithStep13);
    valuesByName.put(14, faithStep14);
    valuesByName.put(15, faithStep15);
    valuesByName.put(16, faithStep16);
    valuesByName.put(17, faithStep17);
    valuesByName.put(18, faithStep18);
    valuesByName.put(19, faithStep19);
    valuesByName.put(20, faithStep20);
    valuesByName.put(21, faithStep21);
    valuesByName.put(22, faithStep22);
    valuesByName.put(23, faithStep23);
    valuesByName.put(24, faithStep24);
    return valuesByName;
  }

  private HashMap<Integer,Pane> getLorenzoHashMap(){
    HashMap<Integer, Pane> lorenzoByName = new HashMap<>();
    lorenzoByName.put(0, faithStepLorenzo0);
    lorenzoByName.put(1, faithStepLorenzo1);
    lorenzoByName.put(2, faithStepLorenzo2);
    lorenzoByName.put(3, faithStepLorenzo3);
    lorenzoByName.put(4, faithStepLorenzo4);
    lorenzoByName.put(5, faithStepLorenzo5);
    lorenzoByName.put(6, faithStepLorenzo6);
    lorenzoByName.put(7, faithStepLorenzo7);
    lorenzoByName.put(8, faithStepLorenzo8);
    lorenzoByName.put(9, faithStepLorenzo9);
    lorenzoByName.put(10, faithStepLorenzo10);
    lorenzoByName.put(11, faithStepLorenzo11);
    lorenzoByName.put(12, faithStepLorenzo12);
    lorenzoByName.put(13, faithStepLorenzo13);
    lorenzoByName.put(14, faithStepLorenzo14);
    lorenzoByName.put(15, faithStepLorenzo15);
    lorenzoByName.put(16, faithStepLorenzo16);
    lorenzoByName.put(17, faithStepLorenzo17);
    lorenzoByName.put(18, faithStepLorenzo18);
    lorenzoByName.put(19, faithStepLorenzo19);
    lorenzoByName.put(20, faithStepLorenzo20);
    lorenzoByName.put(21, faithStepLorenzo21);
    lorenzoByName.put(22, faithStepLorenzo22);
    lorenzoByName.put(23, faithStepLorenzo23);
    lorenzoByName.put(24, faithStepLorenzo24);
    return lorenzoByName;
  }

  @Override
  public void setView(GUIView view) {
    this.view = view;
  }

  @Override
  public void errorMessage(String header, String context) {
    Alert error = new Alert(Alert.AlertType.INFORMATION);
    error.setHeaderText(header);
    error.setContentText(context);
    error.show();
  }

  public void setPopelineSteps(int step){
    HashMap<Integer,Pane> values = getPopelineHashMap();
    values.values().forEach(v->{v.setOpacity(0.0);});
    values.get(step).setOpacity(1.0);
  }

  public void setLorenzoSteps(int step){
    HashMap<Integer,Pane> values = getLorenzoHashMap();
    values.values().forEach(v->{v.setOpacity(0.0);});
    values.get(step).setOpacity(1.0);
  }

  public void setProductionCards(HashMap<Integer,Integer> cardCodes){
    cardCodes.forEach((k,v)->{setProductionCard(v,k);});
  }

  private void setProductionCard(int i, int cc){
    if(i == 1){
      productionCard1.setImage(new Image("image/CC-"+cc+".png"));
    }else if(i == 2){
      productionCard2.setImage(new Image("image/CC-"+cc+".png"));
    }else if(i == 3){
      productionCard3.setImage(new Image("image/CC-"+cc+".png"));
    }
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

  private void showFaithChecks(HashMap<Integer,Boolean> checks){
    if(checks.get(1)){
      faithCheck1.setOpacity(1);
    }
    if(checks.get(2)){
      faithCheck2.setOpacity(1);
    }
    if(checks.get(3)){
      faithCheck3.setOpacity(1);
    }
  }

  public void showFaithChecks(boolean[] checks){
    HashMap<Integer, Boolean> checksMap = new HashMap<>();
    for(int i=0; i<checks.length; i++){
      checksMap.put((i+1), checks[i]);
    }
    showFaithChecks(checksMap);
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

  public void setChest(HashMap<Resource,Integer> resources){
    stone.setText((resources.get(Resource.STONE)!= null?resources.get(Resource.STONE): Integer.valueOf(0)).toString());
    shield.setText((resources.get(Resource.SHIELD)!= null?resources.get(Resource.SHIELD): Integer.valueOf(0)).toString());
    servant.setText((resources.get(Resource.SERVANT)!= null?resources.get(Resource.SERVANT): Integer.valueOf(0)).toString());
    coin.setText((resources.get(Resource.COIN)!= null?resources.get(Resource.COIN): Integer.valueOf(0)).toString());
  }
}

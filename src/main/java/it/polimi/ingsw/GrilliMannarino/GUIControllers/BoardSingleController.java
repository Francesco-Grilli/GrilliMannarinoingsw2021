package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
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
  final Map<Integer, Pane> VALUES_BY_NAME;
  final Map<Integer, Pane> LORENZO_BY_NAME;
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

  {
    final Map<Integer, Pane> valuesByName = new HashMap<>();
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
    VALUES_BY_NAME = Collections.unmodifiableMap(valuesByName);
    final Map<Integer, Pane> lorenzoByName = new HashMap<>();
    valuesByName.put(0, faithStepLorenzo0);
    valuesByName.put(1, faithStepLorenzo1);
    valuesByName.put(2, faithStepLorenzo2);
    valuesByName.put(3, faithStepLorenzo3);
    valuesByName.put(4, faithStepLorenzo4);
    valuesByName.put(5, faithStepLorenzo5);
    valuesByName.put(6, faithStepLorenzo6);
    valuesByName.put(7, faithStepLorenzo7);
    valuesByName.put(8, faithStepLorenzo8);
    valuesByName.put(9, faithStepLorenzo9);
    valuesByName.put(10, faithStepLorenzo10);
    valuesByName.put(11, faithStepLorenzo11);
    valuesByName.put(12, faithStepLorenzo12);
    valuesByName.put(13, faithStepLorenzo13);
    valuesByName.put(14, faithStepLorenzo14);
    valuesByName.put(15, faithStepLorenzo15);
    valuesByName.put(16, faithStepLorenzo16);
    valuesByName.put(17, faithStepLorenzo17);
    valuesByName.put(18, faithStepLorenzo18);
    valuesByName.put(19, faithStepLorenzo19);
    valuesByName.put(20, faithStepLorenzo20);
    valuesByName.put(21, faithStepLorenzo21);
    valuesByName.put(22, faithStepLorenzo22);
    valuesByName.put(23, faithStepLorenzo23);
    valuesByName.put(24, faithStepLorenzo24);
    LORENZO_BY_NAME = Collections.unmodifiableMap(lorenzoByName);
  }

  @Override
  public void setView(GUIView view) {

  }

  @Override
  public void errorMessage(String header, String context) {

  }

  public void setPopelineSteps(int step){
    VALUES_BY_NAME.values().forEach(v->{v.setOpacity(0);});
    VALUES_BY_NAME.get(step).setOpacity(1);
  }

  public void setLorenzoSteps(int step){
    LORENZO_BY_NAME.values().forEach(v->{v.setOpacity(0);});
    LORENZO_BY_NAME.get(step).setOpacity(1);
  }

  public void setProductionCards(HashMap<Integer,Integer> cardCodes){
    cardCodes.forEach((k,v)->{setProductionCard(v,k);});
  }

  private void setProductionCard(int i, int cc){
    if(i == 1){
      productionCard1.setImage(new Image("@/image/CC-"+cc+".png"));
    }else if(i == 2){
      productionCard2.setImage(new Image("@/image/CC-"+cc+".png"));
    }else if(i == 3){
      productionCard3.setImage(new Image("@/image/CC-"+cc+".png"));
    }
  }

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

  public void showFaithChecks(HashMap<Integer,Boolean> checks){
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

}

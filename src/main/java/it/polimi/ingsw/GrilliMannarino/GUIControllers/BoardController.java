package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.*;

public class BoardController implements SmallController{
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
    final Map<Integer, Pane> VALUES_BY_NAME;
    public ImageView productionCard1;
    public ImageView productionCard2;
    public ImageView productionCard3;

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

    public void setProductionCards(HashMap<Integer,Integer> cardCodes){
        cardCodes.forEach(this::setProductionCard);
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
}

package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

public class CardMarketController implements SmallController{
    public ImageView card00;
    public ImageView card10;
    public ImageView card20;
    public ImageView card30;
    public ImageView card01;
    public ImageView card11;
    public ImageView card21;
    public ImageView card31;
    public ImageView card02;
    public ImageView card12;
    public ImageView card22;
    public ImageView card32;
    private HashMap<Faction,HashMap<Integer,ImageView>> cardSlots = new HashMap<>();
    private final Faction blue = Faction.BLUE;
    private final Faction green = Faction.GREEN;
    private final Faction yellow = Faction.YELLOW;
    private final Faction purple = Faction.PURPLE;

    @Override
    public void setView(GUIView view) {

    }

    @Override
    public void errorMessage(String header, String context) {

    }

    private void getHashMapUp(){
        HashMap<Integer,ImageView> blue0 = new HashMap<>();
        HashMap<Integer,ImageView> green2 = new HashMap<>();
        HashMap<Integer,ImageView> purple1 = new HashMap<>();
        HashMap<Integer,ImageView> yellow3 = new HashMap<>();
        blue0.put(0,card00);
        blue0.put(1,card01);
        blue0.put(2,card02);
        green2.put(0,card20);
        green2.put(1,card21);
        green2.put(2,card22);
        yellow3.put(0,card30);
        yellow3.put(1,card31);
        yellow3.put(2,card32);
        purple1.put(0,card10);
        purple1.put(1,card11);
        purple1.put(2,card12);
        this.cardSlots.put(blue,blue0);
        this.cardSlots.put(purple,purple1);
        this.cardSlots.put(green,green2);
        this.cardSlots.put(yellow,yellow3);

    }

    public void setCardSlots(HashMap<Faction,HashMap<Integer, Map.Entry<Integer,Boolean>>> cardSlots) {
        this.getHashMapUp();
        for(Faction fac:cardSlots.keySet()){
            for(Integer lev:cardSlots.get(fac).keySet()){
                int cc = cardSlots.get(fac).get(lev).getKey();
                Image toput = new Image("image/CC-"+cc+".png");
                this.cardSlots.get(fac).get(lev).setImage(toput);
                this.cardSlots.get(fac).get(lev).setDisable(!cardSlots.get(fac).get(lev).getValue());
                this.cardSlots.get(fac).get(lev).setOnMouseClicked(e->this.sendMessage(cc));
            }
        }
    }

    private void sendMessage(int cardCode){

    }
}

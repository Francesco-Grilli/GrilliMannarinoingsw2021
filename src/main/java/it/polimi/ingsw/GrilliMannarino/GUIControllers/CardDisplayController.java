package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

public class CardDisplayController implements SmallController{

    public ImageView card1;
    public ImageView card2;
    public ImageView card3;
    public ImageView card4;
    public ImageView card5;
    public ImageView card6;
    private HashMap<Integer,Integer> momentaryCardCodes = new HashMap<>();

    @Override
    public void setView(GUIView view) {

    }

    @Override
    public void errorMessage(String header, String context) {

    }

    public void setCards(ArrayList<Integer> cardCodes){
        HashMap<Integer,Integer> posCard = new HashMap<>();
        int k=1;
        for(Integer cardCode : cardCodes) {
            posCard.put(k, cardCode);
            k++;
        }
        momentaryCardCodes = posCard;
        posCard.forEach(this::setCard);
    }

    private void setCard(int i, int cc){
        if(i == 1){
            card1.setImage(new Image("@/image/CC-"+cc+".png"));
        }else if(i == 2){
            card2.setImage(new Image("@/image/CC-"+cc+".png"));
        }else if(i == 3){
            card3.setImage(new Image("@/image/CC-"+cc+".png"));
        }else if(i == 4){
            card4.setImage(new Image("@/image/CC-"+cc+".png"));
        }else if(i == 5){
            card5.setImage(new Image("@/image/CC-"+cc+".png"));
        }else if(i == 6){
            card6.setImage(new Image("@/image/CC-"+cc+".png"));
        }
    }

}

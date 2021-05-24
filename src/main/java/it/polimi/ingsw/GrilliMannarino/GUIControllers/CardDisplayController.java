package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import it.polimi.ingsw.GrilliMannarino.Message.ProductionMessage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class CardDisplayController implements SmallController{

    public ImageView card1;
    public ImageView card2;
    public ImageView card3;
    public ImageView card4;
    public ImageView card5;
    public ImageView card6;
    public Button produceButton;
    private GUIView view;
    private HashMap<Integer,Integer> momentaryCardCodes = new HashMap<>();
    private ArrayList<Integer> producingCard = new ArrayList<>();

    @Override
    public void setView(GUIView view) {
        this.view = view;
    }

    @Override
    public void errorMessage(String header, String context) {

    }

    public void setCards(HashMap<Integer, Integer> cardCodes){
        HashMap<Integer, Integer> cardFrom = new HashMap<>(cardCodes); //code, pos
        HashMap<Integer,Integer> posCard = new HashMap<>(); //pos, code

        cardFrom.forEach((cc, pos) -> posCard.put(pos, cc));

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

    public void card1Action() {
        producingCard.add(momentaryCardCodes.get(1));
        card1.setDisable(true);
    }

    public void card2Action() {
        producingCard.add(momentaryCardCodes.get(2));
        card2.setDisable(true);
    }

    public void card3Action() {
        producingCard.add(momentaryCardCodes.get(3));
        card3.setDisable(true);
    }

    public void card4Action() {
        producingCard.add(momentaryCardCodes.get(4));
        card4.setDisable(true);
    }

    public void card5Action() {
        producingCard.add(momentaryCardCodes.get(5));
        card5.setDisable(true);
    }

    public void card6Action() {
        producingCard.add(momentaryCardCodes.get(6));
        card6.setDisable(true);
    }

    public void produce() {
        ProductionMessage message = new ProductionMessage(view.getGameId(), view.getPlayerId());
        message.setSelectCard(true);
        message.setSelectedCard(producingCard);
        new Thread(new Runnable() {
            @Override
            public void run() {
                view.sendMessageToServer(message);
            }
        }).start();
        disableAllButton();
    }

    private void disableAllButton(){
        card1.setDisable(true);
        card2.setDisable(true);
        card3.setDisable(true);
        card4.setDisable(true);
        card5.setDisable(true);
        card6.setDisable(true);
    }
}

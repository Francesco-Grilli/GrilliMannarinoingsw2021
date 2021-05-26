package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import it.polimi.ingsw.GrilliMannarino.Message.LeaderCardMessage;
import it.polimi.ingsw.GrilliMannarino.Message.ProductionMessage;
import javafx.event.ActionEvent;
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
    public Button activateButton;
    public Button sellButton;
    private GUIView view;
    private boolean leaderCard = false;
    private boolean selectStartingLeaderCard = false;
    private int numberCardSelected = 0;

    private HashMap<Integer,Integer> momentaryCardCodes = new HashMap<>();
    private ArrayList<Integer> producingCard = new ArrayList<>();


    @Override
    public void setView(GUIView view) {
        this.view = view;
        disableAllButton();
    }

    @Override
    public void errorMessage(String header, String context) {

    }

    public void setCards(ArrayList<Integer> cardCodes){
        if(leaderCard){
            if(selectStartingLeaderCard){
                produceButton.setText("Select");
                sellButton.setVisible(false);
                activateButton.setVisible(false);
                sellButton.setDisable(true);
                activateButton.setDisable(true);
            }
            else{
                produceButton.setDisable(true);
                produceButton.setVisible(false);
                sellButton.setVisible(true);
                activateButton.setVisible(true);
                sellButton.setDisable(false);
                activateButton.setDisable(false);
            }
        }

        HashMap<Integer, Integer> posCard = new HashMap<>();
        int k = 1;
        for(Integer cc : cardCodes){
            posCard.put(k, cc);
            k++;
        }

        momentaryCardCodes = posCard;
        posCard.forEach(this::setCard);
    }

    public void setCards(HashMap<Integer, Integer> cardCodes){
        if(leaderCard){
            if(selectStartingLeaderCard){
                produceButton.setText("Select");
                sellButton.setVisible(false);
                activateButton.setVisible(false);
                sellButton.setDisable(true);
                activateButton.setDisable(true);
            }
            else {
                produceButton.setDisable(true);
                produceButton.setVisible(false);
                sellButton.setVisible(true);
                activateButton.setVisible(true);
                sellButton.setDisable(false);
                activateButton.setDisable(false);
            }
        }

        HashMap<Integer, Integer> cardFrom = new HashMap<>(cardCodes); //code, pos
        HashMap<Integer,Integer> posCard = new HashMap<>(); //pos, code

        cardFrom.forEach((cc, pos) -> posCard.put(pos, cc));

        momentaryCardCodes = posCard;
        posCard.forEach(this::setCard);
    }

    private void setCard(int i, int cc){
        if(i == 1){
            card1.setImage(new Image("image/CC-"+cc+".png"));
            card1.setDisable(false);
            card1.setVisible(true);
        }else if(i == 2){
            card2.setImage(new Image("image/CC-"+cc+".png"));
            card2.setDisable(false);
            card2.setVisible(true);
        }else if(i == 3){
            card3.setImage(new Image("image/CC-"+cc+".png"));
            card3.setDisable(false);
            card3.setVisible(true);
        }else if(i == 4){
            card4.setImage(new Image("image/CC-"+cc+".png"));
            card4.setDisable(false);
            card4.setVisible(true);
        }else if(i == 5){
            card5.setImage(new Image("image/CC-"+cc+".png"));
            card5.setDisable(false);
            card5.setVisible(true);
        }else if(i == 6){
            card6.setImage(new Image("image/CC-"+cc+".png"));
            card6.setDisable(false);
            card6.setVisible(true);
        }
    }

    public void card1Action() {
        if(leaderCard){
            if(selectStartingLeaderCard){
                if(numberCardSelected<2){
                    numberCardSelected++;
                    producingCard.add(momentaryCardCodes.get(1));
                    card1.setDisable(true);
                    card1.setOpacity(0.5);
                    if(numberCardSelected>=2)
                        disableAllButton();
                }
            }
            else{
                producingCard.add(momentaryCardCodes.get(1));
                disableAllButton();
            }

        }
        else {
            producingCard.add(momentaryCardCodes.get(1));
            card1.setDisable(true);
            card1.setOpacity(0.5);
        }
    }

    public void card2Action() {
        if(leaderCard){
            if(selectStartingLeaderCard){
                if(numberCardSelected<2){
                    numberCardSelected++;
                    producingCard.add(momentaryCardCodes.get(2));
                    card2.setDisable(true);
                    card2.setOpacity(0.5);
                    if(numberCardSelected>=2)
                        disableAllButton();
                }
            }
            else{
                producingCard.add(momentaryCardCodes.get(2));
                disableAllButton();
            }

        }
        else {
            producingCard.add(momentaryCardCodes.get(2));
            card2.setDisable(true);
            card2.setOpacity(0.5);
        }
    }

    public void card3Action() {
        if(leaderCard){
            if(selectStartingLeaderCard){
                if(numberCardSelected<2){
                    numberCardSelected++;
                    producingCard.add(momentaryCardCodes.get(3));
                    card3.setDisable(true);
                    card3.setOpacity(0.5);
                    if(numberCardSelected>=2)
                        disableAllButton();
                }
            }
            else{
                producingCard.add(momentaryCardCodes.get(3));
                disableAllButton();
            }

        }
        else {
            producingCard.add(momentaryCardCodes.get(3));
            card3.setDisable(true);
            card3.setOpacity(0.5);
        }
    }

    public void card4Action() {
        if(leaderCard){
            if(selectStartingLeaderCard){
                if(numberCardSelected<2){
                    numberCardSelected++;
                    producingCard.add(momentaryCardCodes.get(4));
                    card4.setDisable(true);
                    card4.setOpacity(0.5);
                    if(numberCardSelected>=2)
                        disableAllButton();
                }
            }
            else{
                producingCard.add(momentaryCardCodes.get(4));
                disableAllButton();
            }

        }
        else {
            producingCard.add(momentaryCardCodes.get(4));
            card4.setDisable(true);
            card4.setOpacity(0.5);
        }
    }

    public void card5Action() {
        if(leaderCard){
            if(selectStartingLeaderCard){
                if(numberCardSelected<2){
                    numberCardSelected++;
                    producingCard.add(momentaryCardCodes.get(5));
                    card5.setDisable(true);
                    card5.setOpacity(0.5);
                    if(numberCardSelected>=2)
                        disableAllButton();
                }
            }
            else{
                producingCard.add(momentaryCardCodes.get(5));
                disableAllButton();
            }

        }
        else {
            producingCard.add(momentaryCardCodes.get(5));
            card5.setDisable(true);
            card5.setOpacity(0.5);
        }
    }

    public void card6Action() {
        if(leaderCard){
            if(selectStartingLeaderCard){
                if(numberCardSelected<2){
                    numberCardSelected++;
                    producingCard.add(momentaryCardCodes.get(6));
                    card6.setDisable(true);
                    card6.setOpacity(0.5);
                    if(numberCardSelected>=2)
                        disableAllButton();
                }
            }
            else{
                producingCard.add(momentaryCardCodes.get(6));
                disableAllButton();
            }

        }
        else {
            producingCard.add(momentaryCardCodes.get(6));
            card6.setDisable(true);
            card6.setOpacity(0.5);
        }
    }

    public void produce() {
        if(!leaderCard) {
            ProductionMessage message = new ProductionMessage(view.getGameId(), view.getPlayerId());
            message.setSelectCard(true);
            message.setSelectedCard(producingCard);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    view.sendMessageToServer(message);
                }
            }).start();
        }
        else {
            LeaderCardMessage message = new LeaderCardMessage(view.getGameId(), view.getPlayerId());
            if (selectStartingLeaderCard && numberCardSelected==2) {
                message.setSelectLeaderCard(true);
                message.setCards(producingCard);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        view.sendMessageToServer(message);
                    }
                }).start();
            }
        }
        disableAllButton();
        produceButton.setDisable(true);
    }

    private void disableAllButton(){
        card1.setDisable(true);
        card2.setDisable(true);
        card3.setDisable(true);
        card4.setDisable(true);
        card5.setDisable(true);
        card6.setDisable(true);
        card1.setVisible(false);
        card2.setVisible(false);
        card3.setVisible(false);
        card4.setVisible(false);
        card5.setVisible(false);
        card6.setVisible(false);
    }

    public void setLeaderCard(boolean leaderCard) {
        this.leaderCard = leaderCard;
    }

    public void setSelectStartingLeaderCard(boolean selectStartingLeaderCard) {
        this.selectStartingLeaderCard = selectStartingLeaderCard;
    }

    public void activateLeaderCard(ActionEvent actionEvent) {
        if(leaderCard && producingCard.size()==1){
            LeaderCardMessage message = new LeaderCardMessage(view.getGameId(), view.getPlayerId());
            message.setActivateCard(true);
            message.setCardCode(producingCard.get(0));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    view.sendMessageToServer(message);
                }
            }).start();
        }
    }

    public void sellLeaderCard(ActionEvent actionEvent) {
        if(leaderCard && producingCard.size()==1){
            LeaderCardMessage message = new LeaderCardMessage(view.getGameId(), view.getPlayerId());
            message.setSellingCard(true);
            message.setCardCode(producingCard.get(0));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    view.sendMessageToServer(message);
                }
            }).start();
        }
    }
}

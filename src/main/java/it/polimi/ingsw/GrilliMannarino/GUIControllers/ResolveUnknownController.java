package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.Message.ProductionMessage;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;

public class ResolveUnknownController implements SmallController {
    public ImageView card1;
    public Label inputLabel;
    public ImageView inputImage1;
    public ImageView inputImage2;
    public ImageView inputImage3;
    public ImageView inputImage4;
    private GUIView view;

    private HashMap<Integer, HashMap<Resource, Integer>> inputCard;
    private HashMap<Integer, HashMap<Resource, Integer>> outputCard;
    private ArrayList<Integer> selectedCard;
    private ArrayList<Integer> cardToResolve = new ArrayList<>();
    private Integer cardSelected;
    private boolean resolvedInput = false;

    public void resolve(){
        if(!resolvedInput) {
            for (Integer cc : inputCard.keySet()) {
                if (inputCard.get(cc).containsKey(Resource.UNKNOWN)) {
                    for(int i=0; i<inputCard.get(cc).get(Resource.UNKNOWN); i++)
                        cardToResolve.add(cc);
                }
            }
        }
        else{
            inputLabel.setText("Output:");
            for(Integer cc : outputCard.keySet()){
                if(outputCard.get(cc).containsKey(Resource.UNKNOWN)) {
                    for (int i = 0; i < outputCard.get(cc).get(Resource.UNKNOWN); i++)
                        cardToResolve.add(cc);
                }
            }
        }
        showCard();
    }

    private void showCard() {
        if(cardToResolve.size()>0){
            showAllImage();
            card1.setImage(new Image("image/CC-" + cardToResolve.get(0) + ".png"));
            cardSelected = cardToResolve.get(0);
            cardToResolve.remove(0);
        }
        else{
            if(!resolvedInput){
                resolvedInput = true;
                resolve();
            }
            else{
                finished();
            }
        }
    }

    private void finished() {
        ProductionMessage unknownMessage = new ProductionMessage(view.getGameId(), view.getPlayerId());
        unknownMessage.setResolveUnknown(true);
        unknownMessage.setInputCard(inputCard);
        unknownMessage.setOutputCard(outputCard);
        unknownMessage.setSelectedCard(selectedCard);
        new Thread(new Runnable() {
            @Override
            public void run() {
                view.sendMessageToServer(unknownMessage);
            }
        }).start();
    }

    private void showAllImage() {
        inputImage1.setVisible(true);
        inputImage2.setVisible(true);
        inputImage3.setVisible(true);
        inputImage4.setVisible(true);
        inputImage1.setDisable(false);
        inputImage2.setDisable(false);
        inputImage3.setDisable(false);
        inputImage4.setDisable(false);
    }

    private void hideAllImage() {
        inputImage1.setVisible(false);
        inputImage2.setVisible(false);
        inputImage3.setVisible(false);
        inputImage4.setVisible(false);
        inputImage1.setDisable(true);
        inputImage2.setDisable(true);
        inputImage3.setDisable(true);
        inputImage4.setDisable(true);
    }

    public void inputMethod1(MouseEvent mouseEvent) {
        Resource res = Resource.SHIELD;
        if(!resolvedInput){
            if(inputCard.get(cardSelected).get(Resource.UNKNOWN)==1){
                inputCard.get(cardSelected).remove(Resource.UNKNOWN);
                if(inputCard.get(cardSelected).containsKey(res)){
                    inputCard.get(cardSelected).put(res, (inputCard.get(cardSelected).get(res) + 1));
                }
                else
                    inputCard.get(cardSelected).put(res, 1);
            }
            else{
                inputCard.get(cardSelected).put(Resource.UNKNOWN, (inputCard.get(cardSelected).get(Resource.UNKNOWN)-1));
                if(inputCard.get(cardSelected).containsKey(res))
                    inputCard.get(cardSelected).put(res, (inputCard.get(cardSelected).get(res)+1));
                else
                    inputCard.get(cardSelected).put(res, 1);
            }
        }
        else{
            if(outputCard.get(cardSelected).get(Resource.UNKNOWN)==1){
                outputCard.get(cardSelected).remove(Resource.UNKNOWN);
                if(outputCard.get(cardSelected).containsKey(res)){
                    outputCard.get(cardSelected).put(res, (outputCard.get(cardSelected).get(res) + 1));
                }
                else
                    outputCard.get(cardSelected).put(res, 1);
            }
            else{
                outputCard.get(cardSelected).put(Resource.UNKNOWN, (outputCard.get(cardSelected).get(Resource.UNKNOWN)-1));
                if(outputCard.get(cardSelected).containsKey(res))
                    outputCard.get(cardSelected).put(res, (outputCard.get(cardSelected).get(res)+1));
                else
                    outputCard.get(cardSelected).put(res, 1);
            }
        }
        fade(inputImage1);
        hideAllImage();
        showCard();
    }

    private void fade(ImageView img){
        SequentialTransition sequentialTransition = new SequentialTransition();
        FadeTransition fadeOut = new FadeTransition(Duration.millis(100), img);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(100), img);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        sequentialTransition.getChildren().addAll(fadeOut, fadeIn);
        sequentialTransition.play();
    }

    public void inputMethod2(MouseEvent mouseEvent) {
        Resource res = Resource.STONE;
        if(!resolvedInput){
            if(inputCard.get(cardSelected).get(Resource.UNKNOWN)==1){
                inputCard.get(cardSelected).remove(Resource.UNKNOWN);
                if(inputCard.get(cardSelected).containsKey(res)){
                    inputCard.get(cardSelected).put(res, (inputCard.get(cardSelected).get(res) + 1));
                }
                else
                    inputCard.get(cardSelected).put(res, 1);
            }
            else{
                inputCard.get(cardSelected).put(Resource.UNKNOWN, (inputCard.get(cardSelected).get(Resource.UNKNOWN)-1));
                if(inputCard.get(cardSelected).containsKey(res))
                    inputCard.get(cardSelected).put(res, (inputCard.get(cardSelected).get(res)+1));
                else
                    inputCard.get(cardSelected).put(res, 1);
            }
        }
        else{
            if(outputCard.get(cardSelected).get(Resource.UNKNOWN)==1){
                outputCard.get(cardSelected).remove(Resource.UNKNOWN);
                if(outputCard.get(cardSelected).containsKey(res)){
                    outputCard.get(cardSelected).put(res, (outputCard.get(cardSelected).get(res) + 1));
                }
                else
                    outputCard.get(cardSelected).put(res, 1);
            }
            else{
                outputCard.get(cardSelected).put(Resource.UNKNOWN, (outputCard.get(cardSelected).get(Resource.UNKNOWN)-1));
                if(outputCard.get(cardSelected).containsKey(res))
                    outputCard.get(cardSelected).put(res, (outputCard.get(cardSelected).get(res)+1));
                else
                    outputCard.get(cardSelected).put(res, 1);
            }
        }
        fade(inputImage2);
        hideAllImage();
        showCard();
    }

    public void inputMethod3(MouseEvent mouseEvent) {
        Resource res = Resource.SERVANT;
        if(!resolvedInput){
            if(inputCard.get(cardSelected).get(Resource.UNKNOWN)==1){
                inputCard.get(cardSelected).remove(Resource.UNKNOWN);
                if(inputCard.get(cardSelected).containsKey(res)){
                    inputCard.get(cardSelected).put(res, (inputCard.get(cardSelected).get(res) + 1));
                }
                else
                    inputCard.get(cardSelected).put(res, 1);
            }
            else{
                inputCard.get(cardSelected).put(Resource.UNKNOWN, (inputCard.get(cardSelected).get(Resource.UNKNOWN)-1));
                if(inputCard.get(cardSelected).containsKey(res))
                    inputCard.get(cardSelected).put(res, (inputCard.get(cardSelected).get(res)+1));
                else
                    inputCard.get(cardSelected).put(res, 1);
            }
        }
        else{
            if(outputCard.get(cardSelected).get(Resource.UNKNOWN)==1){
                outputCard.get(cardSelected).remove(Resource.UNKNOWN);
                if(outputCard.get(cardSelected).containsKey(res)){
                    outputCard.get(cardSelected).put(res, (outputCard.get(cardSelected).get(res) + 1));
                }
                else
                    outputCard.get(cardSelected).put(res, 1);
            }
            else{
                outputCard.get(cardSelected).put(Resource.UNKNOWN, (outputCard.get(cardSelected).get(Resource.UNKNOWN)-1));
                if(outputCard.get(cardSelected).containsKey(res))
                    outputCard.get(cardSelected).put(res, (outputCard.get(cardSelected).get(res)+1));
                else
                    outputCard.get(cardSelected).put(res, 1);
            }
        }
        fade(inputImage3);
        hideAllImage();
        showCard();
    }

    public void inputMethod4(MouseEvent mouseEvent) {
        Resource res = Resource.COIN;
        if(!resolvedInput){
            if(inputCard.get(cardSelected).get(Resource.UNKNOWN)==1){
                inputCard.get(cardSelected).remove(Resource.UNKNOWN);
                if(inputCard.get(cardSelected).containsKey(res)){
                    inputCard.get(cardSelected).put(res, (inputCard.get(cardSelected).get(res) + 1));
                }
                else
                    inputCard.get(cardSelected).put(res, 1);
            }
            else{
                inputCard.get(cardSelected).put(Resource.UNKNOWN, (inputCard.get(cardSelected).get(Resource.UNKNOWN)-1));
                if(inputCard.get(cardSelected).containsKey(res))
                    inputCard.get(cardSelected).put(res, (inputCard.get(cardSelected).get(res)+1));
                else
                    inputCard.get(cardSelected).put(res, 1);
            }
        }
        else{
            if(outputCard.get(cardSelected).get(Resource.UNKNOWN)==1){
                outputCard.get(cardSelected).remove(Resource.UNKNOWN);
                if(outputCard.get(cardSelected).containsKey(res)){
                    outputCard.get(cardSelected).put(res, (outputCard.get(cardSelected).get(res) + 1));
                }
                else
                    outputCard.get(cardSelected).put(res, 1);
            }
            else{
                outputCard.get(cardSelected).put(Resource.UNKNOWN, (outputCard.get(cardSelected).get(Resource.UNKNOWN)-1));
                if(outputCard.get(cardSelected).containsKey(res))
                    outputCard.get(cardSelected).put(res, (outputCard.get(cardSelected).get(res)+1));
                else
                    outputCard.get(cardSelected).put(res, 1);
            }
        }
        fade(inputImage4);
        hideAllImage();
        showCard();
    }


    public void setInputCard(HashMap<Integer, HashMap<Resource, Integer>> inputCard) {
        this.inputCard = inputCard;
    }

    public void setOutputCard(HashMap<Integer, HashMap<Resource, Integer>> outputCard) {
        this.outputCard = outputCard;
    }

    public void setSelectedCard(ArrayList<Integer> selectedCard) {
        this.selectedCard = selectedCard;
    }

    @Override
    public void setView(GUIView view) {
        this.view=view;
    }

    @Override
    public void errorMessage(String header, String context) {
        Alert error = new Alert(Alert.AlertType.INFORMATION);
        error.setHeaderText(header);
        error.setContentText(context);
        error.show();
    }
}

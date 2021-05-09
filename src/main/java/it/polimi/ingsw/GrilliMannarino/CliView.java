package it.polimi.ingsw.GrilliMannarino;

import javafx.application.Application;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class CliView extends Application implements ClientViewInterface{



    @Override
    public void activateLeaderCard(Integer activatedCard) {

    }

    @Override
    public void sellingLeaderCard(Integer cardCode) {

    }

    @Override
    public void viewError(String errorMessage) {
        System.out.println(errorMessage.toUpperCase(Locale.ROOT));
    }

    @Override
    public void showMarbleMarket(String[][] marbleList, String marbleOut) {
       /* System.out.println("Marble Market:");
        System.out.println();
        int y=0;
        System.out.format("%15");
        for(int x=0; x<marbleList.length; x++){
            System.out.format("Column: %15s", x+1);
        }
        for (int x=0; x<marbleList.length; x++){
            System.out.print("Row " + y+1);
            for(int y=0; y<marbleList[x].length; y++){
                System.out.print(marbleList[x][y]);
            }
            System.out.println();
        }*/

    }

    @Override
    public void selectMarble(ArrayList<ArrayList<String>> returnedMarble) {

    }

    @Override
    public void addedResource(String marbleType, String insertRow) {

    }

    @Override
    public void showProductionCard(HashMap<Integer, Boolean> buyableCard) {

    }

    @Override
    public void setCardIntoProductionLine(Integer selectedCard, Integer positionCard) {

    }

    @Override
    public void isYourTurn() {

    }

    @Override
    public void checkPopeLine(boolean favorActive, Integer checkPosition, Integer faithPosition) {

    }

    @Override
    public void updateFaith(Integer faithPosition) {

    }

    @Override
    public void startGame() {

    }

    @Override
    public void updateResources(HashMap<String, Integer> chestResources, HashMap<String, HashMap<String, Integer>> wareHouseResources) {

    }

    @Override
    public void moveApplied() {

    }

    @Override
    public void looseResource() {

    }

    @Override
    public void createdNewGame(String messageString, Integer gameId) {

    }

    @Override
    public void enteredNewGame(String messageString, Integer gameId) {

    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}

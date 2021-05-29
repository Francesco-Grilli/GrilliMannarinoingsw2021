package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import it.polimi.ingsw.GrilliMannarino.Message.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class ActionsController implements SmallController {

    public Button getResourcesAction;
    public Button buyCardMarket;
    public Button produceNewResources;
    public Button swapResources;
    public Button skipTurn;
    public Button performLeaderAction;

    private GUIView view;

    @Override
    public void setView(GUIView view) {
        this.view = view;
        setButton();
    }

    private void setButton(){
        if(!view.isNormalAction()){
            getResourcesAction.setDisable(true);
            getResourcesAction.setOpacity(0);
            buyCardMarket.setDisable(true);
            buyCardMarket.setOpacity(0);
            produceNewResources.setDisable(true);
            produceNewResources.setOpacity(0);
        }
        if(!view.isLeaderAction()){
            performLeaderAction.setDisable(true);
        }
    }

    @Override
    public void errorMessage(String header, String context) {
        Alert error = new Alert(Alert.AlertType.INFORMATION);
        error.setHeaderText(header);
        error.setContentText(context);
        error.show();
    }

    public void getResourcesAction() {
        MarbleMarketMessage marbleMessage = new MarbleMarketMessage(view.getGameId(), view.getPlayerId());
        marbleMessage.setDisplayMarbleMarket(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                view.sendMessageToServer(marbleMessage);
            }
        }).start();
    }

    public void buyCardAction() {
        BuyProductionCardMessage buyProductionMessage = new BuyProductionCardMessage(view.getGameId(), view.getPlayerId());
        buyProductionMessage.setDisplayCard(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                view.sendMessageToServer(buyProductionMessage);
            }
        }).start();
    }

    public void skipTurnAction() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                view.skipTurn();
            }
        }).start();
    }

    public void swapResourcesAction() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                view.moveResource();
            }
        }).start();
    }

    public void produceResourcesAction() {
        ProductionMessage productionMessage = new ProductionMessage(view.getGameId(), view.getPlayerId());
        productionMessage.setDisplayCard(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                view.sendMessageToServer(productionMessage);
            }
        }).start();
    }

    public void leaderAction(ActionEvent actionEvent) {
        LeaderCardMessage message = new LeaderCardMessage(view.getGameId(), view.getPlayerId());
        message.setShowLeaderCard(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                view.sendMessageToServer(message);
            }
        }).start();
    }
}

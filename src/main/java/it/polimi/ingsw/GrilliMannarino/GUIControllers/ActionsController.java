package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import it.polimi.ingsw.GrilliMannarino.Message.BuyProductionCardMessage;
import it.polimi.ingsw.GrilliMannarino.Message.MarbleMarketMessage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class ActionsController implements SmallController {

    public Button getResourcesAction;
    public Button buyCardMarket;
    public Button produceNewResources;
    public Button performLeaderAction;
    public Button swapResources;
    public Button skipTurn;
    private GUIView view;

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

    public void getResourcesAction() {
        if(view.isNormalAction()) {
            MarbleMarketMessage marbleMessage = new MarbleMarketMessage(view.getGameId(), view.getPlayerId());
            marbleMessage.setDisplayMarbleMarket(true);
            view.sendMessageToServer(marbleMessage);
        }
        else{

        }
    }

    public void buyCardAction() {
        BuyProductionCardMessage buyProductionMessage = new BuyProductionCardMessage(view.getGameId(), view.getPlayerId());
        buyProductionMessage.setDisplayCard(true);
        view.sendMessageToServer(buyProductionMessage);
    }

    public void leaderAction() {

    }

    public void skipTurnAction() {

    }

    public void swapResourcesAction() {

    }

    public void produceResourcesAction() {

    }
}

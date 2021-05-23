package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import it.polimi.ingsw.GrilliMannarino.Message.BuyProductionCardMessage;
import it.polimi.ingsw.GrilliMannarino.Message.MarbleMarketMessage;
import it.polimi.ingsw.GrilliMannarino.Message.ProductionMessage;
import it.polimi.ingsw.GrilliMannarino.Message.TurnMessage;
import javafx.fxml.Initializable;
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
        view.sendMessageToServer(marbleMessage);
    }

    public void buyCardAction() {
        BuyProductionCardMessage buyProductionMessage = new BuyProductionCardMessage(view.getGameId(), view.getPlayerId());
        buyProductionMessage.setDisplayCard(true);
        view.sendMessageToServer(buyProductionMessage);
    }

    public void leaderAction() {

    }

    public void skipTurnAction() {
        TurnMessage turnMessage = new TurnMessage(view.getGameId(), view.getPlayerId());
        view.sendMessageToServer(turnMessage);
    }

    public void swapResourcesAction() {

    }

    public void produceResourcesAction() {
        ProductionMessage productionMessage = new ProductionMessage(view.getGameId(), view.getPlayerId());
        productionMessage.setDisplayCard(true);
        view.sendMessageToServer(productionMessage);
    }
}

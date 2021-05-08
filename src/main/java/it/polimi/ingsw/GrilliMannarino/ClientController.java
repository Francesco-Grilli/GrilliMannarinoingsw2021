package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.Message.*;

public class ClientController implements VisitorInterface {

    private final ClientViewInterface view;

    public ClientController(ClientViewInterface view){
        this.view = view;
    }

    @Override
    public void executeStartGame(StartGameMessage startGame) {
        view.startGame();
    }

    @Override
    public void executeLeaderCard(LeaderCardMessage leaderCard) {
        if(!leaderCard.isActivateCard()){
            if(!leaderCard.isSellingCard()){
                view.viewError("Error on LeaderCard action");
                return;
            }
            //code to selling card
            if(leaderCard.isActivationSellingCorrect())
                view.sellingLeaderCard(leaderCard.getCardCode());
            else
                view.viewError("LeaderCard has not been sold");
            return;
        }
        //code to activate card
        if(leaderCard.isActivationSellingCorrect())
            view.activateLeaderCard(leaderCard.getCardCode());
        else
            view.viewError("leaderCard has not been activated");
    }

    @Override
    public void executeMarbleMarket(MarbleMarketMessage marbleMarketMessage) {
        if(!marbleMarketMessage.isDisplayMarbleMarket()){
            if(!marbleMarketMessage.isDisplayMarblesReturned()){
                if(!marbleMarketMessage.isAddedResource()){
                    view.viewError("Error in MarbleMarket action");
                    return;
                }
                //code to check added resource
                if(marbleMarketMessage.isAddResourceCorrect())
                    view.addedResource(marbleMarketMessage.getMarbleType(), marbleMarketMessage.getInsertRow());
                else
                    view.viewError("Resource could not be added to warehouse");
                return;
            }
            //code to select the marbles
            view.selectMarble(marbleMarketMessage.getReturnedMarble());
            return;
        }
        //code to see the marble
        view.showMarbleMarket(marbleMarketMessage.getMarbleList(), marbleMarketMessage.getMarbleOut());
    }

    @Override
    public void executeBuyProduction(BuyProductionCardMessage buyProductionCardMessage) {
        if(!buyProductionCardMessage.isDisplayCard()){
            if(!buyProductionCardMessage.isSelectCard()){
                view.viewError("Error on buying production card action");
                return;
            }
            //code to check placed correct into productionLine
            if(buyProductionCardMessage.isPlaceCardCorrect())
                view.setCardIntoProductionLine(buyProductionCardMessage.getSelectedCard(), buyProductionCardMessage.getPositionCard());
            else
                view.viewError("Card cannot be placed or bought");
            return;
        }
        //code to display card
        view.showProductionCard(buyProductionCardMessage.getBuyableCard());
    }

    @Override
    public void executeProduction(ProductionMessage productionMessage) {
        if(!productionMessage.isSelectCard()){
            view.viewError("Error on Production action");
            return;
        }
        //code to check if the production was successful
        if(!productionMessage.isProductionCorrect())
            view.viewError("Production configuration wasn't correct");
    }

    @Override
    public void executeEndGame(EndGameMessage endGame) {

    }

    @Override
    public void executeTurnPlayer(TurnMessage turn) {
        view.isYourTurn();
    }

    @Override
    public void executeErrorMessage(ErrorMessage error) {
        view.viewError(error.getError());
    }

    @Override
    public void executePopeLine(PopeLineMessage popeLineMessage) {
        if(!popeLineMessage.isCheckPopeLine()){
            if(!popeLineMessage.isUpdatedPosition()){
                view.viewError("Error on PopeLine action");
                return;
            }
            //code to update faith
            view.updateFaith(popeLineMessage.getFaithPosition());
        }
        //code to check the popeline
        view.checkPopeLine(popeLineMessage.isFavorActive(), popeLineMessage.getCheckPosition(), popeLineMessage.getFaithPosition());
    }

    @Override
    public void executeResource(ResourceMessage resourceMessage) {
        view.updateResources(resourceMessage.getChestResources(), resourceMessage.getWareHouseResources());
    }

    @Override
    public void executeMoveResource(MoveResourceMessage moveResourceMessage) {
        if(!moveResourceMessage.isCanMove()){
            if(!moveResourceMessage.isForceSwap()){
                view.viewError("Error on Move Resources action");
                return;
            }
            // resources will be lost
            view.looseResource();
        }
        // resources have been moved correctly

        view.moveApplied();
    }

    @Override
    public void executeNewGame(NewGameMessage newGameMessage) {
        view.createdNewGame(newGameMessage.getMessageString(), newGameMessage.getGameId());
    }

    @Override
    public void executeEnterGame(EnterGameMessage enterGameMessage) {
        if(enterGameMessage.isEnterGame()){
            view.enteredNewGame(enterGameMessage.getMessageString(), enterGameMessage.getGameId());
        }
        else{
            view.viewError(enterGameMessage.getMessageString());
        }
    }

    @Override
    public void executeSaveStatus(SaveStatusMessage saveStatusMessage) {

    }
}

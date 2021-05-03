package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.Message.*;

public class ClientController implements VisitorInterface {

    private final ClientViewInterface view;

    public ClientController(ClientViewInterface view){
        this.view = view;
    }

    @Override
    public void executeLogin(LoginMessage login) {

    }

    @Override
    public void executeAccount(GuestMessage account) {

    }

    @Override
    public void executeStartGame(StartGameMessage startGame) {

    }

    @Override
    public void executeLeaderCard(LeaderCardMessage leaderCard) {
        if(!leaderCard.isActivateCard()){
            if(!leaderCard.isSellingCard()){
                view.sendError("Error on LeaderCard action");
                return;
            }
            //code to selling card
            if(leaderCard.isActivationSellingCorrect())
                view.sellingLeaderCard(leaderCard.getCardCode());
            else
                view.sendError("LeaderCard has not been sold");
            return;
        }
        //code to activate card
        if(leaderCard.isActivationSellingCorrect())
            view.activateLeaderCard(leaderCard.getCardCode());
        else
            view.sendError("leaderCard has not been activated");
        return;
    }

    @Override
    public void executeMarbleMarket(MarbleMarketMessage marbleMarketMessage) {
        if(!marbleMarketMessage.isDisplayMarbleMarket()){
            if(!marbleMarketMessage.isDisplayMarblesReturned()){
                if(!marbleMarketMessage.isAddedResource()){
                    view.sendError("Error in MarbleMarket action");
                    return;
                }
                //code to check added resource
                if(marbleMarketMessage.isAddResourceCorrect())
                    view.addedResource(marbleMarketMessage.getMarbleType(), marbleMarketMessage.getInsertRow());
                else
                    view.sendError("Resource could not be added to warehouse");
                return;
            }
            //code to select the marbles
            view.selectMarble(marbleMarketMessage.getReturnedMarble());
            return;
        }
        //code to see the marble
        view.showMarbleMarket(marbleMarketMessage.getMarbleList(), marbleMarketMessage.getMarbleOut());
        return;
    }

    @Override
    public void executeBuyProduction(BuyProductionCardMessage buyProductionCardMessage) {
        if(!buyProductionCardMessage.isDisplayCard()){
            if(!buyProductionCardMessage.isSelectCard()){
                view.sendError("Error on buying production card action");
                return;
            }
            //code to check placed correct into productionLine
            if(buyProductionCardMessage.isPlaceCardCorrect())
                view.setCardIntoProductionLine(buyProductionCardMessage.getSelectedCard(), buyProductionCardMessage.getPositionCard());
            else
                view.sendError("Card cannot be placed or bought");
            return;
        }
        //code to display card
        view.showProductionCard(buyProductionCardMessage.getBuyableCard());
        return;
    }

    @Override
    public void executeProduction(ProductionMessage productionMessage) {
        if(!productionMessage.isSelectCard()){
            view.sendError("Error on Production action");
            return;
        }
        //code to check if the production was successful
        if(!productionMessage.isProductionCorrect())
            view.sendError("Production configuration wasn't correct");
        return;
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
        view.sendError(error.getError());
    }

    @Override
    public void executePopeLine(PopeLineMessage popeLineMessage) {
        if(!popeLineMessage.isCheckPopeLine()){
            if(!popeLineMessage.isUpdatedPosition()){
                view.sendError("Error on PopeLine action");
            }
            //code to update faith
            view.updateFaith(popeLineMessage.getFaithPosition());
        }
        //code to check the popeline
        view.checkPopeLine(popeLineMessage.isFavorActive(), popeLineMessage.getCheckPosition(), popeLineMessage.getFaithPosition());
    }
}

package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.Internet.Client;
import it.polimi.ingsw.GrilliMannarino.Message.*;

public class ClientController implements VisitorInterface {

    private final ClientView view;
    private Client client;


    public ClientController(ClientView view){
        this.view = view;
        view.setController(this);
        client = new Client();
        client.start();
        information();
        view.setUpGame();
    }

    public void receiveMessageFromServer(){
        client.receiveMessageFromServer().execute(this);
    }

    private void information(){
        LoginMessage message = null;
        do{
            client.setUpInformation(view.setUpInformation());
            message = client.getUpInformation();
            view.getUpInformation(message);
        }while(!message.isCorrectLogin());

    }

    public void sendMessageToServer(MessageInterface message){
        client.sendMessageToServer(message);
        MessageInterface exec = client.receiveMessageFromServer();
        exec.execute(this);
    }

    @Override
    public void executeStartGame(StartGameMessage startGame) {
        if(startGame.isStart()) {
            view.startGame();
            receiveMessageFromServer();
        }
        else{
            view.createdNewGame("Wait not enough player", startGame.getGameId());
        }
    }

    @Override
    public void executeLeaderCard(LeaderCardMessage leaderCard) {
        if(!leaderCard.isShowLeaderCard()) {
            if (!leaderCard.isActivateCard()) {
                if(!leaderCard.isSelectLeaderCard()){
                    if (!leaderCard.isSellingCard()) {
                        view.viewError("Error on LeaderCard action");
                        return;
                    }
                    //code to selling card
                    if (leaderCard.isActivationSellingCorrect())
                        view.finishedLeaderAction("Leader Card has been correctly sold");
                    else
                        view.viewError("Leader Card has not been sold");
                    return;
                }
                //code to selectLeadercard
                view.selectLeaderCard(leaderCard.getCards());

            }
            //code to activate card
            if (leaderCard.isActivationSellingCorrect())
                view.finishedLeaderAction("Leader Card has been correctly activated, you have finished Leader action");
            else
                view.viewError("Leader Card has not been activated");
            return;
        }
        //code to show leaderCard
        view.showLeaderCard(leaderCard.getCards());
    }

    @Override
    public void executeMarbleMarket(MarbleMarketMessage marbleMarketMessage) {
        if(!marbleMarketMessage.isDisplayMarbleMarket()){
            if(!marbleMarketMessage.isDisplayMarblesReturned()) {
                if (!marbleMarketMessage.isCheckReturnedResource()) {
                    if (!marbleMarketMessage.isAddedResource()) {
                        if (!marbleMarketMessage.isDestroyRemaining()) {
                            view.viewError("Error in MarbleMarket action");
                            return;
                        }
                        //code to check added resource
                        view.finishedNormalAction("You have finished Marble action");
                    }
                    //code to check added resource

                    view.addedResource(marbleMarketMessage.getResourceType(), marbleMarketMessage.getInsertRow(), marbleMarketMessage.getReturnedResource(), marbleMarketMessage.isAddResourceCorrect());
                    return;
                }
                //code to check the returned resources
                view.checkReturnedResource(marbleMarketMessage.getReturnedResource());
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
            if(buyProductionCardMessage.isPlaceCardCorrect()) {
                view.setCardIntoProductionLine(buyProductionCardMessage.getSelectedCard(), buyProductionCardMessage.getPositionCard(), buyProductionCardMessage.getCardInProductionline());
                view.finishedNormalAction("You have placed the card correctly");
            }
            else
                view.viewError("Card cannot be placed or bought");
            return;
        }
        //code to display card
        view.showCardMarket(buyProductionCardMessage.getBuyableCard());
    }

    @Override
    public void executeProduction(ProductionMessage productionMessage) {
        if(!productionMessage.isSelectCard()) {
            if (!productionMessage.isSelectCard()) {
                view.viewError("Error on Production action");
                return;
            }
            //code to check if the production was successful
            if (!productionMessage.isProductionCorrect())
                view.viewError("Production configuration wasn't correct");
            else
                view.finishedNormalAction("Production configuration was correct, you have produced");
            return;
        }
        //code to show card into production
        view.showProductionCard(productionMessage.getProductionCard());
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
            receiveMessageFromServer();
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
        if(newGameMessage.isNewGame()){
            view.createdNewGame(newGameMessage.getMessageString(), newGameMessage.getGameId());
        }
        else {
            view.printInformation("Error on creating a new game");
            view.setUpGame();
        }
    }

    @Override
    public void executeEnterGame(EnterGameMessage enterGameMessage) {
        if(enterGameMessage.isEnterGame()){
            view.enteredNewGame(enterGameMessage.getMessageString(), enterGameMessage.getGameId());
            receiveMessageFromServer();
        }
        else{
            view.printInformation(enterGameMessage.getMessageString());
            view.setUpGame();
        }
    }

    @Override
    public void executeSaveStatus(SaveStatusMessage saveStatusMessage) {

    }

    @Override
    public void executeStartingResource(StartingResourceMessage startingResource) {
        if(startingResource.isFaith())
            view.updateFaith(startingResource.getFaithToAdd());
        if(startingResource.isResource()){
            view.printInformation(startingResource.getMessageToShow());
            view.selectMarbleStarting(startingResource.getMarblesToSelect());
        }
        else if(startingResource.isPlaceResource()){
            if(startingResource.isAddResourceCorrect()){
                view.printInformation("Resource has been placed correctly!");
            }
            else
                view.printInformation("Resource has NOT been placed!");
            view.placeResourceStarting(startingResource.getResourcesLeft());
        }
    }

    @Override
    public void executePopeLineSingle(PopeLineSingleMessage popeLineSingleMessage) {
        if(!popeLineSingleMessage.isCheckPopeLine()){
            if(!popeLineSingleMessage.isUpdatedPosition()){
                view.viewError("Error on PopeLine action");
                return;
            }
            //code to update faith
            view.updateFaithSingle(popeLineSingleMessage.getFaithPosition(), popeLineSingleMessage.getLorenzoFaith());
            receiveMessageFromServer();
        }
        //code to check the popeline
        view.checkPopeLineSingle(popeLineSingleMessage.isFavorActive(), popeLineSingleMessage.getCheckPosition(), popeLineSingleMessage.getFaithPosition());
    }
}

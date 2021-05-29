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
        MessageInterface message = client.receiveMessageFromServer();
        message.execute(this);
    }

    private void information(){
        LoginMessage message = null;
        do{
            view.setUpInformation();
            message = client.getUpInformation();
            view.getUpInformation(message);
        }while(!message.isCorrectLogin());
    }

    public void sendInformationToServer(String nickname, String password, boolean newAccount){
        LoginMessage message = new LoginMessage(nickname);
        message.setPassword(password);
        message.setNewAccount(newAccount);
        client.setUpInformation(message);
    }

    public void sendMessageToServer(MessageInterface message){
        client.sendMessageToServer(message);
        MessageInterface exec = client.receiveMessageFromServer();
        exec.execute(this);
    }

    @Override
    public void executeStartGame(StartGameMessage startGame) {
        if(startGame.isStart()) {
            view.startGame(startGame.getNumberPlayer());
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
                return;
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
        if(!marbleMarketMessage.isSwapRow()) {
            if (!marbleMarketMessage.isDisplayMarbleMarket()) {
                if (!marbleMarketMessage.isDisplayMarblesReturned()) {
                    if (!marbleMarketMessage.isCheckReturnedResource()) {
                        if (!marbleMarketMessage.isAddedResource()) {
                            if (!marbleMarketMessage.isDestroyRemaining()) {
                                view.viewError("Error in MarbleMarket action");
                                return;
                            }
                            //code to check added resource
                            view.finishedNormalAction("You have finished Marble action");
                            return;
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
            return;
        }
        //code to swap row inside marbleMarket

        if(marbleMarketMessage.isCanMove()){
            view.moveAppliedIntoMarbleMarket(marbleMarketMessage.getReturnedResource());
        }
        else {
            // resources will be lost
            view.looseResourceIntoMarbleMarket(marbleMarketMessage.getReturnedResource());
        }

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
        if(!productionMessage.isDisplayCard()) {
            if(!productionMessage.isResolveUnknown()){
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
            //code to resolve unknown
            view.resolveUnknown(productionMessage.getInputCard(), productionMessage.getOutputCard(), productionMessage.getSelectedCard());
            return;
        }
        //code to show card into production
        view.showProductionCard(productionMessage.getProductionCard());
    }

    @Override
    public void executeEndGame(EndGameMessage endGame) {
        view.endGame(endGame.getPlayerRanking());
    }

    @Override
    public void executeTurnPlayer(TurnMessage turn) {
        view.isYourTurn();
    }

    @Override
    public void executeErrorMessage(ErrorMessage error) {
        if(error.isYourTurn())
            view.viewError(error.getError());
        else
            view.printInformation(error.getError());
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
            return;
        }
        //code to check the popeline
        view.checkPopeLine(popeLineMessage.isFavorActive(), popeLineMessage.getCheckPosition(), popeLineMessage.getFaithPosition());
        receiveMessageFromServer();
    }

    @Override
    public void executeResource(ResourceMessage resourceMessage) {
        view.updateResources(resourceMessage.getChestResources(), resourceMessage.getWareHouseResources());
        receiveMessageFromServer();
    }

    @Override
    public void executeMoveResource(MoveResourceMessage moveResourceMessage) {
        if(moveResourceMessage.isCanMove()){
            // resources have been moved correctly
            view.moveApplied();
        }
        else {
            // resources will be lost
            view.looseResource();
        }
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
    public void executePopeLineSingle(PopeLineSingleMessage singleMessage) {
        if(!singleMessage.isCheckPopeLine()){
            if(!singleMessage.isUpdatedPosition()){
                view.viewError("Error on PopeLine action");
                return;
            }
            //code to update faith
            view.updateFaithSingle(singleMessage.getFaithPosition(), singleMessage.getLorenzoFaith());
            receiveMessageFromServer();
            return;
        }
        //code to check the popeline
        view.checkPopeLineSingle(singleMessage.isFavorActive(), singleMessage.getCheckPosition(), singleMessage.getFaithPosition(), singleMessage.getLorenzoFaith(), singleMessage.isLorenzoFavorActive());
        receiveMessageFromServer();
    }

    @Override
    public void executeLorenzoAction(LorenzoTokenMessage lorenzoTokenMessage) {
        if(lorenzoTokenMessage.getToken().getNumber()==0){
            view.printInformation("Lorenzo have added two point of faith");
        }
        else if(lorenzoTokenMessage.getToken().getNumber()==1){
            view.printInformation("Lorenzo have added one point of faith and re-rolled the action");
        }
        else{
            view.printInformation("Lorenzo have discharged 2 card from the card market of faction " + lorenzoTokenMessage.getToken().getFaction().toString());
        }
        receiveMessageFromServer();
    }
}

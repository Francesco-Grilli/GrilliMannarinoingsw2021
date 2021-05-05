package it.polimi.ingsw.GrilliMannarino.Message;

import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

public interface VisitorInterface {

    void executeLogin(LoginMessage login);
    void executeStartGame(StartGameMessage startGame);
    void executeLeaderCard(LeaderCardMessage leaderCard);
    void executeMarbleMarket(MarbleMarketMessage marbleMarketMessage);
    void executeBuyProduction(BuyProductionCardMessage buyProductionCardMessage);
    void executeProduction(ProductionMessage productionMessage);
    void executeEndGame(EndGameMessage endGame);
    void executeTurnPlayer(TurnMessage turn);
    void executeErrorMessage(ErrorMessage error);
    void executePopeLine(PopeLineMessage popeLineMessage);
    void executeResource(ResourceMessage resourceMessage);
    void executeMoveResource(MoveResourceMessage moveResourceMessage);
    void executeCreateAccount(CreateAccountMessage createAccountMessage);
    void executeNewGame(NewGameMessage newGameMessage);

}

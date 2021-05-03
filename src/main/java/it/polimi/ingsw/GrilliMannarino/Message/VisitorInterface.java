package it.polimi.ingsw.GrilliMannarino.Message;

public interface VisitorInterface {

    void executeLogin(LoginMessage login);
    void executeAccount(GuestMessage account);
    void executeStartGame(StartGameMessage startGame);
    void executeLeaderCard(LeaderCardMessage leaderCard);
    void executeMarbleMarket(MarbleMarketMessage marbleMarketMessage);
    void executeBuyProduction(BuyProductionCardMessage buyProductionCardMessage);
    void executeProduction(ProductionMessage productionMessage);
    void executeEndGame(EndGameMessage endGame);
    void executeTurnPlayer(TurnMessage turn);
    void executeErrorMessage(ErrorMessage error);
    void executePopeLine(PopeLineMessage popeLineMessage);

}

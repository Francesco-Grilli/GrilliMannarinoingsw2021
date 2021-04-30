package it.polimi.ingsw.GrilliMannarino.Message;

public interface VisitorInterface {

    void executeLogin(LoginMessage login);
    void executeAccount(GuestMessage account);
    void executeStartGame(StartGameMessage startGame);
    void executeLeaderCard(LeaderCardMessage leaderCard);
    void executeBuyMarket(MarbleMarketMessage buyMarket);
    void executeBuyProduction(BuyProductionMessage buyProduction);
    void executeProduction(ProductionMessage productionMessage);
    void executeEndGame(EndGameMessage endGame);
    void executeTurnPlayer(TurnMessage turn);

}

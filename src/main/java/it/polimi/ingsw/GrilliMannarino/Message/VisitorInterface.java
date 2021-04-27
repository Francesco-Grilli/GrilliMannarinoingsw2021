package it.polimi.ingsw.GrilliMannarino.Message;

public interface VisitorInterface {

    void executeLogin(LoginMessage login);
    void executeAccount(AccountMessage account);
    void executeStartGame(StartGameMessage startGame);
    void executeLeaderCard(LeaderCardMessage leaderCard);
    void executeBuyMarket(BuyMarketMessage buyMarket);
    void executeBuyProduction(BuyProductionMessage buyProduction);
    void executeProduction(ProductionMessage productionMessage);
    void executeEndGame(EndGameMessage endGame);

}

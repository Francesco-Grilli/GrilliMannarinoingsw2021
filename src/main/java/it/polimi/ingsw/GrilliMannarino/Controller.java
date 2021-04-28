package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.Message.*;

public class Controller implements VisitorInterface {

    //implements all method needed to communicate with client

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

    }

    @Override
    public void executeBuyMarket(MarbleMarketMessage buyMarket) {

    }

    @Override
    public void executeBuyProduction(BuyProductionMessage buyProduction) {

    }

    @Override
    public void executeProduction(ProductionMessage productionMessage) {

    }

    @Override
    public void executeEndGame(EndGameMessage endGame) {

    }

    @Override
    public void executeTurn(TurnMessage turn) {

    }
}

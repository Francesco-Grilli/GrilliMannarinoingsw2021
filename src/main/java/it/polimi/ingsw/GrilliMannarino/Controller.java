package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;
import it.polimi.ingsw.GrilliMannarino.Internet.Server;
import it.polimi.ingsw.GrilliMannarino.Message.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Controller implements VisitorInterface {

    //implements all method needed to communicate with client

    private Server server;
    private HashMap<Integer, Game> games;
    private Game game;

    public Controller(){
        server = new Server();
        game = new Game();
    }

    @Override
    public void executeLogin(LoginMessage login) {

    }

    @Override
    public void executeAccount(GuestMessage guest) {
        String playerNickname = guest.getNickName();
    }

    @Override
    public void executeStartGame(StartGameMessage startGame) {

    }

    @Override
    public void executeLeaderCard(LeaderCardMessage leaderCard) {

    }

    @Override
    public void executeBuyMarket(MarbleMarketMessage buyMarket) {

        if (!(game.getActivePlayer().getID() == buyMarket.getPalyerID())) {
            return;
            //should run exception??
        }

        if(!buyMarket.isDisplayMarbleMarket()){
            if (!buyMarket.isSelectColumnRow()){
                if(!buyMarket.isAddedResource()){
                    //should not enter here

                    MarbleMarketMessage message = new MarbleMarketMessage(buyMarket.getPalyerID());
                    server.sendMessageTo(buyMarket.getPalyerID(), message);
                    return;
                }
                //code to add one resource at a time

                try {
                    Marble res = Marble.valueOf(buyMarket.getMarbleType());
                    Row row = Row.valueOf(buyMarket.getInsertRow());
                    if(game.placeResource(row, Marble.getResource(res))){
                        MarbleMarketMessage message = new MarbleMarketMessage(buyMarket.getPalyerID());
                        message.setAddResourceCorrect(true);
                        message.setAddedResource(true);
                        server.sendMessageTo(buyMarket.getPalyerID(), message);
                    }
                    else{
                        MarbleMarketMessage message = new MarbleMarketMessage(buyMarket.getPalyerID());
                        message.setAddedResource(true);
                        server.sendMessageTo(buyMarket.getPalyerID(), message);
                    }
                    return;
                }
                catch(IllegalArgumentException e){
                    e.printStackTrace();
                    MarbleMarketMessage message = new MarbleMarketMessage(buyMarket.getPalyerID());
                    server.sendMessageTo(buyMarket.getPalyerID(), message);
                    return;
                }

            }
            // code to select column or row

            ArrayList<MarbleOption> marbles;
            if(buyMarket.getColumnRow().equals("column")){
                marbles = game.selectMarbleColumn(buyMarket.getColumnRowValue());
            }
            else{
                marbles = game.selectMarbleRow(buyMarket.getColumnRowValue());
            }

            ArrayList<ArrayList<String>> marbleList = new ArrayList<>();
            for(int x=0; x<marbles.size(); x++){
                ArrayList<Marble> tempArray = new ArrayList<>(marbles.get(x).getMarbles());
                ArrayList<String> tempArrayString = new ArrayList<>();
                for(Marble m : tempArray){
                    tempArrayString.add(m.toString());
                }
                marbleList.add(tempArrayString);
            }
            MarbleMarketMessage message = new MarbleMarketMessage(buyMarket.getPalyerID());
            message.setDisplayMarblesReturned(true);
            message.setReturnedMarble(marbleList);
            server.sendMessageTo(buyMarket.getPalyerID(), message);
            return;

        }
        //code to show marble market

        Marble[][] marbles = game.displayMarbleMarket();
        String[][] marbleString = new String[marbles.length][marbles[0].length];
        for (int x=0; x<marbles.length;x++){
            for (int y=0; y<marbles[x].length; y++){
                marbleString[x][y] = marbles[x][y].toString();
            }
        }
        MarbleMarketMessage message = new MarbleMarketMessage(buyMarket.getPalyerID());
        message.setMarbleList(marbleString);
        message.setDisplayMarbleMarket(true);
        message.setMarbleOut(game.displayMarbleOut().toString());
        server.sendMessageTo(buyMarket.getPalyerID(), message);
        return;
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

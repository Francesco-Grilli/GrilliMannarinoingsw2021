package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;
import it.polimi.ingsw.GrilliMannarino.Internet.Server;
import it.polimi.ingsw.GrilliMannarino.Message.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Controller implements VisitorInterface {

    //implements all method needed to communicate with client

    private Server server;
    private HashMap<Integer, Game> activeGames;
    private Game game;
    private boolean leaderCardAction = true;
    private boolean normalAction = true;
    private boolean endGame = true;

    public Controller(){
        server = new Server();
        game = new Game();
    }

    public void startController(){

        server.getMessageFrom(game.getActivePlayer().getID()).execute(this); //get startGame message

        do{

            do{

                try{
                    server.getMessageFrom(game.getActivePlayer().getID()).execute(this);
                }
                catch (NullPointerException e){
                    executeErrorMessage(new ErrorMessage(game.getActivePlayer().getID(), game.getGameId()));
                }

            }while(playerCanStillPlay());

            nextTurn();

        }while(endGame);


    }

    private void nextTurn(){
        TurnMessage message = new TurnMessage(game.getActivePlayer().getID());
        server.sendMessageTo(game.getActivePlayer().getID(), message);

        leaderCardAction = true;
        normalAction = true;
        game.turnExecution();


        TurnMessage nextPlayer = new TurnMessage(game.getActivePlayer().getID());
        server.sendMessageTo(game.getActivePlayer().getID(), nextPlayer);
    }

    private boolean playerCanStillPlay(){
        if(normalAction){
            return true;
        }
        else{
            if(game.hasActiveLeaderCard() && leaderCardAction)
                return true;
            else
                return false;
        }
    }


    private void startGamePlayer(){
        ArrayList<Integer> player = new ArrayList<>(game.getPlayerID());
        player.forEach((p) -> server.sendMessageTo(p, new StartGameMessage(p, player.size(), game.getGameId())));
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
        startGamePlayer();
    }

    @Override
    public void executeLeaderCard(LeaderCardMessage leaderCard) {
        if(!leaderCard.isSellingCard()){
            if(!leaderCard.isCanActivate()){
                server.sendMessageTo(leaderCard.getPlayerId(), new LeaderCardMessage(leaderCard.getPlayerId(), game.getGameId()));
                return;
            }
            //code to activate leaderCard

            LeaderCardMessage message = new LeaderCardMessage(leaderCard.getPlayerId(), game.getGameId());
            if (game.activateLeaderCard(leaderCard.getCardCode())) {
                message.setActivationSellingCorrect(true);
            }
            server.sendMessageTo(leaderCard.getPlayerId(), message);
            return;
        }
        //code for selling the leaderCard

        LeaderCardMessage message = new LeaderCardMessage(leaderCard.getPlayerId(), game.getGameId());
        message.setSellingCard(true);
        if(game.canSellLeaderCard(leaderCard.getCardCode())){
            message.setActivationSellingCorrect(true);
            if(game.sellLeaderCard(leaderCard.getCardCode())){
                updatePopeLine(game);
            }
        }
        server.sendMessageTo(leaderCard.getPlayerId(), message);
        return;

    }

    private void updatePopeLine(Game game){
        HashMap<Integer, Boolean> check = game.checkPopeLine();
        Integer popePosition = game.getPopeLinePosition();
        for(Integer playerId : check.keySet()){
            PopeLineMessage message = new PopeLineMessage(playerId, game.getGameId());
            message.setCheckPopeLine(check.get(playerId));
            message.setCheckPosition(popePosition);
            server.sendMessageTo(playerId, message);
        }
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
            for (MarbleOption marble : marbles) {
                ArrayList<Marble> tempArray = new ArrayList<>(marble.getMarbles());
                ArrayList<String> tempArrayString = new ArrayList<>();
                for (Marble m : tempArray) {
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
    public void executeBuyProduction(BuyProductionMessage buyProduction){
        if(game.getActivePlayer().getID()!=buyProduction.getPlayerId())
            return;

        if(!buyProduction.isDisplayCard()){
            if(!buyProduction.isSelectCard()){

                //errore
            }
            //code to buy production card from market and put into productionLine

            Integer cardCode = buyProduction.getSelectedCard();
            Integer cardPosition = buyProduction.getPositionCard();
            CreationCard card = game.getCardFromCode(cardCode);
            if(card==null) {
                throw new NullPointerException();
            }
            BuyProductionMessage message = new BuyProductionMessage(buyProduction.getPlayerId());
            message.setSelectCard(true);
            if(game.buyCreationCard(card, cardPosition)){
                message.setPlaceCardCorrect(true);
            }

        }
        //code to display cards to the client
        HashMap<Faction, HashMap<Integer, Map.Entry<CreationCard, Boolean>>> cards = game.displayCreationCard();
        HashMap<Integer, Boolean> returnedCards = new HashMap<>();
        for(Faction fac : cards.keySet()){
            for(Integer cardCode : cards.get(fac).keySet()){
                returnedCards.put(cardCode, cards.get(fac).get(cardCode).getValue());
            }
        }
        BuyProductionMessage message = new BuyProductionMessage(buyProduction.getPlayerId());
        message.setDisplayCard(true);
        message.setBuyableCard(returnedCards);
        server.sendMessageTo(buyProduction.getPlayerId(), message);
        return;
    }

    @Override
    public void executeProduction(ProductionMessage productionMessage) {
        if(game.getActivePlayer().getID()!=productionMessage.getPlayerId())
            return;

        if(!productionMessage.isDisplayCard()){
            if(!productionMessage.isSelectCard()){
                ProductionMessage message = new ProductionMessage(productionMessage.getPlayerId());
                server.sendMessageTo(productionMessage.getPlayerId(), message);
                return;
            }
            //code to produce with selected card
            ProductionMessage message = new ProductionMessage(productionMessage.getPlayerId());
            message.setSelectCard(true);
            ArrayList<CreationCard> cardList = new ArrayList<>();
            ArrayList<Integer> cardStringList = new ArrayList<>(productionMessage.getSelectedCard());
            for(Integer i : cardStringList){
                cardList.add(game.getCardFromCode(i));
            }
            if(game.startProduction(cardList)){
                message.setProductionCorrect(true);
            }
            server.sendMessageTo(productionMessage.getPlayerId(), message);
            return;
        }
        //code to display card into production to the client
        HashMap<Integer, CreationCard> cards = game.displayCardInProductionLine();
        ArrayList<Integer> cardList = new ArrayList<>();
        for(Integer i : cards.keySet())
            cardList.add(i);
        ProductionMessage message = new ProductionMessage(productionMessage.getPlayerId());
        message.setDisplayCard(true);
        message.setProductionCard(cardList);
        server.sendMessageTo(productionMessage.getPlayerId(), message);
        return;

    }

    @Override
    public void executeEndGame(EndGameMessage endGame) {

    }

    @Override
    public void executeTurnPlayer(TurnMessage turn) {
        leaderCardAction = false;
        normalAction = false;
    }

    @Override
    public void executeErrorMessage(ErrorMessage error) {
        ErrorMessage message = new ErrorMessage(error.getPlayerId(), error.getGameId());
        server.sendMessageTo(error.getPlayerId(), message);
        return;
    }

    @Override
    public void executePopeLine(PopeLineMessage popeLineMessage) {

    }
}

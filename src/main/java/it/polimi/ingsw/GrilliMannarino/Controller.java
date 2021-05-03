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
                    executeErrorMessage(new ErrorMessage(game.getGameId(), game.getActivePlayer().getID()));
                }

            }while(playerCanStillPlay());

            nextTurn();

        }while(game.isEndGame());


    }

    private void nextTurn(){
        TurnMessage message = new TurnMessage(game.getGameId(), game.getActivePlayer().getID());
        server.sendMessageTo(game.getActivePlayer().getID(), message);

        game.setLeaderCardAction(true);
        game.setNormalAction(true);
        game.turnExecution();


        TurnMessage nextPlayer = new TurnMessage(game.getGameId(), game.getActivePlayer().getID());
        server.sendMessageTo(game.getActivePlayer().getID(), nextPlayer);
    }

    private boolean playerCanStillPlay(){
        if(game.isNormalAction()){
            return true;
        }
        else{
            return game.hasActiveLeaderCard() && game.isLeaderCardAction();
        }
    }


    private void startGamePlayer(){
        ArrayList<Integer> player = new ArrayList<>(game.getPlayerID());
        player.forEach((p) -> server.sendMessageTo(p, new StartGameMessage(game.getGameId(), p, player.size())));
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

        if ((game.getActivePlayer().getID() != leaderCard.getPlayerId())) {
            return;
            //should run exception??
        }
        if(!game.isLeaderCardAction()){
            sendErrorMessage(leaderCard.getGameId(), leaderCard.getPlayerId(), "No Leader Card action left!");
            return;
        }

        if(!leaderCard.isSellingCard()){
            if(!leaderCard.isCanActivate()){
                sendErrorMessage(leaderCard.getGameId(), leaderCard.getPlayerId(), "Error on Leader Card action");
                return;
            }
            //code to activate leaderCard

            LeaderCardMessage message = new LeaderCardMessage(leaderCard.getGameId(), leaderCard.getPlayerId());
            if (game.activateLeaderCard(leaderCard.getCardCode())) {
                message.setActivationSellingCorrect(true);
            }
            server.sendMessageTo(leaderCard.getPlayerId(), message);
            return;
        }
        //code for selling the leaderCard

        LeaderCardMessage message = new LeaderCardMessage(leaderCard.getGameId(), leaderCard.getPlayerId());
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

    //updatePopeLine if someone has activated a new favor
    private void updatePopeLine(Game game){
        HashMap<Integer, Map.Entry<Integer, Boolean>> check = game.checkPopeLine();
        Integer popePosition = game.getPopeLinePosition();
        for(Integer playerId : check.keySet()){
            PopeLineMessage message = new PopeLineMessage(game.getGameId(), playerId);
            message.setCheckPopeLine(true);
            message.setFavorActive(check.get(playerId).getValue());
            message.setFaithPosition(check.get(playerId).getKey());
            message.setCheckPosition(popePosition);
            server.sendMessageTo(playerId, message);
        }
    }

    //updateFaith if someone added some faith without trigger new favor
    private void updateFaith(Game game){
        HashMap<Integer, Integer> playersFaith = game.getPlayersFaith();
        for(Integer playerId : playersFaith.keySet()){
            PopeLineMessage message = new PopeLineMessage(game.getGameId(), playerId);
            message.setUpdatedPosition(true);
            message.setFaithPosition(playersFaith.get(playerId));
            server.sendMessageTo(playerId, message);
        }
    }

    @Override
    public void executeBuyMarket(MarbleMarketMessage buyMarket) {

        if ((game.getActivePlayer().getID() != buyMarket.getPlayerId())) {
            return;
            //should run exception??
        }
        if(!game.isNormalAction()){
            sendErrorMessage(buyMarket.getGameId(), buyMarket.getPlayerId(), "No normal action left!");
            return;
        }

        if(!buyMarket.isDisplayMarbleMarket()){
            if (!buyMarket.isSelectColumnRow()){
                if(!buyMarket.isAddedResource()){
                    if(!buyMarket.isDestroyRemaining()){
                        //should not enter here

                        sendErrorMessage(buyMarket.getGameId(), buyMarket.getPlayerId(), "Error on Buying marble at the market");
                        return;
                    }
                    //code to destroy remaining resources and add pope faith

                    MarbleMarketMessage message = new MarbleMarketMessage(buyMarket.getGameId(), buyMarket.getPlayerId());
                    message.setDestroyRemaining(true);
                    game.setNormalAction(false);
                    if(!buyMarket.getReturnedResource().isEmpty()){
                        int numberOfResource = buyMarket.getReturnedResource().size();
                        if(game.addFaithExceptThis(buyMarket.getPlayerId(), numberOfResource)){
                            updatePopeLine(game);
                        }
                        else{
                            updateFaith(game);
                        }
                    }
                    server.sendMessageTo(buyMarket.getPlayerId(), message);
                    return;

                }
                //code to add one resource at a time

                try {
                    Marble res = Marble.valueOf(buyMarket.getMarbleType());
                    Row row = Row.valueOf(buyMarket.getInsertRow());
                    if(game.placeResource(row, Marble.getResource(res))){
                        MarbleMarketMessage message = new MarbleMarketMessage(buyMarket.getGameId(), buyMarket.getPlayerId());
                        message.setAddResourceCorrect(true);
                        message.setAddedResource(true);
                        server.sendMessageTo(buyMarket.getPlayerId(), message);
                    }
                    else{
                        MarbleMarketMessage message = new MarbleMarketMessage(buyMarket.getGameId(), buyMarket.getPlayerId());
                        message.setAddedResource(true);
                        server.sendMessageTo(buyMarket.getPlayerId(), message);
                    }
                    return;
                }
                catch(IllegalArgumentException e){
                    e.printStackTrace();
                    MarbleMarketMessage message = new MarbleMarketMessage(buyMarket.getGameId(), buyMarket.getPlayerId());
                    server.sendMessageTo(buyMarket.getPlayerId(), message);
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
            MarbleMarketMessage message = new MarbleMarketMessage(buyMarket.getGameId(), buyMarket.getPlayerId());
            message.setDisplayMarblesReturned(true);
            message.setReturnedMarble(marbleList);
            server.sendMessageTo(buyMarket.getPlayerId(), message);
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
        MarbleMarketMessage message = new MarbleMarketMessage(buyMarket.getGameId(), buyMarket.getPlayerId());
        message.setMarbleList(marbleString);
        message.setDisplayMarbleMarket(true);
        message.setMarbleOut(game.displayMarbleOut().toString());
        server.sendMessageTo(buyMarket.getPlayerId(), message);
        return;
    }

    @Override
    public void executeBuyProduction(BuyProductionMessage buyProduction){
        if(game.getActivePlayer().getID()!=buyProduction.getPlayerId())
            return;

        if(!game.isNormalAction()){
            sendErrorMessage(buyProduction.getGameId(), buyProduction.getPlayerId(), "No normal action left!");
            return;
        }

        if(!buyProduction.isDisplayCard()){
            if(!buyProduction.isSelectCard()){

                sendErrorMessage(buyProduction.getGameId(), buyProduction.getPlayerId(), "Error on buying new card production");
                return;
            }
            //code to buy production card from market and put into productionLine

            Integer cardCode = buyProduction.getSelectedCard();
            Integer cardPosition = buyProduction.getPositionCard();
            CreationCard card = game.getCardFromCode(cardCode);
            if(card==null) {
                throw new NullPointerException();
            }
            BuyProductionMessage message = new BuyProductionMessage(buyProduction.getGameId(), buyProduction.getPlayerId());
            message.setSelectCard(true);
            if(game.buyCreationCard(card, cardPosition)){
                game.setNormalAction(false);
                message.setPlaceCardCorrect(true);
            }
            server.sendMessageTo(buyProduction.getPlayerId(), message);
            return;

        }
        //code to display cards to the client
        HashMap<Faction, HashMap<Integer, Map.Entry<CreationCard, Boolean>>> cards = game.displayCreationCard();
        HashMap<Integer, Boolean> returnedCards = new HashMap<>();
        for(Faction fac : cards.keySet()){
            for(Integer cardCode : cards.get(fac).keySet()){
                returnedCards.put(cardCode, cards.get(fac).get(cardCode).getValue());
            }
        }
        BuyProductionMessage message = new BuyProductionMessage(buyProduction.getGameId(), buyProduction.getPlayerId());
        message.setDisplayCard(true);
        message.setBuyableCard(returnedCards);
        server.sendMessageTo(buyProduction.getPlayerId(), message);
        return;
    }

    @Override
    public void executeProduction(ProductionMessage productionMessage) {
        if(game.getActivePlayer().getID()!=productionMessage.getPlayerId())
            return;
        if(!game.isNormalAction()){
            sendErrorMessage(productionMessage.getGameId(), productionMessage.getPlayerId(), "No normal action left!");
            return;
        }


        if(!productionMessage.isDisplayCard()){
            if(!productionMessage.isSelectCard()){
                sendErrorMessage(productionMessage.getGameId(), productionMessage.getPlayerId(), "Error on producing with production card");
                return;
            }
            //code to produce with selected card
            ProductionMessage message = new ProductionMessage(productionMessage.getGameId(), productionMessage.getPlayerId());
            message.setSelectCard(true);
            ArrayList<CreationCard> cardList = new ArrayList<>();
            ArrayList<Integer> cardStringList = new ArrayList<>(productionMessage.getSelectedCard());
            for(Integer i : cardStringList){
                cardList.add(game.getCardFromCode(i));
            }
            if(game.startProduction(cardList)){
                game.setNormalAction(false);    //completed a normal action
                message.setProductionCorrect(true);
            }
            server.sendMessageTo(productionMessage.getPlayerId(), message);
            return;
        }
        //code to display card into production to the client
        HashMap<Integer, CreationCard> cards = game.displayCardInProductionLine();
        ArrayList<Integer> cardList = new ArrayList<>();
        cardList.addAll(cards.keySet());
        ProductionMessage message = new ProductionMessage(productionMessage.getGameId(), productionMessage.getPlayerId());
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
        game.setNormalAction(false);
        game.setLeaderCardAction(false);
    }

    @Override
    public void executeErrorMessage(ErrorMessage error) {
    }

    @Override
    public void executePopeLine(PopeLineMessage popeLineMessage) {

    }

    private void sendErrorMessage(Integer gameId, Integer playerId, String error){
        ErrorMessage message = new ErrorMessage(gameId, playerId);
        message.setError(error);
        server.sendMessageTo(message.getPlayerId(), message);
    }
}

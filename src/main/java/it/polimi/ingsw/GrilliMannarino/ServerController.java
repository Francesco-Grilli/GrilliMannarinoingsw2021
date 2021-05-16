package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;
import it.polimi.ingsw.GrilliMannarino.Internet.Server;
import it.polimi.ingsw.GrilliMannarino.Message.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ServerController implements VisitorInterface {
    //implements all method needed to communicate with client

    private Server server;
    private HashMap<Integer, Game> games;
    private HashMap<String, Integer> nicknamesList;
    private ArrayList<String> playerLogged;
    private Integer nextPlayerId = 1;
    private Integer nextGameId = 1;
    JSONObject nicknameListObj = new JSONObject();
    Scanner scan;
    Thread end = new Thread(() -> writeNickname());

    public ServerController(){
        server = new Server(this);
        nicknamesList = new HashMap<>();
        playerLogged = new ArrayList<>();
        games = new HashMap<>();
        scan = new Scanner(System.in);
        server.startConnection();
        loadNickname();
        Runtime.getRuntime().addShutdownHook(end);
    }

    public synchronized void receiveMessage(MessageInterface message){
        message.execute(this);
    }

    private synchronized void startGamePlayer(Integer gameId, Integer playerId){
        Game g = games.get(gameId);
        if(g.getPlayerID().size()==g.getNumberOfPlayer()) {
            Integer startingPlayer = g.startGame();
            ArrayList<Integer> player = g.getPlayerID();
            player.forEach((p) -> server.sendMessageTo(p, new StartGameMessage(gameId, p, true)));
            TurnMessage message = new TurnMessage(gameId, startingPlayer);
            message.setMyTurn(true);
            g.setStartingResource();
            server.sendMessageTo(startingPlayer, message);
        }
        else{
            server.sendMessageTo(playerId, new StartGameMessage(gameId, playerId, false));
        }
    }

    public synchronized boolean nicknameAlreadyPresent(String nickname) {
        return nicknamesList.containsKey(nickname);
    }

    public synchronized Integer addPlayer(String nickname){
        if(!nicknamesList.containsKey(nickname)){
            nicknamesList.put(nickname, nextPlayerId);
            JSONObject player = new JSONObject();
            player.put("nickname", nickname);
            player.put("playerId", String.valueOf(nextPlayerId));
            nicknameListObj.put(String.valueOf(nextPlayerId), player);
            nextPlayerId++;
            return nextPlayerId-1;
        }
        else{
            return null;
        }
    }

    public synchronized Integer logInPlayer(String nickname){
        if(nicknamesList.containsKey(nickname) && !playerLogged.contains(nickname)) {
            playerLogged.add(nickname);
            return nicknamesList.get(nickname);
        }
        else
            return null;
    }

    public synchronized void writeNickname(){
        System.out.println("Exiting...");
        nicknameListObj.put("nextPlayerId", String.valueOf(nextPlayerId));
        try{
            FileWriter file = new FileWriter("player.json");
            file.write(nicknameListObj.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void loadNickname(){
        try {
            FileReader file = new FileReader("player.json");
            JSONParser parser = new JSONParser();
            nicknameListObj = (JSONObject) parser.parse(file);
            nextPlayerId = Integer.parseInt((String) nicknameListObj.get("nextPlayerId"));
            for(int i=1; i<nextPlayerId; i++){
                JSONObject player = (JSONObject) nicknameListObj.get(String.valueOf(i));
                nicknamesList.put((String) player.get("nickname"), Integer.parseInt((String) player.get("playerId")));
            }

            file.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File does not exist!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void executeTurnPlayer(TurnMessage turn) {
        Game game = games.get(turn.getGameId());
        game.turnExecution();
        if(game.isEndGame()){
            endGame(game);
            games.remove(game.getGameId());
        }
        if (!game.isStartingResource()) {
            game.setStartingResource();
            ArrayList<Integer> player = new ArrayList<>(game.getPlayerID());
            int index = player.indexOf(game.getActivePlayer().getID());
            if(index == 1){
                ArrayList<ArrayList<Marble>> marbles = new ArrayList<>();
                ArrayList<Marble> m = new ArrayList<>(Marble.getOrderedMarble());
                marbles.add(m);
                StartingResourceMessage message = new StartingResourceMessage(game.getGameId(), game.getActivePlayer().getID());
                message.setResource(true);
                message.setMarblesToSelect(marbles);
                message.setMessageToShow("You are entitled to one resource");
                server.sendMessageTo(message.getPlayerId(), message);
            }
            else if (index == 2){
                ArrayList<ArrayList<Marble>> marbles = new ArrayList<>();
                ArrayList<Marble> m = new ArrayList<>(Marble.getOrderedMarble());
                marbles.add(m);
                game.addFaithTo(game.getActivePlayer().getID());
                StartingResourceMessage message = new StartingResourceMessage(game.getGameId(), game.getActivePlayer().getID());
                message.setResource(true);
                message.setMarblesToSelect(marbles);
                message.setMessageToShow("You are entitled to one resource and one point of faith");
                message.setFaith(true);
                message.setFaithToAdd(game.getPlayersFaith().get(message.getPlayerId()));
                server.sendMessageTo(message.getPlayerId(), message);
            }
            else if (index == 3){
                ArrayList<ArrayList<Marble>> marbles = new ArrayList<>();
                ArrayList<Marble> m = new ArrayList<>(Marble.getOrderedMarble());
                marbles.add(m);
                marbles.add(m);
                game.addFaithTo(game.getActivePlayer().getID());
                StartingResourceMessage message = new StartingResourceMessage(game.getGameId(), game.getActivePlayer().getID());
                message.setResource(true);
                message.setMarblesToSelect(marbles);
                message.setMessageToShow("You are entitled to two resources and one point of faith");
                message.setFaith(true);
                message.setFaithToAdd(game.getPlayersFaith().get(message.getPlayerId()));
                server.sendMessageTo(message.getPlayerId(), message);
            }
        }
        else{
            TurnMessage message = new TurnMessage(game.getGameId(), game.getActivePlayer().getID());
            message.setMyTurn(true);
            server.sendMessageTo(message.getPlayerId(), message);
        }
    }

    private synchronized void endGame(Game game){
        HashMap<String, Integer> ranking = new HashMap<>(game.getAllPoints());
        for(Integer player : game.getPlayerID()){
            EndGameMessage message = new EndGameMessage(game.getGameId(), player);
            server.sendMessageTo(player, message);
        }

    }

    @Override
    public synchronized void executeLeaderCard(LeaderCardMessage leaderCard) {
        Game game = games.get(leaderCard.getGameId());
        if ((game.getActivePlayer().getID() != leaderCard.getPlayerId())) {
            sendErrorMessage(leaderCard.getGameId(), leaderCard.getPlayerId(), "Error on playerId");
            return;
            //should run exception??
        }
        if(!game.isLeaderCardAction() || !game.hasActiveLeaderCard()){
            sendErrorMessage(leaderCard.getGameId(), leaderCard.getPlayerId(), "No active Leader Card or No action left!");
            return;
        }
        if(!leaderCard.isShowLeaderCard()) {
            if (!leaderCard.isSellingCard()) {
                if (!leaderCard.isActivateCard()) {
                    sendErrorMessage(leaderCard.getGameId(), leaderCard.getPlayerId(), "Error on Leader Card action");
                    return;
                }
                //code to activate leaderCard

                LeaderCardMessage message = new LeaderCardMessage(leaderCard.getGameId(), leaderCard.getPlayerId());
                message.setActivateCard(true);
                message.setCardCode(leaderCard.getCardCode());
                if (game.activateLeaderCard(leaderCard.getCardCode())) {
                    message.setActivationSellingCorrect(true);
                    game.setLeaderCardAction(false);
                }
                server.sendMessageTo(leaderCard.getPlayerId(), message);
                if (game.isActivatedEnd()) {
                    broadcastMessage(game, "The End is near");
                }
                return;
            }
            //code for selling the leaderCard

            LeaderCardMessage message = new LeaderCardMessage(leaderCard.getGameId(), leaderCard.getPlayerId());
            message.setSellingCard(true);
            message.setCardCode(leaderCard.getCardCode());
            if (game.canSellLeaderCard(leaderCard.getCardCode())) {
                message.setActivationSellingCorrect(true);
                game.setLeaderCardAction(false);
                if (game.sellLeaderCard(leaderCard.getCardCode())) {
                    updatePopeLine(game);
                }
            }
            server.sendMessageTo(leaderCard.getPlayerId(), message);
            return;
        }
        //code to show leadercard

        ArrayList<Integer> cards = new ArrayList<>(game.getLeaderCard());
        LeaderCardMessage message = new LeaderCardMessage(leaderCard.getPlayerId(), leaderCard.getGameId());
        message.setShowLeaderCard(true);
        message.setCards(cards);
        server.sendMessageTo(leaderCard.getPlayerId(), message);

    }

    //updatePopeLine if someone has activated a new favor
    private synchronized void updatePopeLine(Game game){
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
    private synchronized void updateFaith(Game game){
        HashMap<Integer, Integer> playersFaith = game.getPlayersFaith();
        for(Integer playerId : playersFaith.keySet()){
            PopeLineMessage message = new PopeLineMessage(game.getGameId(), playerId);
            message.setUpdatedPosition(true);
            message.setFaithPosition(playersFaith.get(playerId));
            server.sendMessageTo(playerId, message);
        }
    }

    @Override
    public synchronized void executeMarbleMarket(MarbleMarketMessage marbleMarketMessage) {
        Game game = games.get(marbleMarketMessage.getGameId());
        if ((game.getActivePlayer().getID() != marbleMarketMessage.getPlayerId())) {
            sendErrorMessage(marbleMarketMessage.getGameId(), marbleMarketMessage.getPlayerId(), "Error on playerId");
            return;
            //should run exception??
        }
        if(!game.isNormalAction()){
            sendErrorMessage(marbleMarketMessage.getGameId(), marbleMarketMessage.getPlayerId(), "No normal action left!");
            return;
        }

        if(!marbleMarketMessage.isDisplayMarbleMarket()){
            if (!marbleMarketMessage.isSelectColumnRow()){
                if(!marbleMarketMessage.isAddedResource()){
                    if(!marbleMarketMessage.isDestroyRemaining()){
                        //should not enter here

                        sendErrorMessage(marbleMarketMessage.getGameId(), marbleMarketMessage.getPlayerId(), "Error on Buying marble at the market");
                        return;
                    }
                    //code to destroy remaining resources and add pope faith

                    MarbleMarketMessage message = new MarbleMarketMessage(marbleMarketMessage.getGameId(), marbleMarketMessage.getPlayerId());
                    message.setDestroyRemaining(true);
                    game.setNormalAction(false);
                    if(!marbleMarketMessage.getReturnedResource().isEmpty()){
                        int numberOfResource = marbleMarketMessage.getReturnedResource().size();
                        if(game.addFaithExceptThis(marbleMarketMessage.getPlayerId(), numberOfResource)){
                            updatePopeLine(game);
                        }
                        else{
                            updateFaith(game);
                        }
                    }
                    if(game.isActivatedEnd()){
                        broadcastMessage(game, "The End is near");
                    }
                    server.sendMessageTo(marbleMarketMessage.getPlayerId(), message);
                    return;

                }
                //code to add one resource at a time
                Resource res = marbleMarketMessage.getResourceType();
                Row row = marbleMarketMessage.getInsertRow();
                MarbleMarketMessage message = new MarbleMarketMessage(marbleMarketMessage.getGameId(), marbleMarketMessage.getPlayerId());
                message.setAddedResource(true);
                message.setResourceType(marbleMarketMessage.getResourceType());
                message.setInsertRow(marbleMarketMessage.getInsertRow());
                message.setReturnedResource(marbleMarketMessage.getReturnedResource());
                if(game.placeResource(row, res) && marbleMarketMessage.getReturnedResource().contains(res)){
                    message.setAddResourceCorrect(true);
                    marbleMarketMessage.getReturnedResource().remove(res);
                    updateResources(game, marbleMarketMessage.getPlayerId());
                }
                server.sendMessageTo(marbleMarketMessage.getPlayerId(), message);
                return;
            }
            // code to select column or row

            ArrayList<MarbleOption> marbles;
            if(marbleMarketMessage.getColumnRow().equals("C")){
                marbles = game.selectMarbleColumn(marbleMarketMessage.getColumnRowValue());
            }
            else{
                marbles = game.selectMarbleRow(marbleMarketMessage.getColumnRowValue());
            }

            ArrayList<ArrayList<Marble>> marbleList = new ArrayList<>();
            for (MarbleOption marble : marbles) {
                ArrayList<Marble> tempArray = new ArrayList<>(marble.getMarbles());
                if (tempArray.size() != 1 || !tempArray.get(0).equals(Marble.WHITE)) {
                    marbleList.add(tempArray);
                }
            }
            MarbleMarketMessage message = new MarbleMarketMessage(marbleMarketMessage.getGameId(), marbleMarketMessage.getPlayerId());
            message.setDisplayMarblesReturned(true);
            message.setReturnedMarble(marbleList);
            server.sendMessageTo(marbleMarketMessage.getPlayerId(), message);
            return;

        }
        //code to show marble market

        Marble[][] marbles = game.displayMarbleMarket();
        MarbleMarketMessage message = new MarbleMarketMessage(marbleMarketMessage.getGameId(), marbleMarketMessage.getPlayerId());
        ArrayList<ArrayList<Marble>> marblesToReturn = new ArrayList<>();
        for(int x =0; x<marbles.length; x++){
            ArrayList<Marble> temp = new ArrayList<>();
            for(int y=0; y<marbles[x].length; y++){
                temp.add(y, marbles[x][y]);
            }
            marblesToReturn.add(temp);
        }
        message.setMarbleList(marblesToReturn);
        message.setDisplayMarbleMarket(true);
        message.setMarbleOut(game.displayMarbleOut());
        server.sendMessageTo(marbleMarketMessage.getPlayerId(), message);
    }

    @Override
    public synchronized void executeBuyProduction(BuyProductionCardMessage buyProductionCardMessage){
        Game game = games.get(buyProductionCardMessage.getGameId());
        if(game.getActivePlayer().getID()!= buyProductionCardMessage.getPlayerId()) {
            sendErrorMessage(buyProductionCardMessage.getGameId(), buyProductionCardMessage.getPlayerId(), "Error on playerId");
            return;
        }

        if(!game.isNormalAction()){
            sendErrorMessage(buyProductionCardMessage.getGameId(), buyProductionCardMessage.getPlayerId(), "No normal action left!");
            return;
        }

        if(!buyProductionCardMessage.isDisplayCard()){
            if(!buyProductionCardMessage.isSelectCard()){

                sendErrorMessage(buyProductionCardMessage.getGameId(), buyProductionCardMessage.getPlayerId(), "Error on buying new card production");
                return;
            }
            //code to buy production card from market and put into productionLine

            Integer cardCode = buyProductionCardMessage.getSelectedCard();
            Integer cardPosition = buyProductionCardMessage.getPositionCard();
            CreationCard card = game.getCardFromCode(cardCode);
            if(card==null) {
                sendErrorMessage(buyProductionCardMessage.getGameId(), buyProductionCardMessage.getPlayerId(), "Error on card code");
            }
            BuyProductionCardMessage message = new BuyProductionCardMessage(buyProductionCardMessage.getGameId(), buyProductionCardMessage.getPlayerId());
            message.setSelectCard(true);
            if(game.buyCreationCard(card, cardPosition)){
                game.setNormalAction(false);
                message.setPlaceCardCorrect(true);
                message.setSelectedCard(buyProductionCardMessage.getSelectedCard());
                message.setPositionCard(buyProductionCardMessage.getPositionCard());
                HashMap<Integer, CreationCard> cards = game.displayCardInProductionLine();
                HashMap<Integer, Integer> cardToReturn = new HashMap<>();
                for(Integer pos : cards.keySet()){
                    if(cards.get(pos)!=null){
                        cardToReturn.put(cards.get(pos).getCardCode(), pos);
                    }
                }
                message.setCardInProductionline(cardToReturn);
                updateResources(game, buyProductionCardMessage.getPlayerId());
            }
            if(game.isActivatedEnd()){
                broadcastMessage(game, "The End is near");
            }
            server.sendMessageTo(buyProductionCardMessage.getPlayerId(), message);
            return;

        }
        //code to display cards to the client
        HashMap<Faction, HashMap<Integer, Map.Entry<CreationCard, Boolean>>> cards = game.displayCreationCard();
        HashMap<Integer, Boolean> returnedCards = new HashMap<>();
        for(Faction fac : cards.keySet()){
            for(Integer value : cards.get(fac).keySet()){
                returnedCards.put(cards.get(fac).get(value).getKey().getCardCode(), cards.get(fac).get(value).getValue());
            }
        }
        BuyProductionCardMessage message = new BuyProductionCardMessage(buyProductionCardMessage.getGameId(), buyProductionCardMessage.getPlayerId());
        message.setDisplayCard(true);
        message.setBuyableCard(returnedCards);
        server.sendMessageTo(buyProductionCardMessage.getPlayerId(), message);
    }

    @Override
    public synchronized void executeProduction(ProductionMessage productionMessage) {
        Game game = games.get(productionMessage.getGameId());
        if(game.getActivePlayer().getID()!=productionMessage.getPlayerId()) {
            sendErrorMessage(productionMessage.getGameId(), productionMessage.getPlayerId(), "Error on playerId");
            return;
        }
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
            if(game.canProduce(cardList)){
                if(game.startProduction(cardList)){
                    updatePopeLine(game);
                }
                else
                    updateFaith(game);
                game.setNormalAction(false);    //completed a normal action
                message.setProductionCorrect(true);
                updateResources(game, productionMessage.getPlayerId());
            }
            if(game.isActivatedEnd()){
                broadcastMessage(game, "The End is near");
            }
            server.sendMessageTo(productionMessage.getPlayerId(), message);
            return;
        }
        //code to display card into production to the client
        HashMap<Integer, CreationCard> cards = game.displayCardInProductionLine();
        HashMap<Integer, Integer> cardToReturn = new HashMap<>();
        cards.forEach((pos, card) -> cardToReturn.put(card.getCardCode(), pos));
        ProductionMessage message = new ProductionMessage(productionMessage.getGameId(), productionMessage.getPlayerId());
        message.setDisplayCard(true);
        message.setProductionCard(cardToReturn);
        server.sendMessageTo(productionMessage.getPlayerId(), message);

    }

    @Override
    public synchronized void executeErrorMessage(ErrorMessage error) {
    }

    @Override
    public synchronized void executePopeLine(PopeLineMessage popeLineMessage) {

    }

    @Override
    public synchronized void executeResource(ResourceMessage resourceMessage) {

    }

    @Override
    public synchronized void executeMoveResource(MoveResourceMessage moveResourceMessage) {
        Game game = games.get(moveResourceMessage.getGameId());
        if ((game.getActivePlayer().getID() != moveResourceMessage.getPlayerId())) {
            sendErrorMessage(moveResourceMessage.getGameId(), moveResourceMessage.getPlayerId(), "Error on playerId");
            return;
        }

        try {
            Row one = moveResourceMessage.getRowOne();
            Row two = moveResourceMessage.getRowTwo();

            MoveResourceMessage message = new MoveResourceMessage(moveResourceMessage.getGameId(), moveResourceMessage.getPlayerId());
            message.setRowOne(one);
            message.setRowTwo(two);
            if(moveResourceMessage.isForceSwap()){
                message.setForceSwap(true);
                game.forceSwapLine(one, two);
                updateResources(game, moveResourceMessage.getPlayerId());
                if(game.isActivatedEnd()){
                    broadcastMessage(game, "The End is near");
                }
            }
            else{
                if(game.canSwapLine(one, two)){
                    game.forceSwapLine(one, two);
                    message.setCanMove(true);
                    updateResources(game, moveResourceMessage.getPlayerId());
                }
                else{
                    message.setCanMove(false);
                }
                message.setCanMove(game.canSwapLine(one, two));
            }
            server.sendMessageTo(moveResourceMessage.getPlayerId(), message);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            sendErrorMessage(moveResourceMessage.getGameId(), moveResourceMessage.getPlayerId(), "Error on Resource Moving");
        }


    }

    @Override
    public synchronized void executeNewGame(NewGameMessage newGameMessage) {
        Game game = new Game(nextGameId, newGameMessage.getNumberOfPlayer());
        games.put(nextGameId, game);
        nextGameId++;
        game.setPlayer(newGameMessage.getPlayerId(), newGameMessage.getNickname());
        NewGameMessage message = new NewGameMessage(game.getGameId(), newGameMessage.getPlayerId(), newGameMessage.getNickname());
        message.setNewGame(true);
        message.setMessageString("You have created and joined a new game with id: " + game.getGameId());
        server.sendMessageTo(newGameMessage.getPlayerId(), message);
    }

    @Override
    public synchronized void executeEnterGame(EnterGameMessage enterGameMessage) {
        boolean find = false;
        int skip =0;

        while(!find){
            Game game = firstGameFree(skip);
            if(game==null){
                EnterGameMessage message = new EnterGameMessage(null, enterGameMessage.getPlayerId(), enterGameMessage.getNickname());
                message.setMessageString("Error, no game available");
                server.sendMessageTo(enterGameMessage.getPlayerId(), message);
                return;
            }
            else {
                if (game.setPlayer(enterGameMessage.getPlayerId(), enterGameMessage.getNickname())) {
                    EnterGameMessage message = new EnterGameMessage(game.getGameId(), enterGameMessage.getPlayerId(), enterGameMessage.getNickname());
                    message.setEnterGame(true);
                    message.setMessageString("You have joined the game with id: " + game.getGameId());
                    find=true;
                    server.sendMessageTo(message.getPlayerId(), message);
                }
                else
                    skip++;
            }
        }

    }

    @Override
    public synchronized void executeSaveStatus(SaveStatusMessage saveStatusMessage) {

    }

    @Override
    public void executeStartingResource(StartingResourceMessage startingResource) {
        Game game = games.get(startingResource.getGameId());
        if(startingResource.isPlaceResource()){
            Row row = startingResource.getRowToPlace();
            Resource res = startingResource.getResourceToPlace();
            StartingResourceMessage message = new StartingResourceMessage(startingResource.getGameId(), startingResource.getPlayerId());
            message.setPlaceResource(true);
            message.setRowToPlace(row);
            message.setResourceToPlace(res);
            message.setResourcesLeft(startingResource.getResourcesLeft());
            if(game.placeResource(row, res) && startingResource.getResourcesLeft().contains(res)){
                message.setAddResourceCorrect(true);
                message.getResourcesLeft().remove(res);
                updateResources(game, startingResource.getPlayerId());
            }
            server.sendMessageTo(startingResource.getPlayerId(), message);
        }
        else{
            TurnMessage message = new TurnMessage(game.getGameId(), game.getActivePlayer().getID());
            message.setMyTurn(true);
            server.sendMessageTo(message.getPlayerId(), message);
        }
    }

    private synchronized Game firstGameFree(int skip){
        int got = 0;
        for(Integer i : games.keySet()){
            if(games.get(i).hasSpace()){
                if(got >= skip)
                    return games.get(i);
                else
                    got++;
            }
        }
        return null;
    }

    private synchronized void updateResources(Game game, Integer playerId){
        ResourceMessage message = new ResourceMessage(game.getGameId(), playerId);

        HashMap<Resource, Integer> chestResource = new HashMap<>(game.getResourcesFromChest());
        HashMap<Row, HashMap<Resource, Integer>> wareHouseResource = new HashMap<>(game.getResourcesFromWareHouse());
        message.setChestResources(chestResource);
        message.setWareHouseResources(wareHouseResource);
        server.sendMessageTo(playerId, message);

    }

    private synchronized void sendErrorMessage(Integer gameId, Integer playerId, String error){
        ErrorMessage message = new ErrorMessage(gameId, playerId);
        message.setError(error);
        server.sendMessageTo(message.getPlayerId(), message);
    }

    private synchronized void broadcastMessage(Game game, String error){
        for(Integer player : game.getPlayerID()){
            ErrorMessage message = new ErrorMessage(game.getGameId(), player);
            message.setError(error);
            server.sendMessageTo(player, message);
        }
    }

    @Override
    public synchronized void executeStartGame(StartGameMessage startGame) {
        startGamePlayer(startGame.getGameId(), startGame.getPlayerId());
    }

    @Override
    public synchronized void executeEndGame(EndGameMessage endGame) {

    }


}

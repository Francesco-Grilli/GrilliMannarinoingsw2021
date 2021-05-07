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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ServerController implements VisitorInterface {
    //implements all method needed to communicate with client

    private Server server;
    private HashMap<Integer, Game> games;
    private HashMap<String, Integer> nicknamesList;
    private Integer nextPlayerId = 1;
    private Integer nextGameId = 1;
    JSONObject nicknameListObj = new JSONObject();

    public ServerController(){
        server = new Server(this);
        nicknamesList = new HashMap<>();
    }

    public void receiveMessage(MessageInterface message){
        message.execute(this);
    }

    private void startGamePlayer(Integer gameId, Integer playerId){
        ArrayList<Integer> player = new ArrayList<>(games.get(gameId).getPlayerID());
        player.forEach((p) -> server.sendMessageTo(p, new StartGameMessage(gameId, p, player.size())));
    }

    public boolean nicknameAlreadyPresent(String nickname) {
        return nicknamesList.containsKey(nickname);
    }

    public Integer addPlayer(String nickname){
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

    public Integer logInPlayer(String nickname){
        if(nicknamesList.containsKey(nickname))
            return nicknamesList.get(nickname);
        else
            return null;
    }

    public void writeNickname(){
        nicknameListObj.put("nextPlayerId", String.valueOf(nextPlayerId));
        try{
            FileWriter file = new FileWriter("player.json");
            file.write(nicknameListObj.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadNickname(){
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
    public void executeTurnPlayer(TurnMessage turn) {
        Game game = games.get(turn.getGameId());

        game.setLeaderCardAction(true);
        game.setNormalAction(true);
        game.turnExecution();

        TurnMessage message = new TurnMessage(game.getGameId(), game.getActivePlayer().getID());
        message.setMyTurn(true);
        server.sendMessageTo(message.getPlayerId(), message);
    }

    @Override
    public void executeLeaderCard(LeaderCardMessage leaderCard) {
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

        if(!leaderCard.isSellingCard()){
            if(!leaderCard.isActivateCard()){
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
            return;
        }
        //code for selling the leaderCard

        LeaderCardMessage message = new LeaderCardMessage(leaderCard.getGameId(), leaderCard.getPlayerId());
        message.setSellingCard(true);
        message.setCardCode(leaderCard.getCardCode());
        if(game.canSellLeaderCard(leaderCard.getCardCode())){
            message.setActivationSellingCorrect(true);
            game.setLeaderCardAction(false);
            if(game.sellLeaderCard(leaderCard.getCardCode())){
                updatePopeLine(game);
            }
        }
        server.sendMessageTo(leaderCard.getPlayerId(), message);

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
    public void executeMarbleMarket(MarbleMarketMessage marbleMarketMessage) {
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
                    server.sendMessageTo(marbleMarketMessage.getPlayerId(), message);
                    return;

                }
                //code to add one resource at a time

                try {
                    Marble res = Marble.valueOf(marbleMarketMessage.getMarbleType());
                    Row row = Row.valueOf(marbleMarketMessage.getInsertRow());
                    if(game.placeResource(row, Marble.getResource(res))){
                        MarbleMarketMessage message = new MarbleMarketMessage(marbleMarketMessage.getGameId(), marbleMarketMessage.getPlayerId());
                        message.setAddResourceCorrect(true);
                        message.setAddedResource(true);
                        message.setMarbleType(marbleMarketMessage.getMarbleType());
                        message.setInsertRow(marbleMarketMessage.getInsertRow());
                        server.sendMessageTo(marbleMarketMessage.getPlayerId(), message);
                    }
                    else{
                        MarbleMarketMessage message = new MarbleMarketMessage(marbleMarketMessage.getGameId(), marbleMarketMessage.getPlayerId());
                        message.setAddedResource(true);
                        server.sendMessageTo(marbleMarketMessage.getPlayerId(), message);
                    }
                    return;
                }
                catch(IllegalArgumentException e){
                    e.printStackTrace();
                    sendErrorMessage(marbleMarketMessage.getGameId(), marbleMarketMessage.getPlayerId(), "Error on setting the resource into warehouse");
                    return;
                }

            }
            // code to select column or row

            ArrayList<MarbleOption> marbles;
            if(marbleMarketMessage.getColumnRow().equals("column")){
                marbles = game.selectMarbleColumn(marbleMarketMessage.getColumnRowValue());
            }
            else{
                marbles = game.selectMarbleRow(marbleMarketMessage.getColumnRowValue());
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
            MarbleMarketMessage message = new MarbleMarketMessage(marbleMarketMessage.getGameId(), marbleMarketMessage.getPlayerId());
            message.setDisplayMarblesReturned(true);
            message.setReturnedMarble(marbleList);
            server.sendMessageTo(marbleMarketMessage.getPlayerId(), message);
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
        MarbleMarketMessage message = new MarbleMarketMessage(marbleMarketMessage.getGameId(), marbleMarketMessage.getPlayerId());
        message.setMarbleList(marbleString);
        message.setDisplayMarbleMarket(true);
        message.setMarbleOut(game.displayMarbleOut().toString());
        server.sendMessageTo(marbleMarketMessage.getPlayerId(), message);
    }

    @Override
    public void executeBuyProduction(BuyProductionCardMessage buyProductionCardMessage){
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
                updateResources(game);
            }
            server.sendMessageTo(buyProductionCardMessage.getPlayerId(), message);
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
        BuyProductionCardMessage message = new BuyProductionCardMessage(buyProductionCardMessage.getGameId(), buyProductionCardMessage.getPlayerId());
        message.setDisplayCard(true);
        message.setBuyableCard(returnedCards);
        server.sendMessageTo(buyProductionCardMessage.getPlayerId(), message);
    }

    @Override
    public void executeProduction(ProductionMessage productionMessage) {
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
            if(game.startProduction(cardList)){
                game.setNormalAction(false);    //completed a normal action
                message.setProductionCorrect(true);
                updateResources(game);
            }
            server.sendMessageTo(productionMessage.getPlayerId(), message);
            return;
        }
        //code to display card into production to the client
        HashMap<Integer, CreationCard> cards = game.displayCardInProductionLine();
        ArrayList<Integer> cardList = new ArrayList<>(cards.keySet());
        ProductionMessage message = new ProductionMessage(productionMessage.getGameId(), productionMessage.getPlayerId());
        message.setDisplayCard(true);
        message.setProductionCard(cardList);
        server.sendMessageTo(productionMessage.getPlayerId(), message);

    }

    @Override
    public void executeErrorMessage(ErrorMessage error) {
    }

    @Override
    public void executePopeLine(PopeLineMessage popeLineMessage) {

    }

    @Override
    public void executeResource(ResourceMessage resourceMessage) {

    }

    @Override
    public void executeMoveResource(MoveResourceMessage moveResourceMessage) {
        Game game = games.get(moveResourceMessage.getGameId());
        if ((game.getActivePlayer().getID() != moveResourceMessage.getPlayerId())) {
            sendErrorMessage(moveResourceMessage.getGameId(), moveResourceMessage.getPlayerId(), "Error on playerId");
            return;
        }

        try {
            Row one = Row.valueOf(moveResourceMessage.getRowOne());
            Row two = Row.valueOf(moveResourceMessage.getRowTwo());

            MoveResourceMessage message = new MoveResourceMessage(moveResourceMessage.getGameId(), moveResourceMessage.getPlayerId());

            if(moveResourceMessage.isForceSwap()){
                message.setForceSwap(true);
                game.forceSwapLine(one, two);
            }else{
                message.setCanMove(game.canSwapLine(one, two));
            }
            server.sendMessageTo(moveResourceMessage.getPlayerId(), message);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            sendErrorMessage(moveResourceMessage.getGameId(), moveResourceMessage.getPlayerId(), "Error on Resource Moving");
        }


    }

    @Override
    public void executeNewGame(NewGameMessage newGameMessage) {
        Game game = new Game(nextGameId, newGameMessage.getNumberOfPlayer());
        games.put(nextGameId, game);
        nextGameId++;
        game.setPlayer(newGameMessage.getPlayerId(), newGameMessage.getNickname());
        NewGameMessage message = new NewGameMessage(game.getGameId(), newGameMessage.getPlayerId(), newGameMessage.getNickname());
        message.setNewGame(true);
        message.setMessageString("You have created a joined a new game with id: " + game.getGameId());
        server.sendMessageTo(newGameMessage.getPlayerId(), message);
    }

    @Override
    public void executeEnterGame(EnterGameMessage enterGameMessage) {
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

    private Game firstGameFree(int skip){
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

    private void updateResources(Game game){
        ResourceMessage message = new ResourceMessage(game.getGameId(), game.getActivePlayer().getID());

        HashMap<String, Integer> chest;
        HashMap<String, HashMap<String, Integer>> wareHouse = new HashMap<>();
        HashMap<Resource, Integer> chestResource = new HashMap<>(game.getResourcesFromChest());
        HashMap<Row, HashMap<Resource, Integer>> wareHouseResource = new HashMap<>(game.getResourcesFromWareHouse());

        chest = chestResource.keySet().stream().collect(Collectors.toMap(Enum::toString, chestResource::get, (a, b) -> b, HashMap::new));

        for(Row row : wareHouseResource.keySet()){
            HashMap<String, Integer> copy = new HashMap<>();
            for(Resource res : wareHouseResource.get(row).keySet()){
                copy.put(res.toString(), wareHouseResource.get(row).get(res));
            }
            wareHouse.put(row.toString(), new HashMap<String, Integer>(copy));
        }

        message.setChestResources(chest);
        message.setWareHouseResources(wareHouse);
        server.sendMessageTo(game.getActivePlayer().getID(), message);

    }

    private void sendErrorMessage(Integer gameId, Integer playerId, String error){
        ErrorMessage message = new ErrorMessage(gameId, playerId);
        message.setError(error);
        server.sendMessageTo(message.getPlayerId(), message);
    }

    @Override
    public void executeStartGame(StartGameMessage startGame) {
        startGamePlayer(startGame.getGameId(), startGame.getPlayerId());
    }

    @Override
    public void executeEndGame(EndGameMessage endGame) {

    }


}

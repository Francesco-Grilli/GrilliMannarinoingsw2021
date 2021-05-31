package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.*;
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
    private HashMap<String, String> passwordList;
    private ArrayList<String> playerLogged;
    private Integer nextPlayerId = 1;
    private Integer nextGameId = 1;
    JSONObject nicknameListObj = new JSONObject();
    Scanner scan;
    Thread end = new Thread(() -> writeNickname());

    public ServerController(){
        server = new Server(this);
        nicknamesList = new HashMap<>();
        passwordList = new HashMap<>();
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
            player.forEach((p) -> {
                StartGameMessage message = new StartGameMessage(gameId, p, true);
                message.setNumberPlayer(g.getNumberOfPlayer());
                server.sendMessageTo(p, message);
            });
            LeaderCardMessage leaderMessage = new LeaderCardMessage(g.getGameId(), startingPlayer);
            leaderMessage.setSelectLeaderCard(true);
            leaderMessage.setCards(g.selectLeaderCard());
            g.setStartingResource();
            server.sendMessageTo(leaderMessage.getPlayerId(), leaderMessage);
        }
        else{
            server.sendMessageTo(playerId, new StartGameMessage(gameId, playerId, false));
        }
    }

    public synchronized boolean nicknameAlreadyPresent(String nickname) {
        return nicknamesList.containsKey(nickname);
    }

    public synchronized Integer addPlayer(String nickname, String password){
        if(!nicknamesList.containsKey(nickname)){
            nicknamesList.put(nickname, nextPlayerId);
            passwordList.put(nickname, password);
            JSONObject player = new JSONObject();
            player.put("nickname", nickname);
            player.put("playerId", String.valueOf(nextPlayerId));
            player.put("password", password);
            nicknameListObj.put(String.valueOf(nextPlayerId), player);
            nextPlayerId++;
            return nextPlayerId-1;
        }
        else{
            return null;
        }
    }

    public synchronized Integer logInPlayer(String nickname, String password){
        if(nicknamesList.containsKey(nickname) && !playerLogged.contains(nickname) && passwordList.get(nickname).equals(password)) {
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
                passwordList.put((String) player.get("nickname"), (String) player.get("password"));
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
        if(game.getNumberOfPlayer()==1){
            game.turnExecution();
            if(!game.isEndGame()) {
                Map.Entry<LorenzoToken, Boolean> lorenzoTokenMap = game.lorenzoAction();
                if (lorenzoTokenMap.getValue())
                    updatePopeLine(game);
                else
                    updateFaith(game);
                LorenzoTokenMessage lorenzoMessage = new LorenzoTokenMessage(turn.getGameId(), turn.getPlayerId());
                lorenzoMessage.setToken(lorenzoTokenMap.getKey());
                server.sendMessageTo(turn.getPlayerId(), lorenzoMessage);
            }
        }
        else {
            game.turnExecution();
        }
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
            LeaderCardMessage leaderMessage = new LeaderCardMessage(game.getGameId(), game.getActivePlayer().getID());
            leaderMessage.setSelectLeaderCard(true);
            leaderMessage.setCards(game.selectLeaderCard());
            server.sendMessageTo(leaderMessage.getPlayerId(), leaderMessage);
        }
        else{
            TurnMessage message = new TurnMessage(game.getGameId(), game.getActivePlayer().getID());
            message.setMyTurn(true);
            server.sendMessageTo(message.getPlayerId(), message);
        }
    }

    private synchronized void endGame(Game game){
        HashMap<String, Integer> ranking = new HashMap<>(game.getAllPoints());
        HashMap<Integer, Map.Entry<String, Integer>> orderedRank = new HashMap<>(orderRank(ranking));
        for(Integer player : game.getPlayerID()){
            EndGameMessage message = new EndGameMessage(game.getGameId(), player);
            message.setPlayerRanking(orderedRank);
            server.sendMessageTo(player, message);
        }
    }

    private HashMap<Integer, Map.Entry<String, Integer>> orderRank(HashMap<String, Integer> ranking){
        HashMap<Integer, Map.Entry<String, Integer>> orderedRank = new HashMap<>();
        Integer pos = 1;
        ArrayList<String> playerInGame = new ArrayList<>(ranking.keySet());
        ArrayList<Integer> valueInRanking = new ArrayList<>(ranking.values());
        Collections.sort(valueInRanking, Collections.reverseOrder());
        for(Integer score : valueInRanking){
            for(String nick : playerInGame){
                if(ranking.containsKey(nick)){
                    if(ranking.get(nick).equals(score)) {
                        orderedRank.put(pos, new AbstractMap.SimpleEntry<>(nick, ranking.get(nick)));
                        ranking.remove(nick);
                        pos++;
                        break;
                    }
                }
            }
        }

        return orderedRank;
    }

    @Override
    public synchronized void executeLeaderCard(LeaderCardMessage leaderCard) {
        Game game = games.get(leaderCard.getGameId());
        if ((game.getActivePlayer().getID() != leaderCard.getPlayerId())) {
            sendErrorMessage(leaderCard.getGameId(), leaderCard.getPlayerId(), "Error on playerId");
            return;
            //should run exception??
        }
        if(!game.isLeaderCardAction() && !game.hasActiveLeaderCard()){
            sendErrorMessage(leaderCard.getGameId(), leaderCard.getPlayerId(), "No active Leader Card or No action left!");
            return;
        }
        if(!leaderCard.isShowLeaderCard()) {
            if (!leaderCard.isSellingCard()) {
                if(!leaderCard.isSelectLeaderCard()){
                    if (!leaderCard.isActivateCard()) {
                        sendErrorMessage(leaderCard.getGameId(), leaderCard.getPlayerId(), "Error on Leader Card action");
                        return;
                    }
                    //code to activate leaderCard

                    LeaderCardMessage message = new LeaderCardMessage(leaderCard.getGameId(), leaderCard.getPlayerId());
                    message.setActivateCard(true);
                    message.setCardCode(leaderCard.getCardCode());
                    if (game.activateLeaderCard(leaderCard.getCardCode())) {
                        if(game.getLeaderCard(message.getCardCode()) instanceof ResourceManagerLeaderCardTwoSpace){
                            message.setResourceLeaderCard(true);
                            message.setRow(((ResourceManagerLeaderCardTwoSpace) game.getLeaderCard(message.getCardCode())).getRow());
                            message.setRes(game.getLeaderCard(message.getCardCode()).getDefinedResource());
                        }
                        message.setActivationSellingCorrect(true);
                        game.setLeaderCardAction(false);
                    }
                    server.sendMessageTo(leaderCard.getPlayerId(), message);
                    if (game.isActivatedEnd()) {
                        broadcastMessage(game, "The End is near");
                    }
                    return;
                }
                //code to let player select leadercard
                game.setLeaderCards(leaderCard.getCards());

                TurnMessage message = new TurnMessage(game.getGameId(), game.getActivePlayer().getID());
                message.setMyTurn(true);
                server.sendMessageTo(message.getPlayerId(), message);
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
                    updateFaith(game);
                }
                else{
                    PopeLineMessage popeMessage;
                    if(game.numberOfPlayer==1){
                        popeMessage = new PopeLineSingleMessage(leaderCard.getGameId(), leaderCard.getPlayerId());
                        ((PopeLineSingleMessage)popeMessage).setLorenzoFaith(game.getLorenzoFaith());
                    }
                    else{
                        popeMessage = new PopeLineMessage(leaderCard.getGameId(), leaderCard.getPlayerId());
                    }

                    popeMessage.setUpdatedPosition(true);
                    popeMessage.setFaithPosition(game.getPlayersFaith().get(leaderCard.getPlayerId()));
                    server.sendMessageTo(leaderCard.getPlayerId(), popeMessage);
                }

            }
            server.sendMessageTo(leaderCard.getPlayerId(), message);
            return;
        }
        //code to show leadercard

        ArrayList<Integer> cards = new ArrayList<>(game.getLeaderCards());
        LeaderCardMessage message = new LeaderCardMessage(leaderCard.getGameId(), leaderCard.getPlayerId());
        message.setShowLeaderCard(true);
        message.setCards(cards);
        server.sendMessageTo(leaderCard.getPlayerId(), message);

    }

    //updatePopeLine if someone has activated a new favor
    private synchronized void updatePopeLine(Game game){
        HashMap<Integer, Map.Entry<Integer, Boolean>> check = game.checkPopeLine();
        Integer popePosition = game.getPopeLinePosition();
        if(game.getNumberOfPlayer()==1){
            Integer playerId = game.getActivePlayer().getID();
            PopeLineSingleMessage message = new PopeLineSingleMessage(game.getGameId(), playerId);
            message.setCheckPopeLine(true);
            message.setFavorActive(check.get(playerId).getValue());
            message.setFaithPosition(check.get(playerId).getKey());
            message.setCheckPosition(popePosition);
            message.setLorenzoFavorActive(check.get(0).getValue());
            message.setLorenzoCheckPosition(popePosition);
            message.setLorenzoFaith(check.get(0).getKey());
            server.sendMessageTo(playerId, message);
        }
        else {
            for (Integer playerId : check.keySet()) {
                PopeLineMessage message = new PopeLineMessage(game.getGameId(), playerId);
                message.setCheckPopeLine(true);
                message.setFavorActive(check.get(playerId).getValue());
                message.setFaithPosition(check.get(playerId).getKey());
                message.setCheckPosition(popePosition);
                server.sendMessageTo(playerId, message);
            }
        }
    }

    //updateFaith if someone added some faith without trigger new favor
    private synchronized void updateFaith(Game game){
        HashMap<Integer, Integer> playersFaith = game.getPlayersFaith();
        if(game.getNumberOfPlayer()==1){
            Integer playerId = game.getActivePlayer().getID();
            PopeLineSingleMessage message = new PopeLineSingleMessage(game.getGameId(), playerId);
            message.setUpdatedPosition(true);
            message.setFaithPosition(playersFaith.get(playerId));
            message.setLorenzoFaith(playersFaith.get(0));
            server.sendMessageTo(playerId, message);
        }
        else {
            for (Integer playerId : playersFaith.keySet()) {
                PopeLineMessage message = new PopeLineMessage(game.getGameId(), playerId);
                message.setUpdatedPosition(true);
                message.setFaithPosition(playersFaith.get(playerId));
                server.sendMessageTo(playerId, message);
            }
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
        if(!marbleMarketMessage.isSwapRow()) {
            if (!marbleMarketMessage.isDisplayMarbleMarket()) {
                if (!marbleMarketMessage.isSelectColumnRow()) {
                    if (!marbleMarketMessage.isAddedResource()) {
                        if (!marbleMarketMessage.isCheckReturnedResource()) {
                            if (!marbleMarketMessage.isDestroyRemaining()) {
                                //should not enter here

                                sendErrorMessage(marbleMarketMessage.getGameId(), marbleMarketMessage.getPlayerId(), "Error on Buying marble at the market");
                                return;
                            }
                            //code to destroy remaining resources and add pope faith

                            MarbleMarketMessage message = new MarbleMarketMessage(marbleMarketMessage.getGameId(), marbleMarketMessage.getPlayerId());
                            message.setDestroyRemaining(true);
                            game.setNormalAction(false);
                            if (!marbleMarketMessage.getReturnedResource().isEmpty()) {
                                int numberOfResource = marbleMarketMessage.getReturnedResource().size();
                                if (game.addFaithExceptThis(marbleMarketMessage.getPlayerId(), numberOfResource)) {
                                    updatePopeLine(game);
                                } else {
                                    updateFaith(game);
                                }
                            }
                            if (game.isActivatedEnd()) {
                                broadcastMessage(game, "The End is near");
                            }
                            server.sendMessageTo(marbleMarketMessage.getPlayerId(), message);
                            return;
                        }
                        //code to check the returned resources
                        ArrayList<Resource> resources = new ArrayList<>();
                        for (Resource res : marbleMarketMessage.getReturnedResource()) {
                            if (res.equals(Resource.FAITH)) {
                                if (game.addFaithTo(marbleMarketMessage.getPlayerId()))
                                    updatePopeLine(game);
                                else {
                                    PopeLineMessage popeMessage;
                                    if (game.getNumberOfPlayer() == 1) {
                                        popeMessage = new PopeLineSingleMessage(marbleMarketMessage.getGameId(), marbleMarketMessage.getPlayerId());
                                        ((PopeLineSingleMessage) popeMessage).setLorenzoFaith(game.getLorenzoFaith());
                                    } else {
                                        popeMessage = new PopeLineMessage(marbleMarketMessage.getGameId(), marbleMarketMessage.getPlayerId());
                                    }
                                    popeMessage.setUpdatedPosition(true);
                                    popeMessage.setFaithPosition(game.getPlayersFaith().get(marbleMarketMessage.getPlayerId()));
                                    server.sendMessageTo(marbleMarketMessage.getPlayerId(), popeMessage);
                                }
                            } else {
                                resources.add(res);
                            }
                        }

                        MarbleMarketMessage marketMessage = new MarbleMarketMessage(marbleMarketMessage.getGameId(), marbleMarketMessage.getPlayerId());
                        marketMessage.setCheckReturnedResource(true);
                        marketMessage.setReturnedResource(resources);
                        server.sendMessageTo(marketMessage.getPlayerId(), marketMessage);
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
                    if (game.placeResource(row, res) && marbleMarketMessage.getReturnedResource().contains(res)) {
                        message.setAddResourceCorrect(true);
                        marbleMarketMessage.getReturnedResource().remove(res);
                        updateResources(game, marbleMarketMessage.getPlayerId());
                    }
                    server.sendMessageTo(marbleMarketMessage.getPlayerId(), message);
                    return;
                }
                // code to select column or row

                ArrayList<MarbleOption> marbles;
                if (marbleMarketMessage.getColumnRow().equals("C")) {
                    marbles = game.selectMarbleColumn(marbleMarketMessage.getColumnRowValue());
                } else {
                    marbles = game.selectMarbleRow(marbleMarketMessage.getColumnRowValue());
                }

                ArrayList<ArrayList<Marble>> marbleList = new ArrayList<>();
                for (MarbleOption marble : marbles) {
                    ArrayList<Marble> tempArray = new ArrayList<>(marble.getMarbles());
                    marbleList.add(tempArray);
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
            for (int x = 0; x < marbles.length; x++) {
                ArrayList<Marble> temp = new ArrayList<>();
                for (int y = 0; y < marbles[x].length; y++) {
                    temp.add(y, marbles[x][y]);
                }
                marblesToReturn.add(temp);
            }
            message.setMarbleList(marblesToReturn);
            message.setDisplayMarbleMarket(true);
            message.setMarbleOut(game.displayMarbleOut());
            server.sendMessageTo(marbleMarketMessage.getPlayerId(), message);
            return;
        }
        //code to move resources into marble market
        try {
            Row one = marbleMarketMessage.getRowOne();
            Row two = marbleMarketMessage.getRowTwo();

            MarbleMarketMessage message = new MarbleMarketMessage(marbleMarketMessage.getGameId(), marbleMarketMessage.getPlayerId());
            message.setReturnedResource(marbleMarketMessage.getReturnedResource());
            message.setRowOne(one);
            message.setRowTwo(two);
            message.setSwapRow(true);
            if(marbleMarketMessage.isForceSwap()){
                game.forceSwapLine(one, two);
                updateResources(game, marbleMarketMessage.getPlayerId());
                if(game.isActivatedEnd()){
                    broadcastMessage(game, "The End is near");
                }
            }
            else{
                if(game.canSwapLine(one, two)){
                    game.forceSwapLine(one, two);
                    message.setCanMove(true);
                    updateResources(game, marbleMarketMessage.getPlayerId());
                }
                else{
                    message.setCanMove(false);
                }
            }
            server.sendMessageTo(message.getPlayerId(), message);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            sendErrorMessage(marbleMarketMessage.getGameId(), marbleMarketMessage.getPlayerId(), "Error on Resource Moving");
        }
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
                return;
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
        HashMap<Faction, HashMap<Integer, Map.Entry<Integer, Boolean>>> returnedCards = new HashMap<>();

        for(Faction fac : cards.keySet()){
            HashMap<Integer, Map.Entry<Integer, Boolean>> facMap = new HashMap<>();
            if(cards.containsKey(fac)) {
                for (Integer value : cards.get(fac).keySet()) {
                    if(cards.get(fac).containsKey(value))
                        facMap.put(value, new AbstractMap.SimpleEntry<>(cards.get(fac).get(value).getKey().getCardCode(), cards.get(fac).get(value).getValue()));
                }
            }
            returnedCards.put(fac, facMap);
        }
        returnedCards.remove(Faction.NONE);
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
            ArrayList<Integer> cardSelected = new ArrayList<>(productionMessage.getSelectedCard());
            for(Integer i : cardSelected){
                cardList.add(game.getCardFromCode(i));
            }

            boolean unknownPresent = false;
            for(CreationCard c : cardList){
                if(c.getInput().containsKey(Resource.UNKNOWN) || c.getOutput().containsKey(Resource.UNKNOWN)) {
                    unknownPresent = true;
                }
            }
            if(unknownPresent){
                HashMap<Integer, HashMap<Resource, Integer>> input = new HashMap<>();
                HashMap<Integer, HashMap<Resource, Integer>> output = new HashMap<>();
                for(CreationCard c : cardList){
                    if(c.getInput().containsKey(Resource.UNKNOWN) || c.getOutput().containsKey(Resource.UNKNOWN)) {
                        input.put(c.getCardCode(), new HashMap<>(c.getInput()));
                        output.put(c.getCardCode(), new HashMap<>(c.getOutput()));
                        cardSelected.remove(Integer.valueOf(c.getCardCode()));
                    }
                }

                ProductionMessage unknownMessage = new ProductionMessage(game.getGameId(), productionMessage.getPlayerId());
                unknownMessage.setResolveUnknown(true);
                unknownMessage.setInputCard(input);
                unknownMessage.setOutputCard(output);
                unknownMessage.setSelectedCard(cardSelected);
                server.sendMessageTo(unknownMessage.getPlayerId(), unknownMessage);
                return;
            }

            if(productionMessage.isResolveUnknown()){
                HashMap<Integer, HashMap<Resource, Integer>> input = new HashMap<>(productionMessage.getInputCard());
                HashMap<Integer, HashMap<Resource, Integer>> output = new HashMap<>(productionMessage.getOutputCard());
                for(Integer code : input.keySet()){
                    CreationCard card = new CreationCard(code, 0, 0, null, null, input.get(code), output.get(code));
                    cardList.add(card);
                }
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
        for(Integer pos : cards.keySet()){
            if(cards.get(pos)!=null)
                cardToReturn.put(cards.get(pos).getCardCode(), pos);
        }
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
        Game game;
        if(newGameMessage.getNumberOfPlayer()==1){
            game = new GameSinglePlayer(nextGameId);
        }
        else
            game = new Game(nextGameId, newGameMessage.getNumberOfPlayer());
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
    }

    @Override
    public void executePopeLineSingle(PopeLineSingleMessage popeLineSingleMessage) {

    }

    @Override
    public void executeLorenzoAction(LorenzoTokenMessage lorenzoTokenMessage) {

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
        message.setYourTurn(true);
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


    public void removePlayer(String nickName) {
        playerLogged.remove(nickName);
    }
}

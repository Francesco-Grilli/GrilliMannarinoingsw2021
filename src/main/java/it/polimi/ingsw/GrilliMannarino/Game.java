package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Game {

    final protected Integer gameId;
    protected final Integer numberOfPlayer;
    protected boolean start = false;
    protected final Map<Integer, Player> player;
    protected  ArrayList<Integer> playerID;
    protected final Map<Integer, Board> board;
    protected HashMap<Integer, LeaderCard> leaderCards;
    protected HashMap<Integer, LeaderCard> leaderToSet;

    protected Player activePlayer;
    protected Board activeBoard;

    protected int countPlayer = 0;

    protected CardMarketBoardInterface cardMarket;
    protected final MarbleMarketBoardInterface marbleMarket;

    protected boolean leaderCardAction = true;
    protected boolean normalAction = true;
    protected boolean activatedEnd = false;
    protected boolean endGame = false;
    protected int playerWasPlaying = 0;

    public Game(Integer gameId, Integer numberOfPlayer){
        this.gameId = gameId;
        this.numberOfPlayer = numberOfPlayer;
        player = new ConcurrentHashMap<>();
        playerID = new ArrayList<>();
        board = new ConcurrentHashMap<>();
        cardMarket = new CardMarket();
        marbleMarket = new MarbleMarket();
        leaderCards = new HashMap<>();
        leaderToSet = new HashMap<>();
        loadLeaderCards();
    }

    private void loadLeaderCards() {
        JSONArray array;
        JSONParser parser = new JSONParser();
        try {
            FileReader file = new FileReader("leader_cards.json");
            array = (JSONArray) parser.parse(file);
            for(Object o : array){
                JSONObject jsonObject = (JSONObject) ((JSONObject) o).get("leader_card");
                String cardType = (String) jsonObject.get("card_type");
                LeaderCard card = loadSingleLeaderCard(cardType, jsonObject);
                leaderCards.put(card.getCardCode(), card);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private LeaderCard loadSingleLeaderCard(String type, JSONObject card) {
        HashMap<Resource, Integer> priceResources = parseHashMapResources((JSONObject) card.get("price_resources"));
        HashMap<Faction, HashMap<Integer, Integer>> cardPrice = parseHashMapFaction((JSONObject) card.get("price_cards"));
        Resource definedResource = Resource.valueOf(((String) card.get("specified_resource")).toUpperCase());
        Integer points = Integer.parseInt((String) card.get("card_value"));
        Integer cardCode = Integer.parseInt((String) card.get("card_code"));
        LeaderCard toReturn = null;
        if(type.equals("discount")){
            toReturn = new CardMarketLeaderCardDiscount(priceResources, cardPrice, definedResource, points, cardCode);
        }
        else if(type.equals("warehouse")){
            toReturn = new ResourceManagerLeaderCardTwoSpace(priceResources, cardPrice, definedResource, points, cardCode);
        }
        else if(type.equals("marble")){
            toReturn = new MarbleMarketLeaderCardWhiteResource(priceResources, cardPrice, definedResource, points, cardCode);
        }
        else if(type.equals("production")){
            toReturn = new ProductionLineLeaderCardResourceFaith(priceResources, cardPrice, definedResource, points, cardCode);
        }
        return toReturn;
    }

    private HashMap<Resource, Integer> parseHashMapResources(JSONObject resources){
        Resource[] keys = Resource.values();
        HashMap<Resource, Integer> temp = new HashMap<>();
        for(Resource key : keys){
            String resource = key.toString().toLowerCase();
            if(( resources.get(resource)) != null){
                temp.put(key, Integer.parseInt((String) resources.get(resource)));
            }
        }
        return temp;
    }

    private HashMap<Faction, HashMap<Integer, Integer>> parseHashMapFaction(JSONObject faction){
        Faction[] keys = Faction.values();
        HashMap<Faction, HashMap<Integer, Integer>> temp = new HashMap<>();
        Integer[] cardValue = {0, 1, 2, 3};
        for(Faction key : keys){
            String fac = key.toString().toLowerCase();
            if(faction.get(fac)!=null){
                JSONObject t = (JSONObject) faction.get(fac);
                for(Integer i : cardValue){
                    String value = i.toString();
                    if(t.get(value)!=null){
                        HashMap<Integer, Integer> map = new HashMap<>();
                        map.put(i, Integer.parseInt((String) t.get(value)));
                        temp.put(key, map);
                    }
                }
            }
        }
        return temp;
    }


    public ArrayList<Integer> selectLeaderCard(){
        ArrayList<Integer> cardCodes = new ArrayList<>();
        ArrayList<Integer> keySet = new ArrayList<>(leaderCards.keySet());
        Collections.shuffle(keySet);
        leaderToSet.clear();
        int i = 0;
        for(Integer code : keySet){
            if(i==4)
                break;
            else{
                cardCodes.add(code);
                i++;
                leaderToSet.put(code, leaderCards.get(code));
                leaderCards.remove(code);
            }
        }
        return cardCodes;
    }

    public void setLeaderCards(ArrayList<Integer> leaderCards){
        ArrayList<LeaderCard> cardToAdd = new ArrayList<>();
        for(Integer code : leaderCards){
            cardToAdd.add(leaderToSet.get(code));
        }
        activeBoard.setLeaderCards(cardToAdd);
    }

    public synchronized boolean setPlayer(Integer playerId, String nickName) {
        if(player.size()<numberOfPlayer && !player.containsKey(playerId) && !board.containsKey(playerId)){
            player.put(playerId, new Player(nickName, playerId));
            playerID.add(playerId);
            board.put(playerId, new Board(player.get(playerId), cardMarket, marbleMarket));
            return true;
        }
        return false;
    }

    public synchronized boolean hasSpace(){
        return (player.size() < numberOfPlayer && !start);
    }

    protected void setActivePlayer(){
        if(!activatedEnd) {
            countPlayer++;
            int p = playerID.get(countPlayer % playerID.size());
            activePlayer = player.getOrDefault(p, null);
        }
        else{
            if(((countPlayer +1) % playerID.size()) == (playerWasPlaying % playerID.size())){
                endGame = true;
            }
            else{
                countPlayer++;
                int p = playerID.get(countPlayer % playerID.size());
                activePlayer = player.getOrDefault(p, null);
            }
        }
    }

    protected void setActiveBoard(){
        activeBoard = board.get(activePlayer.getID());
    }

    public void turnExecution(){
        setActivePlayer();
        setActiveBoard();
        normalAction = true;
        leaderCardAction = true;
    }

    public Integer startGame(){
        start = true;
        Collections.shuffle(playerID);
        int playerId = playerID.get(0);
        activePlayer  = player.getOrDefault(playerId, null);
        setActiveBoard();
        normalAction = true;
        leaderCardAction = true;
        return playerId;
    }

    //METHOD TO BUY CREATION CARD

    public HashMap<Faction, HashMap<Integer, Map.Entry<CreationCard, Boolean>>> displayCreationCard(){
        return activeBoard.getBuyableCard();
    }

    public boolean buyCreationCard(CreationCard card, Integer cardPosition){
        if(activeBoard.canAddCard(cardPosition, card)){
            if(activeBoard.getCardFromMarket(card)!=null){
                activeBoard.addCard(cardPosition, card);
                if(activeBoard.getNumberOfCards()>=7){
                    activatedEnd=true;
                    playerWasPlaying = countPlayer;
                }
                return true;
            }
        }

        return false;
    }

    //METHOD TO GET TO THE MARBLE MARKET

    public Marble[][] displayMarbleMarket(){
        return marbleMarket.getMarbleBoard();
    }

    public Marble displayMarbleOut(){
        return activeBoard.getMarbleOut();
    }

    public ArrayList<MarbleOption> selectMarbleColumn(int column){
        return removeWhiteMarble(activeBoard.getMarbleColumn(column));
        //add faith remove and remove white marble
    }

    private ArrayList<MarbleOption> removeWhiteMarble(ArrayList<MarbleOption> marbleOptions){
        ArrayList<MarbleOption> marbles = new ArrayList<>(marbleOptions);
        ArrayList<MarbleOption> toReturn = new ArrayList<>();
        for(MarbleOption mo : marbles){
            if(mo.hasWhite() && mo.getMarbles().size()!=1){
                mo.getMarbles().remove(Marble.WHITE);
                toReturn.add(mo);
            }
            else if(mo.hasWhite() && mo.getMarbles().size()==1){

            }
            else
                toReturn.add(mo);
        }
        return toReturn;
    }

    public ArrayList<MarbleOption> selectMarbleRow(int row){
        return removeWhiteMarble(activeBoard.getMarbleRow(row));
    }

    public boolean placeResource(Row row, Resource resource){
        if(activeBoard.canSetResourcesFromMarket(row, resource, 1)){
            activeBoard.setResourcesFromMarket(row, resource, 1);
            return true;
        }
        return false;
    }

    public boolean isStartingResource(){
        return activeBoard.isStartingResource();
    }

    public void setStartingResource(){
        activeBoard.setStartingResource(true);
    }


    //METHOD TO BUY PRODUCE

    public boolean canProduce(ArrayList<CreationCard> creationCards){
        return activeBoard.canProduceWithConfiguration(creationCards);
    }

    public boolean startProduction(ArrayList<CreationCard> creationCards){
        if(activeBoard.canProduceWithConfiguration(creationCards)){
            return activeBoard.produce(creationCards);
        }
        else
            return false;
    }

    public HashMap<Integer, CreationCard> displayCardInProductionLine(){
        return activeBoard.showCardInProductionLine();
    }

    public HashMap<String, Integer> getAllPoints(){
        HashMap<String, Integer> playerPoints = new HashMap<>();
        for(Integer playerId : player.keySet()){
            playerPoints.put(player.get(playerId).getName(), board.get(playerId).getPoints());
        }
        return playerPoints;
    }


    public Integer getGameId() {
        return gameId;
    }

    public CreationCard getCardFromCode(Integer cardCode){
        return activeBoard.getCardFromCode(cardCode);
    }

    //METHOD TO WORK WITH LEADERCARDS

    public ArrayList<Integer> getLeaderCards(){
        return activeBoard.getLeaderCards();
    }

    public boolean activateLeaderCard(int cardCode){
        return activeBoard.activateLeaderCard(cardCode);
    }

    public LeaderCard getLeaderCard(int cc){
        return activeBoard.getLeaderCard(cc);
    }

    public boolean hasActiveLeaderCard(){
        return !activeBoard.getActiveLeaderCards().isEmpty();
    }

    public boolean sellLeaderCard(Integer cardCode){
        boolean sell = activeBoard.sellLeaderCard(cardCode);
        if(activeBoard.getFaith()>=24){
            activatedEnd = true;
            playerWasPlaying = countPlayer;
        }
        return sell;
    }

    public boolean canSellLeaderCard(Integer cardCode){
        return activeBoard.getLeaderCards().contains(cardCode);
    }

    public ArrayList<Integer> getPlayerID() {
        return playerID;
    }

    public HashMap<Integer, Map.Entry<Integer, Boolean>> checkPopeLine(){
        HashMap<Integer, Map.Entry<Integer, Boolean>> checkPope = new HashMap<>();

        for(Integer i : board.keySet()){
            Map.Entry<Integer, Boolean> couple = new AbstractMap.SimpleEntry<>(board.get(i).getFaith(), board.get(i).checkPopeFaith());
            checkPope.put(i, couple);
        }
        updateAllPopelineCheck();

        return checkPope;
    }

    protected void updateAllPopelineCheck(){
        for(Integer i : board.keySet()){
            board.get(i).updateChecks();
        }
    }

    public HashMap<Integer, Integer> getPlayersFaith(){
        HashMap<Integer, Integer> playersFaith = new HashMap<>();

        for(Integer i : board.keySet())
            playersFaith.put(i, board.get(i).getFaith());

        return playersFaith;
    }

    public Integer getLorenzoFaith(){
        return null;
    }

    public Map.Entry<LorenzoToken, Boolean> lorenzoAction(){return null;}

    public Integer getPopeLinePosition(){
        boolean[] arr = activeBoard.getFaithChecks();
        int checkPosition=-1;
        for(int i=0; i<arr.length; i++){
            if(arr[i])
                checkPosition++;
        }
        return checkPosition;   //if check position is -1 means no favor has been ever activate
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public Board getActiveBoard() {
        return activeBoard;
    }

    public boolean isLeaderCardAction() {
        return leaderCardAction;
    }

    public void setLeaderCardAction(boolean leaderCardAction) {
        this.leaderCardAction = leaderCardAction;
    }

    public boolean isNormalAction() {
        return normalAction;
    }

    public void setNormalAction(boolean normalAction) {
        this.normalAction = normalAction;
    }

    public boolean isActivatedEnd() {
        return activatedEnd;
    }

    public boolean addFaithExceptThis(Integer playerId, int numberOfResource) {
        boolean check = false;
        for(Integer player : board.keySet()){
            if(!player.equals(playerId)) {
                for (int i = 0; i < numberOfResource; i++) {
                    if(board.get(player).addPopeFaith())
                        check = true;
                    if(board.get(player).getFaith() >= 24) {
                        activatedEnd = true;
                        playerWasPlaying = countPlayer;
                    }
                }
            }
        }

        return check;
    }

    public boolean addFaithTo(Integer playerId){
        if(board.containsKey(playerId)){
            boolean check = board.get(playerId).addPopeFaith();
            if(board.get(playerId).getFaith()>=24){
                activatedEnd = true;
                playerWasPlaying = countPlayer;
            }
            return check;
        }
        return false;
    }

    //METHOD TO WORK WITH RESOURCEMANAGER
    public HashMap<Resource, Integer> getResourcesFromChest(){
        return activeBoard.getResourcesFromChest();
    }

    public HashMap<Row, HashMap<Resource, Integer>> getResourcesFromWareHouse(){
        return activeBoard.getResourcesFromWareHouse();
    }

    public boolean canSwapLine(Row one, Row two){
        return activeBoard.canSwapLineFromWareHouse(one, two);
    }

    public void forceSwapLine(Row one, Row two){
        activeBoard.forceSwapLineFromWareHouse(one, two);
    }

    public Integer getNumberOfPlayer() {
        return numberOfPlayer;
    }

    public boolean isEndGame() {
        return endGame;
    }

    public Map<Integer, Player> getPlayer() {
        return player;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }
}

package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Game {

    final protected Integer gameId;
    protected final Integer numberOfPlayer;
    protected boolean start = false;
    protected final Map<Integer, Player> player;
    protected  ArrayList<Integer> playerID;
    protected final Map<Integer, Board> board;

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
        //method to set leadercard
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
        return activeBoard.getColumn(column);
        //add faith remove and remove white marble
    }

    public ArrayList<MarbleOption> selectMarbleRow(int row){
        return activeBoard.getRow(row);
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

    public ArrayList<Integer> getLeaderCard(){
        return activeBoard.getLeaderCards();
    }

    public boolean activateLeaderCard(int cardCode){
        return activeBoard.activateLeaderCard(cardCode);
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
        PopeLine.updateChecks();

        return checkPope;
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

    public Integer getPopeLinePosition(){
        boolean[] arr = PopeLine.getFaithChecks();
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
            return board.get(playerId).addPopeFaith();
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

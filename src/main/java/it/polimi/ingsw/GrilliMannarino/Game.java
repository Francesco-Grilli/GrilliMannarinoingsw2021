package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Game {

    final private Integer gameId;
    private final Map<Integer, Player> player;
    private final ArrayList<Integer> playerID;
    private final Map<Integer, Board> board;

    private Player activePlayer;
    private Board activeBoard;

    private int countPlayer = 0;

    private final CardMarketBoardInterface cardMarket;
    private final MarbleMarketBoardInterface marbleMarket;

    private boolean leaderCardAction = true;
    private boolean normalAction = true;
    private boolean endGame = true;

    public Game(){
        gameId = (int) (Math.random()*1000)+1;
        player = new ConcurrentHashMap<>();
        playerID = new ArrayList<>();
        board = new ConcurrentHashMap<>();
        cardMarket = new CardMarket();
        marbleMarket = new MarbleMarket();
    }



    public synchronized boolean setPlayer(Integer playerId, String nickName) {
        if(player.size()<4 && !player.containsKey(playerId) && !board.containsKey(playerId)){
            player.put(playerId, new Player(nickName, playerId));
            playerID.add(playerId);
            board.put(playerId, new Board(player.get(playerId), cardMarket, marbleMarket));
            return true;
        }
        return false;
    }



    private void setActivePlayer(){
        int p = playerID.get(countPlayer % playerID.size());
        activePlayer =  player.getOrDefault(p, null);
    }

    public void setActiveBoard(){
        activeBoard = board.get(activePlayer.getID());
    }

    public void turnExecution(){
        countPlayer++;
        setActivePlayer();
        setActiveBoard();
    }


    //METHOD TO BUY CREATION CARD

    public HashMap<Faction, HashMap<Integer, Map.Entry<CreationCard, Boolean>>> displayCreationCard(){
        return activeBoard.getBuyableCard();
    }

    public boolean buyCreationCard(CreationCard card, Integer cardPosition){
        if(activeBoard.canAddCard(cardPosition, card)){
            if(activeBoard.getCardFromMarket(card)!=null){
                activeBoard.addCard(cardPosition, card);
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


    //METHOD TO BUY PRODUCE

    public boolean startProduction(ArrayList<CreationCard> creationCards){
        if(activeBoard.canProduceWithConfiguration(creationCards)){
            activeBoard.produce(creationCards);
            return true;
        }
        else
            return false;
    }

    public HashMap<Integer, CreationCard> displayCardInProductionLine(){
        return activeBoard.showCardInProductionLine();
    }

    public int getAllPoints(){
        return activeBoard.getPoints();
    }


    public Integer getGameId() {
        return gameId;
    }

    public CreationCard getCardFromCode(Integer cardCode){
        return activeBoard.getCardFromCode(cardCode);
    }

    //METHOD TO WORK WITH LEADERCARDS

    public boolean activateLeaderCard(int cardCode){
        return activeBoard.activateLeaderCard(cardCode);
    }

    public boolean hasActiveLeaderCard(){
        return !activeBoard.getActiveLeaderCards().isEmpty();
    }

    public boolean sellLeaderCard(Integer cardCode){
        return activeBoard.sellLeaderCard(cardCode);
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

    public Integer getPopeLinePosition(){
        boolean[] arr = PopeLine.getFaithChecks();
        int checkPosition;
        for(checkPosition=0; checkPosition<arr.length; checkPosition++){
            if(arr[checkPosition])
                checkPosition++;
        }
        return checkPosition-1;   //if check position is -1 means no favor has been ever activate
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

    public boolean isEndGame() {
        return endGame;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    public boolean addFaithExceptThis(Integer playerId, int numberOfResource) {
        boolean check = false;
        for(Integer player : board.keySet()){
            if(!player.equals(playerId)) {
                for (int i = 0; i < numberOfResource; i++) {
                    if(board.get(player).addPopeFaith())
                        check = true;
                }
            }
        }

        return check;
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
}

package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Game {

    final private Integer gameId;
    private Map<Integer, Player> player;
    private ArrayList<Integer> playerID;
    private Map<Integer, Board> board;

    private Player activePlayer;
    private Board activeBoard;

    private int countPlayer = 0;

    private CardMarketBoardInterface cardMarket;
    private MarbleMarketBoardInterface marbleMarket;

    public Game(){
        gameId = (int) (Math.random()*1000)+1;
        player = new ConcurrentHashMap<>();
        playerID = new ArrayList<>();
        board = new ConcurrentHashMap<>();
        cardMarket = new CardMarket();
        marbleMarket = new MarbleMarket();
    }



    public synchronized void setPlayer(Integer playerId, String nickName) {
        if(player.size()<4 && !player.containsKey(playerId) && !board.containsKey(playerId)){
            player.put(playerId, new Player(nickName, playerId));
            playerID.add(playerId);
            board.put(playerId, new Board(player.get(playerId), cardMarket, marbleMarket));
        }
    }



    public void setActivePlayer(){
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

    public HashMap<Integer, Boolean> checkPopeLine(){
        HashMap<Integer, Boolean> checkPope = new HashMap<>();

        for(Integer i : board.keySet()){
            checkPope.put(i, board.get(i).checkPopeFaith());
        }
        PopeLine.updateChecks();

        return checkPope;
    }

    public Integer getPopeLinePosition(){
        boolean[] arr = PopeLine.getFaithChecks();
        int checkPosition;
        for(checkPosition=0; checkPosition<arr.length; checkPosition++){
            if(arr[checkPosition]==true)
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

}

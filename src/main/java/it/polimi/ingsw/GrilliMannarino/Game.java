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
    private Map<Integer, Player> player;    //map or arrayList?  synchronized
    private ArrayList<Integer> playerID;
    private Map<Integer, Board> board;

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

    public Player getActivePlayer(){
        int p = playerID.get(countPlayer % playerID.size());
        return player.getOrDefault(p, null);
    }

    public void turnExecution(){
        countPlayer++;
    }


    //METHOD TO BUY CREATION CARD

    public HashMap<Faction, HashMap<Integer, CreationCard>> displayCreationCard(){
        Player activePlayer = getActivePlayer();
        Board activeBoard = board.get(activePlayer.getID());

        return activeBoard.getBuyableCard();
    }

    public boolean buyCreationCard(CreationCard card){
        Player activePlayer = getActivePlayer();
        Board activeBoard = board.get(activePlayer.getID());

        if(activeBoard.canBuyCard(card)){
            activeBoard.getCardFromMarket(card);
            return true;
        }
        else
            return false;
    }

    //METHOD TO GET TO THE MARBLE MARKET

    public Marble[][] displayMarbleMarket(){
        Player activePlayer = getActivePlayer();
        Board activeBoard = board.get(activePlayer.getID());

        return marbleMarket.getMarbleBoard();
    }

    public Marble displayMarbleOut(){
        Player activePlayer = getActivePlayer();
        Board activeBoard = board.get(activePlayer.getID());

        return activeBoard.getMarbleOut();
    }

    public ArrayList<MarbleOption> selectMarbleColumn(int column){
        Player activePlayer = getActivePlayer();
        Board activeBoard = board.get(activePlayer.getID());

        return activeBoard.getColumn(column);
    }

    public ArrayList<MarbleOption> selectMarbleRow(int row){
        Player activePlayer = getActivePlayer();
        Board activeBoard = board.get(activePlayer.getID());

        return activeBoard.getRow(row);
    }

    public boolean placeResource(Row row, Resource resource){
        Player activePlayer = getActivePlayer();
        Board activeBoard = board.get(activePlayer.getID());
        if(activeBoard.canSetResourcesFromMarket(row, resource, 1)){
            activeBoard.setResourcesFromMarket(row, resource, 1);
            return true;
        }
        return false;
    }

    public ArrayList<Resource> transformMarble(ArrayList<Marble> marbleList){
        Player activePlayer = getActivePlayer();
        Board activeBoard = board.get(activePlayer.getID());

        return null;
    }


    //METHOD TO BUY PRODUCE

    public boolean startProduction(ArrayList<CreationCard> creationCards){
        Player activePlayer = getActivePlayer();
        Board activeBoard = board.get(activePlayer.getID());

        if(activeBoard.canProduceWithConfiguration(creationCards)){
            activeBoard.produce(creationCards);
            return true;
        }
        else
            return false;
    }

    public int getAllPoints(){
        Player activePlayer = getActivePlayer();
        Board activeBoard = board.get(activePlayer.getID());

        return activeBoard.getPoints();
    }


    public Integer getGameId() {
        return gameId;
    }


}

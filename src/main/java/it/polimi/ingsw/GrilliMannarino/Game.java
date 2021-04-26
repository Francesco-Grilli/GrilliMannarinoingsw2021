package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        marbleMarket = new MarbleMarket(4, 3, new HashMap<>());
    }

    public synchronized void setPlayer(Integer playerId, String nickName) {
        if(player.size()<4 && !player.containsKey(playerId) && !board.containsKey(playerId)){
            player.put(playerId, new Player(nickName, playerId));
            playerID.add(playerId);
            board.put(playerId, new Board(player.get(playerId), cardMarket, marbleMarket));
        }
    }

    private Player getActivePlayer(){
        int p = playerID.get(countPlayer % playerID.size());
        return player.getOrDefault(p, null);
    }

    public void turnExecution(){
        countPlayer++;
    }


    public void resourceMarketShopping(){
        Player activePlayer = getActivePlayer();
        Board activeBoard = board.get(activePlayer.getID());


    }

    public void cardMarketShopping(){
        Player activePlayer = getActivePlayer();
        Board activeBoard = board.get(activePlayer.getID());

        HashMap<Faction, HashMap<Integer, CreationCard>> buyableCard = activeBoard.getBuyableCard();


    }




}

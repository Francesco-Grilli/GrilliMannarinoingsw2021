package it.polimi.ingsw.GrilliMannarino;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Game {

    final private Integer gameId;
    private Map<Integer, Player> player;    //map or arrayList? obviously synchronized

    private Map<Integer, Board> board;

    private CardMarketBoardInterface cardMarket;
    private MarbleMarketBoardInterface marbleMarket;

    public Game(){
        gameId = (int) (Math.random()*1000)+1;
        player = new ConcurrentHashMap<>();
        board = new ConcurrentHashMap<>();
        cardMarket = new CardMarket();
        marbleMarket = new MarbleMarket(4, 3, new HashMap<>());
    }

    public synchronized void setPlayer(Integer playerId, String nickName) {
        if(player.size()<4 && !player.containsKey(playerId) && !board.containsKey(playerId)){
            player.put(playerId, new Player(nickName, playerId));
            board.put(playerId, new Board(player.get(playerId), cardMarket, marbleMarket));
        }
    }
}

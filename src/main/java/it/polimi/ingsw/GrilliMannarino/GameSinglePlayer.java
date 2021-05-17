package it.polimi.ingsw.GrilliMannarino;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class GameSinglePlayer extends Game{

    private CardMarketBoardInterfaceSingle cardMarketSingle;
    private BoardSinglePlayer boardSingle;
    private Integer playerId;

    public GameSinglePlayer(Integer gameId) {
        super(gameId, 1);
        this.cardMarketSingle = new CardMarket();
        this.cardMarket = this.cardMarketSingle;
    }

    @Override
    public Integer startGame() {
        return super.startGame();
    }

    @Override
    public synchronized boolean setPlayer(Integer playerId, String nickName) {
        if(player.size()<numberOfPlayer && !player.containsKey(playerId) && !board.containsKey(playerId)){
            this.playerId = playerId;
            player.put(playerId, new Player(nickName, playerId));
            playerID.add(playerId);
            boardSingle = new BoardSinglePlayer(player.get(playerId), cardMarketSingle, marbleMarket);
            board.put(playerId, boardSingle);
            return true;
        }
        return false;
    }

    @Override
    protected void setActivePlayer() {
        this.activePlayer = player.get(playerID.get(countPlayer));
    }

    @Override
    protected void setActiveBoard() {
        this.activeBoard = board.get(activePlayer.getID());
    }

    @Override
    public void turnExecution() {
        lorenzoAction();
    }

    private void lorenzoAction() {
        System.out.println("Lorenzo is executing an action");
    }

    @Override
    public HashMap<Integer, Map.Entry<Integer, Boolean>> checkPopeLine() {
        HashMap<Integer, Map.Entry<Integer, Boolean>> checkPope = new HashMap<>();

        Map.Entry<Integer, Boolean> playerCouple = new AbstractMap.SimpleEntry<>(activeBoard.getFaith(), activeBoard.checkPopeFaith());
        checkPope.put(playerId, playerCouple);
        Map.Entry<Integer, Boolean> lorenzoCouple = new AbstractMap.SimpleEntry<>(boardSingle.getLorenzoFaith(), boardSingle.checkLorenzoPopeFaith());
        checkPope.put(0, playerCouple);
        PopeLine.updateChecks();

        return checkPope;
    }

    @Override
    public HashMap<Integer, Integer> getPlayersFaith() {
        HashMap<Integer, Integer> playersFaith = new HashMap<>();

        playersFaith.put(playerId, boardSingle.getFaith());
        playersFaith.put(0, boardSingle.getLorenzoFaith());

        return playersFaith;
    }

    @Override
    public boolean sellLeaderCard(Integer cardCode) {
        boolean sell = boardSingle.sellLeaderCard(cardCode);
        if(boardSingle.getFaith()>=24){
            endGame = true;
        }
        return sell;
    }

    @Override
    public Integer getLorenzoFaith() {
        return boardSingle.getLorenzoFaith();
    }

    @Override
    public boolean addFaithExceptThis(Integer playerId, int numberOfResource) {
        boolean check = false;
        for(int i=0; i<numberOfResource; i++){
            if(boardSingle.addLorenzoFaith())
                check=true;
        }
        return check;
    }
}

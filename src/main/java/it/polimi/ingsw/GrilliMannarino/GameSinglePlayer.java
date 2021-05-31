package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.LorenzoToken;

import java.util.*;

public class GameSinglePlayer extends Game{

    private CardMarketBoardInterfaceSingle cardMarketSingle;
    private BoardSinglePlayer boardSingle;
    private Integer playerId;
    private ArrayList<LorenzoToken> tokens;
    private int tokenNumber = 0;
    private boolean win = true;

    public GameSinglePlayer(Integer gameId) {
        super(gameId, 1);
        this.cardMarketSingle = new CardMarket();
        this.cardMarket = this.cardMarketSingle;
        tokens = LorenzoToken.getLorenzoTokens();
        Collections.shuffle(tokens);
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
        if(activatedEnd)
            endGame = true;
        this.normalAction = true;
        this.leaderCardAction = true;
    }

    @Override
    public Map.Entry<LorenzoToken, Boolean> lorenzoAction() {
        System.out.println("Lorenzo is executing an action");

        LorenzoToken activeToken = popLorenzoToken();
        if(activeToken.getNumber().equals(0)){
            Map.Entry<LorenzoToken, Boolean> map = new AbstractMap.SimpleEntry<>(activeToken, boardSingle.doubleAddLorenzoFaith());
            if(boardSingle.getLorenzoFaith()>=24) {
                win = false;
                endGame = true;
            }
            return map;
        }
        else if(activeToken.getNumber().equals(1)){
            Collections.shuffle(tokens);
            Map.Entry<LorenzoToken, Boolean> map = new AbstractMap.SimpleEntry<>(activeToken, boardSingle.addLorenzoFaith());
            if(boardSingle.getLorenzoFaith()>=24) {
                win = false;
                endGame = true;
            }
            return map;
        }
        else{
            if(removeCard(activeToken.getFaction())) {
                win = false;
                endGame = true;
            }
            return new AbstractMap.SimpleEntry<>(activeToken, false);
        }
    }

    private boolean removeCard(Faction faction) {
        return cardMarketSingle.deleteTwoCards(faction);
    }


    private LorenzoToken popLorenzoToken() {
        tokenNumber++;
        return tokens.get(tokenNumber%(tokens.size()));
    }

    @Override
    public HashMap<Integer, Map.Entry<Integer, Boolean>> checkPopeLine() {
        HashMap<Integer, Map.Entry<Integer, Boolean>> checkPope = new HashMap<>();

        Map.Entry<Integer, Boolean> playerCouple = new AbstractMap.SimpleEntry<>(activeBoard.getFaith(), activeBoard.checkPopeFaith());
        checkPope.put(playerId, playerCouple);
        Map.Entry<Integer, Boolean> lorenzoCouple = new AbstractMap.SimpleEntry<>(boardSingle.getLorenzoFaith(), boardSingle.checkLorenzoPopeFaith());
        checkPope.put(0, lorenzoCouple);
        this.updateAllPopelineCheck();
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
        if(boardSingle.getLorenzoFaith()>=24)
            endGame = true;
        return check;
    }

    public boolean isWin() {
        return win;
    }
}

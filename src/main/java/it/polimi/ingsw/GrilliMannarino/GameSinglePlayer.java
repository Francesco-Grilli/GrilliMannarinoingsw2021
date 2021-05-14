package it.polimi.ingsw.GrilliMannarino;

public class GameSinglePlayer extends Game{

    private CardMarketBoardInterfaceSingle cardMarketSingle;
    private BoardSinglePlayer boardSingle;
    private Player playersingle;

    public GameSinglePlayer(Integer gameId) {
        super(gameId, 1);
        this.cardMarketSingle = new CardMarket();
        this.cardMarket = this.cardMarketSingle;
        setActivePlayer();
        setActiveBoard();
    }

    @Override
    public synchronized boolean setPlayer(Integer playerId, String nickName) {
        if(player.size()<numberOfPlayer && !player.containsKey(playerId) && !board.containsKey(playerId)){
            player.put(playerId, new Player(nickName, playerId));
            playerID.add(playerId);
            board.put(playerId, new BoardSinglePlayer(player.get(playerId), cardMarketSingle, marbleMarket));
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

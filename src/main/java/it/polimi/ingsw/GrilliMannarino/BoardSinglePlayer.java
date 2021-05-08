package it.polimi.ingsw.GrilliMannarino;

public class BoardSinglePlayer extends Board{

    public BoardSinglePlayer(Player player, CardMarketBoardInterface cardMarket, MarbleMarketBoardInterface marbleMarket) {
        super(player, cardMarket, marbleMarket);
        this.popeLine = new PopeLineSingle();
    }

}

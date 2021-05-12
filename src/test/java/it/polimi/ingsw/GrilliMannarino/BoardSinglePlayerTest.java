package it.polimi.ingsw.GrilliMannarino;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardSinglePlayerTest {


    @Test
    public void popeLineSingleTest(){
        Board board = new Board(new Player("francesco", 1), new CardMarket(), new MarbleMarket());
        board.addPopeFaith();
        board.doubleAddLorenzoFaith();
        board.addPopeFaith();
        assertEquals(-1, board.getLorenzoFaith());
    }

    @Test
    public void popeLine2(){
        Board board = new BoardSinglePlayer(new Player("francesco", 1), new CardMarket(), new MarbleMarket())board.addPopeFaith();
        board.doubleAddLorenzoFaith();
        board.addPopeFaith();
        assertEquals(2, board.getLorenzoFaith());
    }

}
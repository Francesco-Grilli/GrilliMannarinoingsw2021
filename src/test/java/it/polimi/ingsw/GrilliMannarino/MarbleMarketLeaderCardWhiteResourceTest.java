package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarbleMarketLeaderCardWhiteResourceTest {

  @Test
  public void executeTest(){
    Player p = new Player("prova", 112233);
    MarbleMarket m = new MarbleMarket();
    CardMarket c = new CardMarket();
    Board boardtest = new Board(p, c, m);

    assertNotNull(boardtest.getMarbleMarket());
    assertEquals(boardtest.getMarbleMarket(), m);

    LeaderCard l = new MarbleMarketLeaderCardWhiteResource(new HashMap<>(), new HashMap<>(), Resource.COIN,4,1);

    l.execute(boardtest);

    assertNotNull(boardtest.getMarbleMarket());
    assertNotEquals(boardtest.getMarbleMarket(), m);
    assertEquals(boardtest.getMarbleMarket(),l);
  }

  @Test
  public void getMarbleBoardTest() {
    Player p = new Player("prova", 112233);
    MarbleMarket m = new MarbleMarket();
    CardMarket c = new CardMarket();
    Board boardtest = new Board(p, c, m);

    LeaderCard l = new MarbleMarketLeaderCardWhiteResource(new HashMap<>(), new HashMap<>(), Resource.COIN, 4,1);

    l.execute(boardtest);

    Marble[][] t = boardtest.getMarbleMarket().getMarbleBoard();
    assertArrayEquals(t, boardtest.getMarbleMarket().getMarbleBoard());
    assertNotNull(boardtest.getMarbleMarket().getMarbleBoard());
  }

  @Test
  public void getMarbleOutTest(){
    Player p = new Player("prova", 112233);
    MarbleMarket m = new MarbleMarket();
    CardMarket c = new CardMarket();
    Board boardtest = new Board(p, c, m);

    LeaderCard l = new MarbleMarketLeaderCardWhiteResource(new HashMap<>(), new HashMap<>(), Resource.COIN, 4,1);

    l.execute(boardtest);

    assertNotNull(boardtest.getMarbleMarket().getMarbleOut());
    assertEquals(Marble.WHITE.getClass(),boardtest.getMarbleMarket().getMarbleOut().getClass());
  }

  @Test
  public void getColumnTest(){
    Player p = new Player("prova", 112233);
    MarbleMarket m = new MarbleMarket();
    CardMarket c = new CardMarket();
    Board boardtest = new Board(p, c, m);

    LeaderCard l = new MarbleMarketLeaderCardWhiteResource(new HashMap<>(), new HashMap<>(), Resource.COIN, 4,1);

    l.execute(boardtest);

    Marble[][] t = boardtest.getMarbleMarket().getMarbleBoard();
    assertArrayEquals(t, boardtest.getMarbleMarket().getMarbleBoard());
    assertNotNull(boardtest.getMarbleMarket().getMarbleBoard());
    assertNotNull(boardtest.getMarbleMarket().getColumn(2));
    assertNotNull(boardtest.getMarbleMarket().getMarbleBoard());
    assertNotEquals(t, boardtest.getMarbleMarket().getMarbleBoard());
    assertNotEquals(boardtest.getMarbleMarket().getColumn(2), boardtest.getMarbleMarket().getColumn(2));
  }

  @Test
  public void getRowTest(){
    Player p = new Player("prova", 112233);
    MarbleMarket m = new MarbleMarket();
    CardMarket c = new CardMarket();
    Board boardtest = new Board(p, c, m);

    LeaderCard l = new MarbleMarketLeaderCardWhiteResource(new HashMap<>(), new HashMap<>(), Resource.COIN, 4,1);

    l.execute(boardtest);

    Marble[][] t = boardtest.getMarbleMarket().getMarbleBoard();
    assertArrayEquals(t, boardtest.getMarbleMarket().getMarbleBoard());
    assertNotNull(boardtest.getMarbleMarket().getMarbleBoard());
    ArrayList<MarbleOption> test = boardtest.getMarbleMarket().getRow(2);
    assertNotNull(boardtest.getMarbleMarket().getRow(2));
    test = boardtest.getMarbleMarket().getRow(1);
    test = boardtest.getMarbleMarket().getRow(3);
    assertNotNull(boardtest.getMarbleMarket().getMarbleBoard());
    assertNotEquals(t, boardtest.getMarbleMarket().getMarbleBoard());
    assertNotEquals(boardtest.getMarbleMarket().getRow(2), boardtest.getMarbleMarket().getRow(2));
  }
}

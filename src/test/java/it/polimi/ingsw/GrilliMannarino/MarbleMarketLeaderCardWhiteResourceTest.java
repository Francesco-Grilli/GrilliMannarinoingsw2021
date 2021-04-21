package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarbleMarketLeaderCardWhiteResourceTest {

  @Test
  public void executeTest(){
    Player p = new Player("prova", 112233);
    MarbleMarket m = new MarbleMarket(3,4, new HashMap<>());
    CardMarket c = new CardMarket();
    Board boardtest = new Board(p, c, m);

    assertNotNull(boardtest.getMarbleMarket());
    assertEquals(boardtest.getMarbleMarket(), m);

    LeaderCard l = new MarbleMarketLeaderCardWhiteResource(new HashMap<>(), new HashMap<>(), Resource.COIN,4);

    l.execute(boardtest);

    assertNotNull(boardtest.getMarbleMarket());
    assertNotEquals(boardtest.getMarbleMarket(), m);
    assertEquals(boardtest.getMarbleMarket(),l);
  }
}

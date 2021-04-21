package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class CardMarketLeaderCardDiscountTest {

  @Test
  public void executeTest(){
    Player p = new Player("prova", 112233);
    MarbleMarket m = new MarbleMarket(3,4, new HashMap<>());
    CardMarket c = new CardMarket();
    Board boardtest = new Board(p, c, m);

    assertNotNull(boardtest.getCardMarket());
    assertEquals(boardtest.getCardMarket(), c);

    LeaderCard l = new CardMarketLeaderCardDiscount(new HashMap<>(), new HashMap<>(), Resource.COIN,4);

    l.execute(boardtest);

    assertNotNull(boardtest.getCardMarket());
    assertNotEquals(boardtest.getCardMarket(), c);
    assertEquals(boardtest.getCardMarket(),l);
  }

  @Test
  public void getCardsTest(){
    Player p = new Player("prova", 112233);
    MarbleMarket m = new MarbleMarket(3,4, new HashMap<>());
    CardMarket c = new CardMarket();
    Board boardtest = new Board(p, c, m);
    Resource res = Resource.COIN;

    LeaderCard l = new CardMarketLeaderCardDiscount(new HashMap<>(), new HashMap<>(), res,4);

    l.execute(boardtest);

    HashMap<Faction, HashMap<Integer,CreationCard>> t = c.getCards();
    HashMap<Faction, HashMap<Integer,CreationCard>> t1 = boardtest.getCardMarket().getCards();

    for(Faction fac: t.keySet()){
      for(Integer lev: t.get(fac).keySet()){
        if(t.get(fac).get(lev) != null){
          if(t.get(fac).get(lev).getPrice().get(res) != null){
            assertEquals(t.get(fac).get(lev).getPrice().get(res),t1.get(fac).get(lev).getPrice().get(res)+1);
          }
        }
      }
    }
  }

  @Test
  public void buyCardTest(){
    Player p = new Player("prova", 112233);
    MarbleMarket m = new MarbleMarket(3,4, new HashMap<>());
    CardMarket c = new CardMarket();
    Board boardtest = new Board(p, c, m);
    Resource res = Resource.COIN;

    LeaderCard l = new CardMarketLeaderCardDiscount(new HashMap<>(), new HashMap<>(), res,4);

    l.execute(boardtest);

    assertNull(boardtest.getCardMarket().buyCard(Faction.NONE,1));
    assertNull(boardtest.getCardMarket().buyCard(Faction.GREEN, -1));
    assertNotNull(boardtest.getCardMarket().buyCard(Faction.GREEN,1));
  }

}

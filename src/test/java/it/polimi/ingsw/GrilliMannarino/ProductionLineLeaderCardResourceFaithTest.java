package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

public class ProductionLineLeaderCardResourceFaithTest {

  @Test
  public void executeTest(){
    Player p = new Player("prova", 112233);
    MarbleMarket m = new MarbleMarket();
    CardMarket c = new CardMarket();
    Board boardtest = new Board(p, c, m);

    assertNotNull(boardtest.getProductionLine());

    LeaderCard l = new ProductionLineLeaderCardResourceFaith(new HashMap<>(), new HashMap<>(), Resource.COIN,4);

    l.execute(boardtest);

    assertNotNull(boardtest.getProductionLine());
    assertEquals(boardtest.getProductionLine(), l);
  }

  @Test
  public void getNextFreeSlotTest(){
    Player p = new Player("prova", 112233);
    MarbleMarket m = new MarbleMarket();
    CardMarket c = new CardMarket();
    Board boardtest = new Board(p, c, m);
    assertEquals(4,boardtest.getProductionLine().getNextFreeSlot());
    LeaderCard l = new ProductionLineLeaderCardResourceFaith(new HashMap<>(), new HashMap<>(), Resource.COIN,4);
    l.execute(boardtest);
    assertEquals(5,boardtest.getProductionLine().getNextFreeSlot());
  }

  @Test
  public void getCardsTest(){
    Player p = new Player("prova", 112233);
    MarbleMarket m = new MarbleMarket();
    CardMarket c = new CardMarket();
    Board boardtest = new Board(p, c, m);
    LeaderCard l = new ProductionLineLeaderCardResourceFaith(new HashMap<>(), new HashMap<>(), Resource.COIN,4);
    ProductionLineBoardInterface oldprod = boardtest.getProductionLine();
    l.execute(boardtest);

    HashMap<Integer,CreationCard> cards =  ((ProductionLineBoardInterface) l).getCards();

    HashMap<Resource,Integer> out = new HashMap<>(), in = new HashMap<>();
    in.put(Resource.COIN,1);
    out.put(Resource.UNKNOWN, 1);
    out.put(Resource.FAITH, 1);

    assertEquals(cards.get(oldprod.getNextFreeSlot()).getInput(),in);
    assertEquals(cards.get(oldprod.getNextFreeSlot()).getOutput(),out);
  }

  @Test
  public void addCardTest(){
    Player p = new Player("prova", 112233);
    MarbleMarket m = new MarbleMarket();
    CardMarket c = new CardMarket();
    Board boardtest = new Board(p, c, m);
    LeaderCard l = new ProductionLineLeaderCardResourceFaith(new HashMap<>(), new HashMap<>(), Resource.COIN,4);
    l.execute(boardtest);

    assertTrue(boardtest.getProductionLine().addCard(1, new CreationCard(1,1,1,Faction.GREEN, null, null,null)));
    assertFalse(boardtest.getProductionLine().addCard(1, new CreationCard(2,1,1,Faction.GREEN, null, null,null)));
    assertTrue(boardtest.getProductionLine().addCard(2, new CreationCard(3,1,1,Faction.GREEN, null, null,null)));

    assertEquals(boardtest.getProductionLine().getCards().get(2).getCardCode(),1);
    assertEquals(boardtest.getProductionLine().getCards().get(3).getCardCode(),3);
  }
}

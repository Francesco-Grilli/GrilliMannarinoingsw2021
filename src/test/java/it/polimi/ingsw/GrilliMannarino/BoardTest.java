package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;
import org.apache.xerces.impl.dv.xs.YearDV;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BoardTest {

  @Test
  public void getBuyableCardTest() {
    Board test = new Board(null,new CardMarket(),null);

    HashMap<Faction, HashMap<Integer, Map.Entry<CreationCard, Boolean>>> testAnswer = test.getBuyableCard();
    assertNotNull(testAnswer);
    assertEquals(HashMap.class, test.getBuyableCard().getClass());
  }

  @Test
  public void canProduceWithConfigurationTest(){
    Board test = new Board(null,null,null);
    HashMap<Resource,Integer> res = new HashMap<>(),prod1 = new HashMap<>(), prod2 = new HashMap<>(), prod3 = new HashMap<>();
    res.put(Resource.COIN, 10);
    res.put(Resource.SHIELD, 10);
    res.put(Resource.SERVANT, 10);
    res.put(Resource.STONE, 10);
    test.setResourcesFromProduction(res);

    prod1.put(Resource.COIN, 1);
    prod1.put(Resource.SHIELD, 1);
    prod1.put(Resource.SERVANT, 1);
    prod1.put(Resource.STONE, 1);
    prod2.put(Resource.COIN, 4);
    prod2.put(Resource.SHIELD, 4);
    prod2.put(Resource.SERVANT, 4);
    prod2.put(Resource.STONE, 4);
    prod3.put(Resource.COIN, 10);
    prod3.put(Resource.SHIELD, 10);
    prod3.put(Resource.SERVANT, 10);
    prod3.put(Resource.STONE, 10);



    ArrayList<CreationCard> cards = new ArrayList<>();
    cards.add(new CreationCard(1,1,1,Faction.NONE,new HashMap<>(), prod1, new HashMap<>()));
    assertTrue(test.canProduceWithConfiguration(cards));
    cards.add(new CreationCard(1,1,1,Faction.NONE,new HashMap<>(), prod2, new HashMap<>()));
    assertTrue(test.canProduceWithConfiguration(cards));
    cards.add(new CreationCard(1,1,1,Faction.NONE,new HashMap<>(), prod3, new HashMap<>()));
    assertFalse(test.canProduceWithConfiguration(cards));


  }

  @Test
  public void produceTest(){
    Board test = new Board(null,null,null);
    HashMap<Resource,Integer> res = new HashMap<>(),out = new HashMap<>(),prod1 = new HashMap<>(), prod2 = new HashMap<>();
    res.put(Resource.COIN, 10);
    res.put(Resource.SHIELD, 10);
    res.put(Resource.SERVANT, 10);
    res.put(Resource.STONE, 10);
    test.setResourcesFromProduction(res);

    prod1.put(Resource.COIN, 1);
    prod1.put(Resource.SHIELD, 1);
    prod1.put(Resource.SERVANT, 1);
    prod1.put(Resource.STONE, 1);
    prod2.put(Resource.COIN, 4);
    prod2.put(Resource.SHIELD, 4);
    prod2.put(Resource.SERVANT, 4);
    prod2.put(Resource.STONE, 4);
    ArrayList<CreationCard> cards = new ArrayList<>();
    cards.add(new CreationCard(1,1,1,Faction.NONE,new HashMap<>(), prod1, new HashMap<>()));
    assertTrue(test.canProduceWithConfiguration(cards));
    test.produce(cards);
    out = test.getResources();
    assertEquals(out.get(Resource.STONE),9);
    assertEquals(out.get(Resource.SHIELD),9);
    assertEquals(out.get(Resource.COIN),9);
    assertEquals(out.get(Resource.SERVANT),9);
    cards.add(new CreationCard(1,1,1,Faction.NONE,new HashMap<>(), prod2, new HashMap<>()));
    assertTrue(test.canProduceWithConfiguration(cards));
    test.produce(cards);
    out = test.getResources();
    assertEquals(out.get(Resource.STONE),4);
    assertEquals(out.get(Resource.SHIELD),4);
    assertEquals(out.get(Resource.COIN),4);
    assertEquals(out.get(Resource.SERVANT),4);
    assertFalse(test.canProduceWithConfiguration(cards));
  }

  @Test
  public void getMarblesFromMarketTest(){
    MarbleMarket test = new MarbleMarket();
    Marble[][] t = test.getMarbleBoard();
    assertArrayEquals(t, test.getMarbleBoard());
    assertNotNull(test.getMarbleBoard());
  }

  @Test
  public void getMarbleOutTest(){

    Board test = new Board(null,null,new MarbleMarket());

    assertNotNull(test.getMarbleOut());
    assertEquals(Marble.WHITE.getClass(), test.getMarbleOut().getClass());
  }

  @Test
  public void getRowTest(){

    Board test =  new Board(null,null,new MarbleMarket());
    Marble[][] t = test.getMarblesFromMarket();
    assertArrayEquals(t, test.getMarblesFromMarket());
    assertNotNull(test.getMarblesFromMarket());
    assertNotNull(test.getRow(2));
    assertNotNull(test.getMarblesFromMarket());
    Marble[][] t2 = test.getMarblesFromMarket();
    assertNotEquals(t, t2);
    assertNotEquals(test.getRow(2), test.getRow(2));
  }

  @Test
  public void getColumnTest(){

    Board test =  new Board(null,null,new MarbleMarket());
    Marble[][] t = test.getMarblesFromMarket();
    assertArrayEquals(t, test.getMarblesFromMarket());
    assertNotNull(test.getMarblesFromMarket());
    assertNotNull(test.getColumn(2));
    assertNotNull(test.getMarblesFromMarket());
    assertNotEquals(t, test.getMarblesFromMarket());
    assertNotEquals(test.getColumn(2), test.getColumn(2));
  }

  @Test
  public void setLeaderCardsTest(){
    ArrayList<LeaderCard> t = new ArrayList<>();
    t.add(new CardMarketLeaderCardDiscount(null,null,Resource.COIN,1,12));
    t.add(new CardMarketLeaderCardDiscount(null,null,Resource.COIN,1,13));
    Board test = new Board(null,null,null);
    test.setLeaderCards(t);
    assertTrue(test.getLeaderCards().contains(12));
    assertTrue(test.getLeaderCards().contains(13));
  }

  @Test
  public void activateLeaderCardTest(){
    ArrayList<LeaderCard> t = new ArrayList<>();
    HashMap<Resource,Integer> res1 = new HashMap<>(), res2 = new HashMap<>(),mas = new HashMap<>();
    res1.put(Resource.COIN,3);
    res1.put(Resource.SHIELD,2);
    t.add(new CardMarketLeaderCardDiscount(res1,new HashMap<>(),Resource.COIN,1,12));
    t.add(new CardMarketLeaderCardDiscount(res2,new HashMap<>(),Resource.COIN,1,13));
    Board test = new Board(null,null,null);
    test.setLeaderCards(t);
    assertFalse(test.activateLeaderCard(12));
    mas.put(Resource.COIN,10);
    mas.put(Resource.SHIELD,10);
    test.setResourcesFromProduction(mas);
    assertTrue(test.activateLeaderCard(12));
  }

  @Test
  public void getPointsTest(){
    Board test = new Board(null,null,null);
    assertEquals(0, test.getPoints());
    HashMap<Resource,Integer> res = new HashMap<>();
    res.put(Resource.SERVANT,10);
    test.setResourcesFromProduction(res);
    assertEquals(2, test.getPoints());
  }

  @Test
  public void setResourcesTest(){
    Board test = new Board(null,null,null);
    HashMap<Resource, Integer> res = new HashMap<>();

    //add resources to the first line STONE
    assertNull(test.setResourcesFromMarket(Row.FIRST, Resource.STONE, 1));
    assertNotNull(test.setResourcesFromMarket(Row.FIRST, Resource.SHIELD, 2));
    assertNotNull(test.setResourcesFromMarket(Row.THIRD, Resource.STONE, 3));
    assertEquals(1, test.getResources().get(Resource.STONE));

    //add resources to the third line COIN
    assertNotNull(test.setResourcesFromMarket(Row.THIRD, Resource.COIN, 5));
    assertNull(test.getResources().get(Resource.COIN));
    assertTrue(test.canSetResourcesFromMarket(Row.THIRD, Resource.COIN, 3));
    assertNull(test.setResourcesFromMarket(Row.THIRD, Resource.COIN, 3));
    assertNotNull(test.setResourcesFromMarket(Row.THIRD, Resource.COIN, 1));
    assertNotNull(test.setResourcesFromMarket(Row.SECOND, Resource.COIN, 1));
    assertEquals(3, test.getResources().get(Resource.COIN));

    //add resources to the second line SHIELD
    assertNull(test.setResourcesFromMarket(Row.SECOND, Resource.SHIELD, 1));
    assertEquals(1, test.getResources().get(Resource.SHIELD));
    assertNotNull(test.setResourcesFromMarket(Row.SECOND, Resource.COIN, 1));
    assertNull(test.setResourcesFromMarket(Row.SECOND, Resource.SHIELD, 1));
    assertEquals(2, test.getResources().get(Resource.SHIELD));
    res.put(Resource.COIN, 3);
    res.put(Resource.SHIELD, 6);
    res.put(Resource.SERVANT, 10);
    res.put(Resource.STONE, 3);
    res.put(Resource.COIN, res.get(Resource.COIN)+1);
    res.put(Resource.SERVANT, res.get(Resource.SERVANT)+3);
    test.setResourcesFromProduction(res);
    test.setResourcesFromProduction(res);
    assertEquals(7, test.getResources().get(Resource.STONE));
    assertEquals(11, test.getResources().get(Resource.COIN));
    assertEquals(14, test.getResources().get(Resource.SHIELD));
    assertEquals(26, test.getResources().get(Resource.SERVANT));
  }

  @Test
  public void getResourceTest(){

    Board c = new Board(null,null,null);
    HashMap<Resource, Integer> res = new HashMap<>();
    res.put(Resource.COIN, 5);
    res.put(Resource.SERVANT, 10);
    res.put(Resource.STONE, 5);
    c.setResourcesFromProduction(res);
    assertNull(c.setResourcesFromMarket(Row.THIRD, Resource.STONE, 2));
    assertNull(c.setResourcesFromMarket(Row.SECOND, Resource.COIN, 1));
    assertNull(c.setResourcesFromMarket(Row.FIRST, Resource.SHIELD, 1));
    res = c.getResources();
    assertEquals(6, res.get(Resource.COIN));
    assertEquals(10, res.get(Resource.SERVANT));
    assertEquals(7, res.get(Resource.STONE));
    assertEquals(1, res.get(Resource.SHIELD));
  }

  @Test
  public void canSwapLineFromWareHouseTest(){

    Board manager1 = new Board(null,null,null);
    manager1.setResourcesFromMarket(Row.SECOND, Resource.COIN, 1);
    manager1.setResourcesFromMarket(Row.THIRD, Resource.SHIELD, 2);
    manager1.setResourcesFromMarket(Row.THIRD, Resource.SHIELD, 1);
    manager1.setResourcesFromMarket(Row.FIRST, Resource.STONE, 1);
    assertTrue(manager1.canSwapLineFromWareHouse(Row.SECOND, Row.FIRST));

    Board manager2 = new Board(null,null,null);
    manager2.setResourcesFromMarket(Row.FIRST, Resource.STONE, 1);
    assertTrue(manager2.canSwapLineFromWareHouse(Row.FIRST, Row.SECOND));
    manager2.setResourcesFromMarket(Row.SECOND, Resource.COIN, 1);
    assertTrue(manager2.canSwapLineFromWareHouse(Row.FIRST, Row.SECOND));
    manager2.setResourcesFromMarket(Row.SECOND, Resource.COIN, 1);
    assertFalse(manager2.canSwapLineFromWareHouse(Row.FIRST, Row.SECOND));
    manager2.setResourcesFromMarket(Row.THIRD, Resource.SERVANT, 3);
    assertFalse(manager2.canSwapLineFromWareHouse(Row.THIRD, Row.SECOND));

    Board manager3 = new Board(null,null,null);
    manager3.setResourcesFromMarket(Row.THIRD, Resource.COIN, 2);
    assertFalse(manager3.canSwapLineFromWareHouse(Row.THIRD, Row.FIRST));
  }

  @Test
  public void forceSwapLineFromWareHouseTest(){
    Board manager1 = new Board(null,null,null);
    manager1.setResourcesFromMarket(Row.THIRD, Resource.COIN, 3);
    assertFalse(manager1.canSwapLineFromWareHouse(Row.THIRD, Row.SECOND));
    assertFalse(manager1.canSwapLineFromWareHouse(Row.SECOND, Row.THIRD));
    manager1.forceSwapLineFromWareHouse(Row.THIRD, Row.SECOND);
    assertTrue(manager1.canSwapLineFromWareHouse(Row.THIRD, Row.SECOND));
    manager1.forceSwapLineFromWareHouse(Row.THIRD, Row.SECOND);

    manager1.setResourcesFromMarket(Row.FIRST, Resource.SERVANT, 1);
    assertTrue(manager1.canSwapLineFromWareHouse(Row.FIRST, Row.SECOND));
    manager1.forceSwapLineFromWareHouse(Row.FIRST, Row.SECOND);
    assertTrue(manager1.canSwapLineFromWareHouse(Row.FIRST, Row.SECOND));
    manager1.forceSwapLineFromWareHouse(Row.FIRST, Row.SECOND);

    manager1.setResourcesFromMarket(Row.SECOND, Resource.STONE, 2);
    assertTrue(manager1.canSwapLineFromWareHouse(Row.SECOND, Row.THIRD));
    assertFalse(manager1.canSwapLineFromWareHouse(Row.SECOND, Row.FIRST));
    manager1.forceSwapLineFromWareHouse(Row.SECOND, Row.THIRD);
    manager1.forceSwapLineFromWareHouse(Row.SECOND, Row.THIRD);
    manager1.forceSwapLineFromWareHouse(Row.SECOND, Row.FIRST);
    manager1.forceSwapLineFromWareHouse(Row.SECOND, Row.FIRST);

    Board manager2 = new Board(null,null,null);
    assertTrue(manager2.canSwapLineFromWareHouse(Row.FIRST, Row.SECOND));
    manager2.canSwapLineFromWareHouse(Row.FIRST, Row.SECOND);
  }

  @Test
  public void addPopeFaithTest(){
    Board test = new Board(null,null,null);
    assertEquals(0, test.getFaith());
    assertFalse(test.addPopeFaith());
    assertEquals(1, test.getFaith());
    assertFalse(test.getFaithSteps()[0]);
    assertFalse(test.addPopeFaith());
  }

  /*@Test
  public void getFaithTrackPointsTest(){
    Board test = new Board(null,null,null);
    Board test2 = new Board(null,null,null);

    for(int i=0; i<7; i++) {
      assertFalse(test.addPopeFaith());
      assertFalse(test2.addPopeFaith());
    }

    assertTrue(test.addPopeFaith());
    assertTrue(test.checkPopeFaith());
    assertTrue(test2.checkPopeFaith());
    PopeLine.updateChecks();
    assertFalse(test2.addPopeFaith());
    assertEquals(8, test.getFaith());
    assertEquals(8, test2.getFaith());
    // pope1 = 5;   favor=true, false, false
    // pope2 = 5;   favor=true, false, false
    assertEquals(4, test.getFaithTrackPoints());
    assertEquals(4, test2.getFaithTrackPoints());
    for(int i=0; i<7; i++)
      assertFalse(test2.addPopeFaith());
    assertTrue(test2.addPopeFaith());
    assertTrue(test2.checkPopeFaith());
    assertFalse(test.checkPopeFaith());
    PopeLine.updateChecks();
    // pope1 = 5;   favor= true, false, false
    // pope2 = 12;  favor= true, true, false
    for(int i=0; i<3; i++)
      assertFalse(test2.addPopeFaith());
    //pope2 = 15;   favor=false, true, false
    assertEquals(4, test.getFaithTrackPoints());
    assertEquals(17, test2.getFaithTrackPoints());
    for(int i=0; i<15; i++)
      assertFalse(test.addPopeFaith());
    assertTrue(test.addPopeFaith());
    assertTrue(test.checkPopeFaith());
    assertTrue(test2.checkPopeFaith());
    PopeLine.updateChecks();
    assertEquals(26, test.getFaithTrackPoints());
    assertEquals(21, test2.getFaithTrackPoints());
  }*/

  @Test
  public void getFaithChecksTest(){
    Board test = new Board(null,null,null);
    System.out.println("Popeline static: " + test.getFaithChecks()[0]);
    for(int i=0; i<7; i++){
      assertFalse(test.addPopeFaith());
      assertEquals(i+1, test.getFaith());
    }
    assertTrue(test.addPopeFaith());
    assertEquals(8, test.getFaith());
    assertTrue(test.checkPopeFaith());
    test.updateChecks();
    assertTrue(test.getFaithSteps()[0]);
    assertTrue(test.getFaithChecks()[0]);
  }

  @Test
  public void addLorenzoFaithTest(){}

  @Test
  public void doubleAddLorenzoFaithTest(){}

  @Test
  public void getLorenzoFaithTest(){}

  @Test
  public void getInputOfConfigurationTest(){
    Board test = new Board(null,null,null);
    ArrayList<CreationCard> config = new ArrayList<>();
    HashMap<Resource,Integer> input1= new HashMap<>(), input2= new HashMap<>(), input3;
    input1.put(Resource.COIN,2);
    input1.put(Resource.STONE,3);
    input2.put(Resource.SHIELD,4);
    input2.put(Resource.SERVANT,1);
    config.add(new CreationCard(1,1,1, Faction.NONE,null,input1,null));
    assertEquals(test.getInputOfConfiguration(config), input1);
    config.add(new CreationCard(1,1,1, Faction.NONE,null,input2,null));
    input3 = new HashMap<>(input2);
    input1.forEach((key, value) -> input3.merge(key, value, Integer::sum));
    assertEquals(test.getInputOfConfiguration(config), input3);
  }

  @Test
  public void getOutputOfConfigurationTest(){
    Board test = new Board(null,null,null);
    ArrayList<CreationCard> config = new ArrayList<>();
    HashMap<Resource,Integer> output1= new HashMap<>(), output2= new HashMap<>(), output3;
    output1.put(Resource.FAITH,2);
    output1.put(Resource.SERVANT,3);
    output1.put(Resource.SHIELD,1);
    output2.put(Resource.SHIELD,4);
    output2.put(Resource.SERVANT,1);
    config.add(new CreationCard(1,1,1, Faction.NONE,null,null,output1));
    assertEquals(test.getOutputOfConfiguration(config), output1);
    config.add(new CreationCard(1,1,1, Faction.NONE,null,null,output2));
    output3 = new HashMap<>(output2);
    output1.forEach((key, value) -> output3.merge(key, value, Integer::sum));
    assertEquals(test.getOutputOfConfiguration(config), output3);
  }

}

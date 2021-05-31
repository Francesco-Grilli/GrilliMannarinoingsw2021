package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class ProductionLineTest {
  @Test
  public void addCardTest(){
    ProductionLine test = new ProductionLine();
    HashMap<Resource,Integer> price = new HashMap<>(),input1 = new HashMap<>(),output2 = new HashMap<>();

    assertTrue(test.addCard(1,new CreationCard(2,1,1, Faction.BLUE,price, input1, output2)));
    assertTrue(test.addCard(2,new CreationCard(3,1,1, Faction.BLUE,price, input1, output2)));
    assertTrue(test.addCard(3,new CreationCard(4,1,1, Faction.BLUE,price, input1, output2)));
    assertFalse(test.addCard(0,new CreationCard(5,1,1, Faction.BLUE,price, input1, output2)));
    assertTrue(test.addCard(3,new CreationCard(6,2,1, Faction.BLUE,price, input1, output2)));
    assertTrue(test.addCard(0,new CreationCard(7,2,1, Faction.BLUE,price, input1, output2)));
    assertTrue(test.addCard(1,new CreationCard(8,3,1, Faction.BLUE,price, input1, output2)));
    assertFalse(test.addCard(1,new CreationCard(9,1,1, Faction.BLUE,price, input1, output2)));
  }

  @Test
  public void getCardsTest(){
    ProductionLine test = new ProductionLine();
    HashMap<Integer, CreationCard> testOut = new HashMap<>();
    HashMap<Resource,Integer> voidInput = new HashMap<>(), voidOutput = new HashMap<>();
    voidInput.put(Resource.UNKNOWN,2);
    voidOutput.put(Resource.UNKNOWN,1);
    testOut.put(0, new CreationCard(0,0,0,Faction.NONE,new HashMap<>(), voidInput, voidOutput));

    HashMap<Integer, CreationCard> testOut1 = test.getCards();

    assertEquals(testOut.get(0).getOutput(),test.getCards().get(0).getOutput());
    assertEquals(testOut.get(0).getInput(),test.getCards().get(0).getInput());
    assertEquals(testOut.get(0).getCardCode(),test.getCards().get(0).getCardCode());
    assertEquals(testOut.get(0).getCardLevel(),test.getCards().get(0).getCardLevel());
    assertEquals(testOut.get(0).getFaction(),test.getCards().get(0).getFaction());
    assertEquals(testOut.get(0).getPrice(),test.getCards().get(0).getPrice());
    assertNull(test.getCards().get(1));
    assertNull(test.getCards().get(2));

    test.addCard(1,new CreationCard(1,1,1,Faction.GREEN,new HashMap<>(), new HashMap<>(), new HashMap<>()));
    test.addCard(2,new CreationCard(1,1,2,Faction.BLUE,new HashMap<>(), new HashMap<>(), new HashMap<>()));

    assertEquals(1,test.getCards().get(1).getCardCode());
    assertEquals(1,test.getCards().get(1).getCardLevel());
    assertEquals(1,test.getCards().get(1).getValue());
    assertEquals(Faction.GREEN,test.getCards().get(1).getFaction());
    assertNotNull(test.getCards().get(2));
    assertNull(test.getCards().get(3));

    test.addCard(0,new CreationCard(1,2,3,Faction.PURPLE,new HashMap<>(), new HashMap<>(), new HashMap<>()));

    assertEquals(1,test.getCards().get(1).getCardCode());
    assertEquals(2,test.getCards().get(1).getCardLevel());
    assertEquals(3,test.getCards().get(1).getValue());
    assertEquals(Faction.PURPLE,test.getCards().get(1).getFaction());
    assertNotNull(test.getCards().get(2));
    assertNull(test.getCards().get(3));
  }

  @Test
  public void getNextFreeSlotTest(){
    ProductionLineBoardInterface test = new ProductionLine();

    assertEquals(4, test.getNextFreeSlot());
    test = new ProductionLine(5);
    assertEquals(6,test.getNextFreeSlot());
  }

}

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

    assertTrue(test.addCard(0,new CreationCard(1,1,1, Faction.BLUE,price, input1, output2)));
    assertTrue(test.addCard(1,new CreationCard(1,1,1, Faction.BLUE,price, input1, output2)));
    assertTrue(test.addCard(2,new CreationCard(1,1,1, Faction.BLUE,price, input1, output2)));
    assertFalse(test.addCard(3,new CreationCard(1,1,1, Faction.BLUE,price, input1, output2)));
    assertFalse(test.addCard(0,new CreationCard(1,1,1, Faction.BLUE,price, input1, output2)));
    assertTrue(test.addCard(3,new CreationCard(1,2,1, Faction.BLUE,price, input1, output2)));
    assertFalse(test.addCard(0,new CreationCard(1,2,1, Faction.BLUE,price, input1, output2)));
    assertTrue(test.addCard(1,new CreationCard(1,2,1, Faction.BLUE,price, input1, output2)));
    assertFalse(test.addCard(1,new CreationCard(1,1,1, Faction.BLUE,price, input1, output2)));
    assertTrue(test.addCard(1,new CreationCard(1,3,1, Faction.BLUE,price, input1, output2)));
  }

  @Test
  public void showCardTest(){
    ProductionLine test = new ProductionLine();
    HashMap<Resource,Integer> price = new HashMap<>(),input1 = new HashMap<>(),output2 = new HashMap<>();
    test.addCard(0,new CreationCard(101,1,1, Faction.BLUE,price, input1, output2));
    test.addCard(1,new CreationCard(102,1,1, Faction.BLUE,price, input1, output2));
    test.addCard(2,new CreationCard(103,1,1, Faction.BLUE,price, input1, output2));
    ArrayList<Integer> arrayList = new ArrayList<>();
    arrayList.add(0);
    arrayList.add(101);
    arrayList.add(102);
    arrayList.add(103);

    assertEquals(arrayList,test.showCards());
    test.addCard(0,new CreationCard(104,2,1, Faction.BLUE,price, input1, output2));
    assertNotEquals(arrayList,test.showCards());
    arrayList.set(1,104);
    assertEquals(arrayList,test.showCards());
  }

}

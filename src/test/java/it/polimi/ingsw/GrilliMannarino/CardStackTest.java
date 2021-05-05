package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class CardStackTest {

  @Test
  public void isEmptyTest(){
    CardStack test = new CardStack();

    assertTrue(test.emptyStack());
    test.pushCard(new CreationCard(0,0,0, Faction.GREEN,null,null,null));
    assertFalse(test.emptyStack());
  }

  @Test
  public void pushCardTest(){
    CardStack test = new CardStack();
    CreationCard card = new CreationCard(0,0,0, Faction.GREEN,null,null,null);

    assertTrue(test.emptyStack());
    test.pushCard(card);
    test.pushCard(card);
    assertNotNull(test.popCard());
    assertFalse(test.emptyStack());
    assertNotNull(test.popCard());
    assertNull(test.popCard());

  }

  @Test
  public void popCardTest(){
    CardStack test = new CardStack();
    CreationCard card = new CreationCard(0,0,0, Faction.GREEN,null,null,null);
    assertTrue(test.emptyStack());
    test.pushCard(card);
    assertEquals(card, test.popCard());
  }

  @Test
  public void getCardTest(){
    CardStack test = new CardStack();
    CreationCard card = new CreationCard(0,0,0, Faction.GREEN,null,null,null);

    assertTrue(test.emptyStack());
    assertNull(test.getCard());
    test.pushCard(card);
    assertEquals(card.getCardCode(), test.getCard().getCardCode());
    assertEquals(card.getCardLevel(), test.getCard().getCardLevel());
    assertEquals(card.getFaction(), test.getCard().getFaction());
    assertEquals(card.getValue(), test.getCard().getValue());
    assertEquals(card.getPrice(), test.getCard().getPrice());
  }

  @Test
  public void canAddTest(){
    CardStack test = new CardStack();
    CreationCard card = new CreationCard(0,0,0, Faction.GREEN,null,null,null);

    assertTrue(test.emptyStack());
    assertFalse(test.canAdd(card));

    card = new CreationCard(0,1,0, Faction.GREEN,null,null,null);

    assertTrue(test.canAdd(card));

    test.pushCard(card);

    assertFalse(test.canAdd(card));

    card = new CreationCard(0,2,0, Faction.GREEN,null,null,null);

    assertTrue(test.canAdd(card));
  }

  @Test
  public void addCardTest(){
    CardStack test = new CardStack();
    CreationCard card = new CreationCard(0,0,0, Faction.GREEN,null,null,null);

    assertTrue(test.emptyStack());
    assertFalse(test.addCard(card));

    card = new CreationCard(0,1,0, Faction.GREEN,null,null,null);

    assertTrue(test.canAdd(card));

    assertTrue(test.addCard(card));

    assertFalse(test.addCard(card));

    card = new CreationCard(0,2,0, Faction.GREEN,null,null,null);

    assertTrue(test.addCard(card));
  }

  @Test
  public void getValueTest() {
    int value1 = 1;
    int value2 = 4;
    int cardLevel = 1;
    Faction faction = Faction.GREEN;
    HashMap<Resource, Integer> placeholder = new HashMap<>();

    CreationCard test1 = new CreationCard(0,cardLevel,value1,faction,placeholder, placeholder, placeholder);
    CreationCard test2 = new CreationCard(0,cardLevel,value2,faction,placeholder, placeholder, placeholder);

    CardStack test = new CardStack();
    assertEquals(0,test.getValue());
    test.pushCard(test1);
    assertEquals(1,test.getValue());
    test.pushCard(test2);
    assertEquals(4,test.getValue());
  }

  @Test
  public void getFactionTest() {
    int value = 1;
    int cardLevel = 1;
    Faction faction1 = Faction.GREEN;
    Faction faction2 = Faction.BLUE;
    Faction faction3 = Faction.PURPLE;
    Faction faction4 = Faction.YELLOW;
    HashMap<Resource, Integer> placeholder = new HashMap<>();

    CreationCard test1 = new CreationCard(0,cardLevel,value,faction1,placeholder, placeholder, placeholder);
    CreationCard test2 = new CreationCard(0,cardLevel,value,faction2,placeholder, placeholder, placeholder);
    CreationCard test3 = new CreationCard(0,cardLevel,value,faction3,placeholder, placeholder, placeholder);
    CreationCard test4 = new CreationCard(0,cardLevel,value,faction4,placeholder, placeholder, placeholder);

    CardStack test = new CardStack();
    assertEquals(Faction.NONE,test.getFaction());
    test.pushCard(test1);
    assertEquals(Faction.GREEN,test.getFaction());
    test.pushCard(test2);
    assertEquals(Faction.BLUE,test.getFaction());
    test.pushCard(test3);
    assertEquals(Faction.PURPLE,test.getFaction());
    test.pushCard(test4);
    assertEquals(Faction.YELLOW,test.getFaction());

  }

  @Test
  public void getCardLevelTest() {
    int value = 1;
    int cardLevel1 = 1;
    int cardLevel2 = 2;
    int cardLevel3 = 1;
    int cardLevel4 = 3;
    Faction faction = Faction.GREEN;
    HashMap<Resource, Integer> placeholder = new HashMap<>();

    CreationCard test1 = new CreationCard(0,cardLevel1,value,faction,placeholder, placeholder, placeholder);
    CreationCard test2 = new CreationCard(0,cardLevel2,value,faction,placeholder, placeholder, placeholder);
    CreationCard test3 = new CreationCard(0,cardLevel3,value,faction,placeholder, placeholder, placeholder);
    CreationCard test4 = new CreationCard(0,cardLevel4,value,faction,placeholder, placeholder, placeholder);

    CardStack test = new CardStack();
    assertEquals(0,test.getCardLevel());
    test.pushCard(test1);
    assertEquals(1,test.getCardLevel());
    test.pushCard(test2);
    assertEquals(2,test.getCardLevel());
    test.pushCard(test3);
    assertEquals(1,test.getCardLevel());
    test.pushCard(test4);
    assertEquals(3,test.getCardLevel());

  }

  @Test
  public void getCardCodeTest() {
    int value = 1;
    int cardLevel = 1;
    int cardCode1 = 129;
    int cardCode2 = 113;
    int cardCode3 = 429;
    int cardCode4 = 292;
    Faction faction = Faction.GREEN;
    HashMap<Resource, Integer> placeholder = new HashMap<>();

    CreationCard test1 = new CreationCard(cardCode1,cardLevel,value,faction,placeholder, placeholder, placeholder);
    CreationCard test2 = new CreationCard(cardCode2,cardLevel,value,faction,placeholder, placeholder, placeholder);
    CreationCard test3 = new CreationCard(cardCode3,cardLevel,value,faction,placeholder, placeholder, placeholder);
    CreationCard test4 = new CreationCard(cardCode4,cardLevel,value,faction,placeholder, placeholder, placeholder);

    CardStack test = new CardStack();
    assertEquals(0,test.getCardCode());
    test.pushCard(test1);
    assertEquals(cardCode1,test.getCardCode());
    test.pushCard(test2);
    assertEquals(cardCode2,test.getCardCode());
    test.pushCard(test3);
    assertEquals(cardCode3,test.getCardCode());
    test.pushCard(test4);
    assertEquals(cardCode4,test.getCardCode());
  }

  @Test
  public void getPriceTest() {
    int value = 1;
    int cardLevel = 1;
    int cardCode = 129;
    Faction faction = Faction.GREEN;
    HashMap<Resource, Integer> price1 = new HashMap<>();
    price1.put(Resource.COIN, 1);
    price1.put(Resource.SHIELD, 1);
    HashMap<Resource, Integer> price2 = new HashMap<>();
    price2.put(Resource.SHIELD, 3);
    price2.put(Resource.SERVANT, 5);
    HashMap<Resource, Integer> price3 = new HashMap<>();
    price3.put(Resource.STONE, 5);
    price3.put(Resource.COIN, 2);
    HashMap<Resource, Integer> price4 = new HashMap<>();
    price4.put(Resource.COIN, 1);
    price4.put(Resource.SHIELD, 2);
    price4.put(Resource.STONE, 3);
    price4.put(Resource.SERVANT, 1);

    HashMap<Resource, Integer> priceOut1 = new HashMap<>();
    priceOut1.put(Resource.COIN, 1);
    priceOut1.put(Resource.SHIELD, 1);
    HashMap<Resource, Integer> priceOut2 = new HashMap<>();
    priceOut2.put(Resource.SHIELD, 3);
    priceOut2.put(Resource.SERVANT, 5);
    HashMap<Resource, Integer> priceOut3 = new HashMap<>();
    priceOut3.put(Resource.STONE, 5);
    priceOut3.put(Resource.COIN, 2);
    HashMap<Resource, Integer> priceOut4 = new HashMap<>();
    priceOut4.put(Resource.COIN, 1);
    priceOut4.put(Resource.SHIELD, 2);
    priceOut4.put(Resource.STONE, 3);
    priceOut4.put(Resource.SERVANT, 1);

    HashMap<Resource, Integer> input = new HashMap<>();
    input.put(Resource.SHIELD,1);
    HashMap<Resource, Integer> output = new HashMap<>();
    output.put(Resource.COIN,1);

    CreationCard test1 = new CreationCard(cardCode,cardLevel,value,faction,price1,input,output);
    CreationCard test2 = new CreationCard(cardCode,cardLevel,value,faction,price2,input,output);
    CreationCard test3 = new CreationCard(cardCode,cardLevel,value,faction,price3,input,output);
    CreationCard test4 = new CreationCard(cardCode,cardLevel,value,faction,price4,input,output);

    CardStack test = new CardStack();
    assertEquals(new HashMap<>(),test.getPrice());
    test.pushCard(test1);
    assertEquals(priceOut1,test.getPrice());
    test.pushCard(test2);
    assertEquals(priceOut2,test.getPrice());
    test.pushCard(test3);
    assertEquals(priceOut3,test.getPrice());
    test.pushCard(test4);
    assertEquals(priceOut4,test.getPrice());

  }

  @Test
  public void getInputTest() {
    int value = 1;
    int cardLevel = 1;
    int cardCode = 129;
    Faction faction = Faction.GREEN;
    HashMap<Resource, Integer> price = new HashMap<>();
    price.put(Resource.SHIELD,1);
    HashMap<Resource, Integer> input1 = new HashMap<>();
    input1.put(Resource.COIN, 1);
    input1.put(Resource.SHIELD, 1);
    HashMap<Resource, Integer> input2 = new HashMap<>();
    input2.put(Resource.SHIELD, 3);
    input2.put(Resource.SERVANT, 5);
    HashMap<Resource, Integer> input3 = new HashMap<>();
    input3.put(Resource.STONE, 5);
    input3.put(Resource.COIN, 2);
    HashMap<Resource, Integer> input4 = new HashMap<>();
    input4.put(Resource.COIN, 1);
    input4.put(Resource.SHIELD, 2);
    input4.put(Resource.STONE, 3);
    input4.put(Resource.SERVANT, 1);

    HashMap<Resource, Integer> inputOut1 = new HashMap<>();
    inputOut1.put(Resource.COIN, 1);
    inputOut1.put(Resource.SHIELD, 1);
    HashMap<Resource, Integer> inputOut2 = new HashMap<>();
    inputOut2.put(Resource.SHIELD, 3);
    inputOut2.put(Resource.SERVANT, 5);
    HashMap<Resource, Integer> inputOut3 = new HashMap<>();
    inputOut3.put(Resource.STONE, 5);
    inputOut3.put(Resource.COIN, 2);
    HashMap<Resource, Integer> inputOut4 = new HashMap<>();
    inputOut4.put(Resource.COIN, 1);
    inputOut4.put(Resource.SHIELD, 2);
    inputOut4.put(Resource.STONE, 3);
    inputOut4.put(Resource.SERVANT, 1);

    HashMap<Resource, Integer> output = new HashMap<>();
    output.put(Resource.COIN,1);

    CreationCard test1 = new CreationCard(cardCode,cardLevel,value,faction,price,input1,output);
    CreationCard test2 = new CreationCard(cardCode,cardLevel,value,faction,price,input2,output);
    CreationCard test3 = new CreationCard(cardCode,cardLevel,value,faction,price,input3,output);
    CreationCard test4 = new CreationCard(cardCode,cardLevel,value,faction,price,input4,output);

    CardStack test = new CardStack();
    assertEquals(new HashMap<>(),test.getInput());
    test.pushCard(test1);
    assertEquals(inputOut1,test.getInput());
    test.pushCard(test2);
    assertEquals(inputOut2,test.getInput());
    test.pushCard(test3);
    assertEquals(inputOut3,test.getInput());
    test.pushCard(test4);
    assertEquals(inputOut4,test.getInput());

  }

  @Test
  public void getOutputTest() {
    int value = 1;
    int cardLevel = 1;
    int cardCode = 129;
    Faction faction = Faction.GREEN;
    HashMap<Resource, Integer> price = new HashMap<>();
    price.put(Resource.SHIELD,1);
    HashMap<Resource, Integer> input = new HashMap<>();
    input.put(Resource.COIN,1);

    HashMap<Resource, Integer> output1 = new HashMap<>();
    output1.put(Resource.COIN, 1);
    output1.put(Resource.SHIELD, 1);
    HashMap<Resource, Integer> output2 = new HashMap<>();
    output2.put(Resource.SHIELD, 3);
    output2.put(Resource.SERVANT, 5);
    HashMap<Resource, Integer> output3 = new HashMap<>();
    output3.put(Resource.STONE, 5);
    output3.put(Resource.COIN, 2);
    HashMap<Resource, Integer> output4 = new HashMap<>();
    output4.put(Resource.COIN, 1);
    output4.put(Resource.SHIELD, 2);
    output4.put(Resource.STONE, 3);
    output4.put(Resource.SERVANT, 1);

    HashMap<Resource, Integer> outputOut1 = new HashMap<>();
    outputOut1.put(Resource.COIN, 1);
    outputOut1.put(Resource.SHIELD, 1);
    HashMap<Resource, Integer> outputOut2 = new HashMap<>();
    outputOut2.put(Resource.SHIELD, 3);
    outputOut2.put(Resource.SERVANT, 5);
    HashMap<Resource, Integer> outputOut3 = new HashMap<>();
    outputOut3.put(Resource.STONE, 5);
    outputOut3.put(Resource.COIN, 2);
    HashMap<Resource, Integer> outputOut4 = new HashMap<>();
    outputOut4.put(Resource.COIN, 1);
    outputOut4.put(Resource.SHIELD, 2);
    outputOut4.put(Resource.STONE, 3);
    outputOut4.put(Resource.SERVANT, 1);

    CreationCard test1 = new CreationCard(cardCode,cardLevel,value,faction,price,input,output1);
    CreationCard test2 = new CreationCard(cardCode,cardLevel,value,faction,price,input,output2);
    CreationCard test3 = new CreationCard(cardCode,cardLevel,value,faction,price,input,output3);
    CreationCard test4 = new CreationCard(cardCode,cardLevel,value,faction,price,input,output4);

    CardStack test = new CardStack();
    assertEquals(new HashMap<>(),test.getOutput());
    test.pushCard(test1);
    assertEquals(outputOut1,test1.getOutput());
    test.pushCard(test2);
    assertEquals(outputOut2,test2.getOutput());
    test.pushCard(test3);
    assertEquals(outputOut3,test3.getOutput());
    test.pushCard(test4);
    assertEquals(outputOut4,test4.getOutput());

  }
}

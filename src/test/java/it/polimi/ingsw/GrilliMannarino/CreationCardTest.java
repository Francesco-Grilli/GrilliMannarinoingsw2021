package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

public class CreationCardTest {

    @Test
    public void canProduceTest() {
        int value = 1;
        int cardLevel = 1;
        Faction faction = Faction.GREEN;
        HashMap<Resource, Integer> price = new HashMap<>();
        price.put(Resource.COIN, 1);
        price.put(Resource.SHIELD, 1);
        HashMap<Resource, Integer> input = new HashMap<>();
        input.put(Resource.SHIELD,1);
        HashMap<Resource, Integer> output = new HashMap<>();
        output.put(Resource.COIN,1);

        CreationCard test = new CreationCard(cardLevel,value,faction,price,input,output);

        HashMap<Resource, Integer> input1 = new HashMap<>();
        HashMap<Resource, Integer> input2 = new HashMap<>();
        HashMap<Resource, Integer> input3 = new HashMap<>();
        input1.put(Resource.COIN,4);
        input1.put(Resource.SHIELD,3);
        input1.put(Resource.SERVANT,5);
        input2.put(Resource.SHIELD,1);
        input3.put(Resource.STONE,3);
        input3.put(Resource.COIN,4);
        input3.put(Resource.SERVANT,5);

        assertTrue(test.canProduce(input1));
        assertTrue(test.canProduce(input2));
        assertFalse(test.canProduce(input3));
    }

    @Test
    public void produceTest() {
        int value = 1;
        int cardLevel = 1;
        Faction faction = Faction.GREEN;
        HashMap<Resource, Integer> price = new HashMap<>();
        price.put(Resource.COIN, 1);
        price.put(Resource.SHIELD, 1);
        HashMap<Resource, Integer> input = new HashMap<>();
        input.put(Resource.SHIELD,1);
        HashMap<Resource, Integer> output = new HashMap<>();
        output.put(Resource.COIN,1);

        CreationCard test = new CreationCard(cardLevel,value,faction,price,input,output);

        HashMap<Resource, Integer> input1 = new HashMap<>();
        HashMap<Resource, Integer> input2 = new HashMap<>();
        HashMap<Resource, Integer> input3 = new HashMap<>();
        HashMap<Resource, Integer> output1;
        HashMap<Resource, Integer> output2;
        input1.put(Resource.COIN,4);
        input1.put(Resource.SHIELD,3);
        input1.put(Resource.SERVANT,5);
        input2.put(Resource.SHIELD,1);
        input3.put(Resource.STONE,3);
        input3.put(Resource.COIN,4);
        input3.put(Resource.SERVANT,5);
        output1 = new HashMap<>(input1);
        output2 = new HashMap<>(input2);

        output1.put(Resource.COIN, (output1.get(Resource.COIN) == null ? 0 : output1.get(Resource.COIN)) +output.get(Resource.COIN));
        output2.put(Resource.COIN, (output2.get(Resource.COIN) == null ? 0 : output2.get(Resource.COIN)) +output.get(Resource.COIN));

        assertEquals(output1, test.produce(new HashMap<>(input1), new HashMap<>(input1)));
        assertEquals(output2, test.produce(new HashMap<>(input2), new HashMap<>(input2)));
        assertEquals(new HashMap<>(), test.produce(new HashMap<>(input3), new HashMap<>()));
        assertEquals(new HashMap<>(input1), test.produce(new HashMap<>(input3), new HashMap<>(input1)));
    }

    @Test
    public void canBuyCardTest() {
        int value = 1;
        int cardLevel = 1;
        Faction faction = Faction.GREEN;
        HashMap<Resource, Integer> price = new HashMap<>();
        price.put(Resource.COIN, 1);
        price.put(Resource.SHIELD, 1);
        HashMap<Resource, Integer> input = new HashMap<>();
        input.put(Resource.SHIELD,1);
        HashMap<Resource, Integer> output = new HashMap<>();
        output.put(Resource.COIN,1);

        CreationCard test = new CreationCard(cardLevel,value,faction,price,input,output);

        HashMap<Resource, Integer> input1 = new HashMap<>();
        HashMap<Resource, Integer> input2 = new HashMap<>();
        HashMap<Resource, Integer> input3 = new HashMap<>();
        input1.put(Resource.COIN,4);
        input1.put(Resource.SHIELD,3);
        input1.put(Resource.SERVANT,5);
        input2.put(Resource.SHIELD,1);
        input3.put(Resource.STONE,3);
        input3.put(Resource.COIN,4);
        input3.put(Resource.SERVANT,5);

        assertTrue(test.canBuyCard(input1));
        assertFalse(test.canBuyCard(input2));
        assertFalse(test.canBuyCard(input3));
    }

    @Test
    public void buyCard() {
        int value = 1;
        int cardLevel = 1;
        Faction faction = Faction.GREEN;
        HashMap<Resource, Integer> price = new HashMap<>();
        price.put(Resource.COIN, 1);
        price.put(Resource.SHIELD, 1);
        HashMap<Resource, Integer> input = new HashMap<>();
        input.put(Resource.SHIELD,1);
        HashMap<Resource, Integer> output = new HashMap<>();
        output.put(Resource.COIN,1);

        CreationCard test = new CreationCard(cardLevel,value,faction,price,input,output);

        HashMap<Resource, Integer> input1 = new HashMap<>();
        HashMap<Resource, Integer> input2 = new HashMap<>();
        HashMap<Resource, Integer> input3 = new HashMap<>();
        HashMap<Resource, Integer> output1;
        input1.put(Resource.COIN,4);
        input1.put(Resource.SHIELD,3);
        input1.put(Resource.SERVANT,5);
        input2.put(Resource.SHIELD,1);
        input3.put(Resource.STONE,3);
        input3.put(Resource.COIN,4);
        input3.put(Resource.SERVANT,5);
        output1 = new HashMap<>(input1);

        output1.put(Resource.COIN, 3);
        output1.put(Resource.SHIELD, 2);


        assertEquals(output1, test.buyCard(input1));
        assertEquals(input2, test.buyCard(input2));
        assertEquals(input3, test.buyCard(input3));
    }

    @Test
    public void getValueTest() {
        int value1 = 1;
        int value2 = 4;
        int value3 = 3;
        int value4 = 8;
        int cardLevel = 1;
        Faction faction = Faction.GREEN;
        HashMap<Resource, Integer> placeholder = new HashMap<>();

        CreationCard test1 = new CreationCard(cardLevel,value1,faction,placeholder, placeholder, placeholder);
        CreationCard test2 = new CreationCard(cardLevel,value2,faction,placeholder, placeholder, placeholder);
        CreationCard test3 = new CreationCard(cardLevel,value3,faction,placeholder, placeholder, placeholder);
        CreationCard test4 = new CreationCard(cardLevel,value4,faction,placeholder, placeholder, placeholder);

        assertEquals(1,test1.getValue());
        assertEquals(4,test2.getValue());
        assertEquals(3,test3.getValue());
        assertEquals(8,test4.getValue());

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

        CreationCard test1 = new CreationCard(cardLevel,value,faction1,placeholder, placeholder, placeholder);
        CreationCard test2 = new CreationCard(cardLevel,value,faction2,placeholder, placeholder, placeholder);
        CreationCard test3 = new CreationCard(cardLevel,value,faction3,placeholder, placeholder, placeholder);
        CreationCard test4 = new CreationCard(cardLevel,value,faction4,placeholder, placeholder, placeholder);

        assertEquals(Faction.GREEN,test1.getFaction());
        assertEquals(Faction.BLUE,test2.getFaction());
        assertEquals(Faction.PURPLE,test3.getFaction());
        assertEquals(Faction.YELLOW,test4.getFaction());

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

        CreationCard test1 = new CreationCard(cardLevel1,value,faction,placeholder, placeholder, placeholder);
        CreationCard test2 = new CreationCard(cardLevel2,value,faction,placeholder, placeholder, placeholder);
        CreationCard test3 = new CreationCard(cardLevel3,value,faction,placeholder, placeholder, placeholder);
        CreationCard test4 = new CreationCard(cardLevel4,value,faction,placeholder, placeholder, placeholder);

        assertEquals(1,test1.getCardLevel());
        assertEquals(2,test2.getCardLevel());
        assertEquals(1,test3.getCardLevel());
        assertEquals(3,test4.getCardLevel());

    }
}

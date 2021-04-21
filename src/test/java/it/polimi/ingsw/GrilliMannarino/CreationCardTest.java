package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

public class CreationCardTest {

    @Test
    public void getValueTest() {
        int value1 = 1;
        int value2 = 4;
        int value3 = 3;
        int value4 = 8;
        int cardLevel = 1;
        Faction faction = Faction.GREEN;
        HashMap<Resource, Integer> placeholder = new HashMap<>();

        CreationCard test1 = new CreationCard(0,cardLevel,value1,faction,placeholder, placeholder, placeholder);
        CreationCard test2 = new CreationCard(0,cardLevel,value2,faction,placeholder, placeholder, placeholder);
        CreationCard test3 = new CreationCard(0,cardLevel,value3,faction,placeholder, placeholder, placeholder);
        CreationCard test4 = new CreationCard(0,cardLevel,value4,faction,placeholder, placeholder, placeholder);

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

        CreationCard test1 = new CreationCard(0,cardLevel,value,faction1,placeholder, placeholder, placeholder);
        CreationCard test2 = new CreationCard(0,cardLevel,value,faction2,placeholder, placeholder, placeholder);
        CreationCard test3 = new CreationCard(0,cardLevel,value,faction3,placeholder, placeholder, placeholder);
        CreationCard test4 = new CreationCard(0,cardLevel,value,faction4,placeholder, placeholder, placeholder);

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

        CreationCard test1 = new CreationCard(0,cardLevel1,value,faction,placeholder, placeholder, placeholder);
        CreationCard test2 = new CreationCard(0,cardLevel2,value,faction,placeholder, placeholder, placeholder);
        CreationCard test3 = new CreationCard(0,cardLevel3,value,faction,placeholder, placeholder, placeholder);
        CreationCard test4 = new CreationCard(0,cardLevel4,value,faction,placeholder, placeholder, placeholder);

        assertEquals(1,test1.getCardLevel());
        assertEquals(2,test2.getCardLevel());
        assertEquals(1,test3.getCardLevel());
        assertEquals(3,test4.getCardLevel());

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

        assertEquals(cardCode1,test1.getCardCode());
        assertEquals(cardCode2,test2.getCardCode());
        assertEquals(cardCode3,test3.getCardCode());
        assertEquals(cardCode4,test4.getCardCode());
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

        assertEquals(priceOut1,test1.getPrice());
        assertEquals(priceOut2,test2.getPrice());
        assertEquals(priceOut3,test3.getPrice());
        assertEquals(priceOut4,test4.getPrice());

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

        assertEquals(inputOut1,test1.getInput());
        assertEquals(inputOut2,test2.getInput());
        assertEquals(inputOut3,test3.getInput());
        assertEquals(inputOut4,test4.getInput());

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

        assertEquals(outputOut1,test1.getOutput());
        assertEquals(outputOut2,test2.getOutput());
        assertEquals(outputOut3,test3.getOutput());
        assertEquals(outputOut4,test4.getOutput());

    }

    @Test
    public void getCardTest() {
        HashMap<Resource, Integer> price = new HashMap<>();
        price.put(Resource.SHIELD,1);
        HashMap<Resource, Integer> input = new HashMap<>();
        input.put(Resource.COIN,1);
        HashMap<Resource, Integer> output = new HashMap<>();
        output.put(Resource.SHIELD, 1);
        output.put(Resource.COIN, 1);

        CreationCard test1 = new CreationCard(1, 1,1,Faction.BLUE, price,input,output);
        CreationCard testOut1 = test1.getCard();


        assertEquals(testOut1.getCardCode(), test1.getCardCode());
        assertEquals(testOut1.getCardLevel(), test1.getCardLevel());
        assertEquals(testOut1.getValue(), test1.getValue());
        assertEquals(testOut1.getFaction(), test1.getFaction());
        assertEquals(testOut1.getPrice(), test1.getPrice());
        assertEquals(testOut1.getInput(), test1.getInput());
        assertEquals(testOut1.getOutput(), test1.getOutput());
        assertFalse(testOut1.canAdd(test1));
        assertFalse(test1.addCard(test1));
    }
}

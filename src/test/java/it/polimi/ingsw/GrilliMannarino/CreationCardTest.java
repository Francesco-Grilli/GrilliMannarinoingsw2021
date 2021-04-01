package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CreationCardTest {
    @Test
    public void canProduceTest(){
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
}

package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import org.junit.jupiter.api.Test;


import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ChestTest {

    @Test
    public void addElement(){
        Chest chest = new Chest();
        HashMap<Resource, Integer> elem = new HashMap<>();
        elem.put(Resource.COIN, 10);
        elem.put(Resource.SERVANT, 15);
        chest.addResources(elem);
        HashMap<Resource, Integer> retValue;
        retValue=chest.getResources();
        assertEquals(15, (int) retValue.get(Resource.SERVANT));
        assertEquals(10, (int) retValue.get(Resource.COIN));
    }

    @Test
    public void doubleAddElement(){
        Chest chest = new Chest();
        HashMap<Resource, Integer> elem = new HashMap<>();
        elem.put(Resource.COIN, 10);
        elem.put(Resource.SERVANT, 15);
        chest.addResources(elem);
        HashMap<Resource, Integer> retValue;
        retValue=chest.getResources();
        assertEquals(15, (int) retValue.get(Resource.SERVANT));
        assertEquals(10, (int) retValue.get(Resource.COIN));
        elem = new HashMap<>();
        elem.put(Resource.SHIELD, 100);
        elem.put(Resource.STONE, 2000);
        chest.addResources(elem);
        retValue=chest.getResources();
        assertEquals(15, (int) retValue.get(Resource.SERVANT));
        assertEquals(10, (int) retValue.get(Resource.COIN));
        assertEquals(2000, (int) retValue.get(Resource.STONE));
        assertEquals(100, (int) retValue.get(Resource.SHIELD));
    }

    @Test
    public void updateElement(){
        Chest chest = new Chest();
        HashMap<Resource, Integer> elem = new HashMap<>();
        elem.put(Resource.COIN, 10);
        elem.put(Resource.SERVANT, 15);
        chest.addResources(elem);
        HashMap<Resource, Integer> retValue;
        retValue=chest.getResources();
        assertEquals(15, (int) retValue.get(Resource.SERVANT));
        assertEquals(10, (int) retValue.get(Resource.COIN));
        elem = new HashMap<>();
        elem.put(Resource.COIN, 20);
        elem.put(Resource.SERVANT, 50);
        chest.addResources(elem);
        retValue=chest.getResources();
        assertEquals(65, (int) retValue.get(Resource.SERVANT));
        assertEquals(30, (int) retValue.get(Resource.COIN));
    }

    @Test
    public void removeElementOK(){
        Chest chest = new Chest();
        HashMap<Resource, Integer> elem = new HashMap<>();
        elem.put(Resource.COIN, 10);
        elem.put(Resource.SERVANT, 15);
        chest.addResources(elem);
        HashMap<Resource, Integer> retValue;
        retValue=chest.getResources();
        assertEquals(15, (int) retValue.get(Resource.SERVANT));
        assertEquals(10, (int) retValue.get(Resource.COIN));
        elem.clear();
        elem.put(Resource.SERVANT, 5);
        elem.put(Resource.COIN, 7);
        assertTrue(chest.removeResources(elem));
        retValue=chest.getResources();
        assertEquals(10, (int) retValue.get(Resource.SERVANT));
        assertEquals(3, (int) retValue.get(Resource.COIN));
        elem.clear();
        elem.put(Resource.SERVANT, 1);
        elem.put(Resource.COIN, 1);
        assertTrue(chest.removeResources(elem));
        retValue=chest.getResources();
        assertEquals(9, (int) retValue.get(Resource.SERVANT));
        assertEquals(2, (int) retValue.get(Resource.COIN));
    }

    @Test
    public void removeElementNotEnough(){
        Chest chest = new Chest();
        HashMap<Resource, Integer> elem = new HashMap<>();
        elem.put(Resource.SERVANT, 10);
        elem.put(Resource.STONE, 15);
        elem.put(Resource.COIN, 25);
        chest.addResources(elem);
        elem.clear();
        elem.put(Resource.SERVANT, 20);
        assertFalse(chest.removeResources(elem));
        HashMap<Resource, Integer> retval = chest.getResources();
        retval.clear();
        elem.put(Resource.COIN, 10);
        assertFalse(chest.removeResources(elem));
        elem.put(Resource.SERVANT, 10);
        elem.put(Resource.COIN, 25);
        assertTrue(chest.removeResources(elem));
    }


}
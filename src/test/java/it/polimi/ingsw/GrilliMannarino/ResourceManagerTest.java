package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ResourceManagerTest {

    @Test
    public void getResources(){
        ResourceManager c = new ResourceManager();
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
    public void setResources(){
        ResourceManager manager = new ResourceManager();
        HashMap<Resource, Integer> res = new HashMap<>();
        //add resources to the first line STONE
        assertNull(manager.setResourcesFromMarket(Row.FIRST, Resource.STONE, 1));
        assertNotNull(manager.setResourcesFromMarket(Row.FIRST, Resource.SHIELD, 2));
        assertNotNull(manager.setResourcesFromMarket(Row.THIRD, Resource.STONE, 3));
        assertEquals(1, manager.getResources().get(Resource.STONE));
        //add resources to the third line COIN
        assertNotNull(manager.setResourcesFromMarket(Row.THIRD, Resource.COIN, 5));
        assertEquals(3, manager.getResources().get(Resource.COIN));
        assertNotNull(manager.setResourcesFromMarket(Row.THIRD, Resource.COIN, 1));
        assertNotNull(manager.setResourcesFromMarket(Row.SECOND, Resource.COIN, 1));
        assertEquals(3, manager.getResources().get(Resource.COIN));
        //add resources to the second line SHIELD
        assertNull(manager.setResourcesFromMarket(Row.SECOND, Resource.SHIELD, 1));
        assertEquals(1, manager.getResources().get(Resource.SHIELD));
        assertNotNull(manager.setResourcesFromMarket(Row.SECOND, Resource.COIN, 1));
        assertNotNull(manager.setResourcesFromMarket(Row.SECOND, Resource.SHIELD, 2));
        assertEquals(2, manager.getResources().get(Resource.SHIELD));
        res.put(Resource.COIN, 3);
        res.put(Resource.SHIELD, 6);
        res.put(Resource.SERVANT, 10);
        res.put(Resource.STONE, 3);
        res.put(Resource.COIN, res.get(Resource.COIN)+1);
        res.put(Resource.SERVANT, res.get(Resource.SERVANT)+3);
        manager.setResourcesFromProduction(res);
        manager.setResourcesFromProduction(res);
        assertEquals(7, manager.getResources().get(Resource.STONE));
        assertEquals(11, manager.getResources().get(Resource.COIN));
        assertEquals(14, manager.getResources().get(Resource.SHIELD));
        assertEquals(26, manager.getResources().get(Resource.SERVANT));
    }

    @Test
    public void removeResources(){
        //ADDING RESOURCES
        ResourceManager manager = new ResourceManager();
        HashMap<Resource, Integer> res = new HashMap<>();
        //add resources to the first line STONE
        assertNull(manager.setResourcesFromMarket(Row.FIRST, Resource.STONE, 1));
        assertNotNull(manager.setResourcesFromMarket(Row.FIRST, Resource.SHIELD, 2));
        assertNotNull(manager.setResourcesFromMarket(Row.THIRD, Resource.STONE, 3));
        assertEquals(1, manager.getResources().get(Resource.STONE));
        //add resources to the third line COIN
        assertNotNull(manager.setResourcesFromMarket(Row.THIRD, Resource.COIN, 5));
        assertEquals(3, manager.getResources().get(Resource.COIN));
        assertNotNull(manager.setResourcesFromMarket(Row.THIRD, Resource.COIN, 1));
        assertNotNull(manager.setResourcesFromMarket(Row.SECOND, Resource.COIN, 1));
        assertEquals(3, manager.getResources().get(Resource.COIN));
        //add resources to the second line SHIELD
        assertNull(manager.setResourcesFromMarket(Row.SECOND, Resource.SHIELD, 1));
        assertEquals(1, manager.getResources().get(Resource.SHIELD));
        assertNotNull(manager.setResourcesFromMarket(Row.SECOND, Resource.COIN, 1));
        assertNotNull(manager.setResourcesFromMarket(Row.SECOND, Resource.SHIELD, 2));
        assertEquals(2, manager.getResources().get(Resource.SHIELD));
        res.put(Resource.COIN, 3);
        res.put(Resource.SHIELD, 6);
        res.put(Resource.SERVANT, 10);
        res.put(Resource.STONE, 3);
        res.put(Resource.COIN, res.get(Resource.COIN)+1);
        res.put(Resource.SERVANT, res.get(Resource.SERVANT)+3);
        manager.setResourcesFromProduction(res);
        manager.setResourcesFromProduction(res);
        assertEquals(7, manager.getResources().get(Resource.STONE));
        assertEquals(11, manager.getResources().get(Resource.COIN));
        assertEquals(14, manager.getResources().get(Resource.SHIELD));
        assertEquals(26, manager.getResources().get(Resource.SERVANT));

        //REMOVING RESOURCES
        res.clear();
        res.put(Resource.COIN, 12);
        assertFalse(manager.canRemove(res));
        res.put(Resource.COIN, 11);
        assertTrue(manager.canRemove(res));
        assertNull(manager.remove(res).get(Resource.COIN));
        assertNull(manager.getResources().get(Resource.COIN));
        res.clear();
        res.put(Resource.STONE, 5);
        assertTrue(manager.canRemove(res));
        assertNull(manager.remove(res).get(Resource.STONE));
        assertEquals(2, manager.getResources().get(Resource.STONE));
        assertFalse(manager.canRemove(res));
        res.put(Resource.STONE, 2);
        assertTrue(manager.canRemove(res));
        assertNull(manager.remove(res).get(Resource.STONE));
        assertNull(manager.getResources().get(Resource.STONE));
        res.clear();

        res.put(Resource.SHIELD, 5);
        assertTrue(manager.canRemove(res));
        assertNull(manager.remove(res).get(Resource.SHIELD));
        assertEquals(9, manager.getResources().get(Resource.SHIELD));
        res.put(Resource.SHIELD, 10);
        assertFalse(manager.canRemove(res));
        res.put(Resource.SHIELD, 8);
        assertTrue(manager.canRemove(res));
        assertNull(manager.remove(res).get(Resource.SHIELD));
        assertEquals(1, manager.getResources().get(Resource.SHIELD));
        res.put(Resource.SHIELD, 1);
        assertTrue(manager.canRemove(res));
        assertNull(manager.remove(res).get(Resource.SHIELD));
        assertNull(manager.getResources().get(Resource.SHIELD));

        res.clear();
        res.put(Resource.SERVANT, 27);
        assertFalse(manager.canRemove(res));
        res.put(Resource.SERVANT, 0);
        assertTrue(manager.canRemove(res));
        assertNull(manager.remove(res).get(Resource.SERVANT));
        res.put(Resource.SERVANT, 26);
        assertTrue(manager.canRemove(res));
        assertNull(manager.remove(res).get(Resource.SERVANT));
        assertTrue(manager.getResources().isEmpty());
    }

}
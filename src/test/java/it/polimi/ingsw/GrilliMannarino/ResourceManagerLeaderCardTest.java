package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ResourceManagerLeaderCardTest {

    @Test
    public void setResourcesFromMarketTest(){
        ResourceManagerBoardInterface card = new ResourceManagerLeaderCard(new HashMap<>(), new HashMap<>(), Resource.COIN, 3);
        card.execute(new ResourceManager());

        assertTrue(card.canSetResourcesFromMarket(Row.FIRST, Resource.COIN, 1));
        card.setResourcesFromMarket(Row.FIRST, Resource.COIN, 1);
        assertFalse(card.canSetResourcesFromMarket(Row.FIRST, Resource.COIN, 2));
        assertFalse(card.canSetResourcesFromMarket(Row.FOURTH, Resource.STONE, 1));
        assertFalse(card.canSetResourcesFromMarket(Row.FOURTH, Resource.COIN, 3));
        assertTrue(card.canSetResourcesFromMarket(Row.FOURTH, Resource.COIN, 1));
        assertNull(card.setResourcesFromMarket(Row.FOURTH, Resource.COIN, 1));
        assertTrue(card.canSetResourcesFromMarket(Row.FOURTH, Resource.COIN, 1));
        assertNull(card.setResourcesFromMarket(Row.FOURTH, Resource.COIN, 1));
        assertFalse(card.canSetResourcesFromMarket(Row.FOURTH, Resource.COIN, 1));
        assertNotNull(card.setResourcesFromMarket(Row.FOURTH, Resource.COIN, 1));
    }

    @Test
    public void setResourcesFromProductionTest(){
        ResourceManagerBoardInterface card = new ResourceManagerLeaderCard(new HashMap<>(), new HashMap<>(), Resource.COIN, 3);
        card.execute(new ResourceManager());

        HashMap<Resource, Integer> res = new HashMap<>();
        assertTrue(card.getResources().isEmpty());
        res.put(Resource.COIN, 3);
        res.put(Resource.SERVANT, 1);
        res.put(Resource.STONE, 100);
        res.put(Resource.SHIELD, 55);
        card.setResourcesFromProduction(res);
        res = card.getResources();
        assertEquals(3, res.get(Resource.COIN));
        assertEquals(1, res.get(Resource.SERVANT));
        assertEquals(100, res.get(Resource.STONE));
        assertEquals(55, res.get(Resource.SHIELD));
    }

    @Test
    public void getResourcesTest(){
        ResourceManagerBoardInterface card = new ResourceManagerLeaderCard(new HashMap<>(), new HashMap<>(), Resource.COIN, 3);
        card.execute(new ResourceManager());
        HashMap<Resource, Integer> ret = new HashMap<>();

        card.setResourcesFromMarket(Row.FIRST, Resource.SHIELD, 1);
        card.setResourcesFromMarket(Row.SECOND, Resource.STONE, 2);
        card.setResourcesFromMarket(Row.THIRD, Resource.COIN, 2);
        ret = card.getResources();
        assertEquals(1, ret.get(Resource.SHIELD));
        assertEquals(2, ret.get(Resource.STONE));
        assertEquals(2, ret.get(Resource.COIN));
        assertFalse(card.canSetResourcesFromMarket(Row.FOURTH, Resource.COIN, 5));
        assertTrue(card.canSetResourcesFromMarket(Row.FOURTH, Resource.COIN, 2));
        card.setResourcesFromMarket(Row.FOURTH, Resource.COIN, 2);
        ret = card.getResources();
        assertEquals(4 , ret.get(Resource.COIN));
        assertEquals(1, ret.get(Resource.SHIELD));
        assertEquals(2, ret.get(Resource.STONE));
        ret.clear();
        ret.put(Resource.COIN, 5);
        ret.put(Resource.SHIELD, 20);
        ret.put(Resource.STONE, 15);
        card.setResourcesFromProduction(ret);
        ret = card.getResources();
        assertEquals(9, ret.get(Resource.COIN));
        assertEquals(21, ret.get(Resource.SHIELD));
        assertEquals(17, ret.get(Resource.STONE));
    }

    @Test
    public void removeResourcesTest(){
        ResourceManagerBoardInterface card = new ResourceManagerLeaderCard(new HashMap<>(), new HashMap<>(), Resource.COIN, 3);
        card.execute(new ResourceManager());
        HashMap<Resource, Integer> ret = new HashMap<>();

        card.setResourcesFromMarket(Row.FIRST, Resource.SHIELD, 1);
        card.setResourcesFromMarket(Row.SECOND, Resource.COIN, 1);
        ret.put(Resource.SHIELD, 1);
        ret.put(Resource.COIN, 3);
        assertFalse(card.canRemove(ret));
        card.setResourcesFromMarket(Row.SECOND, Resource.COIN, 1);
        assertFalse(card.canRemove(ret));
        assertFalse(card.remove(ret).isEmpty());
        card.setResourcesFromMarket(Row.FOURTH, Resource.COIN, 1);
        assertTrue(card.canRemove(ret));
        assertTrue(card.remove(ret).isEmpty());

        card.setResourcesFromMarket(Row.THIRD, Resource.SERVANT, 2);
        card.setResourcesFromMarket(Row.FIRST, Resource.SHIELD, 1);
        card.setResourcesFromMarket(Row.SECOND, Resource.COIN, 1);
        card.setResourcesFromMarket(Row.FOURTH, Resource.COIN, 1);
        ret.clear();
        ret.put(Resource.COIN, 3);
        ret.put(Resource.SHIELD, 2);
        ret.put(Resource.STONE, 1);
        ret.put(Resource.SERVANT, 1);
        card.setResourcesFromProduction(ret);
        ret.clear();
        ret.put(Resource.SHIELD, 3);
        ret.put(Resource.COIN, 3);
        ret.put(Resource.SERVANT, 1);
        assertTrue(card.canRemove(ret));
        card.remove(ret);
    }

    @Test
    public void getPointsTest(){
        ResourceManagerBoardInterface card = new ResourceManagerLeaderCard(new HashMap<>(), new HashMap<>(), Resource.COIN, 3);
        card.execute(new ResourceManager());
        HashMap<Resource, Integer> ret = new HashMap<>();

        card.setResourcesFromMarket(Row.THIRD, Resource.SERVANT, 2);
        card.setResourcesFromMarket(Row.FIRST, Resource.SHIELD, 1);
        card.setResourcesFromMarket(Row.SECOND, Resource.COIN, 1);
        card.setResourcesFromMarket(Row.FOURTH, Resource.COIN, 1);
        assertEquals(4, card.getPoints());
        ret.put(Resource.SHIELD, 3);
        ret.put(Resource.COIN, 5);
        ret.put(Resource.SERVANT, 6);
        card.setResourcesFromProduction(ret);
        assertEquals(6 , card.getPoints());
        card.setResourcesFromMarket(Row.FOURTH, Resource.COIN, 1);
        assertEquals(7, card.getPoints());
    }

    @Test
    public void doubleCard(){
        ResourceManagerBoardInterface card = new ResourceManagerLeaderCard(new HashMap<>(), new HashMap<>(), Resource.COIN, 3);
        card.execute(new ResourceManager());
        HashMap<Resource, Integer> ret = new HashMap<>();

        card.setResourcesFromMarket(Row.FIRST, Resource.SHIELD, 1);
        card.setResourcesFromMarket(Row.SECOND, Resource.COIN, 2);
        card.setResourcesFromMarket(Row.FOURTH, Resource.COIN, 1);

        ResourceManagerBoardInterface card2 = new ResourceManagerLeaderCard(new HashMap<>(), new HashMap<>(), Resource.SHIELD, 2);
        card2.execute(card);
        ret = card2.getResources();
        ret.clear();
        ret.put(Resource.COIN, 1);
        ret.put(Resource.SHIELD, 1);
        assertTrue(card2.canRemove(ret));
        card2.remove(ret);
    }

}
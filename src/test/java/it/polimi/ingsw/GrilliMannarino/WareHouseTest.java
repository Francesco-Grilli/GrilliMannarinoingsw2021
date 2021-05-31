package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


class WareHouseTest {

    @Test
    public void canAddResources(){
        WareHouse house = new WareHouse();
        assertTrue(house.canAddResources(Row.THIRD, Resource.SERVANT, 2));
        assertFalse(house.canAddResources(Row.FIRST, Resource.COIN, 5));
        assertTrue(house.canAddResources(Row.THIRD, Resource.SERVANT, 3));
        assertTrue(house.canAddResources(Row.THIRD, Resource.SERVANT, 1));
        assertNull(house.addResources(Row.THIRD, Resource.SERVANT, 3));
        assertFalse(house.canAddResources(Row.THIRD, Resource.SERVANT, 1));
        assertFalse(house.canAddResources(Row.SECOND, Resource.SERVANT, 1));
        assertTrue(house.canAddResources(Row.SECOND, Resource.COIN, 2));
        assertNull(house.addResources(Row.SECOND, Resource.COIN, 2));
        assertFalse(house.canAddResources(Row.SECOND, Resource.COIN, 1));
        assertFalse(house.canAddResources(Row.SECOND, Resource.STONE, 1));
        assertNotNull(house.addResources(Row.SECOND, Resource.STONE, 1));
        assertFalse(house.canAddResources(Row.FIRST, Resource.SERVANT, 1));
        assertFalse(house.canAddResources(Row.FIRST, Resource.COIN, 1));
        assertFalse(house.canAddResources(Row.FIRST, Resource.STONE, 2));
        assertTrue(house.canAddResources(Row.FIRST, Resource.STONE, 1));
        assertNull(house.addResources(Row.FIRST, Resource.STONE, 1));

        HashMap<Resource, Integer> ret;
        ret = house.addResources(Row.THIRD, Resource.SERVANT, 5);
        assertEquals(ret.get(Resource.SERVANT), 5);
        ret = house.addResources(Row.FIRST, Resource.SERVANT, 1);
        assertEquals(ret.get(Resource.SERVANT), 1);
    }

    @Test
    public void addRes2(){
        WareHouse house = new WareHouse();
        HashMap<Resource, Integer> ret;
        assertFalse(house.canAddResources(Row.FIRST, Resource.SHIELD, 5));
        ret = house.addResources(Row.FIRST, Resource.SHIELD, 5);
        assertEquals(ret.get(Resource.SHIELD), 5);
        assertFalse(house.canAddResources(Row.SECOND, Resource.SHIELD, 3));
        ret = house.addResources(Row.SECOND, Resource.SHIELD, 3);
        assertEquals(ret.get(Resource.SHIELD), 3);
        assertTrue(house.canAddResources(Row.THIRD, Resource.COIN, 1));
        ret = house.addResources(Row.THIRD, Resource.COIN, 1);
        assertNull(ret);
        assertTrue(house.canAddResources(Row.THIRD, Resource.COIN, 2));
        ret = house.addResources(Row.THIRD, Resource.COIN, 2);
        assertNull(ret);
    }

    @Test
    public void removeRes(){
        WareHouse house = new WareHouse();
        HashMap<Resource, Integer> ret = new HashMap<>();
        house.addResources(Row.THIRD, Resource.COIN, 2);
        house.addResources(Row.FIRST, Resource.SHIELD, 1);
        house.addResources(Row.SECOND, Resource.STONE, 1);
        assertFalse(house.canRemoveResources(Row.THIRD, Resource.COIN, 3));
        assertFalse(house.canRemoveResources(Row.THIRD, Resource.SERVANT, 1));
        assertTrue(house.canRemoveResources(Row.THIRD, Resource.COIN, 1));
        house.removeResources(Row.THIRD, Resource.COIN, 1);
        assertTrue(house.canRemoveResources(Row.THIRD, Resource.COIN, 1));
        house.removeResources(Row.THIRD, Resource.COIN, 1);
        //should be nothing in third row now
        assertFalse(house.canRemoveResources(Row.SECOND, Resource.STONE, 2));
        house.removeResources(Row.SECOND, Resource.STONE, 2);
        house.addResources(Row.THIRD, Resource.COIN, 3);
        assertTrue(house.canRemoveResources(Row.THIRD, Resource.COIN, 1));
        house.removeResources(Row.THIRD, Resource.COIN, 1);
        try {
            house.removeResources(Row.THIRD, Resource.COIN, -1);
        }
        catch (IllegalArgumentException ignored){

        }

    }


}
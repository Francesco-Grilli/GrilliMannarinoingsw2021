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
    public void addRes1(){
        WareHouse house = new WareHouse();
        assertFalse(house.addResources(Row.FIRST, Resource.COIN, 2));
        assertTrue(house.addResources(Row.FIRST, Resource.COIN, 1));
        assertFalse(house.addResources(Row.FIRST, Resource.COIN, 1));

        assertTrue(house.addResources(Row.THIRD, Resource.SERVANT, 1));
        assertTrue(house.addResources(Row.THIRD, Resource.SERVANT, 2));
        assertFalse(house.addResources(Row.THIRD, Resource.SERVANT, 1));

        assertFalse(house.addResources(Row.SECOND, Resource.SERVANT, 2));
        HashMap<Row, HashMap<Resource, Integer>> ret = new HashMap<>();
        ret = house.getResources();
        assertEquals(1, ret.get(Row.FIRST).get(Resource.COIN));
        assertEquals(3, ret.get(Row.THIRD).get(Resource.SERVANT));

    }

}
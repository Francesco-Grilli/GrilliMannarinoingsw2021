package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CollectionTest {

    @Test
    public void getResources(){
        Collection c = new Collection();
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

}
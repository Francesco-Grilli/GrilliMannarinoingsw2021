package it.polimi.ingsw.GrilliMannarino.GameData;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarbleTest {

    @Test
    public void resourceTest(){
        assertEquals(Resource.COIN, Marble.YELLOW.getResource());
        assertEquals(Resource.UNKNOWN, Marble.WHITE.getResource());
    }

}
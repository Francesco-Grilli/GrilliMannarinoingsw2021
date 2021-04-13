package it.polimi.ingsw.GrilliMannarino.GameData;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RowTest {

    @Test
    public void getNext(){
        assertEquals(Row.SECOND, Row.getNextValue(Row.FIRST));
    }
}
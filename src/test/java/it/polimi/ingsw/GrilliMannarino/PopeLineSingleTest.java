package it.polimi.ingsw.GrilliMannarino;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PopeLineSingleTest {

    @Test
    void test1(){
        PopeLine pope = new PopeLineSingle();
        for(int i=0; i<4; i++){
            assertFalse(pope.addFaith());
            assertEquals(i+1, pope.getFaith());
        }
        assertTrue(pope.addFaith());
        assertEquals(5, pope.getFaith());
        assertTrue(pope.checkPopeFaith());
        PopeLine.updateChecks();
        assertTrue(pope.getFaithSteps()[0]);
        assertTrue(PopeLine.getFaithChecks()[0]);
    }

}
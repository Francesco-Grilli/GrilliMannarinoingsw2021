package it.polimi.ingsw.GrilliMannarino;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PopeLineSingleTest {

    @Test
    void popeWin(){
        PopeLineSingle pope = new PopeLineSingle();
        assertFalse(pope.addFaith());
        assertEquals(1, pope.getFaith());
        assertFalse(pope.doubleAddLorenzoFaith());
        assertFalse(pope.doubleAddLorenzoFaith());
        assertEquals(4, pope.getLorenzoFaith());
        assertTrue(pope.addLorenzoFaith());
        assertTrue(pope.addLorenzoFaith());
        assertEquals(6, pope.getLorenzoFaith());
        assertFalse(pope.checkPopeFaith());
        PopeLineSingle.updateChecks();
        assertFalse(pope.getFaithSteps()[0]);
        for(int i =0; i<10; i++)
            assertFalse(pope.addFaith());
        assertTrue(pope.addFaith());
        assertEquals(12, pope.getFaith());
        assertTrue(pope.checkPopeFaith());
        PopeLineSingle.updateChecks();
        for(int i=0; i<6; i++)
            assertFalse(pope.addFaith());
        for(int i=0; i<8; i++)
            assertTrue(pope.addFaith());
        assertEquals(24, pope.getFaith());
        assertTrue(pope.checkPopeFaith());
        PopeLineSingle.updateChecks();
        assertEquals(27, pope.getPoints());
    }

    @Test
    void lorenzoWin(){
        PopeLineSingle pope = new PopeLineSingle();
        assertFalse(pope.addFaith());
        assertEquals(1, pope.getFaith());
        assertFalse(pope.doubleAddLorenzoFaith());
        assertFalse(pope.doubleAddLorenzoFaith());
        assertTrue(pope.addLorenzoFaith());
        assertEquals(5, pope.getLorenzoFaith());
        assertFalse(pope.checkPopeFaith());
        assertFalse(pope.getFaithSteps()[0]);
        PopeLineSingle.updateChecks();
        assertTrue(PopeLineSingle.getFaithChecks()[0]);
        for(int i=0; i<3; i++)
            assertFalse(pope.doubleAddLorenzoFaith());
        assertTrue(pope.addLorenzoFaith());
        assertEquals(12, pope.getLorenzoFaith());
        assertFalse(pope.checkPopeFaith());
        PopeLineSingle.updateChecks();
        for (int i = 0; i < 3; i++)
            assertFalse(pope.doubleAddLorenzoFaith());
        assertEquals(18, pope.getLorenzoFaith());
        for(int i=0; i<5; i++)
            assertTrue(pope.doubleAddLorenzoFaith());
        assertEquals(24, pope.getLorenzoFaith());
        assertFalse(pope.checkPopeFaith());
        PopeLineSingle.updateChecks();
        boolean[] prep = pope.getFaithSteps();
        for(boolean p: prep)
            assertFalse(p);
        assertEquals(0, pope.getPoints());
    }



}
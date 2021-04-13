package it.polimi.ingsw.GrilliMannarino;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PopeLineSingleTest {

    @BeforeEach
    void resetStaticVariables(){
        PopeLine.reset();
        assertTrue(true);
    }

    @Test
    void popeWin(){
        PopeLineSingle pope = new PopeLineSingle();
        assertFalse(pope.addFaith());
        assertEquals(1, pope.getFaith());
        assertFalse(pope.doubleAddLorenzoFaith());
        assertFalse(pope.doubleAddLorenzoFaith());
        assertEquals(4, pope.getLorenzoFaith());
        assertFalse(pope.doubleAddLorenzoFaith());
        assertTrue(pope.doubleAddLorenzoFaith());
        assertEquals(8, pope.getLorenzoFaith());
        assertFalse(pope.checkPopeFaith());
        PopeLineSingle.updateChecks();
        assertFalse(pope.getFaithSteps()[0]);
        for(int i =0; i<14; i++)
            assertFalse(pope.addFaith());
        assertTrue(pope.addFaith());
        assertEquals(16, pope.getFaith());
        assertTrue(pope.checkPopeFaith());
        PopeLineSingle.updateChecks();
        for(int i=0; i<7; i++)
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
        assertFalse(pope.doubleAddLorenzoFaith());
        assertFalse(pope.addLorenzoFaith());
        assertTrue(pope.addLorenzoFaith());
        assertEquals(8, pope.getLorenzoFaith());
        assertFalse(pope.checkPopeFaith());
        assertFalse(pope.getFaithSteps()[0]);
        PopeLineSingle.updateChecks();
        assertTrue(PopeLineSingle.getFaithChecks()[0]);
        for(int i=0; i<3; i++)
            assertFalse(pope.doubleAddLorenzoFaith());
        assertTrue(pope.doubleAddLorenzoFaith());
        assertEquals(16, pope.getLorenzoFaith());
        assertFalse(pope.checkPopeFaith());
        PopeLineSingle.updateChecks();
        for (int i = 0; i < 3; i++)
            assertFalse(pope.doubleAddLorenzoFaith());
        assertFalse(pope.addLorenzoFaith());
        assertEquals(23, pope.getLorenzoFaith());
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
package it.polimi.ingsw.GrilliMannarino;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

class PopeLineTest {

    @BeforeEach
    void resetStaticVariables(){
        PopeLine.reset();
        assertTrue(true);
    }

    @Test
    void constructorCheck() {
        PopeLine pope = new PopeLine();
        boolean[] checkFalse = new boolean[]{false, false, false};
        assertEquals(0, pope.getFaith());
        assertArrayEquals(checkFalse, pope.getFaithSteps());
    }

    @Test
    void addFaith(){
        PopeLine pope = new PopeLine();
        assertEquals(0, pope.getFaith());
        assertFalse(pope.addFaith());
        assertEquals(1, pope.getFaith());
        assertFalse(pope.getFaithSteps()[0]);
        assertFalse(pope.addFaith());
    }

    @Test
    void activationFavor1(){
        PopeLine pope = new PopeLine();
        System.out.println("Popeline static: " + PopeLine.getFaithChecks()[0]);
        for(int i=0; i<4; i++){
            assertFalse(pope.addFaith());
            assertEquals(i+1, pope.getFaith());
        }
        assertFalse(pope.addFaith());
        assertEquals(5, pope.getFaith());
        assertFalse(pope.checkPopeFaith());
        PopeLine.updateChecks();
        assertFalse(pope.getFaithSteps()[0]);
        assertFalse(PopeLine.getFaithChecks()[0]);
    }

    @Test
    void activationFavor2(){
        PopeLine pope1 = new PopeLine();
        PopeLine pope2 = new PopeLine();
        for(int i=0; i<4; i++){
            assertFalse(pope1.addFaith());
            System.out.println("Popefaith: " + pope1.getFaith());
        }
        assertFalse(pope1.addFaith());
        /*assertEquals(true, pope1.checkPopeFaith());
        assertEquals(true, pope1.getFaithSteps()[0]);
        assertEquals(false, pope2.checkPopeFaith());
        assertEquals(false, pope2.getFaithSteps()[0]);
        assertEquals(false, PopeLine.getFaithChecks()[0]);
        PopeLine.updateChecks();
        assertTrue(PopeLine.getFaithChecks()[0]);*/
    }

    @Test
    void getPoints() {
    }
}
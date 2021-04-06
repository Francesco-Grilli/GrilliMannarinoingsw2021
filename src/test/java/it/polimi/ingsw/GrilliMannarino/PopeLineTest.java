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
        assertTrue(pope.addFaith());
        assertEquals(5, pope.getFaith());
        assertTrue(pope.checkPopeFaith());
        PopeLine.updateChecks();
        assertTrue(pope.getFaithSteps()[0]);
        assertTrue(PopeLine.getFaithChecks()[0]);
    }

    @Test
    void activationFavor2(){
        PopeLine pope1 = new PopeLine();
        PopeLine pope2 = new PopeLine();
        for(int i=0; i<4; i++){
            assertFalse(pope1.addFaith());
            System.out.println("Popefaith: " + pope1.getFaith());
        }
        assertTrue(pope1.addFaith());
        assertFalse(pope2.addFaith());
        assertTrue(pope1.checkPopeFaith());
        assertFalse(pope2.checkPopeFaith());
        PopeLine.updateChecks();
        assertFalse(pope2.getFaithSteps()[0]);
        assertTrue(pope1.getFaithSteps()[0]);
        for(int i=0; i<3; i++)
            assertFalse(pope2.addFaith());
        assertFalse(pope2.addFaith());

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
        PopeLine pope1 = new PopeLine();
        PopeLine pope2 = new PopeLine();

        for(int i=0; i<4; i++) {
            assertFalse(pope1.addFaith());
            assertFalse(pope2.addFaith());
        }

        assertTrue(pope1.addFaith());
        assertTrue(pope1.checkPopeFaith());
        assertFalse(pope2.checkPopeFaith());
        PopeLine.updateChecks();
        assertFalse(pope2.addFaith());
        assertEquals(5, pope1.getFaith());
        assertEquals(5, pope2.getFaith());
        // pope1 = 5;   favor=true, false, false
        // pope2 = 5;   favor=false, false, false
        assertEquals(3, pope1.getPoints());
        assertEquals(1, pope2.getPoints());
        for(int i=0; i<6; i++)
            assertFalse(pope2.addFaith());
        assertTrue(pope2.addFaith());
        assertTrue(pope2.checkPopeFaith());
        assertFalse(pope1.checkPopeFaith());
        PopeLine.updateChecks();
        // pope1 = 5;   favor= true, false, false
        // pope2 = 12;  favor= false, true, false
        for(int i=0; i<3; i++)
            assertFalse(pope2.addFaith());
        //pope2 = 15;   favor=false, true, false
        assertEquals(3, pope1.getPoints());
        assertEquals(12, pope2.getPoints());
        for(int i=0; i<13; i++)
            assertFalse(pope1.addFaith());
        assertTrue(pope1.addFaith());
        assertTrue(pope1.checkPopeFaith());
        assertFalse(pope2.checkPopeFaith());
        PopeLine.updateChecks();
        assertEquals(18, pope1.getPoints());
        assertEquals(12, pope2.getPoints());
        for(int i=0; i<20; i++)
            assertFalse(pope1.addFaith());
        assertEquals(26, pope1.getPoints());
        assertEquals(12, pope2.getPoints());
    }
}
package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MarbleOptionTest {

  @Test
  public void hasWhiteTest(){
    MarbleOption test1 = new MarbleOption(Marble.BLUE);
    MarbleOption test2 = new MarbleOption(Marble.WHITE);

    assertFalse(test1.hasWhite());
    assertTrue(test2.hasWhite());

  }

  @Test
  public void addOptionTest(){
    MarbleOption test1 = new MarbleOption(Marble.BLUE);
    MarbleOption test2 = new MarbleOption(Marble.GREY);

    test1.addOption(Marble.WHITE);
    test2.addOption(Marble.PURPLE);

    assertTrue(test1.hasWhite());
    assertFalse(test2.hasWhite());

    test2.addOption(Marble.WHITE);
    assertTrue(test2.hasWhite());
  }

  @Test
  public void getMarblesTest(){
    MarbleOption test1 = new MarbleOption(Marble.BLUE);
    MarbleOption test2 = new MarbleOption(Marble.GREY);

    test1.addOption(Marble.WHITE);
    test2.addOption(Marble.PURPLE);

    assertTrue(test1.getMarbles().contains(Marble.WHITE));
    assertFalse(test2.getMarbles().contains(Marble.RED));

    assertEquals(2,test1.getMarbles().size());
    assertEquals(2,test2.getMarbles().size());
    test2.addOption(Marble.WHITE);
    assertTrue(test2.getMarbles().contains(Marble.WHITE));
    assertEquals(3,test2.getMarbles().size());
  }
}

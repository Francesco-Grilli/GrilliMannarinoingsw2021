package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import org.junit.jupiter.api.Test;

import java.rmi.MarshalException;

import static org.junit.jupiter.api.Assertions.*;

public class MarbleMarketTest {

  @Test
  public void loadTest(){
    MarbleMarket test = new MarbleMarket();
  }

  @Test
  public void getMarbleBoardTest(){
    MarbleMarket test = new MarbleMarket();
    Marble[][] t = test.getMarbleBoard();
    assertArrayEquals(t, test.getMarbleBoard());
    assertNotNull(test.getMarbleBoard());

  }

  @Test
  public void getMarbleOutTest(){
    MarbleMarket test = new MarbleMarket();

    assertNotNull(test.getMarbleOut());
    assertEquals(Marble.WHITE.getClass(), test.getMarbleOut().getClass());
  }

  @Test
  public void getColumnTest(){
    MarbleMarket test = new MarbleMarket();
    Marble[][] t = test.getMarbleBoard();
    assertArrayEquals(t, test.getMarbleBoard());
    assertNotNull(test.getMarbleBoard());
    assertNotNull(test.getColumn(2));
    assertNotNull(test.getMarbleBoard());
    assertNotEquals(t, test.getMarbleBoard());
    assertNotEquals(test.getColumn(2), test.getColumn(2));
  }

  @Test
  public void getRowTest(){
    MarbleMarket test = new MarbleMarket();
    Marble[][] t = test.getMarbleBoard();
    assertArrayEquals(t, test.getMarbleBoard());
    assertNotNull(test.getMarbleBoard());
    assertNotNull(test.getRow(2));
    assertNotNull(test.getMarbleBoard());
    Marble[][] t2 = test.getMarbleBoard();
    assertNotEquals(t, t2);
    assertNotEquals(test.getRow(2), test.getRow(2));
  }
}

package it.polimi.ingsw.GrilliMannarino.GameData;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResourceTest {

  @Test
  public void getMarbleTest(){
    assertEquals(Marble.PURPLE,Resource.SERVANT.getMarble());
    assertEquals(Marble.GREY,Resource.STONE.getMarble());
    assertEquals(Marble.WHITE,Resource.UNKNOWN.getMarble());
  }
}

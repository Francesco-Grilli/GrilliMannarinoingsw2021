package it.polimi.ingsw.GrilliMannarino.GameData;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResourceTest {

  @Test
  public void getMarbleTest(){
    Resource test = Resource.SERVANT;
    Marble test2 = Marble.BLACK;
    Marble test1 = Marble.PURPLE;
    test2 = Resource.getMarble(test);
    assertEquals(test1,test2);
  }
}

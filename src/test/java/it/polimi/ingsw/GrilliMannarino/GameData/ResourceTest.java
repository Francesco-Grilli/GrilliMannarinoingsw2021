package it.polimi.ingsw.GrilliMannarino.GameData;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResourceTest {

  @Test
  public void getMarbleTest(){
    Resource test = Resource.SERVANT;
    Marble test1 = Marble.PURPLE;
    assertEquals(test1, test.getMarble(test));
  }

}

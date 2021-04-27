package it.polimi.ingsw.GrilliMannarino.GameData;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarbleTest {

  @Test
  public void getResourceTest(){
    Marble test = Marble.BLACK;
    Marble test1 = Marble.PURPLE;
    Resource test2 = Resource.UNKNOWN;

    assertEquals(test2,Marble.getResource(test));

    assertEquals(Resource.SERVANT,Marble.getResource(test1));
  }
}

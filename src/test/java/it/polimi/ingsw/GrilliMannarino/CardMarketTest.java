package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

public class CardMarketTest {

  @Test
  public void loadTest(){
    CardMarketBoardInterface test = new CardMarket();
  }

  @Test
  public void getCardsTest(){
    CardMarketBoardInterface test = new CardMarket();

    HashMap<Faction,HashMap<Integer,CreationCard>> testAnswer = test.getCards();
    assertNotNull(testAnswer);
  }

  @Test
  public void buyCardTest(){
    CardMarketBoardInterface test = new CardMarket();

    assertNull(test.buyCard(Faction.NONE,1));
    assertNull(test.buyCard(Faction.GREEN, -1));
    assertNotNull(test.buyCard(Faction.GREEN,1));
  }

}

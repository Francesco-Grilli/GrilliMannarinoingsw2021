package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;

import java.util.HashMap;

public interface CardMarketBoardInterface {

  HashMap<Faction, HashMap<Integer, CreationCard>> getCards();
  CreationCard buyCard(Faction faction, Integer level);
  CreationCard getCardFromCode(int cardCode);
}

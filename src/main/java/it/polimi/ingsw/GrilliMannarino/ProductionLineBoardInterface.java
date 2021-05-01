package it.polimi.ingsw.GrilliMannarino;

import java.util.ArrayList;
import java.util.HashMap;

public interface ProductionLineBoardInterface {
  boolean addCard(int pos, CreationCard card);
  boolean canAddCArd(int pos, CreationCard card);
  HashMap<Integer, CreationCard> getCards();
  int getNextFreeSlot();
  ArrayList<CreationCard> allUsedCards();
  int getPoints();
}

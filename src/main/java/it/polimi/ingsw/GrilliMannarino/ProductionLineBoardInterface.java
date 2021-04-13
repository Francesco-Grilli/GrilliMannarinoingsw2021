package it.polimi.ingsw.GrilliMannarino;

import java.util.ArrayList;

public interface ProductionLineBoardInterface {
  boolean addCard(int pos, CreationCard card);
  ArrayList<CreationCard> getCards();
}

package it.polimi.ingsw.GrilliMannarino;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public interface ProductionLineBoardInterface {
  boolean addCard(int pos, CreationCard card);
  boolean canAddCArd(int pos, CreationCard card);
  HashMap<Integer, CreationCard> getCards();
  int getNextFreeSlot();
  ArrayList<CreationCard> allUsedCards();
  int getPoints();
  JSONObject getStatus();
  void setStatus(JSONObject status);
  int getNumberOfCards();
}

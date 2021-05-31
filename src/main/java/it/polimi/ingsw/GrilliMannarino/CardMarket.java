package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CardMarket implements CardMarketBoardInterface, GetCarder, CardMarketBoardInterfaceSingle {
  HashMap<Integer, CreationCard> cardsInGame;
  HashMap<Faction, HashMap<Integer, CardStack>> cardStacks;
  HashMap<Integer, CreationCard> playedCards;

  public CardMarket(){
    cardsInGame = new HashMap<>();
    loadCards();
    cardStacks = new HashMap<>();
    orderCardsInStacks();
    playedCards = new HashMap<>();
    for(HashMap<Integer,CardStack> fac: cardStacks.values()){
      for(CardStack cards: fac.values()){
        cards.shuffle();
      }
    }
  }

  /**
   * returns the grid of cards disposed on top of the 12 stacks
   * @return the HashMap of HashMap representing the cards
   */
  public HashMap<Faction, HashMap<Integer, CreationCard>> getCards(){
    HashMap<Faction, HashMap<Integer, CreationCard>> cardCodes = new HashMap<>();
    for(Faction fac: cardStacks.keySet()){
      if(cardStacks.get(fac) != null) {
        cardCodes.put(fac, new HashMap<>());
        for (Integer lev : cardStacks.get(fac).keySet()) {
          if (cardStacks.get(fac).get(lev) != null){
            cardCodes.get(fac).put(lev, cardStacks.get(fac).get(lev).getCard());
          }
        }
      }
    }
    return cardCodes;
  }

  /**
   * buys card that is found at the coordinates faction-level on the grid
   * @param faction the faction coordinate
   * @param level the level coordinate
   * @return the card
   */
  public CreationCard buyCard(Faction faction, Integer level){
    if(cardStacks == null || cardStacks.isEmpty()){
      return null;
    }
    if(cardStacks.get(faction) == null || cardStacks.get(faction).isEmpty()){
      return null;
    }
    if(!(cardStacks.get(faction).get(level) == null || cardStacks.get(faction).get(level).emptyStack())){
      CreationCard cardToReturn = cardStacks.get(faction).get(level).popCard();
      playedCards.put(cardToReturn.getCardCode(),cardToReturn);
      return cardToReturn;
    }else{
      return null;
    }
  }

  /**
   * returns the card corresponding to the specified card code
   * @param cardCode the card code of the card
   * @return the card
   */
  @Override
  public CreationCard getCardFromCode(int cardCode) {
    if(cardsInGame.get(cardCode)!=null){
      return cardsInGame.get(cardCode).getCard();
    }
    return null;
  }

  /**
   * loads the cards from the file CreationCards
   * and calls the method parsecreationcard to add them to the cardsInGame
   */
  private void loadCards(){
    JSONParser jsonParser = new JSONParser();

    try (FileReader reader = new FileReader("creation_cards.json"))
    {
      Object obj = jsonParser.parse(reader);
      JSONArray cardList = (JSONArray) obj;
      cardList.forEach( card -> parseCreationCard( (JSONObject) card ) );

    } catch (ParseException | IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * parses the Json of the card and adds it to the
   * @param card json containing the card data
   */
  private void parseCreationCard(JSONObject card) {
    //Get card object within list
    JSONObject cardObject = (JSONObject) card.get("card");

    Faction cardFaction = Faction.NONE.parseFaction((String) cardObject.get("card_faction"));
    HashMap<Resource,Integer> cardPrice = parseHashMapResources((JSONObject) cardObject.get("card_price"));
    HashMap<Resource,Integer> cardInput = parseHashMapResources((JSONObject) cardObject.get("card_input"));
    HashMap<Resource,Integer> cardOutput = parseHashMapResources((JSONObject) cardObject.get("card_output"));
    int cardCode = Integer.parseInt((String) cardObject.get("card_code"));
    int cardLevel = Integer.parseInt((String) cardObject.get("card_level"));
    int cardValue = Integer.parseInt((String) cardObject.get("card_value"));

    this.cardsInGame.put(cardCode, new CreationCard(cardCode, cardLevel, cardValue,cardFaction,cardPrice,cardInput,cardOutput));
  }

  /**
   * parses the Json of the resources and returns the hashmap to be used in the creation of the CreationCard
   * @param resources the json containing raw data
   * @return the HashMap to be used in the creation of the card
   */
  private HashMap<Resource, Integer> parseHashMapResources(JSONObject resources){
    Resource[] keys = Resource.values();
    HashMap<Resource, Integer> temp = new HashMap<>();
    for(Resource key : keys){
      String resource = key.toString().toLowerCase();
      if(( resources.get(resource)) != null){
        temp.put(key, Integer.parseInt((String) resources.get(resource)));
      }
    }
    return temp;
  }

  /**
   * groups the cards by faction and by level and creates the CardStacks containing the groups
   */
  private void orderCardsInStacks(){
    int l = getMaxLevelOfCards();
    for(Faction fac: Faction.values()){
      HashMap<Integer,CardStack> stacksOfFaction = new HashMap<>();
      cardsInGame.forEach((key,value)->{
        if(fac.equals(value.getFaction())) {
          if (stacksOfFaction.get(value.getCardLevel()) == null) {
            stacksOfFaction.put(value.getCardLevel(), new CardStack());
          }
          stacksOfFaction.get(value.getCardLevel()).pushCard(value);
        }
      });
      if(!(stacksOfFaction.isEmpty())) {
        cardStacks.put(fac, stacksOfFaction);
      }
    }
  }

  /**
   * returns the max level among all the CreationCards istantiated in the game
   * @return the max level of cards
   */
  private int getMaxLevelOfCards(){
    AtomicInteger maxLevel = new AtomicInteger(0);
    cardsInGame.forEach((key,value)->{
      if(value.getCardLevel() > maxLevel.get()){
        maxLevel.set(value.getCardLevel());
      }
    });
    return maxLevel.get();
  }

  public JSONObject getStatus(){
    JSONObject status = new JSONObject();
    JSONArray cardsToSave = new JSONArray();
    this.cardsInGame.forEach((key,value)->{
      JSONObject cardToSave = new JSONObject();
      cardToSave.put("card_code",key);
      cardsToSave.add(cardToSave);
    });
    JSONArray playedCardsToSave = new JSONArray();
    this.playedCards.forEach((key,value)->{
      JSONObject playedCardToSave = new JSONObject();
      playedCardToSave.put("card_code",key);
      playedCardsToSave.add(playedCardToSave);
    });
    JSONArray factionLines = new JSONArray();
    this.cardStacks.forEach((fac,line)->{
      JSONObject jsonLine = new JSONObject();
      JSONArray lineOfStacks = new JSONArray();
      line.forEach((lev, stack)->{
        JSONObject stackIdentity = new JSONObject();
        stackIdentity.put("level",lev.toString());
        stackIdentity.put("stack",stack.getStatus());
        lineOfStacks.add(stackIdentity);
      });
      jsonLine.put("faction",fac.toString());
      jsonLine.put("levels",lineOfStacks);
      factionLines.add(jsonLine);
    });
    status.put("cards_in_game",cardsToSave);
    status.put("cards_played",playedCardsToSave);
    status.put("stacks_of_cards",factionLines);
    return  status;
  }

  public void setStatus(JSONObject status){}

  /**
   * deletes two cards of a faction starting from the lowest possible level
   * @param faction the faction to delete cards from
   * @return if the cards have been removed correctly
   */
  @Override
  public boolean deleteTwoCards(Faction faction) {
    if(factionEmpty(faction)){
      return true;
    }
    effectiveDeletion(faction);
    return factionEmpty(faction);
  }

  /**
   * specifies if the market is empty of creation cards of the given faction
   * @param faction the faction
   * @return if there are no more cards
   */
  private boolean factionEmpty(Faction faction){
    if(cardStacks.get(faction) != null){
      HashMap<Integer, CardStack> temp = cardStacks.get(faction);
      boolean toRet = true;
      for(Integer key:temp.keySet()){
        CardStack moment = temp.get(key);
        toRet = toRet && moment.emptyStack();
      }
      return toRet;
    }
    return true;
  }

  /**
   * deletes two cards at maximum from the given faction pool starting from the lowest level
   * @param faction the given faction
   */
  private void effectiveDeletion(Faction faction){
    if(cardStacks.get(faction) != null){
      HashMap<Integer, CardStack> temp = cardStacks.get(faction);
      int deleted = 0;
      for(Integer i:temp.keySet()){
        CardStack toDelete = temp.get(i);
        if (!toDelete.emptyStack() && deleted < 2) {
          toDelete.popCard();
          deleted++;
        }
      }
    }
  }
}

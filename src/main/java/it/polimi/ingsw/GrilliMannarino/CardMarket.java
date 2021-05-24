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
  }

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

  @Override
  public CreationCard getCardFromCode(int cardCode) {
    if(cardsInGame.get(cardCode)!=null){
      return cardsInGame.get(cardCode).getCard();
    }
    return null;
  }

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

  @Override
  public boolean deleteTwoCards(Faction faction) {
    if(factionEmpty(faction)){
      return true;
    }
    if(cardStacks.get(faction) != null){
      HashMap<Integer, CardStack> temp = cardStacks.get(faction);
      List<Integer> keys = temp.keySet().stream().sorted().collect(Collectors.toList());
      int deleted = 0;
      for(Integer i:keys){
        CardStack toDelete = temp.get(i);
        if(toDelete.emptyStack()){
          break;
        }else{
          toDelete.popCard();
          deleted++;
          if(factionEmpty(faction)){
            return true;
          }else{
            if(deleted >= 2){
              return false;
            }else{
              if(toDelete.emptyStack()){
                break;
              }else{
                toDelete.popCard();
                deleted++;
                if(factionEmpty(faction)){
                  return true;
                }else{
                  return false;
                }
              }
            }
          }
        }
      }
      return true;
    }
    return true;
  }

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
}

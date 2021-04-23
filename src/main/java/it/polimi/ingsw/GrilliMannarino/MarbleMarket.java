package it.polimi.ingsw.GrilliMannarino;


import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class MarbleMarket implements MarbleMarketBoardInterface {
  private Marble[][] marbleBoard;
  private Marble marbleOut;
  private int sizeX;
  private int sizeY;

  public MarbleMarket(){
    loadMarbles();
  }

  public Marble[][] getMarbleBoard() {
    Marble[][] t = marbleBoard.clone();
    return t;
  }

  public Marble getMarbleOut() {
    return marbleOut;
  }

  public ArrayList<MarbleOption> getColumn(int column){
    column = column-1 % this.sizeX;
    ArrayList<MarbleOption> toReturn = new ArrayList<>(this.sizeY);
    for(int pos = 0; pos<this.sizeY; pos++){
      toReturn.add(pos,new MarbleOption(this.marbleBoard[column][pos]));
    }
    shiftColumn(column);
    return toReturn;
  }

  private void shiftColumn(int column){
    Marble temporaryMarble;
    for(int pos = 0; pos<this.sizeY; pos++){
      temporaryMarble = this.marbleBoard[column][pos];
      this.marbleBoard[column][pos] = this.marbleOut;
      this.marbleOut = temporaryMarble;
    }
  }

  public ArrayList<MarbleOption> getRow(int row){
    row = row-1 % this.sizeY;
    ArrayList<MarbleOption> toReturn = new ArrayList<>(this.sizeX);
    for(int pos = 0; pos<this.sizeX; pos++){
      toReturn.add(pos,new MarbleOption(this.marbleBoard[pos][row]));
    }
    shiftRow(row);
    return toReturn;
  }

  private void shiftRow(int row){
    Marble temporaryMarble;
    for(int pos = 0; pos<this.sizeX; pos++){
      temporaryMarble = this.marbleBoard[pos][row];
      this.marbleBoard[pos][row] = this.marbleOut;
      this.marbleOut = temporaryMarble;
    }
  }

  private void loadMarbles(){
    JSONParser jsonParser = new JSONParser();

    try (FileReader reader = new FileReader("marbles.json"))
    {
      Object obj = jsonParser.parse(reader);
      JSONObject marbles = (JSONObject) obj;
      this.sizeX = Integer.parseInt((String) marbles.get("size_x"));
      this.sizeY = Integer.parseInt((String) marbles.get("size_y"));
      marbleParsing((JSONObject) marbles.get("marbles"));


    } catch (ParseException | IOException e) {
      e.printStackTrace();
    }
  }

  private void marbleParsing(JSONObject j){
    HashMap<Marble,Integer> marbles = new HashMap<>();
    for(Marble m: Marble.values()){
      if(j.get(m.toString().toLowerCase()) != null) {
        marbles.put(m, Integer.parseInt((String) j.get(m.toString().toLowerCase())));
      }
    }
    if(!(marbles.values().stream().reduce(0, Integer::sum).equals((this.sizeX*this.sizeY)+1))){
      this.sizeX = 4;
      this.sizeY = 3;
      marbles = new HashMap<>();
      marbles.put(Marble.WHITE,4);
      marbles.put(Marble.PURPLE,2);
      marbles.put(Marble.YELLOW,2);
      marbles.put(Marble.GREY,2);
      marbles.put(Marble.BLUE,2);
      marbles.put(Marble.RED,1);
    }
    this.marbleBoard = new Marble[sizeX][sizeY];
    for(int i = 0; i < sizeX; i++){
      for(int k = 0; k < sizeY; k++){
        marbleBoard[i][k] = Marble.BLACK;
      }
    }
    while(marbles.values().stream().reduce(0, Integer::sum)>0){
      for(Marble key: marbles.keySet()){
        if(marbles.get(key) != null && marbles.get(key)>0){
          if(marbles.values().stream().reduce(0, Integer::sum).equals((this.sizeX*this.sizeY)+1)){
            this.marbleOut = key;
          }else{
            int i = (marbles.values().stream().reduce(0, Integer::sum)-1)/this.sizeX;
            int k = (marbles.values().stream().reduce(0, Integer::sum)-1)%this.sizeX;
            this.marbleBoard[k][i] = key;
          }
          marbles.put(key, marbles.get(key)-1);
        }
      }
    }
  }
}

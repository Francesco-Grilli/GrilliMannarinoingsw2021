package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {
        CliView cli = new CliView();
        /*Marble[][] marble = new Marble[3][4];
        marble[0] = new Marble[]{Marble.PURPLE, Marble.BLUE, Marble.PURPLE, Marble.GREY};
        marble[1] = new Marble[]{Marble.WHITE, Marble.RED, Marble.WHITE, Marble.WHITE};
        marble[2] = new Marble[]{Marble.WHITE, Marble.GREY, Marble.YELLOW, Marble.YELLOW};
        cli.showMarbleMarket(marble, Marble.BLACK);*/
        /*ArrayList<ArrayList<Marble>> marbleList = new ArrayList<>();
        Map<Row, HashMap<Resource, Integer>> warehouse = new TreeMap<>();
        HashMap<Resource, Integer> res = new HashMap<>();
        res.put(Resource.FAITH, 2);
        warehouse.put(Row.THIRD, res);
        HashMap<Resource, Integer> res2 = new HashMap<>();
        res2.put(Resource.COIN, 1);
        warehouse.put(Row.FIRST, res2);
        HashMap<Resource, Integer> res3 = new HashMap<>();
        warehouse.put(Row.SECOND, res3);
        res.clear();
        res2.clear();
        res3.clear();
        cli.setWarehouse(warehouse);
        ArrayList<Marble> marble = new ArrayList<>();
        marble.add(Marble.PURPLE);
        marbleList.add(marble);
        ArrayList<Marble> marble2 = new ArrayList<>();
        marble2.add(Marble.GREY);
        marble2.add(Marble.RED);
        marble2.add(Marble.BLUE);
        marbleList.add(marble2);
        cli.selectMarble(marbleList);
        cli.selectAction();*/
        ArrayList<Integer> cards = new ArrayList<>();
        cards.add(1);
        cards.add(7);
        cli.startGame();
        cli.showProductionCard(cards);
    }
}

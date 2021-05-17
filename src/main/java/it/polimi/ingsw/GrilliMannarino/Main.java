package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GUIControllers.GUIController;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        //CliView cli = new CliView();
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
        /*ArrayList<Integer> cards = new ArrayList<>();
        cards.add(49);
        cards.add(50);
        cards.add(51);
        cards.add(52);
        cards.add(53);
        cards.add(54);
        cards.add(55);
        cards.add(56);
        cards.add(57);
        cards.add(58);
        cards.add(59);
        cards.add(60);
        cards.add(61);
        cards.add(62);
        cards.add(63);
        cards.add(64);
        /*HashMap<Integer, Boolean> cards = new HashMap<>();
        cards.put(3, true);
        cards.put(7, false);
        cards.put(4, true);*/
        /*cli.startGame();
        cli.showLeaderCard(cards);
        for(int i=0; i<100; i++){
            System.out.println((int) (Math.random()*4));
        }*/
        GUIController.main(new String[1]);

    }
}

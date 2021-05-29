package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.Message.BuyProductionCardMessage;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.Map;

public class CardMarketController implements SmallController{
    public ImageView card00;
    public ImageView card10;
    public ImageView card20;
    public ImageView card30;
    public ImageView card01;
    public ImageView card11;
    public ImageView card21;
    public ImageView card31;
    public ImageView card02;
    public ImageView card12;
    public ImageView card22;
    public ImageView card32;
    public ImageView productionCard1;
    public ImageView productionCard2;
    public ImageView productionCard3;
    private HashMap<Faction,HashMap<Integer,ImageView>> cardSlots = new HashMap<>();
    private HashMap<Integer, ImageView> cardProduceSlots = new HashMap<>();
    private final Faction blue = Faction.BLUE;
    private final Faction green = Faction.GREEN;
    private final Faction yellow = Faction.YELLOW;
    private final Faction purple = Faction.PURPLE;
    private Integer cardCodeProduce;

    private GUIView view;

    @Override
    public void setView(GUIView view) {
        this.view = view;
    }

    @Override
    public void errorMessage(String header, String context) {
        Alert error = new Alert(Alert.AlertType.INFORMATION);
        error.setHeaderText(header);
        error.setContentText(context);
        error.show();
    }

    private void getHashMapUp(){
        HashMap<Integer,ImageView> blue0 = new HashMap<>();
        HashMap<Integer,ImageView> green2 = new HashMap<>();
        HashMap<Integer,ImageView> purple1 = new HashMap<>();
        HashMap<Integer,ImageView> yellow3 = new HashMap<>();
        blue0.put(1,card00);
        blue0.put(2,card01);
        blue0.put(3,card02);
        green2.put(1,card20);
        green2.put(2,card21);
        green2.put(3,card22);
        yellow3.put(1,card30);
        yellow3.put(2,card31);
        yellow3.put(3,card32);
        purple1.put(1,card10);
        purple1.put(2,card11);
        purple1.put(3,card12);
        this.cardSlots.put(blue,blue0);
        this.cardSlots.put(purple,purple1);
        this.cardSlots.put(green,green2);
        this.cardSlots.put(yellow,yellow3);
        cardProduceSlots.put(1, productionCard1);
        cardProduceSlots.put(2, productionCard2);
        cardProduceSlots.put(3, productionCard3);
        cardProduceSlots.keySet().forEach(pos -> cardProduceSlots.get(pos).setDisable(true));
    }

    public void setCardSlots(HashMap<Faction,HashMap<Integer, Map.Entry<Integer,Boolean>>> cardSlots) {
        this.getHashMapUp();
        for(Faction fac:cardSlots.keySet()){
            for(Integer lev:cardSlots.get(fac).keySet()){
                int cc = cardSlots.get(fac).get(lev).getKey();
                Image toput = new Image("image/CC-"+cc+".png");
                this.cardSlots.get(fac).get(lev).setImage(toput);
                this.cardSlots.get(fac).get(lev).setDisable(!cardSlots.get(fac).get(lev).getValue());
                if(!cardSlots.get(fac).get(lev).getValue())
                    this.cardSlots.get(fac).get(lev).setOpacity(0.5);
                this.cardSlots.get(fac).get(lev).setOnMouseClicked(e->this.setCardCodeProduce(cc));
            }
        }
    }

    private void setCardCodeProduce(int cardCode){
        this.cardCodeProduce = cardCode;
        cardProduceSlots.keySet().forEach(pos -> cardProduceSlots.get(pos).setDisable(false));
    }


    private void sendMessage(int pos){
        BuyProductionCardMessage message = new BuyProductionCardMessage(view.getGameId(), view.getPlayerId());
        message.setSelectedCard(cardCodeProduce);
        message.setPositionCard(pos);
        message.setSelectCard(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                view.sendMessageToServer(message);
            }
        }).start();
        cardProduceSlots.forEach((p, i) -> i.setDisable(true));
    }

    public void setProductionLine(HashMap<Integer, Integer> productionLine) {
        HashMap<Integer, Integer> line = new HashMap<>(); //position, cc
        productionLine.forEach((cc, p) -> line.put(p, cc));
        for(Integer pos : line.keySet()){
            cardProduceSlots.get(pos).setImage(new Image("image/CC-" + line.get(pos) + ".png"));
        }
        cardProduceSlots.forEach((pos, img) -> img.setOnMouseClicked(e -> sendMessage(pos)));
    }

}

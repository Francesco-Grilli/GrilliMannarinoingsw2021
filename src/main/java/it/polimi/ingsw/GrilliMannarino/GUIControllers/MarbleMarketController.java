package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.Message.MarbleMarketMessage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;

public class MarbleMarketController implements SmallController{

    public ImageView marble0_0;
    public ImageView marble1_0;
    public ImageView marble2_0;
    public ImageView marble3_0;
    public ImageView marble0_1;
    public ImageView marble1_1;
    public ImageView marble2_1;
    public ImageView marble3_1;
    public ImageView marble0_2;
    public ImageView marble1_2;
    public ImageView marble2_2;
    public ImageView marble3_2;
    public ImageView marbleOut;
    public Button col1;
    public Button col3;
    public Button col2;
    public Button col4;
    public Button row3;
    public Button row2;
    public Button row1;
    public ImageView backArrow;

    private HashMap<Integer, Button> column = new HashMap<>();
    private HashMap<Integer, Button> row = new HashMap<>();
    private GUIView view;

    @Override
    public void setView(GUIView view) {
        this.view = view;
        initializeMap();
    }

    @Override
    public void errorMessage(String header, String context) {
        Alert error = new Alert(Alert.AlertType.INFORMATION);
        error.setHeaderText(header);
        error.setContentText(context);
        error.show();
    }

    private void initializeMap(){
        column.put(1, col1);
        column.put(2, col2);
        column.put(3, col3);
        column.put(4, col4);
        row.put(1, row1);
        row.put(2, row2);
        row.put(3, row3);
        column.forEach((pos, but) -> but.setOnAction(e -> sendMessage("C", pos)));
        row.forEach((pos, but) -> but.setOnAction(e -> sendMessage("R", pos)));
    }

    private void setMarbles(Marble[][] marbles){
        String t00 = marbles[0][0].toString();
        marble0_0.setImage(new Image("image/"+t00+".png"));
        String t01 = marbles[0][1].toString();
        marble0_1.setImage(new Image("image/"+t01+".png"));
        String t02 = marbles[0][2].toString();
        marble0_2.setImage(new Image("image/"+t02+".png"));
        String t10 = marbles[1][0].toString();
        marble1_0.setImage(new Image("image/"+t10+".png"));
        String t11 = marbles[1][1].toString();
        marble1_1.setImage(new Image("image/"+t11+".png"));
        String t12 = marbles[1][2].toString();
        marble1_2.setImage(new Image("image/"+t12+".png"));
        String t20 = marbles[2][0].toString();
        marble2_0.setImage(new Image("image/"+t20+".png"));
        String t21 = marbles[2][1].toString();
        marble2_1.setImage(new Image("image/"+t21+".png"));
        String t22 = marbles[2][2].toString();
        marble2_2.setImage(new Image("image/"+t22+".png"));
        String t30 = marbles[3][0].toString();
        marble3_0.setImage(new Image("image/"+t30+".png"));
        String t31 = marbles[3][1].toString();
        marble3_1.setImage(new Image("image/"+t31+".png"));
        String t32 = marbles[3][2].toString();
        marble3_2.setImage(new Image("image/"+t32+".png"));
    }

    public void setMarbles(ArrayList<ArrayList<Marble>> marbles){
        Marble[][] marbles2d = new Marble[marbles.size()][marbles.get(0).size()];
        for(int i=0; i<marbles.size(); i++){
            for(int x=0; x<marbles.get(i).size(); x++){
                marbles2d[i][x] = marbles.get(i).get(x);
            }
        }
        setMarbles(marbles2d);
    }

    public void setMarbleOut(Marble marble){
        String t = marble.toString();
        marbleOut.setImage(new Image("image/"+t+".png"));
    }

    private void sendMessage(String rc, int pos){
        MarbleMarketMessage message = new MarbleMarketMessage(view.getGameId(), view.getPlayerId());
        message.setSelectColumnRow(true);
        message.setColumnRowValue(pos);
        message.setColumnRow(rc);
        new Thread(new Runnable() {
            @Override
            public void run() {
                view.sendMessageToServer(message);
            }
        }).start();
    }

    public void goBack(MouseEvent mouseEvent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                view.backToActionSelect();
            }
        }).start();
    }
}

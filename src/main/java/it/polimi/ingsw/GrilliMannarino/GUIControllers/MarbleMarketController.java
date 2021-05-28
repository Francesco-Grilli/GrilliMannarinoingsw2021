package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

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

    private GUIView view;

    @Override
    public void setView(GUIView view) {
        this.view = view;
    }

    @Override
    public void errorMessage(String header, String context) {

    }

    public void setMarbles(Marble[][] marbles){
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
        marble3_1.setImage(new Image("image/"+t31+".png"));
    }

    public void setMarbleOut(Marble marble){
        String t = marble.toString();
        marbleOut.setImage(new Image("image/"+t+".png"));
    }
}

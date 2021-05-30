package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VictoryScreenController {
  public ImageView victoryScreen;
  public Label scoreField;

  public void setResult(boolean victory,int score){
    if(victory){
      victoryScreen.setImage(new Image("image/Victory.png"));
    }else{
      victoryScreen.setImage(new Image("image/Defeat.png"));
    }
    scoreField.setText(Integer.toString(score));
  }
}

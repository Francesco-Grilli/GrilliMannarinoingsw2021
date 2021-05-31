package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VictoryScreenController implements SmallController{
  public ImageView victoryScreen;
  public Label scoreField;
  private GUIView view;

  public void setResult(boolean victory,int score){
    if(victory){
      victoryScreen.setImage(new Image("image/Victory.png"));
    }else{
      victoryScreen.setImage(new Image("image/Defeat.png"));
    }
    scoreField.setText(Integer.toString(score));
  }

  @Override
  public void setView(GUIView view) {
    this.view=view;
  }

  @Override
  public void errorMessage(String header, String context) {

  }
}

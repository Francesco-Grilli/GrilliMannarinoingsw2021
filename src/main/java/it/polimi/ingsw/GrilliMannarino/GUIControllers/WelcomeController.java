package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class WelcomeController implements SmallController{

  private GUIView view;

  public void printNothing(){
    System.out.println("Do nothing");
  }

  public void getLogin(){

  }

  public void getNewAccount(){

  }

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
}

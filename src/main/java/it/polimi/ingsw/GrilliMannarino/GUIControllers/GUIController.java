package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIController extends Application implements GUIControllerInterface {

  private GUIView requestHandler;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception{
    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Welcome.fxml"));
    primaryStage.setTitle("prova");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }
}

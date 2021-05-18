package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;

public class GUIController extends Application implements GUIControllerInterface {

  private GUIView requestHandler;
  HashMap<String, Scene> sceneMap;

  public GUIController(){
    sceneMap = new HashMap<>();
  }

  private void setUpScene(){
    Parent accountRoot = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
  }

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {



  }

  @Override
  public void setScene(String scene, AccountManagingController c) {

  }
}

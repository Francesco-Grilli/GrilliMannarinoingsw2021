package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.ClientController;
import it.polimi.ingsw.GrilliMannarino.GUIView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GUIController extends Application implements GUIControllerInterface, SmallController {

  private GUIView requestHandler;
  HashMap<String, String> sceneMap;
  SmallController activeController;
  public BorderPane background;

  public GUIController(){
    sceneMap = new HashMap<>();
  }

  private void setUpScene(){
      sceneMap.put("account", "AccountManaging.fxml");
      sceneMap.put("welcome", "Welcome.fxml");
      sceneMap.put("board", "Board.fxml");
  }

  public static void main(String[] args) {
    launch(args);
  }

  Stage stage;

  @Override
  public void start(Stage primaryStage) throws Exception{

    this.stage = primaryStage;
    GUIView view = new GUIView();
    view.setScreenHandler(this);
    this.requestHandler = view;
    setUpScene();

    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Background.fxml"));
    Parent root = null;
    try {
      root = loader.load();
      background = (BorderPane) root;
      SmallController wc = loader.getController();
      this.activeController = wc;
    } catch (IOException e) {
      e.printStackTrace();
    }
    Scene scene = new Scene(root);
    FXMLLoader object = new FXMLLoader(getClass().getClassLoader().getResource("Board.fxml"));
    Pane pane = object.load();
    background.setCenter(pane);
    stage.setTitle("prova");
    stage.setScene(scene);
    stage.show();


    Thread task = new Thread(new Runnable() {
      @Override
      public void run() {
        ClientController cc = new ClientController(view);
      }
    });
    task.setDaemon(true);
    task.start();

  }

  @Override
  public void setScene(String scene, GUIView cont) {

    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(sceneMap.get(scene)));
    Parent root = null;
    try {
      root = loader.load();
      SmallController asd = loader.getController();
      asd.setController(cont);
      activeController = asd;
    } catch (IOException e) {
      e.printStackTrace();
    }
    background.setCenter(root);
    stage.show();

  }

  @Override
  public void setController(GUIView controller) {

  }

  @Override
  public void errorMessage(String header, String context) {
    activeController.errorMessage(header, context);
  }
}

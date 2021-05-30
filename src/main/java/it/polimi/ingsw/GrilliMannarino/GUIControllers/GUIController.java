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
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class GUIController extends Application implements GUIControllerInterface {

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
      sceneMap.put("createGame", "CreateGame.fxml");
      sceneMap.put("action", "Actions.fxml");
      sceneMap.put("cardDisplay", "CardDisplay.fxml");
      sceneMap.put("resources", "Resources.fxml");
      sceneMap.put("resolveUnknown", "ResolveUnknown.fxml");
      sceneMap.put("endGame", "EndGame.fxml");
      sceneMap.put("cardMarket", "CardMarket.fxml");
      sceneMap.put("boardSingle", "BoardSingle.fxml");
      sceneMap.put("marbleMarket", "MarbleMarket.fxml");
      sceneMap.put("warehouse", "Warehouse.fxml");
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

    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Welcome.fxml"));
    Parent root = null;
    try {
      root = loader.load();
      SmallController wc = loader.getController();
      wc.setView(view);
      this.activeController = wc;
    } catch (IOException e) {
      e.printStackTrace();
    }
    stage.setTitle("prova");
    stage.setScene(new Scene(root));
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
    String url = sceneMap.get(scene);
    URL net = getClass().getClassLoader().getResource(url);
    FXMLLoader loader = new FXMLLoader(net);
    Parent root = null;
    try {
      root = loader.load();
      SmallController asd = loader.getController();
      asd.setView(cont);
      activeController = asd;
    } catch (IOException e) {
      e.printStackTrace();
    }
    stage.setTitle("prova");
    stage.setScene(new Scene(root));
    stage.show();

  }

  @Override
  public void errorMessage(String header, String context) {
    activeController.errorMessage(header, context);
  }

  @Override
  public SmallController getActiveController() {
    return activeController;
  }

}

package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GUIControllers.*;
import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application{

    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.stage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EndGame.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            EndGameController edc = (EndGameController) loader.getController();
            HashMap<Integer, Map.Entry<String, Integer>> ranking = new HashMap<>();
            ranking.put(1, new AbstractMap.SimpleEntry<>("Giovanni", 100));
            ranking.put(2, new AbstractMap.SimpleEntry<>("Lorenzo", 55));
            ranking.put(3, new AbstractMap.SimpleEntry<>("Casa", 10));
            edc.setPlayerRanking(ranking);
            edc.endGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("prova");
        stage.setScene(new Scene(root));
        stage.show();

    }
}

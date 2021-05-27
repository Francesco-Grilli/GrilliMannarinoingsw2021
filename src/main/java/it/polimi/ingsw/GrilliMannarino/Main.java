package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GUIControllers.GUIController;
import it.polimi.ingsw.GrilliMannarino.GUIControllers.ResolveUnknownController;
import it.polimi.ingsw.GrilliMannarino.GUIControllers.ResourcesController;
import it.polimi.ingsw.GrilliMannarino.GUIControllers.SmallController;
import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {

    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.stage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ResolveUnknown.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            ResolveUnknownController wc = (ResolveUnknownController) loader.getController();
            HashMap<Integer, HashMap<Resource, Integer>> input = new HashMap<>();
            HashMap<Integer, HashMap<Resource, Integer>> output = new HashMap<>();
            HashMap<Resource, Integer> inputUnk = new HashMap<>();
            inputUnk.put(Resource.UNKNOWN, 2);
            HashMap<Resource, Integer> outputUnk = new HashMap<>();
            outputUnk.put(Resource.UNKNOWN, 1);
            input.put(0, inputUnk);
            output.put(0, outputUnk);
            HashMap<Resource, Integer> input64 = new HashMap<>();
            input64.put(Resource.COIN, 1);
            HashMap<Resource, Integer> output64 = new HashMap<>();
            output64.put(Resource.UNKNOWN, 1);
            output64.put(Resource.FAITH, 1);
            output.put(64, output64);
            input.put(64, input64);
            wc.setOutputCard(output);
            wc.setInputCard(input);
            wc.resolve();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("prova");
        stage.setScene(new Scene(root));
        stage.show();

    }
}

package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import it.polimi.ingsw.GrilliMannarino.Message.EnterGameMessage;
import it.polimi.ingsw.GrilliMannarino.Message.NewGameMessage;
import it.polimi.ingsw.GrilliMannarino.Message.StartGameMessage;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.awt.*;

public class CreateGameController implements SmallController {

    public Slider numberPlayer;
    public Button startButton;
    public Button joinButton;
    public Button newGameButton;
    public Label showNumberPlayer;
    private GUIView view;

    public void joinGame(){
        EnterGameMessage enterGame = new EnterGameMessage(null, view.getPlayerId(), view.getNickname());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.sendMessageToServer(enterGame);
            }
        });
        thread.start();
        disableAllButton();
    }

    public void newGame(){
        int nop = (int) numberPlayer.getValue();
        NewGameMessage message = new NewGameMessage(null, view.getPlayerId(), view.getNickname());
        message.setNumberOfPlayer(nop);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.sendMessageToServer(message);
            }
        });
        thread.start();
        disableAllButton();
    }

    @Override
    public void setView(GUIView view) {
        this.view = view;
        startButton.setDisable(true);
        startButton.setOpacity(0);
    }

    public void showStartButton(){
        startButton.setDisable(false);
        startButton.setOpacity(1);
        newGameButton.setDisable(true);
        joinButton.setDisable(true);
        numberPlayer.setDisable(true);
    }

    @Override
    public void errorMessage(String header, String context) {
        Alert error = new Alert(Alert.AlertType.INFORMATION);
        error.setHeaderText(header);
        error.setContentText(context);
        error.show();
    }

    public void startGame() {
        System.out.println("Starting game");
        StartGameMessage message = new StartGameMessage(view.getGameId(), view.getPlayerId(), true);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.sendMessageToServer(message);
            }
        });
        thread.start();

    }

    public void setNumberOfPlayer() {
        showNumberPlayer.setText(Integer.toString((int)numberPlayer.getValue()));
    }

    public void disableAllButton(){
        startButton.setDisable(true);
        joinButton.setDisable(true);
        newGameButton.setDisable(true);
        numberPlayer.setDisable(true);
    }
}

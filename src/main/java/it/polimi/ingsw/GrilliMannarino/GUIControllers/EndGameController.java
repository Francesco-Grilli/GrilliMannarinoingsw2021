package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.Map;

public class EndGameController implements SmallController {


    public Label player1;
    public Label player2;
    public Label player3;
    public Label player4;
    public Label nickname1;
    public Label nickname2;
    public Label nickname3;
    public Label nickname4;
    public Label score1;
    public Label score2;
    public Label score3;
    public Label score4;

    private GUIView view;
    private HashMap<Integer, Map.Entry<String, Integer>> playerRanking;
    HashMap<Integer, Label> playerMap = new HashMap<>();
    HashMap<Integer, Label> nicknameMap = new HashMap<>();
    HashMap<Integer, Label> scoreMap = new HashMap<>();

    private void endGame(){
        setUpLabelMap();
        playerMap.forEach((pos, l) -> {
            if(pos>view.getGameId())
                l.setVisible(false);
            else{
                nicknameMap.get(pos).setText(playerRanking.get(pos).getKey());
                scoreMap.get(pos).setText(playerRanking.get(pos).getValue().toString());
            }
        });
    }

    private void setUpLabelMap() {
        playerMap.put(1, player1);
        playerMap.put(2, player2);
        playerMap.put(3, player3);
        playerMap.put(4, player4);
        nicknameMap.put(1, nickname1);
        nicknameMap.put(2, nickname2);
        nicknameMap.put(3, nickname3);
        nicknameMap.put(4, nickname4);
        scoreMap.put(1, score1);
        scoreMap.put(2, score2);
        scoreMap.put(3, score3);
        scoreMap.put(4, score4);
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

    public void setPlayerRanking(HashMap<Integer, Map.Entry<String, Integer>> playerRanking) {
        this.playerRanking = playerRanking;
        endGame();
    }
}

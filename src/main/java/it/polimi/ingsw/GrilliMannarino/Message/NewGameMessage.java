package it.polimi.ingsw.GrilliMannarino.Message;

public class NewGameMessage extends Message implements MessageInterface{

    /**
     * newGame is true if have been added a new game
     */
    private boolean newGame = false;
    private final String nickname;
    private int numberOfPlayer;
    private String messageString;

    public NewGameMessage(Integer gameId, Integer playerId, String nickname) {
        super(gameId, playerId);
        this.nickname = nickname;
    }

    @Override
    public void execute(VisitorInterface visitor) {
        visitor.executeNewGame(this);
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isNewGame() {
        return newGame;
    }

    public void setNewGame(boolean newGame) {
        this.newGame = newGame;
    }

    public void setNumberOfPlayer(int numberOfPlayer){
        if(numberOfPlayer<=4 && numberOfPlayer>=1){
            this.numberOfPlayer = numberOfPlayer;
        }
        else
            this.numberOfPlayer = 4;
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    public String getMessageString() {
        return messageString;
    }

    public void setMessageString(String messageString) {
        this.messageString = messageString;
    }
}

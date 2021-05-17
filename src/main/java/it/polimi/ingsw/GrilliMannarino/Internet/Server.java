package it.polimi.ingsw.GrilliMannarino.Internet;

import it.polimi.ingsw.GrilliMannarino.Game;
import it.polimi.ingsw.GrilliMannarino.Message.MessageInterface;
import it.polimi.ingsw.GrilliMannarino.ServerController;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

    private ServerSocket serverSocket;
    private HashMap<Integer, ClientHandler> handlerList = new HashMap<>();
    private boolean keepGoing = true;
    private ServerController controller;

    public static void main(String[] args) {

        new ServerController();

    }

    public void startConnection(){
        Thread thread = new Thread(new ConnectionHandler(this));
        thread.start();
    }

    public Server(ServerController controller){
        this.controller = controller;
    }

    public void setKeepGoing(boolean keepGoing) {
        this.keepGoing = keepGoing;
    }

    public void sendMessageTo(Integer playerId, MessageInterface message){
        handlerList.get(playerId).sendMessage(message);
    }

    public void messageToController(MessageInterface message){
        controller.receiveMessage(message);
    }

    public MessageInterface getMessageFrom() {
        return null;
    }

    public void setHandlerList(Integer playerId, ClientHandler handler) {
        handlerList.put(playerId, handler);
    }

    public Integer createNewAccount(String nickname, String password){
        return controller.addPlayer(nickname, password);
    }

    public void createNewClient(Socket socket) {
        Thread thread = new Thread(new ClientHandler(socket, this));
        thread.start();
    }

    public Integer logIn(String nickname, String password) {
        return controller.logInPlayer(nickname, password);
    }
}

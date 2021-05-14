package it.polimi.ingsw.GrilliMannarino.Internet;

import it.polimi.ingsw.GrilliMannarino.Message.LoginMessage;
import it.polimi.ingsw.GrilliMannarino.Message.MessageInterface;

import java.io.*;
import java.net.Socket;


public class ClientHandler implements  Runnable{

    private final Socket socket;
    private final Server server;
    private  Integer playerId;
    private  String nickName;
    
    private InputStream is;
    private OutputStream os;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientHandler(Socket socket, Server server){
        this.server=server;
        this.socket=socket;
    }

    @Override
    public void run() {
        setUpStream();
        setUpInformation();
        setUpGame();
    }

    private void setUpGame() {
        boolean goOn = true;
        while(goOn){
            try{
                MessageInterface message = (MessageInterface) in.readObject();
                server.messageToController(message);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                goOn = false;
            }
        }

    }

    private void setUpInformation() {
        boolean login = false;

        while(!login){
            try{
                LoginMessage message = (LoginMessage) in.readObject();

                if(message.isNewAccount()){
                    Integer playerId = server.createNewAccount(message.getNickname());
                    if(playerId!=null){
                        login = true;
                        server.setHandlerList(playerId, this);
                        LoginMessage messageOut = new LoginMessage(message.getNickname());
                        messageOut.setPlayerId(playerId);
                        this.playerId = playerId;
                        this.nickName = message.getNickname();
                        messageOut.setMessage("Account created");
                        messageOut.setCorrectLogin(true);
                        out.writeObject(messageOut);
                    }
                    else{
                        LoginMessage messageOut = new LoginMessage(message.getNickname());
                        messageOut.setMessage("Error with new account");
                        out.writeObject(messageOut);
                    }
                }
                else{
                    Integer playerId = server.logIn(message.getNickname());
                    if(playerId!=null){
                        login = true;
                        server.setHandlerList(playerId, this);
                        LoginMessage messageOut = new LoginMessage(message.getNickname());
                        messageOut.setPlayerId(playerId);
                        this.playerId = playerId;
                        this.nickName = message.getNickname();
                        messageOut.setMessage("Login was correct");
                        messageOut.setCorrectLogin(true);
                        out.writeObject(messageOut);
                    }
                    else{
                        LoginMessage messageOut = new LoginMessage(message.getNickname());
                        messageOut.setMessage("Error with login");
                        out.writeObject(messageOut);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void setUpStream(){
        try {
            
            is = socket.getInputStream();
            os = socket.getOutputStream();
            out = new ObjectOutputStream(os);
            in = new ObjectInputStream(is);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(MessageInterface message) {
        try{
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

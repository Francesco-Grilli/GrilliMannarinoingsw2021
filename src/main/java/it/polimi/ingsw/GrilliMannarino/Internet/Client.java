package it.polimi.ingsw.GrilliMannarino.Internet;

import it.polimi.ingsw.GrilliMannarino.CliView;
import it.polimi.ingsw.GrilliMannarino.ClientController;
import it.polimi.ingsw.GrilliMannarino.Message.LoginMessage;
import it.polimi.ingsw.GrilliMannarino.Message.MessageInterface;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private Socket socket;
    private InputStream is;
    private OutputStream os;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public Client(){

    }

    public void start(){
        setUpStream();
    }

    public void setUpInformation(LoginMessage message) {

        try {
            output.reset();
            output.writeUnshared(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setUpStream() {
        try{
            socket = new Socket(ConnectionUtil.host, ConnectionUtil.port);

            is = socket.getInputStream();
            os = socket.getOutputStream();
            input = new ObjectInputStream(is);
            output = new ObjectOutputStream(os);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ClientController(new CliView());
        //else the GuiView
    }

    public void sendMessageToServer(MessageInterface message){
        try{
            output.reset();
            output.writeUnshared(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MessageInterface receiveMessageFromServer(){
        try{
            return (MessageInterface) input.readUnshared();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LoginMessage getUpInformation() {
        try {
            return (LoginMessage) input.readUnshared();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}

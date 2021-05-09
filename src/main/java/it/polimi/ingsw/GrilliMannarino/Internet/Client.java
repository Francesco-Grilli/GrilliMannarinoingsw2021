package it.polimi.ingsw.GrilliMannarino.Internet;

import it.polimi.ingsw.GrilliMannarino.CliView;
import it.polimi.ingsw.GrilliMannarino.ClientController;
import it.polimi.ingsw.GrilliMannarino.Message.MessageInterface;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private Socket socket;
    private ClientController controller;
    private InputStream is;
    private OutputStream os;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public Client(ClientController controller){
        this.controller = controller;
    }

    public void start(){
        setUpStream();
        setUpInformation();
    }

    private void setUpInformation() {



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
        if(args[0]=="CLI")
            new ClientController(new CliView());
        //else the GuiView
    }

    public void sendMessageToServer(MessageInterface message){
        try{
            output.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MessageInterface receiveMessageFromServer(){
        try{
            return (MessageInterface) input.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}

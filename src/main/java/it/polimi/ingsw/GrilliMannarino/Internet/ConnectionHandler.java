package it.polimi.ingsw.GrilliMannarino.Internet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler implements Runnable{

    private final Server server;
    private ServerSocket serverSocket;

    public ConnectionHandler(Server server){
        this.server = server;
    }

    @Override
    public void run() {

        try{
            serverSocket = new ServerSocket(ConnectionUtil.port);

            while(!Thread.currentThread().isInterrupted()){

                Socket socket = serverSocket.accept();
                server.createNewClient(socket);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

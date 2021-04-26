package it.polimi.ingsw.GrilliMannarino.Internet;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler implements  Runnable{

    private final Socket socket;
    private final Server server;
    private final int playerId;
    private final String nickName;

    BufferedReader input = null;
    InputStream is = null;
    InputStreamReader in = null;

    private DataOutputStream output;


    public ClientHandler(Socket socket, Server server, int playerId, String nickName){
        this.server=server;
        this.socket=socket;
        this.playerId=playerId;
        this.nickName=nickName;
    }

    @Override
    public void run() {
        setUpStream();
    }

    private void setUpStream(){
        try {
            is = socket.getInputStream();
            in = new InputStreamReader(is);
            input = new BufferedReader(in);
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        try {
            output.write(message.getBytes(StandardCharsets.UTF_8));
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

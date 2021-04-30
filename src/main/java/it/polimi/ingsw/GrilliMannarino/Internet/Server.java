package it.polimi.ingsw.GrilliMannarino.Internet;

import it.polimi.ingsw.GrilliMannarino.Game;
import it.polimi.ingsw.GrilliMannarino.Message.MessageInterface;
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
    private ArrayList<String> nickNameList = new ArrayList<>();
    private boolean keepGoing = true;
    private Game game = new Game();

    private void start(){
        try {
            serverSocket = new ServerSocket(ConnectionUtil.port, 4);
        } catch (IOException e) {
            e.printStackTrace();
        }
        do{
            try {
                Socket socket = serverSocket.accept();

                Thread thread = new Thread(getStartingInformation(socket));
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }while(keepGoing && handlerList.size()<4);
    }




    public void setKeepGoing(boolean keepGoing) {
        this.keepGoing = keepGoing;
    }




    public static void main(String[] args) {

        new Server().start();

    }




    private int getRandomId(){
        int playerId;
        do{
            playerId = (int) (Math.random()*1000 +1);
        }while(handlerList.containsKey(playerId));

        return playerId;
    }




    private ClientHandler getStartingInformation(Socket socket){
        BufferedReader in = null;
        OutputStream out = null;
        InputStream is = null;
        InputStreamReader isr = null;

        try {
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            in = new BufferedReader(isr);
            out = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


        JSONObject obj = new JSONObject();
        JSONParser parser = new JSONParser();
        try {
            String str = in.readLine();
            obj = (JSONObject) parser.parse(str);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        while((nickNameList.contains(obj.get("nickName").toString()))){
            obj.clear();
            obj.put("nickName", "Error");

            try {
                out.write(obj.toString().getBytes(StandardCharsets.UTF_8));
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            obj.clear();
            try {
                String str = in.readLine();
                obj = (JSONObject) parser.parse(str);
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }

        int playerId = getRandomId();
        ClientHandler client = new ClientHandler(socket, this, playerId, obj.get("nickName").toString());
        nickNameList.add(obj.get("nickName").toString());
        handlerList.put(playerId, client);

        obj.clear();
        obj.put("nickName", "OK");
        try {
            out.write(obj.toString().getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            is.close();
            isr.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return client;
    }



    public void broadCast(String message){
        for (Integer c : handlerList.keySet()){
            handlerList.get(c).sendMessage(message);
        }
    }

    public void sendMessageTo(Integer playerId, MessageInterface message){
        handlerList.get(playerId).sendMessage(message);
    }

}

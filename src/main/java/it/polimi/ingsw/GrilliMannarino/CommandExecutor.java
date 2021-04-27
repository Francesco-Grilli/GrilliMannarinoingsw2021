package it.polimi.ingsw.GrilliMannarino;

import java.util.HashMap;

public class CommandExecutor {

    private HashMap<String, Command> commands;

    public CommandExecutor(){
        commands = new HashMap<>();
        commands.put("login", new Login());
    }

}

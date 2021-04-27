package it.polimi.ingsw.GrilliMannarino;

import org.json.simple.JSONObject;

public interface Command {

    public void execute(JSONObject jsonObject);

}

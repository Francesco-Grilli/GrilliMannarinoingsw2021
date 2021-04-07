package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Resource;

import java.util.HashMap;

public abstract class LeaderCard {
    protected HashMap<Resource, Integer> resourcePrice;
    protected int[][] cardPrice;
    protected Resource definedResource;
    protected int value = 0;

    public abstract int getValue();

    public abstract void execute();
}

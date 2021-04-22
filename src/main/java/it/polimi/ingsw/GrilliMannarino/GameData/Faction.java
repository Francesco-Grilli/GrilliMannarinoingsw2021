package it.polimi.ingsw.GrilliMannarino.GameData;


import java.util.Locale;

public enum Faction {
    GREEN, PURPLE, YELLOW, BLUE, NONE;

    public Faction parseFaction(String  faction){
        if(faction == null){
            return Faction.NONE;
        }else{
            switch (faction.toUpperCase(Locale.ROOT)){
                case "GREEN":
                    return Faction.GREEN;
                case "PURPLE":
                    return Faction.PURPLE;
                case "YELLOW":
                    return Faction.YELLOW;
                case "BLUE":
                    return Faction.BLUE;
                default:
                    return Faction.NONE;
            }
        }
    }
}

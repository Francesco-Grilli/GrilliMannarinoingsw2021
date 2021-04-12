package it.polimi.ingsw.GrilliMannarino.GameData;

public enum Row {
    FIRST(1), SECOND(2), THIRD(3), FOURTH(4), FIFTH(5);

    private final int value;

    Row(int value) {
        this.value = value;
    }

    public int getValue(){ return value; }
}

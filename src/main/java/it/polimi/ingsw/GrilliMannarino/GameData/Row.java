package it.polimi.ingsw.GrilliMannarino.GameData;


import java.util.ArrayList;

public enum Row {
    FIRST(1, 1), SECOND(2, 2), THIRD(3, 3), FOURTH(4, 2), FIFTH(5, 2);

    private final int value;
    private final int maxValue;
    private static Row[] arr = values();

    Row(int value, int maxValue) {
        this.value = value;
        this.maxValue = maxValue;

    }

    public int getValue(){ return value; }

    public int getMaxValue(){return maxValue;}

    public static Row getNextValue(Row r){
        return arr[((r.getValue())%arr.length)];
    }

    public static ArrayList<Row> orderedRow(){
        ArrayList<Row> toReturn = new ArrayList<>();
        toReturn.add(Row.FIRST);
        toReturn.add(Row.SECOND);
        toReturn.add(Row.THIRD);
        toReturn.add(Row.FOURTH);
        toReturn.add(Row.FIFTH);
        return toReturn;
    }
}

package it.polimi.ingsw.GrilliMannarino;

public class PopeLineSingle extends PopeLine{


    private PopeLine lorenzo;

    public PopeLineSingle(){
        lorenzo = new PopeLine();
    }

    public boolean addLorenzoFaith(){
        return lorenzo.addFaith();
    }

    public boolean doubleAddLorenzoFaith(){
        return lorenzo.addFaith() || lorenzo.addFaith();
    }

    @Override
    public boolean checkPopeFaith() {
        return super.checkPopeFaith();
    }

    public int getLorenzoFaith(){
        return lorenzo.getFaith();
    }
}

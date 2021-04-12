package it.polimi.ingsw.GrilliMannarino;

public class PopeLineSingle extends PopeLine{


    private PopeLine lorenzo;

    public PopeLineSingle(){
        lorenzo = new PopeLine();
    }

    /**
     * add one point of faith to Lorenzo il Magnifico
     * @return true if adding a new point of faith trigger a Pope's favor
     */
    public boolean addLorenzoFaith(){
        return lorenzo.addFaith();
    }

    /**
     * add two point of faith to Lorenzo il Magnifico
     * @return true if adding a new point of faith trigger a Pope's favor
     */
    public boolean doubleAddLorenzoFaith(){
        boolean check=false;
        if(lorenzo.addFaith())
            check=true;
        if(lorenzo.addFaith())
            check=true;
        return check;
    }

    /**
     * @return the faith's value of Lorenzo il Magnifico
     */
    public int getLorenzoFaith(){
        return lorenzo.getFaith();
    }
}

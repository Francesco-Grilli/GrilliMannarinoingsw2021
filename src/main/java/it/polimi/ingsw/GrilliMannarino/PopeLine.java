package it.polimi.ingsw.GrilliMannarino;

public class PopeLine {

    /**
     * is the number of favor that are used in the PopeLine
     */
    protected final int NUMBERFAVOR = 3;

    /**
     * favorValue is the value of each Pope's favor
     */
    protected final int[] favorValue = new int[]{2, 3, 4};

    /**
     * trackValue is the point's value of the track
     */
    protected final int[] trackValue = new int[]{1, 2, 4, 6, 9, 12, 16, 20};

    /**
     * favorActivate is the value of score which activate the Pope's favor
     */
    protected final int[] favorActivate = new int[]{8, 16, 24};

    /**
     * last value of track in the PopeLine
     */
    protected final int finalTrack = 24;

    /**
     * the number of faith --> the position in the track
     */
    protected int faith;

    /**
     * faithSteps count if the player has reached the Pope space before or at the same time of other player
     */
    protected final boolean[] faithSteps;

    /**
     * faithChecks keep trace of the Pope's favor that has been activated
     */
    protected static final boolean[] faithChecks = new boolean[3];
    protected static int maxFaith;


    public PopeLine(){
        faith = 0;
        faithSteps = new boolean[NUMBERFAVOR];
        maxFaith = 0;
    }

    /**
     * addFaith is used to add a new faith point
     * @return return true if adding a new point faith trigger a Pope's favor
     */
    public boolean addFaith(){
        if(faith<finalTrack)
            faith++;
        if(faith>maxFaith)
            maxFaith=faith;
        return checkAdd();
    }

    /**
     * getLastFaith calculate the index of the last Pope's favor that has been activated
     * @return the first index of the boolean that is not true in faithChecks
     */
    private int getLastFaith(){
        int index=0;
        for(boolean f : faithChecks){
            if(f)
                index++;
        }
        return index;   //it can be increased to 3 out of bound should use exception?
    }


    /**
     * checkPopeFaith check if, after an increment of faith, a new Pope's favor is triggered
     * @return true if has been activated a new Pope's favor
     */
    private boolean checkAdd(){
        int index = getLastFaith();
        if(index<=2){
            return faith >= favorActivate[index];
        }
        return false;
    }

    /**
     * checkPopeFaith is called by the Board if anyone has returned true when has been added a new popeFaith
     * @return true if the player has triggered the Pope's favor, false if the favor has been discarded
     */
    public boolean checkPopeFaith(){
        int index = getLastFaith();
        if(faith >= favorActivate[index]-index-3) {
            faithSteps[index] = true;
            return true;
        }
        return false;
    }

    /**
     * updateChecks is called when at least one addFaith return true. It update the faithChecks
     */
    public static void updateChecks(){
        int index=0;
        for(boolean f : faithChecks){
            if(f)
                index++;
        }
        faithChecks[index]=true;
    }

    /**
     * getpoints calculate the amount of points from the Pope's favors that has been activated and not discarded and from the
     * track of faith position
     * @return the amount of points
     */
    public int getPoints(){
        return getFavorPoints()+getTrackPoint();
    }

    private int getTrackPoint(){
        if(faith<3)
            return 0;
        return trackValue[(faith/3)-1];
    }

    private int getFavorPoints(){
        int points = 0;
        for(int i=0; i<NUMBERFAVOR; i++){
            if(faithSteps[i])
                points += favorValue[i];
        }

        return points;
    }

    /**
     * @return the value of faith
     */
    public int getFaith() {
        return faith;
    }

    /**
     * @return the faithSteps, private to every single object of PopeLine
     */
    public boolean[] getFaithSteps() {
        return faithSteps;
    }

    /**
     * @return the faithChecks, static array global to all object of PopeLine
     */
    public static boolean[] getFaithChecks() {
        return faithChecks;
    }

    public static void reset(){
        for(int i=0; i<3; i++)
            faithChecks[i]=false;
    }


}

package it.polimi.ingsw.GrilliMannarino;

public class PopeLine {

    /**
     * NUMBERFAVOR is the number of favor that are used in the PopeLine
     */
    private final int NUMBERFAVOR = 3;

    /**
     * favorValue is the value of each Pope's favor
     */
    private final int[] favorValue = new int[]{2, 3, 4};

    /**
     * favorActivate is the value of score which activate the Pope's favor
     */
    private final int[] favorActivate = new int[]{5, 12, 19};
    private int faith;

    /**
     * faithSteps count if the player has reached the Pope space before or at the same time of other player
     */
    private boolean[] faithSteps;

    /**
     * faithChecks keep trace of the Pope's favor that has been activated
     */
    private static boolean[] faithChecks = new boolean[3];
    private int maxFaith;


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
        faith++;
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
     * checkPopeFaith check if after an increment of faith a new Pope's favor is triggered
     * @return true if has been activated a new Pope's favor
     */
    private boolean checkAdd(){
        int index = getLastFaith();
        if(faith >= favorActivate[index])
            return true;
        return false;
    }

    /**
     * checkPopeFaith is called by the Board if anyone ha return true when has been added a new pointFaith
     * @return true if the player has triggered the Pope's favor, false if the favor has been discarded
     */
    public boolean checkPopeFaith(){
        int index = getLastFaith();
        if(faith >= favorActivate[index]) {
            faithSteps[index] = true;
            return true;
        }
        return false;
    }

    /**
     * updateChecks is called when at least one addFaith return true
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
     * calculate the amount of points from the Pope's favors that has been activated and not discarded
     * @return the amount of points
     */
    public int getPoints(){
        int points = 0;
        for(int i=0; i<NUMBERFAVOR; i++){
            if(faithSteps[i])
                points += favorValue[i];
        }
        return points;
    }


    public int getFaith() {
        return faith;
    }

    public boolean[] getFaithSteps() {
        return faithSteps;
    }

    public static boolean[] getFaithChecks() {
        return faithChecks;
    }

    public static void reset(){
        for(int i=0; i<3; i++)
            faithChecks[i]=false;
    }


}

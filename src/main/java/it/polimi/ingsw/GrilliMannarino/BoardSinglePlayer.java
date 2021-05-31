package it.polimi.ingsw.GrilliMannarino;

public class BoardSinglePlayer extends Board{

    private PopeLineSingle popelineLorenzo;
    private CardMarketBoardInterfaceSingle cardMarketSingle;

    public BoardSinglePlayer(Player player, CardMarketBoardInterfaceSingle cardMarket, MarbleMarketBoardInterface marbleMarket) {
        super(player, cardMarket, marbleMarket);
        this.popelineLorenzo = new PopeLineSingle();
        this.popeLine = popelineLorenzo;
    }

    /**
     * adds one point of faith to the lorenzo counter in the popelineSinge during a single player match
     * @return returns if adding the said faith points triggers a pope action
     */
    public boolean addLorenzoFaith(){
        try{
            return popelineLorenzo.addLorenzoFaith();
        }catch(ClassCastException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * adds two point of faith to the lorenzo counter in the popelineSinge during a single player match
     * @return returns if adding the said faith points triggers a pope action
     */
    public boolean doubleAddLorenzoFaith(){
        try{
            return popelineLorenzo.doubleAddLorenzoFaith();
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * returns the faith points of lorenzo in a single player game
     * @return the points
     */
    public int getLorenzoFaith(){
        try{
            return popelineLorenzo.getLorenzoFaith();
        }catch(ClassCastException e){
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * checks if the popeline single can activate a pope favor due to lorenzo's position
     * @return
     */
    public boolean checkLorenzoPopeFaith(){
        return popelineLorenzo.checkLorenzoPopeFaith();
    }

    /**
     * checkPopeFaith is called by the Board if anyone has returned true when has been added a new popeFaith
     * @return true if the player has triggered the Pope's favor, false if the favor has been discarded
     */
    public boolean checkPopeFaith(){
        return popelineLorenzo.checkPopeFaith();
    }

}

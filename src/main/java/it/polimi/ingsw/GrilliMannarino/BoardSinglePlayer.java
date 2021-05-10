package it.polimi.ingsw.GrilliMannarino;

public class BoardSinglePlayer extends Board{

    private PopeLineSingle popelineLorenzo;
    private CardMarketBoardInterfaceSingle cardMarketSingle;

    public BoardSinglePlayer(Player player, CardMarketBoardInterfaceSingle cardMarket, MarbleMarketBoardInterface marbleMarket) {
        super(player, cardMarket, marbleMarket);
        this.popelineLorenzo = new PopeLineSingle();
        this.popeLine = popelineLorenzo;
    }

    public boolean addLorenzoFaith(){
        try{
            return ((PopeLineSingle) popeLine).addLorenzoFaith();
        }catch(ClassCastException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean doubleAddLorenzoFaith(){
        try{
            return ((PopeLineSingle) popeLine).doubleAddLorenzoFaith();
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return false;
    }

    public int getLorenzoFaith(){
        try{
            return ((PopeLineSingle) popeLine).getLorenzoFaith();
        }catch(ClassCastException e){
            e.printStackTrace();
        }
        return -1;
    }

}

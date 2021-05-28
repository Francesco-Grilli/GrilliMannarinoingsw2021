package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.Message.MarbleMarketMessage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class ResourcesController implements SmallController{


    public ImageView resource1;
    public ImageView resource2;
    public ImageView resource3;
    public ImageView resource4;
    public ImageView arrow1;
    public ImageView arrow2;
    public ImageView arrow3;
    public ImageView arrow4;
    public Button confirmButton;

    private int m1 =0;
    private int m2 =0;
    private int m3 =0;
    private int m4 =0;

    private boolean viewMarble = false;
    private boolean selectStartingResource = false;
    private ArrayList<Marble> marbleArray1;
    private ArrayList<Marble> marbleArray2;
    private ArrayList<Marble> marbleArray3;
    private ArrayList<Marble> marbleArray4;
    private ArrayList<ArrayList<Marble>> returnedMarble;

    private ArrayList<Resource> resourceToReturn = new ArrayList<>();

    private GUIView view;



    @Override
    public void setView(GUIView view) {
        this.view = view;
        disableArrow();
    }

    @Override
    public void errorMessage(String header, String context) {

    }

    public void setReturnedMarble(ArrayList<ArrayList<Marble>> returnedMarble) {
        this.returnedMarble = returnedMarble;
        int k=1;
        for(ArrayList<Marble> arr : returnedMarble){
            if(arr.size()==1){
                placeImage(k, arr.get(0).toString(), false, arr);
            }
            else{
                placeImage(k, arr.get(0).toString(), true, arr);
            }
            k++;
        }
    }

    private void placeImage(int pos, String marble, boolean multiple, ArrayList<Marble> array){
        if(pos==1){
            resource1.setImage(new Image("image/" + marble + ".png"));
            resource1.setDisable(false);
            if(multiple) {
                arrow1.setVisible(true);
                arrow1.setDisable(false);
            }
            this.marbleArray1 = array;
        }
        else if(pos ==2){
            resource2.setImage(new Image("image/" + marble + ".png"));
            resource2.setDisable(false);
            if(multiple) {
                arrow2.setVisible(true);
                arrow2.setDisable(false);
            }
            this.marbleArray2 = array;
        }
        else if(pos==3){
            resource3.setImage(new Image("image/" + marble + ".png"));
            resource3.setDisable(false);
            if(multiple) {
                arrow3.setVisible(true);
                arrow3.setDisable(false);
            }
            this.marbleArray3 = array;
        }
        else if(pos==4){
            resource4.setImage(new Image("image/" + marble + ".png"));
            resource4.setDisable(false);
            if(multiple) {
                arrow4.setVisible(true);
                arrow4.setDisable(false);
            }
            this.marbleArray4 = array;
        }
    }


    public void selectArrow1(MouseEvent mouseEvent) {
        m1++;
        Marble marbleToSet = marbleArray1.get(m1 % (marbleArray1.size()));
        resource1.setImage(new Image("image/" + marbleToSet.toString() + ".png"));
        resource1.setVisible(true);
        resource1.setDisable(false);
    }

    public void selectArrow2(MouseEvent mouseEvent) {
        m2++;
        Marble marbleToSet = marbleArray2.get(m2%(marbleArray2.size()));
        resource2.setImage(new Image("image/" + marbleToSet.toString() + ".png"));
        resource2.setVisible(true);
        resource2.setDisable(false);
    }

    public void selectArrow3(MouseEvent mouseEvent) {
        m3++;
        Marble marbleToSet = marbleArray3.get(m3%(marbleArray3.size()));
        resource3.setImage(new Image("image/" + marbleToSet.toString() + ".png"));
        resource3.setVisible(true);
        resource3.setDisable(false);
    }

    public void selectArrow4(MouseEvent mouseEvent) {
        m4++;
        Marble marbleToSet = marbleArray4.get(m4%(marbleArray4.size()));
        resource4.setImage(new Image("image/" + marbleToSet.toString() + ".png"));
        resource4.setVisible(true);
        resource4.setDisable(false);
    }

    private void disableArrow(){
        arrow1.setVisible(false);
        arrow1.setDisable(true);
        arrow2.setVisible(false);
        arrow2.setDisable(true);
        arrow3.setVisible(false);
        arrow3.setDisable(true);
        arrow4.setVisible(false);
        arrow4.setDisable(true);
    }

    public void selectResource1(MouseEvent mouseEvent) {
        Marble toReturn = marbleArray1.get(m1%marbleArray1.size());
        resourceToReturn.add(Marble.getResource(toReturn));
        resource1.setDisable(true);
        resource1.setOpacity(0.5);
        arrow1.setDisable(true);
        arrow1.setVisible(false);
    }

    public void selectResource2(MouseEvent mouseEvent) {
        Marble toReturn = marbleArray2.get(m2%marbleArray2.size());
        resourceToReturn.add(Marble.getResource(toReturn));
        resource2.setDisable(true);
        resource2.setOpacity(0.5);
        arrow2.setDisable(true);
        arrow2.setVisible(false);
    }

    public void selectResource3(MouseEvent mouseEvent) {
        Marble toReturn = marbleArray3.get(m3%marbleArray3.size());
        resourceToReturn.add(Marble.getResource(toReturn));
        resource3.setDisable(true);
        resource3.setOpacity(0.5);
        arrow3.setDisable(true);
        arrow3.setVisible(false);
    }

    public void selectResource4(MouseEvent mouseEvent) {
        Marble toReturn = marbleArray4.get(m4%marbleArray4.size());
        resourceToReturn.add(Marble.getResource(toReturn));
        resource4.setDisable(true);
        resource4.setOpacity(0.5);
        arrow4.setDisable(true);
        arrow4.setVisible(false);
    }

    public void confirmMethod(ActionEvent actionEvent) {
        if(viewMarble){
            if(resourceToReturn.size()==returnedMarble.size()){
                MarbleMarketMessage message = new MarbleMarketMessage(view.getGameId(), view.getPlayerId());
                message.setCheckReturnedResource(true);
                message.setReturnedResource(resourceToReturn);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        view.sendMessageToServer(message);
                    }
                }).start();
            }
        }
        else if(selectStartingResource){
            if(resourceToReturn.size()==returnedMarble.size()){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        view.showPlaceResourceIntoWareHouse(resourceToReturn);
                    }
                }).start();
            }
        }
    }

    public void setSelectStartingResource(boolean selectStartingResource) {
        this.selectStartingResource = selectStartingResource;
    }

    public void setViewMarble(boolean viewMarble) {
        this.viewMarble = viewMarble;
    }
}

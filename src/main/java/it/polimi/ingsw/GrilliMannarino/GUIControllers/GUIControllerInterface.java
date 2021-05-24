package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;

public interface GUIControllerInterface {

    void setScene(String scene, GUIView cont);

    void errorMessage(String header, String context);

    SmallController getActiveController();
}

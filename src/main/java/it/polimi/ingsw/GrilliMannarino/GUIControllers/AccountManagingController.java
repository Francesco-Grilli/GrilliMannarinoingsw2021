package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import it.polimi.ingsw.GrilliMannarino.Message.LoginMessage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AccountManagingController {

  GUIView controller;
  
  public TextField usernameField;
  public Label accountLabel;
  public TextField passwordField;
  public TextField confirmPasswordField;
  public Button accountButton;

  public void newAccountConfig(){
    confirmPasswordField.setDisable(false);
    confirmPasswordField.setOpacity(1);
    accountLabel.setText("Create a New Account");
    accountButton.setText("Conferma");
    accountButton.setOnAction(t->newAccountCreation());
  }

  public void loginConfig(){
    confirmPasswordField.setDisable(true);
    confirmPasswordField.setOpacity(0);
    accountLabel.setText("Log into Your Account");
    accountButton.setText("Login");
    accountButton.setOnAction(t->loginAccount());
  }

  public void newAccountCreation(){
    if (passwordField.getText().equals(confirmPasswordField.getText())){
      if(!usernameField.getText().equals("")){
        controller.sendInformationToServer(usernameField.getText(), passwordField.getText(), true);
      }
      else{
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Error with Username");
        error.setContentText("Username is empty");
        error.show();
      }
    }
    else {
      Alert error = new Alert(Alert.AlertType.ERROR);
      error.setHeaderText("Error with Password");
      error.setContentText("The passwords do not match");
      error.show();
    }
  }

  public void errorDisplay(String message){
    Alert error = new Alert(Alert.AlertType.ERROR);
    error.setHeaderText(message);
    error.show();
  }

  public void loginAccount(){

  }
}

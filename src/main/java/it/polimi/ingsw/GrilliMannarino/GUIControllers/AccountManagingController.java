package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
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

  }

  public void loginAccount(){

  }
}

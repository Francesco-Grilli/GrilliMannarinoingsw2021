package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AccountManagingController {
  
  public TextField usernameField;
  public Label accountLabel;
  public TextField passwordField;
  public TextField confirmPasswordField;
  public Button accountButton;

  public void newAccountConfig(){
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

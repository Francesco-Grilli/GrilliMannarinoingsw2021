package it.polimi.ingsw.GrilliMannarino.GUIControllers;

import it.polimi.ingsw.GrilliMannarino.GUIView;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AccountManagingController implements SmallController{

  GUIView controller;
  
  public TextField usernameField;
  public Label accountLabel;
  public TextField passwordField;
  public TextField confirmPasswordField;
  public Button accountButton;

  @Override
  public void setView(GUIView view){
    this.controller = view;
  }

  @Override
  public void errorMessage(String header, String context) {
    Alert error = new Alert(Alert.AlertType.INFORMATION);
    error.setHeaderText(header);
    error.setContentText(context);
    error.show();
  }

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
        errorMessage("Error with Username", "Username is empty");
      }
    }
    else {
      errorMessage("Error with Password", "The passwords do not match");
    }
  }


  public void loginAccount(){
    if(!usernameField.getText().equals(""))
      controller.sendInformationToServer(usernameField.getText(), passwordField.getText(), false);
    else
      errorMessage("Error with username", "Username is empty");
    }
}

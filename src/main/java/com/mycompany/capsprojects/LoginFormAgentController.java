package com.mycompany.capsprojects;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginFormAgentController {

    @FXML
    private Button agentLoginButton;
    @FXML
    private Text agentCreateAccount;
    @FXML
    private Text forgotPasswordAgent;
    
    @FXML
    private void goToDashboard() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomePage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        Stage stage = (Stage) agentLoginButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void goToAgentSignIn() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateAccountAgent.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        Stage stage = (Stage) agentCreateAccount.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void goToForgotPasswordPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ForgotPassword.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        Stage stage = (Stage) forgotPasswordAgent.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}


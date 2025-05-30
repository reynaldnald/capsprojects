package com.mycompany.capsprojects;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginFormAdminController {

    @FXML
    private Button adminLoginButton;
    @FXML
    private Text adminCreateAccount;
    @FXML
    private Text forgotPasswordAdmin;
    
    @FXML
    private void goToDashboard() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomePage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        Stage stage = (Stage) adminLoginButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void goToAdminSignIn() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateAccountAdmin.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        Stage stage = (Stage) adminCreateAccount.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void goToForgotPasswordPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ForgotPassword.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        Stage stage = (Stage) forgotPasswordAdmin.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}


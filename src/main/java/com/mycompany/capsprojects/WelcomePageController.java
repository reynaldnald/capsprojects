package com.mycompany.capsprojects;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WelcomePageController {
    
    @FXML
    private Button adminButton;
    @FXML
    private Button agentButton;
    
    @FXML
    private void goToAdminLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginFormAdmin.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        Stage stage = (Stage) adminButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void goToAgentLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginFormAgent.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        Stage stage = (Stage) agentButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}

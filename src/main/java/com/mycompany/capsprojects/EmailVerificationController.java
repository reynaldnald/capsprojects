/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.capsprojects;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author TUF GAMING
 */
public class EmailVerificationController {

    @FXML
    private TextField otpTxt;
    
    @FXML
    private Button verifyButton;
    
    private PendingAdmin pendingAdmin;

    public void setPendingAdmin(PendingAdmin admin) {
        this.pendingAdmin = admin;
    }
    
    @FXML
    private void verifyOTP() {
        String otp = otpTxt.getText();

        if (otp.equals("1234")) {
            new CreateAccountAdminController().adminRegisterService(
                    pendingAdmin.getName(),
                    pendingAdmin.getId(),
                    pendingAdmin.getEmail(),
                    pendingAdmin.getPassword()
            );
            
        } else {
            showAlert(AlertType.ERROR, "OTP is invalid or expired");
        }

    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Registration");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

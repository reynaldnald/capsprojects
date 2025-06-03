package com.mycompany.capsprojects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    TextField adminNumberTxt, passwordTxt;

    String apiurl = "http://localhost:4500/loginAdmin";

    @FXML
    private void goToDashboard(String adminNo, String pass) throws IOException {

        try {
            URL url = new URL(apiurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestProperty("Content-Type", "application/json");
            
            String json = String.format(
                    "{\"id_number\":\"%s\", \"password\":\"%s\"}",
                    adminNo,pass);
            
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();
            InputStream is = (status == 200) ? conn.getInputStream() : conn.getErrorStream();

            StringBuilder response = new StringBuilder();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
            }
            
            if(status == 200) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) adminLoginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            }
            else {
                showAlert(AlertType.ERROR, "Invalid ID Number or Password. Please try again.");
            }
        } catch (Exception ex) {
                showAlert(AlertType.ERROR, "Error occured. Please try again.");
        }
        
    }

    @FXML
    private void signIn() throws IOException {
        String adminNumber = adminNumberTxt.getText();
        String password = passwordTxt.getText();

        if (adminNumber.isBlank() || password.isBlank()) {
            showAlert(AlertType.ERROR, "All fields are required");
            return;
        }

        goToDashboard(adminNumber, password);
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

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Log in");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

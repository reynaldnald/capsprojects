package com.mycompany.capsprojects;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class CreateAccountAgentController {
    @FXML
    TextField agentNameTxt, agentNumberTxt, emailAgentTxt, passwordAgentTxt, confirmPassAgentTxt;

    String apiurl  = "http://localhost:4500/registerAgent";
    @FXML
    public void signUpUser() {
        String name = agentNameTxt.getText();
        String idnumber = agentNumberTxt.getText();
        String email = emailAgentTxt.getText();
        String password = passwordAgentTxt.getText();
        String confirmPass = confirmPassAgentTxt.getText();

        if (name.isBlank() || idnumber.isBlank() || email.isBlank() || password.isBlank()) {
            showAlert(Alert.AlertType.ERROR, "Please fill in all fields");
            return;
        }
        if (!password.equals(confirmPass)) {
            showAlert(Alert.AlertType.ERROR, "Password doesn't match");
            return;
        }
        agentRegisterService(name, idnumber, email, password);

    }

    public void agentRegisterService(String name, String idnumber, String email, String password) {
        try {
            String json = String.format(
                    "{\"name\":\"%s\", \"id_number\":\"%s\", \"email\":\"%s\", \"password\":\"%s\"}",
                    name, idnumber, email, password
            );
            URL url = new URL(apiurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            
            
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
            switch (status) {
                case 200 -> showAlert(AlertType.CONFIRMATION, "Account Created Successfully!");
                case 400 -> showAlert(AlertType.ERROR, "ID number not found or invalid!");
                default -> showAlert(AlertType.ERROR, "Error Occured. Please try again later");
            }
            System.out.println("Response from server: " + response.toString());
            
        } catch (Exception ex) {
            System.out.println(ex);
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


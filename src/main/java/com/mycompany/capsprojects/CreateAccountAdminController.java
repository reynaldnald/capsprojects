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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
public class CreateAccountAdminController {

    @FXML
    TextField adminNameTxt, adminNumberTxt, emailAdminTxt, passwordAdminTxt, confirmPassAdminTxt;

    String apiurl  = "http://localhost:4500/registerAdmin";
    @FXML
    public void signUpUser() {
        String name = adminNameTxt.getText();
        String idnumber = adminNumberTxt.getText();
        String email = emailAdminTxt.getText();
        String password = passwordAdminTxt.getText();
        String confirmPass = confirmPassAdminTxt.getText();

        if (name.isBlank() || idnumber.isBlank() || email.isBlank() || password.isBlank()) {
            showAlert(AlertType.ERROR, "Please fill in all fields");
            return;
        }
        if (!password.equals(confirmPass)) {
            showAlert(AlertType.ERROR, "Password doesn't match");
            return;
        }
        adminRegisterService(name, idnumber, email, password);

    }

    public void adminRegisterService(String name, String idnumber, String email, String password) {
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
            
            System.out.println("Response from server: " + response.toString());
            
//           
//            
//            try(BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"))){
//                
//            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void showAlert(AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Registration");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}

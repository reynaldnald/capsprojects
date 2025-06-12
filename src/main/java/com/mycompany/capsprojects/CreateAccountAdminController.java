package com.mycompany.capsprojects;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CreateAccountAdminController {

    @FXML
    TextField adminNameTxt, adminNumberTxt, emailAdminTxt, passwordAdminTxt, confirmPassAdminTxt;

    @FXML
    Label emailValidatetxt;
    @FXML
    CheckBox termsCondition;

    String registerAPI = "http://localhost:4500/registerAdmin";
    String validateInputAPI = "http://localhost:4500/validate/admin";

    @FXML
    public void initialize() {
        emailAdminTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isBlank()) {
                emailValidatetxt.setText("");
                emailValidatetxt.setVisible(false);
                emailValidatetxt.setStyle("");
            } else if (!isValidEmail(newValue)) {
                emailValidatetxt.setText("Invalid email address format!");
                emailValidatetxt.setVisible(true);
                emailAdminTxt.setStyle("-fx-border-color: red;");

            } else {
                emailValidatetxt.setText("");
                emailAdminTxt.setStyle("");
                emailValidatetxt.setVisible(true);
            }
        });
    }

    @FXML
    public void signUpUser() {
        // text controllers
        String name = adminNameTxt.getText();
        String idnumber = adminNumberTxt.getText();
        String email = emailAdminTxt.getText();
        String password = passwordAdminTxt.getText();
        String confirmPass = confirmPassAdminTxt.getText();

        // Error will occur when no values in the field
        if (name.isBlank() || idnumber.isBlank() || email.isBlank() || password.isBlank()) {
            showAlert(AlertType.ERROR, "Please fill in all fields");
            return;
        }
        // Checks if the password is matched
        if (!password.equals(confirmPass)) {
            showAlert(AlertType.ERROR, "Password doesn't match");
            return;
        }
        // Validates format of email
        if (!isValidEmail(email)) {
            showAlert(AlertType.ERROR, "Invalid email address format!");
            return;
        }

        try {
            String json = String.format(
                    "{\"name\":\"%s\", \"id_number\":\"%s\", \"email\":\"%s\"}",
                    name, idnumber, email
            );
            URL url = new URL(validateInputAPI);
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
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);

            String message = jsonObject.has("message") ? jsonObject.get("message").getAsString() : "Unknown response";

            if (status == 200) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CheckOTPMessage.fxml"));
                Parent root = loader.load();

                EmailVerificationController controller = loader.getController();
                controller.setPendingAdmin(new PendingAdmin(name, idnumber, email, password));

                Stage stage = new Stage();
                stage.setTitle("Email Verification");
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                showAlert(AlertType.ERROR, message);
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }

    }

    public void adminRegisterService(String name, String idnumber, String email, String password) {
        try {
            String json = String.format(
                    "{\"name\":\"%s\", \"id_number\":\"%s\", \"email\":\"%s\", \"password\":\"%s\"}",
                    name, idnumber, email, password
            );
            URL url = new URL(registerAPI);
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
                case 200 ->
                    showAlert(AlertType.CONFIRMATION, "Account Created Successfully!");
                case 400 ->
                    showAlert(AlertType.ERROR, "ID number not found or invalid!");
                default ->
                    showAlert(AlertType.ERROR, "Error Occured. Please try again later.");
            }
            System.out.println("Response from server: " + response.toString());

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    // Toast and alert box
    private void showAlert(AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Registration");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}

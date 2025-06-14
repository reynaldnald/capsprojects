/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.capsprojects;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author TUF GAMING
 */
public class EmailVerificationController {

    @FXML
    private TextField otpTxt;

    @FXML
    private Button verifyButton;

    @FXML
    private final Gson gson = new Gson();

    private PendingAdmin pendingAdmin;

    public void setPendingAdmin(PendingAdmin admin) {
        this.pendingAdmin = admin;
    }

    @FXML
    private void verifyOTP() {
        String otp = otpTxt.getText().trim();

        if (otp.isEmpty()) {
            showAlert(AlertType.ERROR, "Please enter the OTP!");
            return;
        }

        JsonObject requestBody = new JsonObject();

        requestBody.addProperty("name", pendingAdmin.getName());
        requestBody.addProperty("email", pendingAdmin.getEmail());
        requestBody.addProperty("password", pendingAdmin.getPassword());
        requestBody.addProperty("id_number", pendingAdmin.getId());
        requestBody.addProperty("otp", otp);

        new Thread(() -> {
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(pendingAdmin.getApiUrl()))
                        .header("Content-Type", "application/json")
                        .POST(BodyPublishers.ofString(gson.toJson(requestBody)))
                        .build();

                HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    javafx.application.Platform.runLater(() -> {
                        try {
                            var loader = new FXMLLoader(getClass().getResource("EmailVerificationMessage.fxml"));
                            Parent root = loader.load();
                            Scene scene = new Scene(root);

                            Stage stage = (Stage) verifyButton.getScene().getWindow();
                            stage.setScene(scene);
                            stage.show();

                        } catch (IOException e) {
                            showAlert(AlertType.ERROR, "Failed to load next screen: " + e.getMessage());
                        }
                    });

                } else {
                    javafx.application.Platform.runLater(() -> {
                        JsonObject responseJson = gson.fromJson(response.body(), JsonObject.class);
                        String errorMessage = responseJson.has("message") ? responseJson.get("message").getAsString() : "Error Occured. Please Try Again!";
                        showAlert(AlertType.ERROR, errorMessage);
                    });

                }
            } catch (Exception e) {
                javafx.application.Platform.runLater(() -> {
                    showAlert(AlertType.ERROR, e.getMessage());;
                });
            }
        }).start();

    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Registration");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

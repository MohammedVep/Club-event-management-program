package com.example.clubeventmanagementprogram.controller;
import com.example.clubeventmanagementprogram.service.AuthenticationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button submitButton;
    @FXML private Button registerButton;
    private AuthenticationService authService;

    public void initialize() {
        authService = new AuthenticationService();
        setupButtonActions();
    }

    private void setupButtonActions() {
        submitButton.setOnAction(event -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (authService.authenticate(username, password)) {
                try {
                    // Load the home screen's FXML file.
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/Users/mohammedvepari/IdeaProjects/Club-event-management-program/src/main/resources/com/example/clubeventmanagementprogram/home-view.fxml")); // Replace with the path to your FXML file.
                    Parent homeSceneParent = loader.load();
                    Scene homeScene = new Scene(homeSceneParent);

                    //Get the current stage and set the scene, then show the stage.
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(homeScene);
                    window.show();
                } catch (IOException e) {
                    // Here you should handle any exceptions that occur.
                    e.printStackTrace();
                }
            } else {
                // show error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText("Invalid Credentials");
                alert.setContentText("The username or password you entered is incorrect. Please try again.");
                alert.showAndWait();

                // clear fields
                usernameField.clear();
                passwordField.clear();
            }
        });
        registerButton.setOnAction(event -> {
            try {
                // Load the Register screen's FXML file.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Users/mohammedvepari/IdeaProjects/Club-event-management-program/src/main/resources/com/example/clubeventmanagementprogram/register-view.fxml"));
                Parent registerSceneParent = loader.load();
                Scene registerScene = new Scene(registerSceneParent);

                // Get the current stage and set the scene, then show the stage.
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(registerScene);
                window.show();
            } catch (IOException e) {
                // Here you should handle any exceptions that occur.
                e.printStackTrace();
            }
        });
    }
}
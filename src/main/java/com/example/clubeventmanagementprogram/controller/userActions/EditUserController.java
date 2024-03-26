package com.example.clubeventmanagementprogram.controller.userActions;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class EditUserController {

    @FXML
    private GridPane editGridPane;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private String originalUsername; // Store the original username to check if it's changed

    public void initialize() {
        // Initialize the originalUsername with the initial value from the usernameField
        originalUsername = usernameField.getText();
    }

    @FXML
    void handleSaveUser(ActionEvent event) {
        // Check if the username has changed
        if (!usernameField.getText().equals(originalUsername)) {
            // Perform validation or check logic for username change here
            // For demonstration purposes, let's just show an alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Username Changed");
            alert.setHeaderText(null);
            alert.setContentText("Username cannot be changed!");
            alert.showAndWait();
            return; // Exit method without saving if username is changed
        }

        // Save user information logic here
        // For demonstration purposes, let's just show an alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User Information Saved");
        alert.setHeaderText(null);
        alert.setContentText("User information saved successfully!");
        alert.showAndWait();

        // You would typically have code here to save the edited user information to the system/database
    }

    @FXML
    void loadUserScene(ActionEvent event) {
        // Implement logic to load the previous user scene here
        // For demonstration purposes, let's just close the current stage
        cancelButton.getScene().getWindow().hide();
    }
}

package com.example.clubeventmanagementprogram.controller.userActions;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class DeleteUserController {

    @FXML
    private GridPane deleteGridPane;

    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;

    @FXML
    void handleDeleteUser(ActionEvent event) {
        // Implement deletion logic here
        // For demonstration purposes, let's just show an alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User Deletion");
        alert.setHeaderText(null);
        alert.setContentText("User deleted successfully!");
        alert.showAndWait();

        // You would typically have code here to delete the user from the system/database
    }

    @FXML
    void loadUserScene(ActionEvent event) {
        // Implement logic to load the previous user scene here
        // For demonstration purposes, let's just close the current stage
        noButton.getScene().getWindow().hide();
    }
}

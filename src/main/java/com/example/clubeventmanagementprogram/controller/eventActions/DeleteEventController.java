package com.example.clubeventmanagementprogram.controller.clubActions;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class DeleteEventController {

    @FXML
    private GridPane deleteEventGridPane;

    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;

    @FXML
    void handleDeleteEvent(ActionEvent event) {
        // Implement deletion logic here
        // For demonstration purposes, let's just show an alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Event Deletion");
        alert.setHeaderText(null);
        alert.setContentText("Event deleted successfully!");
        alert.showAndWait();

        // You would typically have code here to delete the event from the system/database
    }

    @FXML
    void loadEventScene(ActionEvent event) {
        // Implement logic to load the previous event scene here
        // For demonstration purposes, let's just close the current stage
        noButton.getScene().getWindow().hide();
    }
}

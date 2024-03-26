package com.example.clubeventmanagementprogram.controller.eventActions;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;

public class EditEventController {

    @FXML
    private GridPane editEventGridPane;

    @FXML
    private TextField eventNameField;

    @FXML
    private TextField eventDescriptionField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private TextField startTimeField;

    @FXML
    private TextField endTimeField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    // You may add a method to initialize fields with existing event data

    @FXML
    void handleSaveAndLoadClubScene(ActionEvent event) {
        // Save event information logic here
        String eventName = eventNameField.getText();
        String eventDescription = eventDescriptionField.getText();
        LocalDate startDate = startDatePicker.getValue();
        String startTime = startTimeField.getText();
        String endTime = endTimeField.getText();

        // For demonstration purposes, let's just show an alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Event Information Saved");
        alert.setHeaderText(null);
        alert.setContentText("Event information saved successfully!");
        alert.showAndWait();

        // You would typically have code here to save the edited event information to the system/database
    }

    @FXML
    void loadClubScene(ActionEvent event) {
        // Implement logic to load the previous club scene here
        // For demonstration purposes, let's just close the current stage
        cancelButton.getScene().getWindow().hide();
    }
}

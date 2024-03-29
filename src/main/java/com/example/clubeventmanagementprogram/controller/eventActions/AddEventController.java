package com.example.clubeventmanagementprogram.controller.eventActions;

import com.example.clubeventmanagementprogram.dao.EventDAO;
import com.example.clubeventmanagementprogram.model.Event;
import com.example.clubeventmanagementprogram.service.EventService;
import com.example.clubeventmanagementprogram.service.EventServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class AddEventController {

    @FXML
    private TextField eventNameField;

    @FXML
    private TextField eventDescriptionField;

    @FXML
    private DatePicker eventDatePicker;

    @FXML
    private TextField startTimeField;

    @FXML
    private TextField endTimeField;

    @FXML
    Button saveButton;

    @FXML
    Button cancelButton;

    EventDAO eventDAO = new EventDAO();
    EventService eventService = new EventServiceImpl(eventDAO);

    @FXML
    public void initialize(){
        saveButton.setOnAction(event -> {
            handleSaveAction(event);
        });
        cancelButton.setOnAction(event -> {
            loadEventScene(event);
        });
    }

    @FXML
    void handleSaveAction(ActionEvent event) {
        // Save event information logic here
        try{
            String eventName = eventNameField.getText();
            String eventDescription = eventDescriptionField.getText();
            LocalDate eventDate = eventDatePicker.getValue();
            String eventStartTime = startTimeField.getText();
            String eventEndTime = endTimeField.getText();
            List<Event> eventsFromDb = eventService.getAllEvents();
            int eventId = eventsFromDb.size() + 1;
            Event newEvent = new Event(eventId, eventName, eventDescription, eventDate, eventStartTime, eventEndTime);
            eventService.addEvent(newEvent);
            Parent eventsViewRoot = FXMLLoader.load(getClass().getResource("/com/example/clubeventmanagementprogram/events-view.fxml"));
            Scene eventsViewScene = new Scene(eventsViewRoot);

            // Get the current Stage and set the scene to clubs view
            Stage currentStage = (Stage) saveButton.getScene().getWindow();
            currentStage.setScene(eventsViewScene);
        } catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    @FXML
    void loadEventScene(ActionEvent event) {
        try {
            // Load Event scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/events-view.fxml"));
            Parent root = loader.load();

            // Create a new scene and load it to the stage
            Scene scene = new Scene(root);

            // Getting the current stage
            Stage stage = (Stage) cancelButton.getScene().getWindow();

            // Setting the new scene to the stage
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

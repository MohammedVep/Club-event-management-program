package com.example.clubeventmanagementprogram.controller.eventActions;

import com.example.clubeventmanagementprogram.dao.EventDAO;
import com.example.clubeventmanagementprogram.model.Event;
import com.example.clubeventmanagementprogram.service.EventService;
import com.example.clubeventmanagementprogram.service.EventServiceImpl;
import com.example.clubeventmanagementprogram.controller.IEventUpdatable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class EditEventController {

    private Event currentEvent;

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

    private IEventUpdatable eventUpdatable;

    public void setEventUpdatable(IEventUpdatable eventUpdatable){
        this.eventUpdatable = eventUpdatable;
    }

    EventDAO eventDAO = new EventDAO();
    EventService eventService = new EventServiceImpl(eventDAO);
    public EditEventController(){

    }

    @FXML
    public void initialize(){
        cancelButton.setOnAction(event -> loadEventScene());
        saveButton.setOnAction(event -> handleSaveAction(event));
        if(currentEvent != null){
            eventNameField.setText(currentEvent.getEventName());
            eventDescriptionField.setText(currentEvent.getDescription());
            startDatePicker.setValue(currentEvent.getDate());
            startTimeField.setText(currentEvent.getStartTime());
            endTimeField.setText(currentEvent.getEndTime());
        }
    }
    public EditEventController(ObservableList<Event> events) {
        this.eventService = new EventServiceImpl(new EventDAO());
    }

    public void setCurrentEvent(Event event){
        this.currentEvent = event;
        System.out.println("Set event: " + event);
        if (event != null){
            eventNameField.setText(currentEvent.getEventName());
            eventDescriptionField.setText(currentEvent.getDescription());
            startDatePicker.setValue(currentEvent.getDate());
            startTimeField.setText(currentEvent.getStartTime());
            endTimeField.setText(currentEvent.getEndTime());
        }
    }

    @FXML
    void handleSaveAction(ActionEvent event) {
       try{
           currentEvent.setEventName(eventNameField.getText());
           currentEvent.setDescription(eventDescriptionField.getText());
           currentEvent.setDate(startDatePicker.getValue());
           currentEvent.setStartTime(startTimeField.getText());
           currentEvent.setEndTime(endTimeField.getText());
           EventService eventService = new EventServiceImpl(eventDAO);
           eventUpdatable.updateEventTable();

           if (eventUpdatable != null){
               eventUpdatable.updateEventTable();
           }

           Parent eventViewRoot = FXMLLoader.load(getClass().getResource("/com/example/clubeventmanagementprogram/events-view.fxml"));
           Scene eventViewScene = new Scene(eventViewRoot);

           // Get the current Stage and set the scene to clubs view
           Stage currentStage = (Stage) saveButton.getScene().getWindow();
           currentStage.setScene(eventViewScene);
       } catch (IOException e){
           throw new RuntimeException(e);
       }

    }

    @FXML
    public void loadEventScene() {
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

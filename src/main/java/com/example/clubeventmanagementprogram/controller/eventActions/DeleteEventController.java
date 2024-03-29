package com.example.clubeventmanagementprogram.controller.eventActions;

import com.example.clubeventmanagementprogram.dao.EventDAO;
import com.example.clubeventmanagementprogram.model.Club;
import com.example.clubeventmanagementprogram.model.Event;
import com.example.clubeventmanagementprogram.service.EventService;
import com.example.clubeventmanagementprogram.service.EventServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DeleteEventController {

    @FXML
    private TableView<Event> eventsTable;

    @FXML
    Button yesButton;

    @FXML
    Button noButton;

    private EventDAO eventDAO = new EventDAO();
    private EventService eventService = new EventServiceImpl(eventDAO);

    public DeleteEventController(TableView<Event> eventsTable){
        this.eventsTable = eventsTable;
    }

    @FXML
    public void initalize(){
        yesButton.setOnAction(e -> deleteSelectedEvent());
        noButton.setOnAction(e -> loadEventScene());
    }
    @FXML
    void deleteSelectedEvent() {
        Event selectedEvent = eventsTable.getSelectionModel().getSelectedItem();

        if (selectedEvent != null) {
            eventService.deleteEvent(selectedEvent.getId());
            eventsTable.getItems().remove(selectedEvent);
            try {
                // Load the events view
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/events-view.fxml"));
                Parent eventsViewRoot = loader.load();
                Scene eventsViewScene = new Scene(eventsViewRoot);

                // Get the current Stage and set the scene to events view
                Stage currentStage = (Stage) noButton.getScene().getWindow();
                currentStage.setScene(eventsViewScene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("No Event selected in the table.");
        }
    }

    @FXML
    public void loadEventScene() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/events-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) noButton.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

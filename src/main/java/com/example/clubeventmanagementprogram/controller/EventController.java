package com.example.clubeventmanagementprogram.controller;

import com.example.clubeventmanagementprogram.controller.eventActions.DeleteEventController;
import com.example.clubeventmanagementprogram.controller.eventActions.EditEventController;
import com.example.clubeventmanagementprogram.model.Event;
import com.example.clubeventmanagementprogram.service.EventService;
import com.example.clubeventmanagementprogram.utils.Context;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.example.clubeventmanagementprogram.utils.Context.eventService;

public class EventController implements IEventUpdatable{

    @FXML
    private Label usernameLabel;

    @FXML
    private Label manageEventsLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Button backButton;

    @FXML
    private TableView<Event> eventTableView;

    @FXML
    private TableColumn<Event, String> eventNameColumn;

    @FXML
    private TableColumn<Event, String> eventDescriptionColumn;

    @FXML
    private TableColumn<Event, LocalDate> dateColumn;

    @FXML
    private TableColumn<Event, LocalTime> startTimeColumn;

    @FXML
    private TableColumn<Event, LocalTime> endTimeColumn;

    @FXML
    private TableColumn<Event, Boolean> checkBoxColumn;

    @FXML
    private Button addButton;                 // Place the 'Add' button below the table
    @FXML
    private Button editButton;                // Place the 'Edit' button below the table
    @FXML
    private Button deleteButton;              // Place the 'Delete' button below the table
    private ObservableList<Event> eventData = FXCollections.observableArrayList();
    private Event currentEvent;

    @Override
    public void updateEventTable(){
        eventData.clear();
        List<Event> updatedList = eventService.getAllEvents();
        eventData.addAll(updatedList);
    }




    public void initialize() {
        logoutButton.setOnAction(event -> handleLogout(event));
        addButton.setOnAction(event -> handleAddEvent(event));
        editButton.setOnAction(event -> handleEditEvent(event));
        deleteButton.setOnAction(event -> handleDeleteEvent(event));
        backButton.setOnAction(event -> handleGoBack(event));
        checkBoxColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
// Set cell factory
        checkBoxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkBoxColumn));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        eventDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        eventTableView.setItems(eventData);
        loadEventData();
    }

    private void loadEventData(){
        EventService eventService = Context.getEventService();
        List<Event> eventsFromDb = eventService.getAllEvents();
        eventData.addAll(eventsFromDb);
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage;
        try {
            // Load the login screen
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/login-view.fxml"));
            Scene loginScene = new Scene(fxmlLoader.load());

            // Get the current stage
            currentStage = (Stage) source.getScene().getWindow();
            currentStage.setTitle("Login");
            // Set the login scene to the current stage
            currentStage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleGoBack(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage currentStage;
        try {
            // Load the home screen
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/home-view.fxml"));
            Scene homeScene = new Scene(fxmlLoader.load());

            // Get the current stage
            currentStage = (Stage) source.getScene().getWindow();
            currentStage.setTitle("Main Menu");

            // Set the home scene to the current stage
            currentStage.setScene(homeScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleAddEvent(ActionEvent event) {
        Node source = (Node) event.getSource();
        try {
            // Load the add-event view
            Parent addEventRoot = FXMLLoader.load(getClass().getResource("/com/example/clubeventmanagementprogram/add-event.fxml"));
            Scene addEventScene = new Scene(addEventRoot);

            // Get the current stage and set the scene to add-event
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.setTitle("Add Event");
            currentStage.setScene(addEventScene);

        } catch(IOException e) {
            // handle the exception (print stack trace or show an alert here)
            System.err.println("Error loading Add Event page");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditEvent(ActionEvent event) {
        // Initialize it here
        currentEvent = eventTableView.getSelectionModel().getSelectedItem();

        Node source = (Node) event.getSource();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/edit-event.fxml"));
            Parent editClubRoot = loader.load();

            EditEventController editEventController = loader.getController();
            editEventController.setEventUpdatable(this);
            // Get currently selected club and pass it to the EditEventController
            if(currentEvent != null) {
                editEventController.setCurrentEvent(currentEvent);
                Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                currentStage.setTitle("Edit Event");
                currentStage.setScene(new Scene(editClubRoot));
            } else {
                // Show some error message to the user or write some error log
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Selection Error");
                alert.setHeaderText("No Event Selected");
                alert.setContentText("Please select a event to edit.");
                alert.showAndWait();
            }
        } catch(IOException e){
            System.err.println("Error loading Edit Event page");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteEvent(ActionEvent event) {
        currentEvent = eventTableView.getSelectionModel().getSelectedItem();
        if(currentEvent == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setHeaderText("No Event Selected");
            alert.setContentText("Please select a event to delete.");
            alert.showAndWait();
            return;
        }

        Node source = (Node) event.getSource();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/delete-event.fxml"));
            DeleteEventController controller = new DeleteEventController(eventTableView);
            loader.setController(controller);
            Parent deleteEventRoot = loader.load();
            Scene deleteEventScene = new Scene(deleteEventRoot);
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.setTitle("Delete Event");
            currentStage.setScene(deleteEventScene);
        } catch(IOException e){
            System.err.println("Error loading Delete Event page");
            e.printStackTrace();
        }
    }
}

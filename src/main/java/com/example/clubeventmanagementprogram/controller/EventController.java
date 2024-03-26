package com.example.clubeventmanagementprogram.controller;

import com.example.clubeventmanagementprogram.controller.clubActions.DeleteClubController;
import com.example.clubeventmanagementprogram.controller.clubActions.EditClubController;
import com.example.clubeventmanagementprogram.controller.eventActions.DeleteEventController;
import com.example.clubeventmanagementprogram.controller.eventActions.EditEventController;
import com.example.clubeventmanagementprogram.dao.EventDAO;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private Label usernameLabel;              // Display the Username at the upper left corner

    @FXML
    private Label manageEventsLabel;           // Display the 'Manage Clubs' label at the center top

    @FXML
    private Button logoutButton;

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

    private Event currentEvent;

    @Override
    public void updateEventTable(){
        eventData.clear();
        List<Event> updatedList = eventService.getAllEvents();
        eventData.addAll(updatedList);
    }

    private ObservableList<Event> eventData = FXCollections.observableArrayList();

    private void loadEventData(){
        EventService eventService = Context.getEventService();
        List<Event> eventsFromDb = eventService.getAllEvents();
        eventData.addAll(eventsFromDb);
    }

    public void initalize() {
        logoutButton.setOnAction(event -> handleLogout(event));
        addButton.setOnAction(event -> handleAddEvent(event));
        editButton.setOnAction(event -> handleEditEvent(event));
        deleteButton.setOnAction(event -> handleDeleteEvent(event));

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

            // Set the login scene to the current stage
            currentStage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleAddEvent(ActionEvent event) {
        Node source = (Node) event.getSource();
        try {
            // Load the add-club view
            Parent addEventRoot = FXMLLoader.load(getClass().getResource("/com/example/clubeventmanagementprogram/add-event.fxml"));
            Scene addEventScene = new Scene(addEventRoot);

            // Get the current stage and set the scene to add-club
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.setScene(addEventScene);

        } catch(IOException e) {
            // handle the exception (print stack trace or show an alert here)
            System.err.println("Error loading Add Club page");
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
            // Get currently selected club and pass it to the EditClubController
            if(currentEvent != null) {
                editEventController.setCurrentEvent(currentEvent);
                Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                currentStage.setScene(new Scene(editClubRoot));
            } else {
                // Show some error message to the user or write some error log
                System.err.println("No club was selected");
            }
        } catch(IOException e){
            System.err.println("Error loading Edit Club page");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteEvent(ActionEvent event) {
        Node source = (Node) event.getSource();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/delete-event.fxml"));
            DeleteEventController controller = new DeleteEventController(eventTableView);
            loader.setController(controller);
            Parent deleteEventRoot = loader.load();
            Scene deleteEventScene = new Scene(deleteEventRoot);
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.setScene(deleteEventScene);
        } catch(IOException e){
            System.err.println("Error loading Delete Club page");
            e.printStackTrace();
        }
    }
}

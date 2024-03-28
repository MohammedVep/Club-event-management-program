package com.example.clubeventmanagementprogram.controller;
import com.example.clubeventmanagementprogram.dao.EventDAO;
import com.example.clubeventmanagementprogram.model.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class EventTrackingChartController {

    EventDAO eventDao = new EventDAO();

    @FXML
    private Label trackEventsLabel;

    @FXML
    private TableView<Event> eventTable;

    @FXML
    private TableColumn<Event, String> eventNameColumn;

    @FXML
    private TableColumn<Event, String> eventDescriptionColumn;

    @FXML
    private TableColumn<Event, String> dateColumn;

    @FXML
    private TableColumn<Event, String> startTimeColumn;

    @FXML
    private TableColumn<Event, String> endTimeColumn;

    @FXML
    Button backButton;

    @FXML
    Button logoutButton;

    public void initialize() {
        backButton.setOnAction(this::handleGoBack);

        eventNameColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("eventName"));
        eventDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("description"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("date"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("endTime"));

        List<Event> events = eventDao.getTop5Events();
        eventTable.getItems().setAll(events);
    }

    @FXML
    private void handleGoBack(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage currentStage;
        try {
            // Load the home screen
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/home-view.fxml"));
            Scene loginScene = new Scene(fxmlLoader.load());

            // Get the current stage
            currentStage = (Stage) source.getScene().getWindow();

            // Set the home scene to the current stage
            currentStage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
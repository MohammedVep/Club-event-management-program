package com.example.clubeventmanagementprogram.controller;

import com.example.clubeventmanagementprogram.model.Club;
import com.example.clubeventmanagementprogram.service.ClubService;
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

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ClubController {

    @FXML
    private Label usernameLabel;              // Display the Username at the upper left corner

    @FXML
    private Label manageClubsLabel;           // Display the 'Manage Clubs' label at the center top

    @FXML
    private Button logoutButton;              // Place the 'Log Out' button at the upper right corner

    @FXML
    private TableView<Club> clubTableView;    // Display the Club details in a Table format

    @FXML
    private TableColumn<Club, String> clubNameColumn;
    @FXML
    private TableColumn<Club, Boolean> checkboxColumn;
    @FXML
    private TableColumn<Club, String> descriptionColumn;
    @FXML
    private TableColumn<Club, String> topicsColumn;
    @FXML
    private TableColumn<Club, LocalDate> dateAddedColumn;

    @FXML
    private Button addButton;                 // Place the 'Add' button below the table
    @FXML
    private Button editButton;                // Place the 'Edit' button below the table
    @FXML
    private Button deleteButton;              // Place the 'Delete' button below the table

    // Bind properties to the club data and setup Actions.
    private ObservableList<Club> clubData = FXCollections.observableArrayList();
    private void loadClubData() {
        ClubService clubService = Context.getClubService();
        List<Club> clubsFromDb = clubService.getAllClubs();
        clubData.addAll(clubsFromDb);
    }
    public void initialize() {
        // Set up `logoutButton` to call `handleLogout()`
        logoutButton.setOnAction(event -> handleLogout(event));

        // Set up add, edit, and delete buttons
        addButton.setOnAction(event -> handleAddClub(event));
        editButton.setOnAction(event -> handleEditClub());
        deleteButton.setOnAction(event -> handleDeleteClub());

        // Set up table columns to pull data from Club objects - replace methods with your getters
        clubNameColumn.setCellValueFactory(new PropertyValueFactory<>("clubName"));
        checkboxColumn.setCellValueFactory(new PropertyValueFactory<>("checkbox"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        topicsColumn.setCellValueFactory(new PropertyValueFactory<>("topics"));
        dateAddedColumn.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
        // Bind the TableView items to the ObservableList
        clubTableView.setItems(clubData);

        // Load the club data from the database
        loadClubData();
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage;
        try {
            // Load the login screen
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Users/mohammedvepari/IdeaProjects/Club-event-management-program/src/main/resources/com/example/clubeventmanagementprogram/login-view.fxml"));
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
    private void handleAddClub(ActionEvent event) {
        Node source = (Node) event.getSource();
        try {
            // Load the add-club view
            Parent addClubRoot = FXMLLoader.load(getClass().getResource("/Users/mohammedvepari/IdeaProjects/Club-event-management-program/src/main/resources/com/example/clubeventmanagementprogram/add-club.fxml"));
            Scene addClubScene = new Scene(addClubRoot);

            // Get the current stage and set the scene to add-club
            Stage currentStage = (Stage) source.getScene().getWindow(); // assuming clubNameField is valid in this context
            currentStage.setScene(addClubScene);

        } catch(IOException e) {
            // handle the exception (print stack trace or show an alert here)
            System.err.println("Error loading add-club.fxml");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditClub() {
        // Code to switch to EditClubController view...
    }

    @FXML
    private void handleDeleteClub() {
        // Code to switch to DeleteClubController view...
    }
}
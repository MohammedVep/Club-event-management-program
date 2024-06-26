package com.example.clubeventmanagementprogram.controller;

import com.example.clubeventmanagementprogram.controller.clubActions.DeleteClubController;
import com.example.clubeventmanagementprogram.controller.clubActions.EditClubController;
import com.example.clubeventmanagementprogram.model.Club;
import com.example.clubeventmanagementprogram.model.User;
import com.example.clubeventmanagementprogram.service.AuthenticationService;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.io.IOException;
import java.util.List;

import static com.example.clubeventmanagementprogram.utils.Context.clubService;

public class ClubController implements IClubUpdatable{

    private AuthenticationService authenticationService;

    @FXML
    private Label usernameLabel;              // Display the Username at the upper left corner

    @FXML
    private Button backButton;

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
    private Button addButton;                 // Place the 'Add' button below the table
    @FXML
    private Button editButton;                // Place the 'Edit' button below the table
    @FXML
    private Button deleteButton; // Place the 'Delete' button below the table

    private Club currentClub;   // declare it here

    @Override
    public void updateClubTable() {
        // Clear the old list
        clubData.clear();

        // Fetch the updated list
        List<Club> updatedList = clubService.getAllClubs();

        // Add the updated list to the observable list
        clubData.addAll(updatedList);
    }

    public ClubController() {
        this.authenticationService = new AuthenticationService();
    }

    public void updateUIAfterLogin(){
        // Set up the username label text
        User currentUser = authenticationService.getCurrentUser();
        if(currentUser != null) {
            usernameLabel.setText(currentUser.getUserName());
        } else {
            usernameLabel.setText("User");
        }
    }

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

        // Set up add, edit, delete and backButtons buttons
        addButton.setOnAction(event -> handleAddClub(event));
        editButton.setOnAction(event -> handleEditClub(event));
        deleteButton.setOnAction(event -> handleDeleteClub(event));
        backButton.setOnAction(event -> handleGoBack(event));

        // Set up table columns to pull data from Club objects
        // Set cell value factory
        checkboxColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
// Set cell factory
        checkboxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkboxColumn));
        clubNameColumn.setCellValueFactory(new PropertyValueFactory<>("clubName"));
        checkboxColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        topicsColumn.setCellValueFactory(new PropertyValueFactory<>("topics"));;
        // Bind the TableView items to the ObservableList
        clubTableView.setItems(clubData);

        // Load the club data from the database
        loadClubData();
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
    private void handleAddClub(ActionEvent event) {
        Node source = (Node) event.getSource();
        try {
            // Load the add-club view
            Parent addClubRoot = FXMLLoader.load(getClass().getResource("/com/example/clubeventmanagementprogram/add-club.fxml"));
            Scene addClubScene = new Scene(addClubRoot);

            // Get the current stage and set the scene to add-club
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.setTitle("Add Club");
            currentStage.setScene(addClubScene);

        } catch(IOException e) {
            // handle the exception (print stack trace or show an alert here)
            System.err.println("Error loading Add Club page");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditClub(ActionEvent event) {
        // Initialize it here
        currentClub = clubTableView.getSelectionModel().getSelectedItem();

        Node source = (Node) event.getSource();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/edit-club.fxml"));
            Parent editClubRoot = loader.load();

            EditClubController editClubController = loader.getController();
            editClubController.setClubUpdatable(this);
            // Get currently selected club and pass it to the EditClubController
            if(currentClub != null) {
                editClubController.setCurrentClub(currentClub);
                Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                currentStage.setTitle("Edit club");
                currentStage.setScene(new Scene(editClubRoot));
            } else {
                // Show some error message to the user or write some error log
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Selection Error");
                alert.setHeaderText("No Club Selected");
                alert.setContentText("Please select a club to edit.");
                alert.showAndWait();
            }
        } catch(IOException e){
            System.err.println("Error loading Edit Club page");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteClub(ActionEvent event) {
        currentClub = clubTableView.getSelectionModel().getSelectedItem();
        if(currentClub == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setHeaderText("No Club Selected");
            alert.setContentText("Please select a club to delete.");
            alert.showAndWait();
            return;
        }
        Node source = (Node) event.getSource();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/delete-club.fxml"));
            DeleteClubController controller = new DeleteClubController(clubTableView);
            loader.setController(controller);
            Parent deleteClubRoot = loader.load();
            Scene deleteClubScene = new Scene(deleteClubRoot);
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.setTitle("Delete Club");
            currentStage.setScene(deleteClubScene);
        } catch(IOException e){
            System.err.println("Error loading Delete Club page");
            e.printStackTrace();
        }
    }
}
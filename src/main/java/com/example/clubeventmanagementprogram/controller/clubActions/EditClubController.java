package com.example.clubeventmanagementprogram.controller.clubActions;
import com.example.clubeventmanagementprogram.dao.ClubDAO;
import com.example.clubeventmanagementprogram.model.Club;
import com.example.clubeventmanagementprogram.service.ClubService;
import com.example.clubeventmanagementprogram.service.ClubServiceImpl;
import com.example.clubeventmanagementprogram.controller.IClubUpdatable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class EditClubController{

    @FXML
    private TextField clubNameField;

    @FXML
    private TextField clubDescriptionField;
    private IClubUpdatable clubUpdatable;

    public void setClubUpdatable(IClubUpdatable clubUpdatable){
        this.clubUpdatable = clubUpdatable;
    }


    ClubDAO clubDao = new ClubDAO(); // Create DAO
    ClubService clubService = new ClubServiceImpl(clubDao);
    @FXML
    private TextField topicsField;

    @FXML
    Button editButton;

    @FXML
    Button cancelButton;
    // Store the club that is currently selected in the table view
    private Club currentClub;

    @FXML
    public void initialize() {
        // Cancel button action
        cancelButton.setOnAction(event -> loadClubScene());
        // Save button action
        editButton.setOnAction(event -> handleSaveAction(event));

        if(currentClub != null) {
            clubNameField.setText(currentClub.getClubName());
            clubDescriptionField.setText(currentClub.getDescription());
            topicsField.setText(currentClub.getTopics());
        }
    }

    public EditClubController(){

    }

    public EditClubController(ObservableList<Club> clubs) {
        this.clubService = new ClubServiceImpl(new ClubDAO());
    }
    public void setCurrentClub(Club club) {
        this.currentClub = club;
        System.out.println("Set club: " + club);  // This is for debugging, remove it later
        if(club != null) {
            clubNameField.setText(currentClub.getClubName());
            clubDescriptionField.setText(currentClub.getDescription());
            topicsField.setText(currentClub.getTopics());
        }
    }


    @FXML
    private void handleSaveAction(ActionEvent event) {
        try {
            currentClub.setClubName(clubNameField.getText());
            currentClub.setDescription(clubDescriptionField.getText());
            currentClub.setTopics(topicsField.getText());

            ClubService clubService = new ClubServiceImpl(clubDao);
            clubService.updateClub(currentClub);

            // inform ClubController to update its table data
            if (clubUpdatable != null) {
                clubUpdatable.updateClubTable();
            }


            // Load the clubs view
            Parent clubsViewRoot = FXMLLoader.load(getClass().getResource("/com/example/clubeventmanagementprogram/clubs-view.fxml"));
            Scene clubsViewScene = new Scene(clubsViewRoot);

            // Get the current Stage and set the scene to clubs view
            Stage currentStage = (Stage) editButton.getScene().getWindow();
            currentStage.setScene(clubsViewScene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void loadClubScene() {
        try {
            // Load Club scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/clubs-view.fxml"));
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
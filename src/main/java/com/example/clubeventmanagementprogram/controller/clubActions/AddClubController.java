package com.example.clubeventmanagementprogram.controller.clubActions;

import com.example.clubeventmanagementprogram.dao.ClubDAO;
import com.example.clubeventmanagementprogram.model.Club;
import com.example.clubeventmanagementprogram.service.ClubService;
import com.example.clubeventmanagementprogram.service.ClubServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AddClubController {

    @FXML
    private TextField clubNameField;

    @FXML
    private TextField clubDescriptionField;

    private ObservableList<Club> clubs;
    @FXML
    private TextArea topicsArea;

    @FXML
    Button addButton;

    @FXML
    Button cancelButton;

    @FXML
    public void initialize() {
        // Cancel button action
        cancelButton.setOnAction(event -> {
            loadClubScene();
        });

        // Save button action
        addButton.setOnAction(event -> {
            handleSaveAction(event);
            loadClubScene();
        });
    }

    public AddClubController(ObservableList<Club> clubs) {
        this.clubs = clubs;
    }

    ClubDAO clubDao = new ClubDAO(); // Create DAO

    @FXML
    private void handleSaveAction(ActionEvent event) {
        try {
            String clubName = clubNameField.getText();
            String clubDescription = clubDescriptionField.getText();

            // Fetch topics from the user's input and split into a list
            List<String> topics = Arrays.asList(topicsArea.getText().split("\\n"));



            // Add an ID for the club
            int clubId = clubs.size() + 1;
            Club newClub = new Club(clubId, clubName, clubDescription, topics);
            newClub.setId(clubId);
            // Persist club using DAO and service layer
            ClubService clubService = new ClubServiceImpl(clubDao);
            clubService.addClub(newClub);

            // Load the clubs view
            Parent clubsViewRoot = FXMLLoader.load(getClass().getResource("/Users/mohammedvepari/IdeaProjects/Club-event-management-program/src/main/resources/com/example/clubeventmanagementprogram/clubs-view.fxml"));
            Scene clubsViewScene = new Scene(clubsViewRoot);

            // Get the current Stage and set the scene to clubs view
            Stage currentStage = (Stage) clubNameField.getScene().getWindow();
            currentStage.setScene(clubsViewScene);

        } catch (IOException e) {
            // Either handle this exception more gracefully or rethrow it
            throw new RuntimeException(e);
        }

    }
    @FXML
    private void loadClubScene() {
        try {
            // Load Club scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/club.fxml"));
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

package com.example.clubeventmanagementprogram.controller.clubActions;

import com.example.clubeventmanagementprogram.dao.ClubDAO;
import com.example.clubeventmanagementprogram.model.Club;
import com.example.clubeventmanagementprogram.service.ClubService;
import com.example.clubeventmanagementprogram.service.ClubServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DeleteClubController {

    @FXML
    private TableView<Club> clubsTable;

    @FXML
    Button deleteButton;
    @FXML
    Button cancelButton;

    private ClubDAO clubDAO = new ClubDAO();
    private ClubService clubService = new ClubServiceImpl(clubDAO); // passing ClubDAO instance

    public DeleteClubController(TableView<Club> clubsTable){
        this.clubsTable = clubsTable;
    }

    @FXML
    public void initialize() {
        deleteButton.setOnAction(e -> deleteSelectedClub());
        cancelButton.setOnAction(e -> loadClubScene());
    }

    @FXML
    private void deleteSelectedClub() {
        Club selectedClub = clubsTable.getSelectionModel().getSelectedItem();

        if (selectedClub != null) {
            clubService.deleteClub(selectedClub.getId());
            clubsTable.getItems().remove(selectedClub);
            try {
                // Load the clubs view
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/clubs-view.fxml"));
                Parent clubsViewRoot = loader.load();
                Scene clubsViewScene = new Scene(clubsViewRoot);

                // Get the current Stage and set the scene to clubs view
                Stage currentStage = (Stage) deleteButton.getScene().getWindow();
                currentStage.setScene(clubsViewScene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("No Club selected in the table.");
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
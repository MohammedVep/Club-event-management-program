package com.example.clubeventmanagementprogram.controller.clubActions;
import com.example.clubeventmanagementprogram.controller.ClubController;
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
    private Button deleteButton;

    private ClubDAO clubDAO = new ClubDAO();
    private ClubService clubService = new ClubServiceImpl(clubDAO); // passing ClubDAO instance

    public void initialize() {
        deleteButton.setOnAction(e -> deleteSelectedClub());
    }

    private void deleteSelectedClub() {
        Club selectedClub = clubsTable.getSelectionModel().getSelectedItem();

        if (selectedClub != null) {
            clubService.deleteClub(selectedClub.getId());
            clubsTable.getItems().remove(selectedClub);
            try {
                // Load the clubs view
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Users/mohammedvepari/IdeaProjects/Club-event-management-program/src/main/resources/com/example/clubeventmanagementprogram/clubs-view.fxml"));
                Parent clubsViewRoot = loader.load();
                Scene clubsViewScene = new Scene(clubsViewRoot);

                // Get the current Stage and set the scene to clubs view
                Stage currentStage = (Stage) deleteButton.getScene().getWindow();
                currentStage.setScene(clubsViewScene);

                // Get a reference to ClubController instance
                ClubController clubController = loader.getController();

                // Then you can call any public methods on clubController, if needed
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("No Club selected in the table.");
        }
    }
}
package com.example.clubeventmanagementprogram.controller.clubActions;
import com.example.clubeventmanagementprogram.dao.ClubDAO;
import com.example.clubeventmanagementprogram.model.Club;
import com.example.clubeventmanagementprogram.service.ClubService;
import com.example.clubeventmanagementprogram.service.ClubServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;

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
        } else {
            System.out.println("No Club selected in the table.");
        }
    }
}
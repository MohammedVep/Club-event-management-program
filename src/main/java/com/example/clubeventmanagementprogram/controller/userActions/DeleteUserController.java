package com.example.clubeventmanagementprogram.controller.userActions;

import com.example.clubeventmanagementprogram.dao.UserDAO;
import com.example.clubeventmanagementprogram.model.Event;
import com.example.clubeventmanagementprogram.model.User;
import com.example.clubeventmanagementprogram.service.UserService;
import com.example.clubeventmanagementprogram.service.UserServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DeleteUserController {

    @FXML
    private TableView<User> usersTable;

    @FXML
    Button yesButton;

    @FXML
    Button noButton;

    private UserDAO userDAO = new UserDAO();
    private UserService userService = new UserServiceImpl(userDAO);

    public DeleteUserController(TableView<User> usersTable){
        this.usersTable = usersTable;
    }

    @FXML
    public void initialize(){
        yesButton.setOnAction(e -> handleDeleteUser(e));
        noButton.setOnAction(e -> loadUserScene(e));
    }

    @FXML
    void handleDeleteUser(ActionEvent event) {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            userService.deleteUser(selectedUser.getUserId());
            usersTable.getItems().remove(selectedUser);
            try {
                // Load the user view
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/users-view.fxml"));
                Parent usersViewRoot = loader.load();
                Scene usersViewScene = new Scene(usersViewRoot);

                // Get the current Stage and set the scene to events view
                Stage currentStage = (Stage) noButton.getScene().getWindow();
                currentStage.setScene(usersViewScene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("No User selected in the table.");
        }
    }

    @FXML
    public void loadUserScene(ActionEvent event) {
        try {
            // Load Users scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/users-view.fxml"));
            Parent root = loader.load();

            // Create a new scene and load it to the stage
            Scene scene = new Scene(root);

            // Getting the current stage
            Stage stage = (Stage) noButton.getScene().getWindow();

            // Setting the new scene to the stage
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

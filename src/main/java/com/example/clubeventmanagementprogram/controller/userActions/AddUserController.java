package com.example.clubeventmanagementprogram.controller.userActions;

import com.example.clubeventmanagementprogram.dao.UserDAO;
import com.example.clubeventmanagementprogram.model.User;
import com.example.clubeventmanagementprogram.service.UserService;
import com.example.clubeventmanagementprogram.service.UserServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AddUserController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    Button saveButton;

    @FXML
    Button cancelButton;

    UserDAO userDAO = new UserDAO();
    UserService userService = new UserServiceImpl(userDAO);

    @FXML
    public void initialize(){
        saveButton.setOnAction(event -> {
            handleSaveUser(event);
        });
        cancelButton.setOnAction(event -> {
            loadUserScene();
        });
    }
    @FXML
    // method is call when user click save button it retrives data from fields.
    private void handleSaveUser(ActionEvent event) {
        // Method to handle saving user details
        try{
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            List<User> usersFromDb = userService.getAllUsers();
            int userId = usersFromDb.size() + 1;
            User newUser = new User(userId, username, email, password);
            userService.addUser(newUser);
            Parent usersViewRoot = FXMLLoader.load(getClass().getResource("/com/example/clubeventmanagementprogram/users-view.fxml"));
            Scene usersViewScene = new Scene(usersViewRoot);

            Stage currentStage = (Stage) saveButton.getScene().getWindow();
            currentStage.setScene(usersViewScene);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    // method is called when user click cancel button to go back to previous page.
    private void loadUserScene() {
        try {
            // Load Users scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/users-view.fxml"));
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

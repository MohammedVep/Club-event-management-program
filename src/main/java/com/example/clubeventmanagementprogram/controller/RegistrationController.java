package com.example.clubeventmanagementprogram.controller;

import com.example.clubeventmanagementprogram.model.Club;
import com.example.clubeventmanagementprogram.model.User;
import com.example.clubeventmanagementprogram.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrationController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField emailField;

    private UserService userService;

    private ObservableList<User> users;

    @FXML
    private Button registerButton;

    public RegistrationController() {

    }
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @FXML
    public void initialize() {
        registerButton.setOnAction(this::register);
    }

    public void setUsers(ObservableList<User> users) {
        this.users = users;
    }


    @FXML
    public void register(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        int id = users.size() + 1;
        // Validation checks here - make sure fields are not empty etc.
        User user = new User(id ,username, password,email);
        user.setUserName(username);
        user.setPassword(password);
        user.setEmail(email);

        try {
            userService.addUser(user);
            showMessage("Registration successful!");
            // Clear the input fields or navigate to another screen
            // Load Home page view
            Parent homePageParent = FXMLLoader.load(getClass().getResource("/com/example/clubeventmanagementprogram/home-view.fxml"));
            Scene homePageScene = new Scene(homePageParent);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(homePageScene);
            appStage.show();
        } catch (Exception e) {
            // Handle exceptions properly, inform the user
            showMessage("Registration failed: " + e.getMessage());
        }
    }

    private void showMessage(String message) {
        // Display this message to the user

    }
}

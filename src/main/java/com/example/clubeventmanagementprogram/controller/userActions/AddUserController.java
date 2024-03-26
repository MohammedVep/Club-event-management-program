package com.example.clubeventmanagementprogram.controller.userActions;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AddUserController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button cancelButton;

    @FXML
    // method is call when user click save button it retrives data from fields.
    private void handleSaveUser() {
        // Method to handle saving user details
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        // Perform necessary actions to save user details (e.g., validation, database operations)
        // For example:
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        // Do not print passwords in real-world applications, it's just for demonstration purposes
        System.out.println("Password: " + password);
    }

    @FXML
    // method is called when user click cancel button to go back to previous page.
    private void loadUserScene() {
        // Method to handle loading user scene
        // You can implement this method to navigate back to the user scene or perform any other necessary actions
        System.out.println("Loading user scene...");
    }
}

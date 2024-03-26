package com.example.clubeventmanagementprogram.controller;

import com.example.clubeventmanagementprogram.utils.LogoutUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

import com.example.clubeventmanagementprogram.service.AuthenticationService;

public class HomeController {
    private String currentUsername;
    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ListView<String> userListView;
    public static int getUserId(String username) {
        String getUserIdQuery = "SELECT user_id FROM users WHERE username = ?";
        int userId = -1; // default to an invalid ID
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://p-5mcdvllaz4.pg.biganimal.io:5432/postgres", "postgres", "password_here");
             PreparedStatement getUserIdStmt = conn.prepareStatement(getUserIdQuery)) {

            getUserIdStmt.setString(1, username);
            ResultSet rs = getUserIdStmt.executeQuery();
            if(rs.next()) {
                userId = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Error getting user id: " + e.getMessage());
        }
        return userId;
    }

    @FXML
    private Label usernameLabel;
    @FXML private Button logoutButton;
    @FXML private Button clubsButton, eventsButton, trackButton, financialButton, usersButton, reportsButton;

    private AuthenticationService authService;
    public void initialize() {
        authService = new AuthenticationService();
        String currentUser = (authService.getCurrentUser()!=null) ? authService.getCurrentUser().getUserName() : "User";
        usernameLabel.setText(currentUser);
        setupButtonActions();
    }

    private void setupButtonActions() {
        logoutButton.setOnAction(event -> {
            try {
                // Any cleanup tasks before logout. Consider encapsulating them in a separate function or service.
                int userId = getUserId(this.currentUsername);
                LogoutUtils.clearUserData(userId);
                LogoutUtils.stopBackgroundTasks();

                // pass required components to the resetUserViews method
                LogoutUtils.resetUserViews(usernameTextField, passwordField, userListView);

                // Load login screen's FXML file
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/example/clubeventmanagementprogram/login-view.fxml")); // Replace with the path to your login FXML file
                Parent loginSceneParent = loader.load();
                Scene loginScene = new Scene(loginSceneParent);

                // Get the current stage and set the scene, then show the stage.
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(loginScene);
                window.show();
            } catch (IOException e) {
                // Handle exception
                e.printStackTrace();
            }
        });

        clubsButton.setOnAction(event -> {
            try {
                // Load the new FXML document
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/clubs-view.fxml"));
                Parent root = loader.load();

                // Create a new scene
                Scene scene = new Scene(root, 600, 400); // you can specify preferred scene dimensions (e.g., 600x400 here)

                // Get current stage and set the new scene onto it
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);

                // set the new window title
                stage.setTitle("Manage Clubs");

                // Show the new scene
                stage.show();

            } catch (IOException e) {
                System.err.println("Failed to load Manage Clubs Page.");
                e.printStackTrace();
            }
        });

        eventsButton.setOnAction(event -> {
            // Load Events scene
        });

        trackButton.setOnAction(event -> {
            // Load Track scene
        });

        financialButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/financial-view.fxml"));
                Parent root = loader.load();
                // Create a new scene
                Scene scene = new Scene(root, 600, 400); // you can specify preferred scene dimensions (e.g., 600x400 here)

                // Get current stage and set the new scene onto it
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);

                // set the new window title
                stage.setTitle("Manage Financial Transactions");

                // Show the new scene
                stage.show();

            } catch (IOException e){
                System.err.println("Failed to load Manage Financial Transactions Page");
                e.printStackTrace();
            }
        });

        usersButton.setOnAction(event -> {
            // Load Users scene
        });

        reportsButton.setOnAction(event -> {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/generate-reports.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root, 600, 400);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);

                stage.setTitle("Generate Reports");
                stage.show();
            } catch (IOException e){
                System.err.println("Failed to load Generate Reports");
                e.printStackTrace();
            }
        });
    }
}
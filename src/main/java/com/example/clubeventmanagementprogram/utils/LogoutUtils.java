package com.example.clubeventmanagementprogram.utils;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LogoutUtils {

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void submitTask(Runnable task) {
        executorService.submit(task);
    }
    // clear user data
    public static void clearUserData(int userId) {
        String clearUserDataQuery = "UPDATE users SET logged_in = false WHERE user_id = ?";

        String clearAuthDataQuery = "DELETE FROM authData WHERE user_id = ?";
        // This query would delete the session row specific to the user.

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "N7IR+fk”hbU#@");
             PreparedStatement clearUserStmt = conn.prepareStatement(clearUserDataQuery);
             PreparedStatement clearAuthStmt = conn.prepareStatement(clearAuthDataQuery)) {

            clearUserStmt.setInt(1, userId);
            clearUserStmt.executeUpdate();

            clearAuthStmt.setInt(1, userId);
            clearAuthStmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error clearing user data: " + e.getMessage());
        }
    }

    // stop any running background tasks or services
    public static void stopBackgroundTasks() {
        executorService.shutdown(); // prevent new tasks from being submitted
        try {
            // Wait for existing tasks to terminate
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow(); // Cancel currently executing tasks
                // Wait for tasks to respond being cancelled
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("LogoutUtils did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            executorService.shutdownNow();
            Thread.currentThread().interrupt(); // Preserve interrupt status
        }
    }

    // reset any views or UI components that hold user data
    public static void resetUserViews(TextField usernameTextField, PasswordField passwordField, ListView<String> userListView) {
        // run on UI thread
        Platform.runLater(() -> {
            if(usernameTextField != null) {
                usernameTextField.clear();
            }
            if(passwordField != null){
                passwordField.clear();
            }
            if(userListView != null){
                userListView.getItems().clear();
            }
            // Clear or reset other view components that may be holding or displaying data related to the current user
        });
    }
}
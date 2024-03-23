package com.example.clubeventmanagementprogram.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class EventTrackingController {
    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    public EventTrackingController(String dbUrl, String dbUsername, String dbPassword) {
        this.URL = dbUrl;
        this.USERNAME = dbUsername;
        this.PASSWORD = dbPassword;
    }

    public void trackEvent(String eventName, int userId, String eventData) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO events (event_name, user_id, event_data, created_at) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, eventName);
                statement.setInt(2, userId);
                statement.setString(3, eventData);
                statement.setObject(4, LocalDateTime.now());

                statement.executeUpdate();
                System.out.println("Event tracked successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error tracking event: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String URL = "jdbc:postgresql://localhost:5432/postgres";
        String USERNAME = "postgres";
        String PASSWORD = "N7IR+fk”hbU#@";

        EventTrackingController controller = new EventTrackingController(URL, USERNAME, PASSWORD);

        // Example usage:
        int userId = 123;
        String eventData = "{\"page\": \"homepage\", \"action\": \"click_button\"}";
        controller.trackEvent("user_interaction", userId, eventData);
    }
}


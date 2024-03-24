package com.example.clubeventmanagementprogram.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class EventTrackingController {
    private String URL = "jdbc:postgresql://p-5mcdvllaz4.pg.biganimal.io:5432/postgres";
    private String USERNAME = "edb_admin";
    private String PASSWORD = "Rlo:On)YMYtL%Ob";

    public EventTrackingController(String dbUrl, String dbUsername, String dbPassword) {
        this.URL = dbUrl;
        this.USERNAME = dbUsername;
        this.PASSWORD = dbPassword;
    }

    public void trackEvent(String eventName, String description, LocalDate date, LocalTime startTime, LocalTime endTime) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO events (eventName, description, date, startTime, endTime) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, eventName);
                statement.setString(2, description);
                statement.setDate(3, java.sql.Date.valueOf(date));
                statement.setTime(4, java.sql.Time.valueOf(startTime));
                statement.setTime(5, java.sql.Time.valueOf(endTime));

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

    }
}


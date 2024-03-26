package com.example.clubeventmanagementprogram.dao;

import com.example.clubeventmanagementprogram.model.Event;

import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {
    private static final String URL = "jdbc:postgresql://p-5mcdvllaz4.pg.biganimal.io:5432/postgres";
    private static final String USERNAME = "edb_admin";
    private static final String PASSWORD = "Rlo:On)YMYtL%Ob";

    public Event getEventById(int id) {
        String sql = "SELECT * FROM events WHERE eventid = ?";
        Event event = null;

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int eventId = rs.getInt("id");
                String eventName = rs.getString("EventName");
                String description = rs.getString("Description");
                LocalDate date = rs.getDate("Date").toLocalDate();
                String startTime = rs.getString("StartTime");
                String endTime = rs.getString("EndTime");

                event = new Event(eventId, eventName, description, date, startTime, endTime);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return event;
    }

    public List<Event> getAllEvents() {
        String sql = "SELECT * FROM events";
        List<Event> events = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String eventName = rs.getString("EventName");
                String description = rs.getString("Description");
                LocalDate date = rs.getDate("Date").toLocalDate();
                String startTime = rs.getString("StartTime");
                String endTime = rs.getString("EndTime");

                Event event = new Event(id, eventName, description, date, startTime, endTime);
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }

    public void createEvent(Event event) {
        String sql = "INSERT INTO events (eventName, description, date, startTime, endTime) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, event.getEventName());
            pstmt.setString(2, event.getDescription());
            pstmt.setDate(3, java.sql.Date.valueOf(event.getDate()));
            pstmt.setTime(4, java.sql.Time.valueOf(event.getStartTime()));
            pstmt.setTime(5, java.sql.Time.valueOf(event.getEndTime()));
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEvent(Event event) {
        String sql = "UPDATE events SET eventName = ?, description = ?, date = ?, startTime = ?, endTime = ? WHERE eventId = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, event.getEventName());
            pstmt.setString(2, event.getDescription());
            pstmt.setDate(3, java.sql.Date.valueOf(event.getDate()));
            pstmt.setTime(4, java.sql.Time.valueOf(event.getStartTime()));
            pstmt.setTime(5, java.sql.Time.valueOf(event.getEndTime()));
            pstmt.setInt(6, event.getId());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEvent(int eventId) {
        String sql = "DELETE FROM events WHERE eventId = ?";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, eventId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    System.err.print("Event is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
                throw new RuntimeException("Error deleting event with ID " + eventId, e);
            } finally {
                if (conn != null){
                    try {
                        conn.setAutoCommit(true);
                        conn.close();
                    } catch (SQLException excep){
                        excep.printStackTrace();
                    }
                }
            }
        }
    }


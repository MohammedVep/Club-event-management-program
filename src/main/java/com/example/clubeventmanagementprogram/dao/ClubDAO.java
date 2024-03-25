package com.example.clubeventmanagementprogram.dao;

import com.example.clubeventmanagementprogram.model.Club;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClubDAO {
    private static final String URL = "jdbc:postgresql://p-5mcdvllaz4.pg.biganimal.io:5432/postgres";
    private static final String USERNAME = "edb_admin";
    private static final String PASSWORD = "Rlo:On)YMYtL%Ob";

    public void saveClub(Club club) {
        String sql = "INSERT INTO clubs (clubname, description, topics) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            // use a transaction to ensure both inserts (club and topics) happen atomically
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, club.getClubName());
            pstmt.setString(2, club.getDescription());
            pstmt.setString(3, club.getTopics());
            pstmt.executeUpdate();


            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateClub(Club club) {
        String sqlClub = "UPDATE clubs SET clubname = ?, description = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // use a transaction to ensure all operations happen atomically
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(sqlClub);

            pstmt.setString(1, club.getClubName());
            pstmt.setString(2, club.getDescription());
            pstmt.setString(3, club.getTopics());
            pstmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteClub(int clubId) {
        String sqlDeleteFromClub = "DELETE FROM clubs WHERE id = ?";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            conn.setAutoCommit(false);


            PreparedStatement pstmtClub = conn.prepareStatement(sqlDeleteFromClub);
            pstmtClub.setInt(1, clubId);
            pstmtClub.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch(SQLException excep) {
                    excep.printStackTrace();
                }
            }
            throw new RuntimeException("Error deleting club with ID " + clubId, e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        }
    }

    public Club getClub(int clubId) {
        String sqlClub = "SELECT * FROM clubs WHERE id = ?";

        Club club = null;

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement pstmtClub = conn.prepareStatement(sqlClub);
            pstmtClub.setInt(1, clubId);

            ResultSet rsClub = pstmtClub.executeQuery();

            if (rsClub.next()) {
                int id = rsClub.getInt("id");
                String clubName = rsClub.getString("clubName");
                String description = rsClub.getString("description");
                String topics = rsClub.getString("topics");



                club = new Club(id, clubName, description, topics);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return club;
    }

    public List<Club> getAllClubs() {
        String sqlClubs = "SELECT * FROM clubs";

        List<Club> clubs = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmtClubs = conn.prepareStatement(sqlClubs);
             ResultSet rsClubs = pstmtClubs.executeQuery()) {

            while (rsClubs.next()) {
                int clubId = rsClubs.getInt("id");
                String clubName = rsClubs.getString("clubName");
                String description = rsClubs.getString("description");

                String topics = rsClubs.getString("topics");

                Club club = new Club(clubId, clubName, description, topics);
                clubs.add(club);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clubs;
    }
}

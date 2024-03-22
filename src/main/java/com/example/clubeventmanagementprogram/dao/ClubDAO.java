package com.example.clubeventmanagementprogram.dao;

import com.example.clubeventmanagementprogram.model.Club;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClubDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "N7IR+fk”hbU#@";

    public void saveClub(Club club) {
        String sql = "INSERT INTO clubs (clubname, description) VALUES (?, ?)";
        String insertTopicSql = "INSERT INTO club_topics (club_id, topic_id) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            // use a transaction to ensure both inserts (club and topics) happen atomically
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, club.getClubName());
            pstmt.setString(2, club.getDescription());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                int clubId = rs.getInt(1); // id of the newly inserted club

                PreparedStatement topicStmt = conn.prepareStatement(insertTopicSql);

                for (String topic : club.getTopics()) { // assuming Club class now has List<String> or Set<String> getTopics method
                    topicStmt.setInt(1, clubId);
                    topicStmt.setInt(2, Integer.parseInt(topic)); // replace with getting topicId from topics table
                    topicStmt.addBatch();
                }

                topicStmt.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getTopicIdByTopicName(String topicName) {
        String sql = "SELECT id FROM topics WHERE name = ?";
        int topicId = 0;

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, topicName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                topicId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topicId;
    }

    public void updateClub(Club club) {
        String sqlClub = "UPDATE clubs SET clubname = ?, description = ? WHERE id = ?";
        String deleteOldTopicsSql = "DELETE FROM club_topics WHERE club_id = ?";
        String insertTopicsSql = "INSERT INTO club_topics (club_id, topic_id) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // use a transaction to ensure all operations happen atomically
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(sqlClub);

            pstmt.setString(1, club.getClubName());
            pstmt.setString(2, club.getDescription());
            pstmt.setInt(3, club.getId());
            pstmt.executeUpdate();

            // remove all old topics connected with club
            PreparedStatement deleteOldTopicsStmt = conn.prepareStatement(deleteOldTopicsSql);
            deleteOldTopicsStmt.setInt(1, club.getId());
            deleteOldTopicsStmt.executeUpdate();

            // insert new topics connected with club
            PreparedStatement insertNewTopicsStmt = conn.prepareStatement(insertTopicsSql);

            for (String topic : club.getTopics()) {
                insertNewTopicsStmt.setInt(1, club.getId());
                insertNewTopicsStmt.setInt(2, getTopicIdByTopicName(topic));
                insertNewTopicsStmt.addBatch();
            }

            insertNewTopicsStmt.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteClub(int clubId) {
        String sqlDeleteFromClub = "DELETE FROM clubs WHERE id = ?";
        String sqlDeleteFromClubTopic = "DELETE FROM club_topics WHERE club_id = ?";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            conn.setAutoCommit(false);

            PreparedStatement pstmtClubTopic = conn.prepareStatement(sqlDeleteFromClubTopic);
            pstmtClubTopic.setInt(1, clubId);
            pstmtClubTopic.executeUpdate();

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
        String sqlClubTopics = "SELECT t.name FROM topics t INNER JOIN club_topics ct ON t.id = ct.topic_id WHERE ct.club_id = ?";

        Club club = null;

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement pstmtClub = conn.prepareStatement(sqlClub);
            pstmtClub.setInt(1, clubId);

            ResultSet rsClub = pstmtClub.executeQuery();

            if (rsClub.next()) {
                int id = rsClub.getInt("id");
                String clubName = rsClub.getString("clubname");
                String description = rsClub.getString("description");

                List<String> topics = new ArrayList<>();
                PreparedStatement pstmtTopics = conn.prepareStatement(sqlClubTopics);
                pstmtTopics.setInt(1, clubId);
                ResultSet rsTopics = pstmtTopics.executeQuery();

                while (rsTopics.next()) {
                    topics.add(rsTopics.getString("name"));
                }

                club = new Club(id, clubName, description, topics);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return club;
    }

    public List<Club> getAllClubs() {
        String sqlClubs = "SELECT * FROM clubs";
        String sqlClubTopics = "SELECT t.name FROM topics t INNER JOIN club_topics ct ON t.id = ct.topic_id WHERE ct.club_id = ?";
        List<Club> clubs = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmtClubs = conn.prepareStatement(sqlClubs);
             ResultSet rsClubs = pstmtClubs.executeQuery()) {

            while (rsClubs.next()) {
                int clubId = rsClubs.getInt("id");
                String clubName = rsClubs.getString("clubname");
                String description = rsClubs.getString("description");

                List<String> topics = new ArrayList<>();
                PreparedStatement pstmtTopics = conn.prepareStatement(sqlClubTopics);
                pstmtTopics.setInt(1, clubId);
                ResultSet rsTopics = pstmtTopics.executeQuery();

                while (rsTopics.next()) {
                    topics.add(rsTopics.getString("name"));
                }

                Club club = new Club(clubId, clubName, description, topics);
                clubs.add(club);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clubs;
    }
}

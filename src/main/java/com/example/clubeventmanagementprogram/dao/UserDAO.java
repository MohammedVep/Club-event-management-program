package com.example.clubeventmanagementprogram.dao;


import com.example.clubeventmanagementprogram.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class UserDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "N7IR+fk”hbU#@";

    private static final String FIND_BY_USERNAME_SQL = "SELECT * FROM users WHERE username = ?";

    public User findByUsername(String username) {
        User user = null;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(FIND_BY_USERNAME_SQL)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                // assuming User class has a constructor User(int id, String name, String password)
                user = new User(id, name, email, password);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by username", e);
        }
        return user;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                users.add(new User(id, name, email, password));
            }

        } catch(SQLException e) {
            System.out.println("Error fetching users: " + e.getMessage());
        }
        return users;
    }

    public User getUserById(int id) {
        User user = null;
        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM users WHERE user_id = ?")) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    user = new User(id, name, email, password);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error fetching user with id " + id + ": " + e.getMessage());
        }
        return user;
    }

    public void deleteUser(int id) {
        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement("DELETE FROM users WHERE user_id = ?")) {

            pstmt.setInt(1, id);
            int deletedRows = pstmt.executeUpdate();

        } catch(SQLException e) {
            System.out.println("Error deleting user with id " + id + ": " + e.getMessage());
        }
    }

    public void addUser(User user) {
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword()); // Consider hashing the password before saving
            pstmt.setString(3, user.getEmail());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void editUser(User user) {
        String sql = "UPDATE users SET username = ?, password = ?, email = ? WHERE user_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword()); // Consider hashing the password before saving
            pstmt.setString(3, user.getEmail());
            pstmt.setInt(4, user.getUserId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
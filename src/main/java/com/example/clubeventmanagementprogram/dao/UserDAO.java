package com.example.clubeventmanagementprogram.dao;


import com.example.clubeventmanagementprogram.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class UserDAO {
    private static final String URL = "jdbc:postgresql://p-5mcdvllaz4.pg.biganimal.io:5432/postgres";
    private static final String USERNAME = "edb_admin";
    private static final String PASSWORD = "Rlo:On)YMYtL%Ob";

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
        String sqlUsers = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (Connection connection = connect();
             PreparedStatement pstmtUser = connection.prepareStatement(sqlUsers);
             ResultSet rs = pstmtUser.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                User user = new User(id, name, email, password);
                users.add(user);
            }

        } catch(SQLException e) {
            System.out.println("Error fetching users: " + e.getMessage());
        }
        return users;
    }

    public User getUserById(int id) {
        String sqlUser = "SELECT * FROM users WHERE id = ?";
        User user = null;
        try (Connection connection = connect()) {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM users WHERE user_id = ?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                user = new User(userId, name, email, password);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void deleteUser(int id) {
        String sqlDeleteFromUser = "DELETE FROM users WHERE user_id = ?";
        Connection conn = null;
        try {
            conn = connect();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(sqlDeleteFromUser);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            conn.commit();

        } catch(SQLException e) {
            if (conn != null){
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch(SQLException excep){
                    excep.printStackTrace();
                }
            }
            throw new RuntimeException("Error deleting user with ID " + id, e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch(SQLException excep){
                    excep.printStackTrace();
                }
            }
        }
    }

    public void addUser(User user) {
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword()); // Consider hashing the password before saving
            pstmt.setString(3, user.getEmail());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void editUser(User user) {
        String sql = "UPDATE users SET username = ?, password = ?, email = ? WHERE user_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword()); // Consider hashing the password before saving
            pstmt.setString(3, user.getEmail());
            pstmt.setInt(4, user.getUserId());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
package com.example.clubeventmanagementprogram.dao;

import com.example.clubeventmanagementprogram.model.FinancialTransaction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class FinancialTransactionDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "N7IR+fk”hbU#@";

    public void addTransaction(FinancialTransaction transaction) {
        String sql = "INSERT INTO FinancialTransaction (transactionName, date, description, transactionAmount) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, transaction.getTransactionName());
            pstmt.setDate(2, java.sql.Date.valueOf(transaction.getDate()));
            pstmt.setString(3, transaction.getDescription());
            pstmt.setDouble(4, transaction.getTransactionAmount());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public FinancialTransaction getTransactionById(int id) {
        String SQL = "SELECT * FROM FinancialTransaction WHERE financial_id = ?";
        FinancialTransaction transaction = null;

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                transaction = new FinancialTransaction(rs.getInt("financial_id"),
                        rs.getString("transactionName"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("description"),
                        rs.getDouble("transactionAmount"));
                transaction.setFinancial_id(rs.getInt("financial_id"));
                transaction.setTransactionName(rs.getString("transactionName"));
                transaction.setDate(rs.getDate("date").toLocalDate());
                transaction.setDescription(rs.getString("description"));
                transaction.setTransactionAmount(rs.getDouble("transactionAmount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public List<FinancialTransaction> getAllTransactions() {
        List<FinancialTransaction> transactions = new ArrayList<>();
        String SQL = "SELECT * FROM FinancialTransaction";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                FinancialTransaction transaction = new FinancialTransaction(rs.getInt("financial_id"),
                        rs.getString("transactionName"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("description"),
                        rs.getDouble("transactionAmount"));
                transaction.setFinancial_id(rs.getInt("financial_id"));
                transaction.setTransactionName(rs.getString("transactionName"));
                transaction.setDate(rs.getDate("date").toLocalDate());
                transaction.setDescription(rs.getString("description"));
                transaction.setTransactionAmount(rs.getDouble("transactionAmount"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public void updateTransaction(FinancialTransaction transaction) {
        String SQL = "UPDATE FinancialTransaction SET transactionName = ?, date = ?, description = ?, transactionAmount = ? WHERE financial_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, transaction.getTransactionName());
            pstmt.setDate(2, java.sql.Date.valueOf(transaction.getDate()));
            pstmt.setString(3, transaction.getDescription());
            pstmt.setDouble(4, transaction.getTransactionAmount());
            pstmt.setInt(5, transaction.getFinancial_id());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTransaction(int id) {
        String SQL = "DELETE FROM FinancialTransaction WHERE financial_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

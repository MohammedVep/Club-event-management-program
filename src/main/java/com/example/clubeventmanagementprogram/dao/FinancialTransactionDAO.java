package com.example.clubeventmanagementprogram.dao;

import com.example.clubeventmanagementprogram.model.FinancialTransaction;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FinancialTransactionDAO {
    private static final String URL = "jdbc:postgresql://p-5mcdvllaz4.pg.biganimal.io:5432/postgres";
    private static final String USERNAME = "edb_admin";
    private static final String PASSWORD = "Rlo:On)YMYtL%Ob";

    public void addTransaction(FinancialTransaction transaction) {
        String sql = "INSERT INTO FinancialTransaction (transactionName, date, description, transactionAmount) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, transaction.getTransactionName());
            pstmt.setDate(2, java.sql.Date.valueOf(transaction.getDate()));
            pstmt.setString(3, transaction.getDescription());
            pstmt.setDouble(4, transaction.getTransactionAmount());
            pstmt.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public FinancialTransaction getTransactionById(int transactionId) {
        String SQL = "SELECT * FROM FinancialTransaction WHERE financial_id = ?";
        FinancialTransaction transaction = null;

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, transactionId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                int id = rs.getInt("financial_id");
                String transactionName = rs.getString("transactionName");
                LocalDate date = rs.getDate("date").toLocalDate();
                String description = rs.getString("description");
                double transactionAmount = rs.getDouble("transactionAmount");

                transaction = new FinancialTransaction(id, transactionName, date, description, transactionAmount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public List<FinancialTransaction> getAllTransactions() {
        List<FinancialTransaction> transactions = new ArrayList<>();
        String SQL = "SELECT * FROM FinancialTransaction";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int financial_id = rs.getInt("financial_id");
                String transactionName = rs.getString("transactionName");
                LocalDate date = rs.getDate("date").toLocalDate();
                String description = rs.getString("description");
                double transactionAmount = rs.getDouble("transactionAmount");
                FinancialTransaction transaction = new FinancialTransaction(financial_id, transactionName, date, description, transactionAmount);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public void updateTransaction(FinancialTransaction transaction) {
        String SQL = "UPDATE FinancialTransaction SET transactionName = ?, date = ?, description = ?, transactionAmount = ? WHERE financial_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, transaction.getTransactionName());
            pstmt.setDate(2, java.sql.Date.valueOf(transaction.getDate()));
            pstmt.setString(3, transaction.getDescription());
            pstmt.setDouble(4, transaction.getTransactionAmount());
            pstmt.setInt(5, transaction.getFinancial_id());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTransaction(int id) {
        String SQL = "DELETE FROM FinancialTransaction WHERE financial_id = ?";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
            throw new RuntimeException("Error deleting financial transaction with ID " + id, e);
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
}

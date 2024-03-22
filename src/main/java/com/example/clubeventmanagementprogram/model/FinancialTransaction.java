package com.example.clubeventmanagementprogram.model;

import java.time.LocalDate;

public class FinancialTransaction {
    private int financial_id;
    private String transactionName;
    private LocalDate date;
    private String description;
    private double transactionAmount;

    // Constructor
    public FinancialTransaction(int financial_id, String transactionName, LocalDate date, String description, double transactionAmount) {
        this.financial_id = financial_id;
        this.transactionName = transactionName;
        this.date = date;
        this.description = description;
        this.transactionAmount = transactionAmount;
    }

    // Getters and Setters
    public int getFinancial_id() {
        return financial_id;
    }

    public void setFinancial_id(int financial_id) {
        this.financial_id = financial_id;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}
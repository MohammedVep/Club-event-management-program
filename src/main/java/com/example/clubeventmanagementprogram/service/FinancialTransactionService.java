package com.example.clubeventmanagementprogram.service;

import com.example.clubeventmanagementprogram.model.FinancialTransaction;

import java.util.List;
public interface FinancialTransactionService {
    void addTransaction(FinancialTransaction transaction);
    void updateTransaction(FinancialTransaction transaction);
    void deleteTransaction(int id);
    FinancialTransaction getFinancialTransactionById(int id);
    List<FinancialTransaction> getAllFinancialTransactions();
}

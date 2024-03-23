package com.example.clubeventmanagementprogram.service;
import com.example.clubeventmanagementprogram.dao.FinancialTransactionDAO;
import com.example.clubeventmanagementprogram.model.FinancialTransaction;

import java.util.List;

public class FinancialTransactionServiceImpl implements FinancialTransactionService{
    private FinancialTransactionDAO financialTransactionDAO;

    public FinancialTransactionServiceImpl(FinancialTransactionDAO financialTransactionDAO) {
        this.financialTransactionDAO = financialTransactionDAO;
    }

    @Override
    public void addTransaction(FinancialTransaction transaction) {
        financialTransactionDAO.addTransaction(transaction);
    }

    @Override
    public void updateTransaction(FinancialTransaction transaction) {
        financialTransactionDAO.updateTransaction(transaction);
    }

    @Override
    public void deleteTransaction(int id) {
        financialTransactionDAO.deleteTransaction(id);
    }

    @Override
    public FinancialTransaction getFinancialTransactionById(int id) {
        return financialTransactionDAO.getTransactionById(id);
    }

    @Override
    public List<FinancialTransaction> getAllFinancialTransactions() {
        return financialTransactionDAO.getAllTransactions();
    }
}

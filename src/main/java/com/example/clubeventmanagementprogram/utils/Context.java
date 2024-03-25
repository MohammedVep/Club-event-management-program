package com.example.clubeventmanagementprogram.utils;

import com.example.clubeventmanagementprogram.dao.ClubDAO;
import com.example.clubeventmanagementprogram.dao.FinancialTransactionDAO;
import com.example.clubeventmanagementprogram.service.ClubService;
import com.example.clubeventmanagementprogram.service.ClubServiceImpl;
import com.example.clubeventmanagementprogram.service.FinancialTransactionService;
import com.example.clubeventmanagementprogram.service.FinancialTransactionServiceImpl;

public class Context {
    private static ClubDAO clubDAO = new ClubDAO(); // Assuming you have a ClubDAOImpl class
    public static ClubService clubService = new ClubServiceImpl(clubDAO);

    private static FinancialTransactionDAO financialTransactionDAO = new FinancialTransactionDAO();
    public static FinancialTransactionService financialTransactionService = new FinancialTransactionServiceImpl(financialTransactionDAO);

    public static ClubService getClubService() {
        return clubService;
    }

    public static FinancialTransactionService getFinancialTransactionService() {
        return financialTransactionService;
    }
}
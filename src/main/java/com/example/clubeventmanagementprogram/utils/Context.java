package com.example.clubeventmanagementprogram.utils;

import com.example.clubeventmanagementprogram.dao.ClubDAO;
import com.example.clubeventmanagementprogram.dao.EventDAO;
import com.example.clubeventmanagementprogram.dao.FinancialTransactionDAO;
import com.example.clubeventmanagementprogram.dao.UserDAO;
import com.example.clubeventmanagementprogram.service.*;

public class Context {
    private static ClubDAO clubDAO = new ClubDAO(); // Assuming you have a ClubDAOImpl class
    public static ClubService clubService = new ClubServiceImpl(clubDAO);

    private static FinancialTransactionDAO financialTransactionDAO = new FinancialTransactionDAO();
    public static FinancialTransactionService financialTransactionService = new FinancialTransactionServiceImpl(financialTransactionDAO);
    private static EventDAO eventDAO = new EventDAO();
    public static EventService eventService = new EventServiceImpl(eventDAO);
    private static UserDAO userDAO = new UserDAO();
    public static UserService userService = new UserServiceImpl(userDAO);

    public static ClubService getClubService() {
        return clubService;
    }

    public static FinancialTransactionService getFinancialTransactionService() {
        return financialTransactionService;
    }
    public static EventService getEventService() {
        return eventService;
    }
    public static UserService getUserService() {
        return userService;
    }
}
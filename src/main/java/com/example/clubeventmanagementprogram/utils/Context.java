package com.example.clubeventmanagementprogram.utils;

import com.example.clubeventmanagementprogram.dao.ClubDAO;
import com.example.clubeventmanagementprogram.service.ClubService;
import com.example.clubeventmanagementprogram.service.ClubServiceImpl;

public class Context {
    private static ClubDAO clubDAO = new ClubDAO(); // Assuming you have a ClubDAOImpl class
    private static ClubService clubService = new ClubServiceImpl(clubDAO);

    public static ClubService getClubService() {
        return clubService;
    }
}
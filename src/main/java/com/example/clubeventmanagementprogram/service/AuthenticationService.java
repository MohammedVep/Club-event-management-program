package com.example.clubeventmanagementprogram.service;

import com.example.clubeventmanagementprogram.dao.UserDAO;
import com.example.clubeventmanagementprogram.model.User;

public class AuthenticationService {
    private static AuthenticationService instance;
    private UserDAO userDao;
    private User currentUser;

    public AuthenticationService() {
        this.userDao = new UserDAO();
    }

    public static AuthenticationService getInstance() {
        if (instance == null) {
            instance = new AuthenticationService();
        }
        return instance;
    }
    public boolean authenticate(String username, String password) {
        User user = userDao.findByUsername(username);

        if (user == null || !user.getPassword().equals(password)) {
            return false; // User not found or password did not match
        }

        // We found a user and the password matches. Save this user as the current user.
        currentUser = user;
        return true; // Successful authentication
    }

    public User getCurrentUser() {
        return currentUser;
    }
}

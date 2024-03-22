package com.example.clubeventmanagementprogram.service;
import com.example.clubeventmanagementprogram.dao.UserDAO;
import com.example.clubeventmanagementprogram.model.User;

public class AuthenticationService {
    private UserDAO userDao;
    private User currentUser;

    public AuthenticationService() {
        this.userDao = new UserDAO();
    }

    public boolean authenticate(String username, String password) {
        User user = userDao.findByUsername(username);

        if (user == null) {
            return false; // User not found
        }

        // Verify that the password matches the one stored in the database
        if (user.getPassword().equals(password)) { // don't store passwords as plain text!
            return true; // Successful authentication
        } else {
            return false; // Password did not match
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }
}

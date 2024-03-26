package com.example.clubeventmanagementprogram.service;

import com.example.clubeventmanagementprogram.dao.UserDAO;
import com.example.clubeventmanagementprogram.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDAO userDao;
    public UserServiceImpl() {
        // Initialization during construction
        this.userDao = new UserDAO(); // Assuming UserDaoImpl is a concrete class that implements UserDao
    }

    public UserServiceImpl(UserDAO userDao) {
        this.userDao = userDao;
    }
    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        userDao.editUser(user);
    }

    @Override
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}

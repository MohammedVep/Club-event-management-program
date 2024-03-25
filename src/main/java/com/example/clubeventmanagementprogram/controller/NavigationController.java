package com.example.clubeventmanagementprogram.controller;

import com.example.clubeventmanagementprogram.model.User;
import com.example.clubeventmanagementprogram.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigationController {

    private UserService userService;
    private ObservableList<User> users;

    public NavigationController(UserService userService) {
        this.userService = userService;
        this.users = FXCollections.observableArrayList(userService.getAllUsers());
    }

    public void goToRegisterView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/register-view.fxml"));
            Parent root = loader.load();

            RegistrationController registrationController = loader.getController();
            registrationController.setUserService(userService);
            registrationController.setUsers(users);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
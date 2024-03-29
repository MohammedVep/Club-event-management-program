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
    private Stage mainStage;
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
            mainStage.setTitle("Register");
            mainStage.setScene(new Scene(root));
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToLoginView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/login-view.fxml"));
            Parent root = loader.load();
            LoginController loginController = loader.getController();
            loginController.setUserService(userService);
            loginController.setUsers(users);
            mainStage.setTitle("Login");
            mainStage.setScene(new Scene(root));
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
}
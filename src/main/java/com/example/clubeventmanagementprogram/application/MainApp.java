package com.example.clubeventmanagementprogram.application;

import com.example.clubeventmanagementprogram.controller.LoginController;
import com.example.clubeventmanagementprogram.controller.NavigationController;
import com.example.clubeventmanagementprogram.service.UserService;
import com.example.clubeventmanagementprogram.service.UserServiceImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        UserService userService = new UserServiceImpl();
        NavigationController navigationController = new NavigationController(userService);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/login-view.fxml"));
        Parent root = loader.load();

        // Connect controller with navigator and services
        LoginController loginController = loader.getController();
        loginController.setNavigationController(navigationController);
        loginController.setUserService(userService);

        navigationController.setMainStage(primaryStage);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
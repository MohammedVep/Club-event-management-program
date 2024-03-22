package com.example.clubeventmanagementprogram.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Load the FXML file for the login page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/login-view.fxml"));

        // Load the scene
        Parent root = loader.load();

        // Create the scene and add the root node to it
        Scene scene = new Scene(root);

        // Set the scene for the primaryStage
        primaryStage.setScene(scene);

        // Show the primaryStage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
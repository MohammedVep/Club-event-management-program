package com.example.clubeventmanagementprogram.controller.userActions;

import com.example.clubeventmanagementprogram.controller.IUserUpdatable;
import com.example.clubeventmanagementprogram.dao.UserDAO;
import com.example.clubeventmanagementprogram.model.User;
import com.example.clubeventmanagementprogram.service.UserService;
import com.example.clubeventmanagementprogram.service.UserServiceImpl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EditUserController {

    private User currentUser;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private IUserUpdatable userUpdatable;

    public void setUserUpdatable(IUserUpdatable userUpdatable){
        this.userUpdatable = userUpdatable;
    }

    UserDAO userDAO = new UserDAO();
    UserService userService = new UserServiceImpl(userDAO);
    public EditUserController(){

    }

    public void initialize() {
        // Initialize the originalUsername with the initial value from the usernameField
        cancelButton.setOnAction(event -> loadUserScene(event));
        saveButton.setOnAction(event -> handleSaveUser(event));
        if(currentUser != null) {
            usernameField.setText(currentUser.getUserName());
            passwordField.setText(currentUser.getPassword());
            emailField.setText(currentUser.getEmail());
        }
    }

    public EditUserController(ObservableList<User> users){
        this.userService = new UserServiceImpl(new UserDAO());
    }

    public void setCurrentUser(User user){
        this.currentUser = user;
        System.out.println("Set user: " + user);
        if (user != null){
            usernameField.setText(currentUser.getUserName());
            emailField.setText(currentUser.getEmail());
            passwordField.setText(currentUser.getPassword());
        }
    }

    @FXML
    void handleSaveUser(ActionEvent event) {
        try{
            currentUser.setUserName(usernameField.getText());
            currentUser.setEmail(emailField.getText());
            currentUser.setPassword(passwordField.getText());
            UserService userService = new UserServiceImpl(userDAO);
            userUpdatable.updateUserTable();

            if (userUpdatable != null){
                userUpdatable.updateUserTable();
            }

            Parent userViewRoot = FXMLLoader.load(getClass().getResource("/com/example/clubeventmanagementprogram/users-view.fxml"));
            Scene userViewScene = new Scene(userViewRoot);

            // Get the current Stage and set the scene to clubs view
            Stage currentStage = (Stage) saveButton.getScene().getWindow();
            currentStage.setScene(userViewScene);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void loadUserScene(ActionEvent event) {
        try {
            // Load User scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/users-view.fxml"));
            Parent root = loader.load();

            // Create a new scene and load it to the stage
            Scene scene = new Scene(root);

            // Getting the current stage
            Stage stage = (Stage) cancelButton.getScene().getWindow();

            // Setting the new scene to the stage
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

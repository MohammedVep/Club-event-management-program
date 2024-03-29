package com.example.clubeventmanagementprogram.controller;

import com.example.clubeventmanagementprogram.controller.eventActions.DeleteEventController;
import com.example.clubeventmanagementprogram.controller.eventActions.EditEventController;
import com.example.clubeventmanagementprogram.controller.userActions.DeleteUserController;
import com.example.clubeventmanagementprogram.controller.userActions.EditUserController;
import com.example.clubeventmanagementprogram.dao.UserDAO;
import com.example.clubeventmanagementprogram.model.Event;
import com.example.clubeventmanagementprogram.model.User;
import com.example.clubeventmanagementprogram.service.UserService;
import com.example.clubeventmanagementprogram.utils.Context;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.example.clubeventmanagementprogram.utils.Context.userService;

public class UserController implements IUserUpdatable{

    @FXML
    private Label usernameLabel;

    @FXML
    private Label manageUsersLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Button backButton;

    @FXML
    private TableView<User> userTableView;
    @FXML
    private TableColumn<User, Boolean> checkBoxColumn;

    @FXML
    private TableColumn<User, String> userNameColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private Button addButton;                 // Place the 'Add' button below the table
    @FXML
    private Button editButton;                // Place the 'Edit' button below the table
    @FXML
    private Button deleteButton;              // Place the 'Delete' button below the table

    private User currentUser;

    private ObservableList<User> userData = FXCollections.observableArrayList();

    @Override
    public void updateUserTable(){
        userData.clear();
        List<User> updatedList = userService.getAllUsers();
        userData.addAll(updatedList);
    }

    private void loadUserData(){
        UserService userService = Context.getUserService();
        List<User> usersFromDb = userService.getAllUsers();
        userData.addAll(usersFromDb);
    }

    public void initialize() {
        logoutButton.setOnAction(event -> handleLogout(event));
        addButton.setOnAction(event -> handleAddUser(event));
        editButton.setOnAction(event -> handleEditUser(event));
        deleteButton.setOnAction(event -> handleDeleteUser(event));
        backButton.setOnAction(event -> handleGoBack(event));
        checkBoxColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        checkBoxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkBoxColumn));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        userTableView.setItems(userData);
        loadUserData();
    }

    @FXML
    private void handleGoBack(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage currentStage;
        try {
            // Load the home screen
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/home-view.fxml"));
            Scene homeScene = new Scene(fxmlLoader.load());

            // Get the current stage
            currentStage = (Stage) source.getScene().getWindow();
            currentStage.setTitle("Main Menu");
            // Set the home scene to the current stage
            currentStage.setScene(homeScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage;
        try {
            // Load the login screen
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/login-view.fxml"));
            Scene loginScene = new Scene(fxmlLoader.load());

            // Get the current stage
            currentStage = (Stage) source.getScene().getWindow();
            currentStage.setTitle("Login");
            // Set the login scene to the current stage
            currentStage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleAddUser(ActionEvent event) {
        Node source = (Node) event.getSource();
        try {
            // Load the add-club view
            Parent addUserRoot = FXMLLoader.load(getClass().getResource("/com/example/clubeventmanagementprogram/add-user.fxml"));
            Scene addUserScene = new Scene(addUserRoot);

            // Get the current stage and set the scene to add-club
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.setTitle("Add user");
            currentStage.setScene(addUserScene);

        } catch(IOException e) {
            // handle the exception (print stack trace or show an alert here)
            System.err.println("Error loading Add User page");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditUser(ActionEvent event) {
        // Initialize it here
        currentUser = userTableView.getSelectionModel().getSelectedItem();

        Node source = (Node) event.getSource();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/edit-user.fxml"));
            Parent editUserRoot = loader.load();

            EditUserController editUserController = loader.getController();
            editUserController.setUserUpdatable(this);
            // Get currently selected user and pass it to the EditUserController
            if(currentUser != null) {
                editUserController.setCurrentUser(currentUser);
                Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                currentStage.setTitle("Edit User");
                currentStage.setScene(new Scene(editUserRoot));
            } else {
                // Show some error message to the user or write some error log
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Selection Error");
                alert.setHeaderText("No User Selected");
                alert.setContentText("Please select a user to edit.");
                alert.showAndWait();
            }
        } catch(IOException e){
            System.err.println("Error loading Edit user page");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteUser(ActionEvent event) {
        currentUser = userTableView.getSelectionModel().getSelectedItem();
        if (currentUser == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setHeaderText("No User Selected");
            alert.setContentText("Please select a user to delete.");
            alert.showAndWait();
            return;
        }
        Node source = (Node) event.getSource();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/delete-user.fxml"));
            DeleteUserController controller = new DeleteUserController(userTableView);
            loader.setController(controller);
            Parent deleteUserRoot = loader.load();
            Scene deleteUserScene = new Scene(deleteUserRoot);
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.setTitle("Delete User");
            currentStage.setScene(deleteUserScene);
        } catch(IOException e){
            System.err.println("Error loading Delete User page");
            e.printStackTrace();
        }
    }

}

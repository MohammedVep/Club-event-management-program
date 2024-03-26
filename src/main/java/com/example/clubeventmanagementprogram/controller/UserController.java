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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
        checkBoxColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        checkBoxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkBoxColumn));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        userTableView.setItems(userData);
        loadUserData();
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
            // Get currently selected club and pass it to the EditClubController
            if(currentUser != null) {
                editUserController.setCurrentUser(currentUser);
                Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                currentStage.setScene(new Scene(editUserRoot));
            } else {
                // Show some error message to the user or write some error log
                System.err.println("No user was selected");
            }
        } catch(IOException e){
            System.err.println("Error loading Edit user page");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteUser(ActionEvent event) {
        Node source = (Node) event.getSource();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/delete-user.fxml"));
            DeleteUserController controller = new DeleteUserController(userTableView);
            loader.setController(controller);
            Parent deleteUserRoot = loader.load();
            Scene deleteUserScene = new Scene(deleteUserRoot);
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.setScene(deleteUserScene);
        } catch(IOException e){
            System.err.println("Error loading Delete User page");
            e.printStackTrace();
        }
    }

}

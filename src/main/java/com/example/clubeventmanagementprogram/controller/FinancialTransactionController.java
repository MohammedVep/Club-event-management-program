package com.example.clubeventmanagementprogram.controller;

import com.example.clubeventmanagementprogram.controller.FinancialTransactionActions.DeleteTransactionController;
import com.example.clubeventmanagementprogram.controller.FinancialTransactionActions.EditTransactionController;
import com.example.clubeventmanagementprogram.model.FinancialTransaction;
import com.example.clubeventmanagementprogram.model.User;
import com.example.clubeventmanagementprogram.service.AuthenticationService;
import com.example.clubeventmanagementprogram.service.FinancialTransactionService;
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
import java.util.List;
import static com.example.clubeventmanagementprogram.utils.Context.financialTransactionService;

public class FinancialTransactionController implements IFinancialTransactionUpdatable{

    private AuthenticationService authenticationService;
    @FXML
    private Label usernameLabel;

    @FXML
    private Label manageFinancialTransactionsLabel;

    @FXML
    private Button backButton;

    private FinancialTransaction currentFinancialTransaction;

    @FXML
    private TableColumn<FinancialTransaction, Boolean> checkboxColumn;

    @FXML
    private Button logoutButton;

    @FXML
    private TableView<FinancialTransaction> financialTransactionTableView;

    @FXML
    private TableColumn<FinancialTransaction, String> transactionNameColumn;

    @FXML
    private TableColumn<FinancialTransaction, LocalDate> dateColumn;

    @FXML
    private TableColumn<FinancialTransaction, String> descriptionColumn;

    @FXML
    private TableColumn<FinancialTransaction, Double> transactionAmountColumn;

    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    private ObservableList<FinancialTransaction> financialTransactionData = FXCollections.observableArrayList();

    public FinancialTransactionController(){
        this.authenticationService = new AuthenticationService();
    }
    @Override
    public void updateFinancialTransactionTable(){
        financialTransactionData.clear();
        List<FinancialTransaction> updatedList = financialTransactionService.getAllFinancialTransactions();
        financialTransactionData.addAll(updatedList);
    }
    private void loadFinancialTransactionData() {
        FinancialTransactionService financialTransactionService = Context.getFinancialTransactionService();
        List<FinancialTransaction> financialTransactionsFromDb = financialTransactionService.getAllFinancialTransactions();
        financialTransactionData.addAll(financialTransactionsFromDb);
    }

    public void initialize(){
        logoutButton.setOnAction(event -> handleLogout(event));
        addButton.setOnAction(event -> handleAddFinancialTransaction(event));
        editButton.setOnAction(event -> handleEditFinancialTransaction(event));
        deleteButton.setOnAction(event -> handleDeleteFinancialTransaction(event));
        backButton.setOnAction(event -> handleGoBack(event));

        // Set up the username label text
        User currentUser = authenticationService.getCurrentUser();
        if(currentUser != null) {
            usernameLabel.setText(currentUser.getUserName());
        }
        // Set cell value factory
        checkboxColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
// Set cell factory
        checkboxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkboxColumn));
        transactionNameColumn.setCellValueFactory(new PropertyValueFactory<>("transactionName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        transactionAmountColumn.setCellValueFactory(new PropertyValueFactory<>("transactionAmount"));
        financialTransactionTableView.setItems(financialTransactionData);
        loadFinancialTransactionData();
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
    private void handleAddFinancialTransaction(ActionEvent event){
        Node source = (Node) event.getSource();
        try {
            // Load the add-transaction view
            Parent addFinancialTransactionRoot = FXMLLoader.load(getClass().getResource("/com/example/clubeventmanagementprogram/add-transaction.fxml"));
            Scene addFinancialTransactionScene = new Scene(addFinancialTransactionRoot);

            // Get the current stage and set the scene to add-transaction
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.setTitle("Add Transaction");
            currentStage.setScene(addFinancialTransactionScene);

        } catch(IOException e) {
            // handle the exception (print stack trace or show an alert here)
            System.err.println("Error loading Add Financial Transaction page");
            e.printStackTrace();
        }
    }
    @FXML
    private void handleEditFinancialTransaction(ActionEvent event){
        currentFinancialTransaction = financialTransactionTableView.getSelectionModel().getSelectedItem();
        Node source = (Node) event.getSource();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/edit-transaction.fxml"));
            Parent editFinancialTransactionRoot = loader.load();
            EditTransactionController editTransactionController = loader.getController();
            editTransactionController.setFinancialTransactionUpdatable(this);
            if(currentFinancialTransaction != null){
                editTransactionController.setCurrentFinancialTransaction(currentFinancialTransaction);
                Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                currentStage.setTitle("Edit Transaction");
                currentStage.setScene(new Scene(editFinancialTransactionRoot));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Selection Error");
                alert.setHeaderText("No Transaction Selected");
                alert.setContentText("Please select a transaction to edit.");
                alert.showAndWait();
            }

        } catch(IOException e){
            System.err.println("Error loading Edit Financial Transaction page");
            e.printStackTrace();
        }
    }
    @FXML
    private void handleDeleteFinancialTransaction(ActionEvent event){
        currentFinancialTransaction = financialTransactionTableView.getSelectionModel().getSelectedItem();
        if (currentFinancialTransaction == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setHeaderText("No Transaction Selected");
            alert.setContentText("Please select a transaction to delete.");
            alert.showAndWait();
        }
        Node source = (Node) event.getSource();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/delete-transaction.fxml"));
            DeleteTransactionController controller = new DeleteTransactionController(financialTransactionTableView);
            loader.setController(controller);
            Parent deleteFinancialTransactionRoot = loader.load();
            Scene deleteFinancialTransactionScene = new Scene(deleteFinancialTransactionRoot);
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.setTitle("Delete Transaction");
            currentStage.setScene(deleteFinancialTransactionScene);

        } catch(IOException e){
            System.err.println("Error loading Delete Club page");
            e.printStackTrace();
        }
    }
}

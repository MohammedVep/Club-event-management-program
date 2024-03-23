package com.example.clubeventmanagementprogram.controller;

import com.example.clubeventmanagementprogram.model.FinancialTransaction;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class FinancialTransactionController {
    @FXML
    private Label usernameLabel;

    @FXML
    private Label manageFinancialTransactionsLabel;

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
        transactionNameColumn.setCellValueFactory(new PropertyValueFactory<>("transactionName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        transactionAmountColumn.setCellValueFactory(new PropertyValueFactory<>("transactionAmount"));
        financialTransactionTableView.setItems(financialTransactionData);
        loadFinancialTransactionData();
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage;
        try {
            // Load the login screen
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Users/mohammedvepari/IdeaProjects/Club-event-management-program/src/main/resources/com/example/clubeventmanagementprogram/login-view.fxml"));
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
    private void handleAddFinancialTransaction(ActionEvent event){
        // Code to work later
    }
    @FXML
    private void handleEditFinancialTransaction(ActionEvent event){
        // Code to work later
    }
    @FXML
    private void handleDeleteFinancialTransaction(ActionEvent event){
        // Code to work later
    }
}

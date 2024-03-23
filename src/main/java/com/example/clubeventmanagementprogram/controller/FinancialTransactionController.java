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
}

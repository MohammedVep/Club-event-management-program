package com.example.clubeventmanagementprogram.controller.FinancialTransactionActions;

import com.example.clubeventmanagementprogram.dao.FinancialTransactionDAO;
import com.example.clubeventmanagementprogram.model.FinancialTransaction;
import com.example.clubeventmanagementprogram.service.FinancialTransactionService;
import com.example.clubeventmanagementprogram.service.FinancialTransactionServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class AddTransactionController {

    @FXML
    private TextField transactionNameField;

    @FXML
    private DatePicker transactionDatePicker;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField transactionAmountField;
    private ObservableList<FinancialTransaction> financialTransactions;

    @FXML
    Button addButton;

    @FXML
    Button cancelButton;

    @FXML
    public void initialize() {
        // Cancel button action
        cancelButton.setOnAction(event -> {
            loadTransactionScene();
        });

        // Save button action
        addButton.setOnAction(event -> {
            handleSaveAction(event);
        });
    }

    public AddTransactionController(){

    }

    public AddTransactionController(ObservableList<FinancialTransaction> financialTransactions) {
        this.financialTransactions = financialTransactions;
    }

    FinancialTransactionDAO financialTransactionDao = new FinancialTransactionDAO();

    FinancialTransactionService financialTransactionService = new FinancialTransactionServiceImpl(financialTransactionDao);

    @FXML
    private void handleSaveAction(ActionEvent event){
        try {
            String transactionName = transactionNameField.getText();
            LocalDate transactionDate = transactionDatePicker.getValue();
            String description = descriptionField.getText();
            double transactionAmount;
            try{
                transactionAmount = Double.parseDouble(transactionAmountField.getText());
            } catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText("Invalid transaction amount");
                alert.setContentText("Please enter a valid number for the transaction amount.");
                alert.showAndWait();

                return;
            }
            List<FinancialTransaction> financialTransactionsFromDb = financialTransactionService.getAllFinancialTransactions();
            int financialTransactionId = financialTransactionsFromDb.size() + 1;
            FinancialTransaction newTransaction = new FinancialTransaction(financialTransactionId, transactionName, transactionDate, description, transactionAmount);
            newTransaction.setFinancial_id(financialTransactionId);
            FinancialTransactionService financialTransactionService = new FinancialTransactionServiceImpl(financialTransactionDao);
            financialTransactionService.addTransaction(newTransaction);
            // Load the Transaction view
            Parent transactionViewRoot = FXMLLoader.load(getClass().getResource("/com/example/clubeventmanagementprogram/financial-view.fxml"));
            Scene transactionViewScene = new Scene(transactionViewRoot);

            // Get the current Stage and set the scene to Transaction view
            Stage currentStage = (Stage) addButton.getScene().getWindow();
            currentStage.setScene(transactionViewScene);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void loadTransactionScene() {
        try {
            // Load Club scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/financial-view.fxml"));
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
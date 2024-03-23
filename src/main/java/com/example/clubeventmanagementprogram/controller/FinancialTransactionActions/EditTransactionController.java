package com.example.clubeventmanagementprogram.controller.FinancialTransactionActions;

import com.example.clubeventmanagementprogram.dao.FinancialTransactionDAO;
import com.example.clubeventmanagementprogram.model.FinancialTransaction;
import com.example.clubeventmanagementprogram.service.FinancialTransactionService;
import com.example.clubeventmanagementprogram.service.FinancialTransactionServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
public class EditTransactionController {

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
    Button editButton;

    @FXML
    Button cancelButton;

    private FinancialTransaction currentTransaction;

    @FXML
    public void initialize(){
        transactionNameField.setText(currentTransaction.getTransactionName());
        transactionDatePicker.setValue(currentTransaction.getDate());
        descriptionField.setText(currentTransaction.getDescription());
        transactionAmountField.setText(String.valueOf(currentTransaction.getTransactionAmount()));
        cancelButton.setOnAction(event -> {
            loadTransactionScene();
        });

        // Save button action
        editButton.setOnAction(event -> {
            handleSaveAction(event);
        });
    }

    public EditTransactionController(ObservableList<FinancialTransaction> financialTransactions) {
        this.financialTransactions = financialTransactions;
    }

    FinancialTransactionDAO financialTransactionDao = new FinancialTransactionDAO();

    @FXML
    private void handleSaveAction(ActionEvent event){
        try{
            currentTransaction.setTransactionName(transactionNameField.getText());
            currentTransaction.setDate(transactionDatePicker.getValue());
            currentTransaction.setDescription(descriptionField.getText());
            currentTransaction.setTransactionAmount(Double.parseDouble(transactionAmountField.getText()));

            FinancialTransactionService financialTransactionService = new FinancialTransactionServiceImpl(financialTransactionDao);
            financialTransactionService.updateTransaction(currentTransaction);

            // Load the Transaction view
            Parent transactionViewRoot = FXMLLoader.load(getClass().getResource("/Users/mohammedvepari/IdeaProjects/Club-event-management-program/src/main/resources/com/example/clubeventmanagementprogram/financial-view.fxml"));
            Scene transactionViewScene = new Scene(transactionViewRoot);

            // Get the current Stage and set the scene to Transaction view
            Stage currentStage = (Stage) editButton.getScene().getWindow();
            currentStage.setScene(transactionViewScene);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void loadTransactionScene() {
        try {
            // Load Club scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Users/mohammedvepari/IdeaProjects/Club-event-management-program/src/main/resources/com/example/clubeventmanagementprogram/financial-view.fxml"));
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

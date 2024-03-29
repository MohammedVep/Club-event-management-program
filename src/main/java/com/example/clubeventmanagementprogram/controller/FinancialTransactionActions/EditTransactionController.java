package com.example.clubeventmanagementprogram.controller.FinancialTransactionActions;

import com.example.clubeventmanagementprogram.controller.IFinancialTransactionUpdatable;
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

    private IFinancialTransactionUpdatable financialTransactionUpdatable;

    public void setFinancialTransactionUpdatable(IFinancialTransactionUpdatable financialTransactionUpdatable){
        this.financialTransactionUpdatable = financialTransactionUpdatable;
    }


    @FXML
    Button editButton;

    @FXML
    Button cancelButton;

    private FinancialTransaction currentTransaction;

    @FXML
    public void initialize(){
        cancelButton.setOnAction(event -> {
            loadTransactionScene();
        });

        // Save button action
        editButton.setOnAction(event -> {
            handleSaveAction(event);
        });
        if(currentTransaction != null){
            transactionNameField.setText(currentTransaction.getTransactionName());
            transactionDatePicker.setValue(currentTransaction.getDate());
            descriptionField.setText(currentTransaction.getDescription());
            transactionAmountField.setText(String.valueOf(currentTransaction.getTransactionAmount()));
        }
    }

    public EditTransactionController(){

    }

    public EditTransactionController(ObservableList<FinancialTransaction> financialTransactions) {
        this.financialTransactionService = new FinancialTransactionServiceImpl(new FinancialTransactionDAO());
    }

    FinancialTransactionDAO financialTransactionDao = new FinancialTransactionDAO();
    FinancialTransactionService financialTransactionService = new FinancialTransactionServiceImpl(financialTransactionDao);

    public void setCurrentFinancialTransaction(FinancialTransaction financialTransaction){
        this.currentTransaction = financialTransaction;
        System.out.println("Set transaction: " + financialTransaction);
        if(financialTransaction != null){
            transactionNameField.setText(currentTransaction.getTransactionName());
            transactionDatePicker.setValue(currentTransaction.getDate());
            descriptionField.setText(currentTransaction.getDescription());
            transactionAmountField.setText(String.valueOf(currentTransaction.getTransactionAmount()));
        }
    }

    @FXML
    private void handleSaveAction(ActionEvent event){
        try{
            currentTransaction.setTransactionName(transactionNameField.getText());
            currentTransaction.setDate(transactionDatePicker.getValue());
            currentTransaction.setDescription(descriptionField.getText());
            try {
                currentTransaction.setTransactionAmount(Double.parseDouble(transactionAmountField.getText()));
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText("Invalid transaction amount");
                alert.setContentText("Please enter a valid number for the transaction amount.");
                alert.showAndWait();

                return;
            }

            FinancialTransactionService financialTransactionService = new FinancialTransactionServiceImpl(financialTransactionDao);
            financialTransactionService.updateTransaction(currentTransaction);

            if (financialTransactionUpdatable != null){
                financialTransactionUpdatable.updateFinancialTransactionTable();
            }

            // Load the Transaction view
            Parent transactionViewRoot = FXMLLoader.load(getClass().getResource("/com/example/clubeventmanagementprogram/financial-view.fxml"));
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

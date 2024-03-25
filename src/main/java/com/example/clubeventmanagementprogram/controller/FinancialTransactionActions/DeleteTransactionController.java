package com.example.clubeventmanagementprogram.controller.FinancialTransactionActions;

import com.example.clubeventmanagementprogram.controller.FinancialTransactionController;
import com.example.clubeventmanagementprogram.dao.FinancialTransactionDAO;
import com.example.clubeventmanagementprogram.model.FinancialTransaction;
import com.example.clubeventmanagementprogram.service.FinancialTransactionService;
import com.example.clubeventmanagementprogram.service.FinancialTransactionServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
public class DeleteTransactionController {

    @FXML
    private TableView<FinancialTransaction> financialTransactionsTable;

    @FXML
    private Button deleteButton;
    @FXML
    private Button cancelButton;

    private FinancialTransactionDAO financialTransactionDao = new FinancialTransactionDAO();
    private FinancialTransactionService financialTransactionService = new FinancialTransactionServiceImpl(financialTransactionDao);

    public void initialize(){
        deleteButton.setOnAction(e -> deleteSelectedFinancialTransaction());
        cancelButton.setOnAction(e -> loadFinancialTransactionScene());
    }

    @FXML
    private void deleteSelectedFinancialTransaction() {
        FinancialTransaction selectedFinancialTransaction = financialTransactionsTable.getSelectionModel().getSelectedItem();

        if(selectedFinancialTransaction != null) {
            financialTransactionService.deleteTransaction(selectedFinancialTransaction.getFinancial_id());
            financialTransactionsTable.getItems().remove(selectedFinancialTransaction);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/financial-view.fxml"));
                Parent financialViewRoot = loader.load();
                Scene financialViewScene = new Scene(financialViewRoot);

                Stage currentStage = (Stage) deleteButton.getScene().getWindow();
                currentStage.setScene(financialViewScene);

            } catch (IOException e){
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("No Financial Transaction selected in the table.");
        }
    }

    @FXML
    private void loadFinancialTransactionScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/financial-view.fxml"));
            Parent financialViewRoot = loader.load();
            Scene financialViewScene = new Scene(financialViewRoot);

            Stage currentStage = (Stage) cancelButton.getScene().getWindow();
            currentStage.setScene(financialViewScene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

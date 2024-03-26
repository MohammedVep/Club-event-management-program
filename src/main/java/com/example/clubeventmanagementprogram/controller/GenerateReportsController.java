package com.example.clubeventmanagementprogram.controller;

import com.example.clubeventmanagementprogram.dao.FinancialTransactionDAO;
import com.example.clubeventmanagementprogram.model.FinancialTransaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class GenerateReportsController {

    private Stage mainStage;

    @FXML
    Button yesButton;

    @FXML
    Button noButton;

    public void setMainStage(Stage stage) {
        this.mainStage = stage;
    }

    @FXML
    public void initialize() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Generate Reports");
        alert.setHeaderText("Are you sure you want to generate financial history?");
        yesButton.setOnAction(event -> {
            generateFinancialHistory();
        });
        noButton.setOnAction(event -> {
            navigateToHomePage();
        });
    }

    private void generateFinancialHistory() {
        // Suppose you have a Data Access Object (DAO) for FinancialTransaction
        FinancialTransactionDAO financialTransactionDAO = new FinancialTransactionDAO();
        List<FinancialTransaction> transactions = financialTransactionDAO.getAllTransactions();

        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(doc, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.newLineAtOffset(25, 700);

            for (FinancialTransaction transaction : transactions) {
                String text = transaction.toString(); // make sure to override toString() in FinancialTransaction to format your output
                contentStream.showText(text);
                contentStream.newLineAtOffset(0, -15);
            }
            contentStream.endText();
            contentStream.close();

            doc.save("FinancialHistory.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clubeventmanagementprogram/home-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) noButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Failed to load the home page: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
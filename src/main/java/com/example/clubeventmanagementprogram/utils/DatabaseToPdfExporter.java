package com.example.clubeventmanagementprogram.utils;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class DatabaseToPdfExporter {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://p-5mcdvllaz4.pg.biganimal.io:5432/postgres";
        String user = "edb_admin";
        String password = "Rlo:On)YMYtL%Ob";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM clubs";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    try (PDDocument document = new PDDocument()) {
                        PDPage page = new PDPage();
                        document.addPage(page);

                        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                            contentStream.beginText();
                            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                            contentStream.newLineAtOffset(100, 700);

                            while (rs.next()) {
                                String clubName = rs.getString("name");
                                // Add more fields as needed
                                contentStream.showText("Club Name: " + clubName);
                                contentStream.newLineAtOffset(0, -20);
                            }

                            contentStream.endText();
                        }

                        document.save("clubs_data.pdf");
                    }
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
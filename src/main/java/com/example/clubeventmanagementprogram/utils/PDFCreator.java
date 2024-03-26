package com.example.clubeventmanagementprogram.utils;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.scene.chart.XYChart;

import java.io.FileNotFoundException;
import java.util.List;

public class PDFCreator {
    public static void createPdf(String filename, List<XYChart.Data> dataList) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(filename);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        for (XYChart.Data data : dataList) {
            Paragraph para = new Paragraph(data.toString());
            document.add(para);
        }
        document.close();
    }
}
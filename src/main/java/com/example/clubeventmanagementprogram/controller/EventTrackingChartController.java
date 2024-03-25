package com.example.clubeventmanagementprogram.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class EventTrackingChartController {

    @FXML
    private BarChart<String, Number> barChart;

    public void initialize() {
        barChart.setTitle("Event Tracking Data");

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Event Counts");

        // Add data to the series (you can fetch this data from your database)
        series.getData().add(new XYChart.Data<>("Event 1", 10));
        series.getData().add(new XYChart.Data<>("Event 2", 20));
        series.getData().add(new XYChart.Data<>("Event 3", 15));
        series.getData().add(new XYChart.Data<>("Event 4", 30));

        barChart.getData().add(series);
    }
}

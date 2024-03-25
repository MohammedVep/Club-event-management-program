import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class EventTrackingChart {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Event Tracking Chart");

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Event Tracking Data");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Event Counts");

        // Add data to the series (you can fetch this data from your database)
        series.getData().add(new XYChart.Data<>("Event 1", 10));
        series.getData().add(new XYChart.Data<>("Event 2", 20));
        series.getData().add(new XYChart.Data<>("Event 3", 15));
        series.getData().add(new XYChart.Data<>("Event 4", 30));

        barChart.getData().add(series);

        Scene scene = new Scene(barChart, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

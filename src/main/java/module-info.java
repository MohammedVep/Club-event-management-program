module com.example.clubeventmanagementprogram {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires kernel;
    requires layout;
    requires org.apache.pdfbox;

    opens com.example.clubeventmanagementprogram to javafx.fxml;
    exports com.example.clubeventmanagementprogram.application;
    exports com.example.clubeventmanagementprogram.controller to javafx.fxml;
    exports com.example.clubeventmanagementprogram.controller.clubActions to javafx.fxml;
    exports com.example.clubeventmanagementprogram.controller.FinancialTransactionActions to javafx.fxml;

    opens com.example.clubeventmanagementprogram.controller to javafx.base, javafx.fxml;
}
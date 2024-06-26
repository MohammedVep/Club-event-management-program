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
    requires java.desktop;

    opens com.example.clubeventmanagementprogram to javafx.fxml;
    exports com.example.clubeventmanagementprogram.application;
    exports com.example.clubeventmanagementprogram.controller to javafx.fxml;
    exports com.example.clubeventmanagementprogram.controller.clubActions to javafx.fxml;
    exports com.example.clubeventmanagementprogram.controller.FinancialTransactionActions to javafx.fxml;
    exports com.example.clubeventmanagementprogram.controller.eventActions to javafx.fxml;
    exports com.example.clubeventmanagementprogram.controller.userActions to javafx.fxml;
    exports com.example.clubeventmanagementprogram.model to javafx.base, javafx.fxml;

    opens com.example.clubeventmanagementprogram.controller to javafx.base, javafx.fxml;
    opens com.example.clubeventmanagementprogram.controller.clubActions to javafx.base, javafx.fxml;
    opens com.example.clubeventmanagementprogram.controller.FinancialTransactionActions to javafx.base, javafx.fxml;
    opens com.example.clubeventmanagementprogram.controller.userActions to javafx.base, javafx.fxml;
    opens com.example.clubeventmanagementprogram.controller.eventActions to javafx.base, javafx.fxml;
    opens com.example.clubeventmanagementprogram.model to javafx.base, javafx.fxml;
}
module workshop_javafx_jdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports application;
    exports gui;
    opens gui to javafx.fxml;
    opens application to javafx.fxml;

}
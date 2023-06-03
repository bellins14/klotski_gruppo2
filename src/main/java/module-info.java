module com.klotski.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires javafx.web;
    requires java.scripting;
    requires jdk.jsobject;
    requires com.fasterxml.jackson.databind;

    opens com.klotski.app to javafx.fxml;
    exports com.klotski.app;
}
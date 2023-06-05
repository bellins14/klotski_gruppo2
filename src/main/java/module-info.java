module com.project.klotski {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;


    opens com.klotski.app to javafx.fxml;
    exports com.klotski.app;
}
module com.project.klotski {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;


    opens com.project.klotski to javafx.fxml;
    exports com.project.klotski;
}
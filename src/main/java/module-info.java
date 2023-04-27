module com.example.progetto {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.progetto to javafx.fxml;
    exports com.example.progetto;
}
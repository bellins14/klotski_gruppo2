package com.project.klotski;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //prendo i dati dal file ".fxml"
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view.fxml"));
        //creo una nuova "scene"
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);

        stage.setScene(scene);
        //Controller controller = fxmlLoader.getController();
        //imposto una dimensione fissa della schermata, ovvero decido che l'utente non può ingrandirla
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
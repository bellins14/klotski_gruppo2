package com.klotski.app;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Carico il file "view.fxml", il quale corrisponde al file che viene utilizzato in Scene Builder per definire
        //l'interfaccia utente
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view.fxml"));
        //Creo una scena che contiene il contenuto dell'interfaccia utente (1000 x 700)
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        stage.setScene(scene);
        //aggiungo un foglio di stile alla scena corrente
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        //impedisco all'utente di ridimensionare la finestra
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
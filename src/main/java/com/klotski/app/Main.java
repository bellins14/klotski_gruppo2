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

        /*
        /*webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                // Esegui lo script JavaScript nel file HTML
                //webEngine.executeScript("yourJavaScriptCode();");
                System.out.println("DIO");
            }
            else if(newValue == Worker.State.FAILED){
                System.out.println("CANE");
            }
        });*/
       FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view.fxml"));
        //creo una nuova "scene"
        Scene scene = new Scene(fxmlLoader.load(),1000,700);
        stage.setScene(scene);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        //imposto una dimensione fissa della schermata, ovvero decido che l'utente non può ingrandirla
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
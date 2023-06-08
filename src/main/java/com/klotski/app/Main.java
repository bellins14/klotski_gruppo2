package com.klotski.app;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * Classe che rappresenta l'inizializzazione dell'interfaccia grafica
 */
public class Main extends Application {

    /**
     * Metodo principale per inzializzare l'applicazione
     *
     * @param stage oggetto della classe Stage che rappresenta l'elemento principale
     *              in cui vengono visualizzate le interfaccie grafiche dell'applicazione
     * */
    @Override
    public void start(Stage stage) {
        // Carica l'icona dall'URL specificato
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("img/icons/icon.png")));

        // Imposta l'icona dell'applicazione
        stage.getIcons().add(icon);

        // Imposta il titolo dell'applicazione
        stage.setTitle("Klotski Game - Gruppo2");

        // Carico il file "view.fxml", il quale corrisponde al file che viene utilizzato in Scene Builder per definire
        //l'interfaccia utente
        //Creo una scena che contiene il contenuto dell'interfaccia utente (1000 x 700)
        Scene scene = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view.fxml")); //TODO: sposta in costants
            scene = new Scene(fxmlLoader.load(), 1000, 700);
            stage.setScene(scene);
            //aggiungo un file css alla scena
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            //impedisco all'utente di ridimensionare la finestra
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception e) {
            Utility.setAlert(Alert.AlertType.ERROR,"Errore nel caricamento dell'applicazione",e.toString());
        }


    }
    /**
     * Metodo main dove si lancia l'applicazione
     *
     * @param args eventuali parametri da terminale
     * */
    public static void main(String[] args) {
        launch();
    }
}
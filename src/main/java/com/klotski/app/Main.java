package com.klotski.app;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.Objects;

import static com.klotski.app.Constants.*;

/**
 * Classe che rappresenta l'inizializzazione dell'UI
 */
public class Main extends Application {

    /**
     * Metodo principale per inizializzare l'applicazione
     *
     * @param stage oggetto della classe Stage che rappresenta l'elemento principale
     *              in cui vengono visualizzate le interfacce grafiche dell'applicazione
     * */
    @Override
    public void start(Stage stage) {

        // Carica l'icona dall'URL specificato
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(ICON_PATH_NAME)));

        // Imposta l'icona dell'applicazione
        stage.getIcons().add(icon);

        // Imposta il titolo dell'applicazione
        stage.setTitle(APPLICATION_NAME);

        // Carica il file "view.fxml", il quale corrisponde al file che viene utilizzato in Scene Builder per definire
        //l'interfaccia utente
        //Crea una scena che contiene il contenuto dell'interfaccia utente (1000 x 700)
        Scene scene;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(VIEW_FXML_FILENAME));
            scene = new Scene(fxmlLoader.load(), SCENE_V, SCENE_V1);
            stage.setScene(scene);
            //aggiungo un file css alla scena
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(STYLE_CSS_FILENAME)).toExternalForm());
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
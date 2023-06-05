package com.klotski.app;

import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.*;

/**
 * Classe utilizzata per metodi / varibili ausiliari
 */
public class HelperFunctions {
    static  int EMPTY_LOG_SIZE = 0;
    static int SINGLE_LOG_SIZE = 1;


    /**
     * Metodo che si occupa del cambio di configurazione una volta clickato il bottone apposito.
     *
     * @param alertType tipo di alert
     * @param title titolo dell'alert
     * @param text contenuto dell'alert
     */
    public static void setAlert(Alert.AlertType alertType, String title, String text){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }


    /**
     * Metodo che si occupa di controllare se è presente una connessione ad internet, utilizzato per
     * la NBM
     */
    public static boolean isInternetConnected() {

        try {
            URI uri = new URI("https://www.google.com"); // Sostituisci con l'URI di un server noto
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");

            int responseCode = connection.getResponseCode();
            return (responseCode == HttpURLConnection.HTTP_OK);
        }
        catch (UnknownHostException host){
            return  false;
        }
        catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }


}

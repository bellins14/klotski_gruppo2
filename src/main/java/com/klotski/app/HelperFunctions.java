package com.klotski.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;
import java.io.FileWriter;
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
    /**
     * Metodo che serve per riscrivere il file per la richiesta della NBM.
     *
     * @throws IOException Eccezione per scrittura file "solver.html"
     */
    static void updateHTMLFile(Configuration _configuration) throws IOException {
        ObjectMapper om = new ObjectMapper();
        String game = "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <title>Klotski Game</title>\n" +
                "    <script src=\"https://unpkg.co/klotski/dist/klotski.min.js\"></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<script>\n" +
                "      var klotski = new Klotski();\n" +
                "      var game = " +
                "          " + om.writeValueAsString(_configuration) +
                "\n" +
                "var result = klotski.solve(game);" +
                "</script>" +
                "</body>\n" +
                "</html>";
        FileWriter file = new FileWriter("src/main/resources/com/klotski/app/solver.html");
        file.write(game);
        file.close();
    }



    public static int extractIntValue(String jsonString, String key) {
        int valueStart = jsonString.indexOf("\"" + key + "\":") + (key.length() + 3);
        int valueEnd = jsonString.indexOf(",", valueStart);

        if (valueEnd == -1) {
            valueEnd = jsonString.indexOf("}", valueStart);
        }

        String valueString = jsonString.substring(valueStart, valueEnd).trim();

        return Integer.parseInt(valueString);
    }




}

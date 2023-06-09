package com.klotski.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import java.io.FileWriter;
import java.io.IOException;
import java.net.*;

import static com.klotski.app.Constants.*;

/**
 * Classe che fornisce dei metodi di utilità per le classi del Klotski.
 */
public class Utility {

    private Utility() {
        // Costruttore privato per evitare l'istanziazione della classe
    }


    /**
     * Metodo per mostrare una finestra di alert.
     *
     * @param alertType tipo di alert
     * @param title     titolo dell'alert
     * @param text      contenuto dell'alert
     */
    public static void setAlert(Alert.AlertType alertType, String title, String text) {
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
        } catch (UnknownHostException host) {
            return false;
        } catch (IOException | URISyntaxException e) {
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
        FileWriter file = new FileWriter(NBM_SOLVER_HTML_FILE);
        file.write(game);
        file.close();
    }


    /**
     * Metodo per estrarre l'intero associato ad una key di una stringa in formato JSON
     * @param jsonString stringa in formato json.
     * @param key chiave.
     * @return intero associato
     */
    public static int extractIntValue(String jsonString, String key) {
        int valueStart = jsonString.indexOf("\"" + key + "\":") + (key.length() + 3);
        int valueEnd = jsonString.indexOf(",", valueStart);

        if (valueEnd == -1) {
            valueEnd = jsonString.indexOf("}", valueStart);
        }

        String valueString = jsonString.substring(valueStart, valueEnd).trim();

        return Integer.parseInt(valueString);
    }


    /**
     * Metodo che controlla che non ci sia overlapping tra pezzi durante il loro spostamento.
     *
     * @param piece pezzo che si vuole.
     * @param deltaX quantità di cui si muove il pezzo orizzontalmente.
     * @param deltaY quantità di cui si muove il pezzo verticalmente.
     * @return false se si overlappa, true se è tutto a posto.
     */
    public static boolean isNotOverlapping(Piece piece, Pane blockPane, double deltaX, double deltaY) {
        // Calcola la nuova posizione del bottone
        double newX = piece.getLayoutX() + deltaX;
        double newY = piece.getLayoutY() + deltaY;

        // Itera su tutti gli elementi figli della Pane
        ObservableList<Node> children = blockPane.getChildren();
        for (Node child : children) {
            // Verifica se l'elemento figlio è un bottone diverso da quello selezionato
            if (child instanceof Piece otherPiece && child != piece) {
                // Verifica se il nuovo bottone si sovrappone all'altro bottone
                if (newX + piece.getWidth() > otherPiece.getLayoutX() &&
                        newX < otherPiece.getLayoutX() + otherPiece.getWidth() &&
                        newY + piece.getHeight() > otherPiece.getLayoutY() &&
                        newY < otherPiece.getLayoutY() + otherPiece.getHeight()) {
                    return false;
                }
            }
        }

        return true;
    }

}

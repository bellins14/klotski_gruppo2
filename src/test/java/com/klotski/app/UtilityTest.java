package com.klotski.app;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.klotski.app.Constants.NBM_SOLVER_HTML_FILE;
import static com.sun.javafx.webkit.KeyCodeMap.lookup;
import static org.junit.jupiter.api.Assertions.*;

class UtilityTest {

    //Test del metodo isInternetConnected()
    //Eseguibile solo con la connessione a internet attiva e funzionante
    @Test
    void isInternetConnected() {
        System.out.println("test isInternetConnected");

        //Prendi il risultato di isInternetConnected
        boolean result = Utility.isInternetConnected();

        //Controlla che sia true
        assertTrue(result);
    }

    //Test del metodo updateHTMLFile()
    @Test
    void updateHTMLFile() throws JsonProcessingException {
        System.out.println("test updateHTMLFile");

        //Crea una nuova configurazione iniziale 1
        Configuration configuration = new Configuration();

        assertDoesNotThrow(() -> {
            Utility.updateHTMLFile(configuration);
        }, "Expected updateHTMLFile() not to throw an exception.");

        //Verifica che il file sia stato creato correttamente
        File file = new File(NBM_SOLVER_HTML_FILE);
        assertTrue(file.exists(), "Expected HTML file to be created.");

        //Verifica il contenuto del file
        String fileContent = null;
        try {
            fileContent = Files.readString(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Controlla che il contenuto del file non sia null
        assertNotNull(fileContent);

        //Verifica che il contenuto del file corrisponda all'output atteso
        String expectedContent = "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <title>Klotski Game</title>\n" +
                "    <script src=\"https://unpkg.co/klotski/dist/klotski.min.js\"></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<script>\n" +
                "      var klotski = new Klotski();\n" +
                "      var game = " +
                "          " + new ObjectMapper().writeValueAsString(configuration) +
                "\n" +
                "var result = klotski.solve(game);" +
                "</script>" +
                "</body>\n" +
                "</html>";

        //Controlla
        assertEquals(expectedContent, fileContent);
    }

    //Test del metodo extractIntValue()
    @Test
    void extractIntValue() {
        System.out.println("test extractIntValue");

        //Stringa da testare
        String testingJsonString = "{\"key1\": 10, \"key2\": 20, \"key3\": 30}";

        //Chiave della stringa da testare
        int value1 = Utility.extractIntValue(testingJsonString, "key1");

        //Controlla che l'intero estratto sia uguale a 10
        assertEquals(10, value1);

        //Chiave della stringa da testare
        int value2 = Utility.extractIntValue(testingJsonString, "key2");

        //Controlla che l'intero estratto sia uguale a 20
        assertEquals(20, value2);

        //Chiave della stringa da testare
        int value3 = Utility.extractIntValue(testingJsonString, "key3");

        //Controlla che l'intero estratto sia uguale a 30
        assertEquals(30, value3);
    }
}
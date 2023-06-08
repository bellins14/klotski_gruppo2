package com.klotski.app;

import com.jfoenix.controls.*;
import javafx.animation.StrokeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.*;
import java.util.Objects;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import static com.klotski.app.Constants.*;


/**
 * Classe che gestisce tutta la logica dell'applicazione.
 */
public class Controller {
    //Pannello "Pane" dove andrò a inserire i vari Piece
    @FXML
    private Pane blockPane;
    @FXML
    private JFXButton undo;
    //Testo per il numero di mosse
    @FXML
    private Text textCounter;
    @FXML
    private JFXButton reset;
    @FXML
    private JFXButton NBM;
    private Game game;

    //un bottone posso muoverlo con le frecce solo dopo averlo selezionato con il mouse
    private Piece selectedBlock;




    /**
     * Costruttore di default
     */
    public Controller() {
    }


    /**
     * Metodo chiamato di default all'avvio dell'applicazione.
     * Inizializza la pane e gestisce gli input da tastiera e da mouse sui blocchi.
     */
    //primo metodo chiamato di default
    public void initialize() {

        //Crea un nuovo gioco
        game = new Game();

        //Setta il counter con le mosse
        textCounter.setText("Moves : " + game.getCounter());

        //Setta la dimensione del pane
        blockPane.setMaxWidth(400);
        blockPane.setMaxHeight(500);

        //Prende i blocchi della configurazione attuale
        Piece[] blocks = game.getConfiguration().getPieces();

        //Inizializza il pane con i blocchi della configurazione attuale
        for (Piece block : blocks) {

            // renderizza l'immagine per ogni blocco (parte che era in Piece)
            Image pieceImage = new Image(Objects.requireNonNull(getClass().getResource(block.getImageName())).toString());
            ImagePattern piecePattern = new ImagePattern(pieceImage);
            block.setFill(piecePattern);
            blockPane.getChildren().add(block);

            //forse va fuori
            blockPane.setStyle("-fx-border-color: black");

            // Aggiunge un gestore eventi per la selezione di un bottone
            block.setOnMouseClicked(event -> {
                selectedBlock = (Piece) event.getSource();
                for (Piece b : blocks) {
                    b.setEffect(null);
                    b.setStrokeWidth(3);
                }
                // Attiva l'illuminazione del pulsante selezionato
                StrokeTransition strokeTransition = new StrokeTransition(Duration.millis(200), block);
                strokeTransition.setFromValue(Color.grayRgb(3));
                strokeTransition.setToValue(Color.grayRgb(6));
                strokeTransition.setCycleCount(2);
                strokeTransition.setAutoReverse(true);
                strokeTransition.play();
                selectedBlock = block;
                // aumenta lo spessore del bordo
                block.setStrokeWidth(5);
            });
        }

        // Stampa stato all'interno del file ConfigurationLog.json
        // Inserisco il primo record relativo alla configurazione iniziale


        // Aggiunge un gestore eventi per la pressione dei tasti freccia
        blockPane.setOnKeyPressed(event -> {
            if (selectedBlock != null) {
                //di quanto si deve spostare il mio bottone selezionato
                //tutte le casistiche per evitare che il bottone vada fuori dalla Pane e che non si sovrapponga con altri bottoni
                //getCode mi traduce il comando da tastiera in un codice
                int keyCode = event.getCode().ordinal();
                game.moveBlock(selectedBlock, blockPane, keyCode, game.getConfiguration(), game.getLog());
                if (game.checkWin(blockPane)) {
                    reset();
                }
                textCounter.setText("Moves : " + game.getCounter());

            }

        });
        // Per consentire il focus della tastiera sul pannello
        blockPane.setFocusTraversable(true);
    }


    /**
     * Metodo che si occupa del reset alla configurazione di partenza designata.
     */
    @FXML
    void reset() {
        game.setCounter(0);
        textCounter.setText("Moves : " + game.getCounter());
        blockPane.getChildren().clear();

        int ls = game.getLog().size();

        for (int i = 1; i < ls; i++) {
            game.popLog();
        }

        //Ora gameLog ha solo 1 elemento, la configurazione iniziale
        UtilityJackson.serializeConfigurationLog(game.getLog(), LOG_FILE); // Aggiorno lo storico
        UtilityJackson.serializeConfiguration(game.getLog().peek(), DC_FILE); // Aggiorno la serializzazione
        initialize();
    }


    /**
     * Metodo che si occupa del cambio di configurazione una volta cliccato il bottone apposito.
     *
     * @param event classe FXML che codifica un evento.
     */
    @FXML
    void configurationClicked(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        int configurationIndex = Integer.parseInt(clickedButton.getUserData().toString());
        if (game.getSelectedConf() != configurationIndex) {
            game.setCounter(0);
            textCounter.setText("Moves : " + game.getCounter());
            game.setSelectedConf(configurationIndex);
            blockPane.getChildren().clear();
            game.clearLog();
            game.setConfiguration(new Configuration(game.getSelectedConf()));
            game.jacksonSerialize();
            initialize();
        }
    }


    /**
     * Metodo che abilita l'esecuzione di JavaScript nella WebView, quindi registra un listener per il cambio di stato del caricamento del worker della WebView.
     * Quando il caricamento è completato con successo (Worker.State.SUCCEEDED), viene eseguito uno script JavaScript nella pagina caricata (NBM).
     * Il risultato della NBM prodotto dallo script viene utilizzato per spostare un nodo nell'interfaccia utente.
     * In caso di errore durante il caricamento (Worker.State.FAILED), viene stampato un messaggio di errore.
     *
     * @throws IOException Eccezione per apertura/chiusura file "solver.html"
     */
    @FXML
    void nextBestMove() throws IOException {
        if (Utility.isInternetConnected()) {
            NBM.setDisable(true);
            game.nextBestMove(blockPane, textCounter);
            if (game.checkWin(blockPane)) {
                reset();
            }
            Timer timer = new Timer();
            TimerTask enableButtonNBM = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> NBM.setDisable(false));
                }
            };

            timer.schedule(enableButtonNBM, 500);
        } else {
            Utility.setAlert(Alert.AlertType.ERROR, "Errore", "NBM non disponibile, connettiti a internet");
        }
    }

    /**
     * Metodo che gestisce undo.
     */
    @FXML
    void undo() {
        if (game.getCounter() != 0) {
            game.setCounter(game.getCounter() - 1);
            textCounter.setText("Moves : " + game.getCounter());
            blockPane.getChildren().clear();

            game.popLog();
            UtilityJackson.serializeConfigurationLog(game.getLog(), LOG_FILE);
            UtilityJackson.serializeConfiguration(game.getLog().peek(), DC_FILE);
            initialize();
        } else {
            Utility.setAlert(Alert.AlertType.WARNING, "Undo", "Non hai spostato nessun blocco!");
        }
    }
}
package com.klotski.app;

import com.jfoenix.controls.*;
import javafx.animation.StrokeTransition;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import java.io.*;
import java.util.Objects;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe che gestisce tutta la logica dell'applicazione.
 */
public class Controller {
    //Pannello "Pane" dove andrò a inserire i vari Piece
    @FXML
    private Pane blockPane;
    @FXML
    private JFXButton undo;
    private WebEngine webEngine = new WebEngine();
    //Testo per il numero di mosse
    @FXML
    private Text textCounter;
    @FXML
    private JFXButton reset;
    @FXML
    private JFXButton NBM;
    private final Game game;
    protected Configuration _configuration;
    //un bottone posso muoverlo con le frecce solo dopo averlo selezionato con il mouse
    private Piece selectedBlock;
    //configurazione selezionata
    private int selectedConf;
    private Stack<Configuration> log;


    /**
     * Costruttore di default
     */
    public Controller() {
        selectedConf = 1;
        log = new Stack<>();
        _configuration = new Configuration(selectedConf);
        game = new Game();
    }


    /**
     * Metodo chiamato di default all'avvio dell'applicazione.
     * Inizializza la pane e gestisce gli input da tastiera e da mouse sui blocchi.
     */
    //primo metodo chiamato di default
    public void initialize() {
        // Leggiamo il log
        log = UtilityJackson.deserializeConfigurationLog();
        if (log.size() == HelperFunctions.EMPTY_LOG_SIZE) { // Il log è vuoto
            _configuration = new Configuration(selectedConf);
            // ser(conf) -> des(conf) -> log.push(des(conf)) -> ser(stack);
            UtilityJackson.serializeConfiguration(_configuration);
            log.push(UtilityJackson.deserializeConfiguration());
            UtilityJackson.serializeConfigurationLog(log);
            // Debug
            //System.out.println("Nessuna Configurazione Salvata");
            game.setCounter(0);

        } else if (log.size() == HelperFunctions.SINGLE_LOG_SIZE) { // Nel log c'è solo una configurazione, quella iniziale.
            _configuration = UtilityJackson.deserializeConfiguration();
            selectedConf = Configuration.isInitialConfiguration(_configuration);
            // Debug
            //System.out.println("Una Configurazione Salvata");
            game.setCounter(0);

        } else { // Nel log c'è più di una configurazione
            _configuration = game.getInitConfiguration(log);
            selectedConf = Configuration.isInitialConfiguration(_configuration);
            UtilityJackson.serializeConfiguration(log.peek());
            _configuration = UtilityJackson.deserializeConfiguration();
            game.setCounter(log.size() - 1);
            textCounter.setText("Moves : " + game.getCounter());
        }

        // Inizializzo _configuration e gli passo come parametro la configurazione iniziale
        // _configuration = new Configuration(selectedConf);
        Piece[] blocks = _configuration.getBlocks();


        // Da non toccare
        blockPane.setMaxWidth(400);
        blockPane.setMaxHeight(500);
        //con questo ciclo for inizializzo la pane
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
                game.moveBlock(selectedBlock,blockPane,keyCode,_configuration,log);
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
        int ls = log.size();
        for (int i = 1; i < ls; i++) {
            log.pop();
        }
        UtilityJackson.serializeConfigurationLog(log); // Aggiorno lo storico
        UtilityJackson.serializeConfiguration(log.peek()); // Aggiorno la serializzazione
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
        if (selectedConf != configurationIndex) {
            game.setCounter(0);
            textCounter.setText("Moves : " + game.getCounter());
            //conf = configurationIndex;
            selectedConf = configurationIndex;
            blockPane.getChildren().clear();
            log.clear();
            _configuration = new Configuration(selectedConf);
            // ser(conf) -> des(conf) -> log.push(des(conf)) -> ser(stack);
            UtilityJackson.serializeConfiguration(_configuration);
            log.push(UtilityJackson.deserializeConfiguration());
            UtilityJackson.serializeConfigurationLog(log);
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
        if (HelperFunctions.isInternetConnected()) {
            NBM.setDisable(true);
            //NBM.setDisable(true);
            //aggiorno il file html con la nuova configurazione

            //carico il file html
            game.NBMDAJE(blockPane, _configuration, log,textCounter);
            if(game.checkWin(blockPane)){
                reset();
            }
            // utilizzo la classe Timer cosi tengo conto della corretta gestione del threading e non blocco
            //l'interfaccia utente durante il ritardo
            Timer timer = new Timer();
            TimerTask enableButtonNBM = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> NBM.setDisable(false));
                }
            };

            timer.schedule(enableButtonNBM, 500);
        } else {
            HelperFunctions.setAlert(Alert.AlertType.ERROR, "ERRORE", "NBM NON DISPONBILE, CONNETTITI AD INTERNET");
        }
    }


    /**
     * Metodo che serve per caricare il file per il risolvimento della NBM.
     */


    /**
     * Metodo che gestisce undo.
     */
    @FXML
    void undo() {
        if (game.getCounter() != 0) {
            game.setCounter(game.getCounter()- 1);
            textCounter.setText("Moves : " + game.getCounter());
            blockPane.getChildren().clear();
            log.pop();
            UtilityJackson.serializeConfigurationLog(log);
            UtilityJackson.serializeConfiguration(log.peek());
            initialize();
        } else {
            HelperFunctions.setAlert(Alert.AlertType.WARNING, "UNDO", "NON HAI SPOSTATO NESSUN BLOCCO!");
        }
    }
}
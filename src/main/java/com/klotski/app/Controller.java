package com.klotski.app;

import com.jfoenix.controls.*;
import javafx.animation.StrokeTransition;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import java.io.*;
import java.util.Objects;

import static com.klotski.app.Constants.*;


/**
 * Classe che gestisce l'interazione del gioco con la grafica (Java FX).
 */
public class Controller {

    //Elementi di FXML:

    //Pannello "Pane" dove inserire i pezzi della configurazione attuale del gioco
    @FXML
    private Pane blockPane;
    //Bottone undo
    @FXML
    private JFXButton undo;
    //Testo per il numero di mosse
    @FXML
    private Text textCounter;
    //Bottone reset
    @FXML
    private JFXButton reset;
    //Bottone NBM
    @FXML
    private JFXButton NBM;


    private boolean gameEnded = false;

    //Gioco
    private Game game;

    //Pezzo selezionato: un pezzo puo' essere mosso solo con le frecce della tastiera
    // solo dopo essere stato selezionato con il mouse
    private Piece selectedPiece;

    //Necessario per la connessione all'NBM
    private WebEngine webEngine;

    //Costruttore non necessario

    /**
     * Metodo chiamato di default all'avvio dell'applicazione.
     * Inizializza la pane e gestisce gli input da mouse e da tastiera sui pezzi.
     */
    public void initialize() {
        //Crea un nuovo gioco
        game = new Game(LOG_FILE,DC_FILE);

        //Setta il counter con le mosse
        textCounter.setText("Moves : " + game.getMoveCounter());

        //Setta le dimensioni massime del pane
        blockPane.setMaxHeight(MAX_PANE_HEIGHT);
        blockPane.setMaxWidth(MAX_PANE_WIDTH);

        //Setta il border color della pane
        blockPane.setStyle(PANE_BORDER_COLOR);

        //Prende i pezzi della configurazione attuale
        Piece[] currentPieces = game.getConfiguration().getPieces();

        //Per ogni pezzo della configurazione attuale
        for (Piece piece : currentPieces) {

            //Renderizza l'immagine del pezzo (parte esclusivamente grafica)
            Image pieceImage = new Image(Objects.requireNonNull(getClass().getResource(piece.getImageName())).toString());
            ImagePattern piecePattern = new ImagePattern(pieceImage);
            piece.setFill(piecePattern);

            //Aggiunge il pezzo al pane
            blockPane.getChildren().add(piece);


            //Aggiunge un gestore eventi per la selezione di un bottone
            piece.setOnMouseClicked(event -> {
                selectedPiece = (Piece) event.getSource();

                //Diminuisci lo spessore per tutti i pezzi
                for (Piece p : currentPieces) {
                    p.setEffect(null);
                    p.setStrokeWidth(UNSELECTED_PIECE_STROKE_WIDTH);
                }

                //Attiva l'illuminazione del pulsante selezionato
                StrokeTransition strokeTransition = new StrokeTransition(Duration.millis(STROKE_TRANSITION_MILLIS), piece);
                strokeTransition.setFromValue(STROKE_START_COLOR);
                strokeTransition.setToValue(STROKE_END_COLOR);
                strokeTransition.setCycleCount(CYCLE_COUNT);
                strokeTransition.setAutoReverse(TRANSITION_AUTOREVERSE);
                strokeTransition.play();
                selectedPiece = piece;

                //Aumenta lo spessore del per il pezzo selezionato
                piece.setStrokeWidth(SELECTED_PIECE_STROKE_WIDTH);
            });
        }

        //Aggiunge un gestore eventi per la pressione dei tasti freccia
        blockPane.setOnKeyPressed(event -> {
            if (selectedPiece != null) {

                //getCode traduce il comando da tastiera in un codice
                int keyCode = event.getCode().ordinal();

                //Sposta il pezzo
                try {
                    game.movePiece(selectedPiece, keyCode);
                    //Aggiorna il testo con il counter delle mosse
                    textCounter.setText("Moves : " + game.getMoveCounter());

                }catch (Exception e){
                    //Il giocatore ha vinto
                    //Resetta il gioco
                    reset();
                    //Lancia alert di vittoria
                    Utility.setAlert(Alert.AlertType.INFORMATION, "Vittoria", "Hai vinto");

                }
            }

        });
        // Per consentire il focus della tastiera sul pannello
        blockPane.setFocusTraversable(true);
    }


    /**
     * Metodo chiamato quando viene premuto il tasto reset
     * Si occupa del reset alla configurazione di partenza designata.
     */
    @FXML
    private void reset() {
        //Cambia la configurazione attuale di game con la configurazione iniziale
        game.reset(game.getInitialSelectedConf());

        //Pulisce il pane
        blockPane.getChildren().clear();

        //Fai ripartire l'inizializzazione
        initialize();
    }


    /**
     * Metodo chiamato quando viene premuto il tasto cambio di configurazione iniziale
     * Si occupa di sostituire la configurazione attuale con la configurazione iniziale selezionata.
     *
     * @param event classe FXML che codifica un evento.
     */
    @FXML
    private void configurationClicked(ActionEvent event) {

        //Prendi il bottone cliccato
        Button clickedButton = (Button) event.getSource();

        //Prendi il numero della configurazione selezionata
        int configurationNumber = Integer.parseInt(clickedButton.getUserData().toString());

        //Se la configurazione iniziale selezionata è diversa da quella attuale
        if (game.getInitialSelectedConf() != configurationNumber) {

            //Cambia la configurazione attuale di game
            game.reset(configurationNumber);

            //Aggiorna il testo con il counter delle mosse
            textCounter.setText("Moves : " + game.getMoveCounter());

            //Pulisci il pane
            blockPane.getChildren().clear();

            //Fai ripartire l'inizializzazione
            initialize();
        }
    }


    /**
     * Metodo chiamato quando viene premuto il tasto NBM
     * Abilita l'esecuzione di JavaScript nella WebView, quindi registra un listener per il cambio di stato del caricamento del worker della WebView.
     * Quando il caricamento è completato con successo (Worker.State.SUCCEEDED), viene eseguito uno script JavaScript nella pagina caricata (NBM).
     * Il risultato della NBM prodotto dallo script viene utilizzato per spostare un nodo nell'interfaccia utente.
     * In caso di errore durante il caricamento (Worker.State.FAILED), viene stampato un messaggio di errore.
     *
     * @throws IOException Eccezione per apertura/chiusura file "solver.html"
     */
    @FXML
    private void nextBestMove() throws IOException {
        //guardo se il computer è connesso a internet perché per lo script JS ci serve essere connessi
        if (Utility.isInternetConnected() || !gameEnded) {
            try {
                //disabilito il bottone per evitare che l'utente clicchi più volte e quindi mandi troppe richieste
                NBM.setDisable(true);
                //aggiorno il file html dove è presente lo script con la configurazione attuale
                Utility.updateHTMLFile(game.getConfiguration());
                //carico il file html
                loadHTMLFile();
                //abilito JS
                this.webEngine.setJavaScriptEnabled(true);
                //aggiungo un listener per vedere quando ha finito il caricamento del file
                this.webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                    //se il caricamento è andato a buon fine
                    if (newValue == Worker.State.SUCCEEDED) {
                        // Esegui lo script JavaScript nella pagina caricata nella WebView
                        Object result = webEngine.executeScript(NBM_SCRIPT);
                        //se il risultato è una Stringa estrapolo le informazioni perché l'output
                        //è {step: 1, blockIdx: 6, dirIdx: 0} quindi devo estrapolare le informazioni che
                        //mi interessano
                        if (result instanceof String jsonString) {
                            jsonString = jsonString.substring(1, 35);
                            int blockIdx = Utility.extractIntValue(jsonString, "blockIdx");
                            int dirIdx = Utility.extractIntValue(jsonString, "dirIdx");
                            //converto le mosse NBM in valori interi che corrispondo alle frecce della tastiera
                            dirIdx = (dirIdx == 0) ? ARROW_DOWN : ((dirIdx == 1) ? ARROW_RIGHT : ((dirIdx == 2) ? ARROW_UP : ((dirIdx == 3) ? ARROW_LEFT : -1)));
                            //prendo il corrispettivo nodo che dovrò spostare
                            Node node = blockPane.getChildren().get(blockIdx);

                            try {
                                //Sposta il pezzo
                                game.movePiece((Piece) node, dirIdx);

                                //riabilita il bottone NBM
                                NBM.setDisable(false);

                                //Aggiorna il testo con il counter delle mosse
                                textCounter.setText("Moves : " + game.getMoveCounter());

                            }catch (Exception e){
                                //Il giocatore ha vinto

                                //Resetta il gioco
                                reset();

                                //riabilita il bottone NBM
                                NBM.setDisable(false);

                                //Lancia alert di vittoria
                                Utility.setAlert(Alert.AlertType.INFORMATION, "Vittoria", "Hai vinto");

                            }

                        } // Fine if
                    }
                    if (newValue == Worker.State.FAILED) {
                        Utility.setAlert(Alert.AlertType.ERROR, "Errore", "Errore nel caricamento dello script");
                    }
                });
            }
            catch (Exception e){
                Utility.setAlert(Alert.AlertType.WARNING,"NBM","Caricamento dell'NBM in caricamento...");
            }
            //Imposta un timer per evitare che il bottone NBM possa essere premuto troppo velocemente
        } else {
            Utility.setAlert(Alert.AlertType.ERROR, "Errore", "NBM non disponibile, connettiti a internet");
        }
    }


    /**
     * Metodo che carica il file per la risoluzione della NBM.
     */
    private void loadHTMLFile() {
        File prova = new File(NBM_SOLVER_HTML_FILE);
        if (prova.exists()) {
            try {
                StringBuilder contentBuilder = new StringBuilder();
                FileReader reader = new FileReader(prova);
                int character;
                while ((character = reader.read()) != -1) {
                    contentBuilder.append((char) character);
                }
                reader.close();
                String htmlContent = contentBuilder.toString();
                WebView webView = new WebView();
                this.webEngine = webView.getEngine();
                this.webEngine.loadContent(htmlContent); // Carica il contenuto HTML nella WebView
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Il file HTML non esiste.");
        }
    }

    /**
     * Metodo chiamato quando viene premuto il tasto undo
     * Sostituisce la configurazione attuale con quella precedente (se disponibile)
     * Se non disponibile lancia un alert
     */
    @FXML
    private void undo() {
        try{
            //Setta la configurazione attuale con quella precedente
            game.undo();

            //Aggiorna il testo con il counter delle mosse
            textCounter.setText("Moves : " + game.getMoveCounter());

            //Pulisci il pane
            blockPane.getChildren().clear();

            //Fai ripartire l'inizializzazione con il nuovo file di log
            initialize();
        }catch (Exception e){
            //Se non è possibile fare undo mostra un alert
            Utility.setAlert(Alert.AlertType.WARNING, "Undo", "Non hai spostato nessun pezzo!");
        }
    }


    /**
     * Metodo chiamato alla pressione delle frecce o dei tasti ASDW con blocco selezionato
     * Muove il pezzo selezionato nella direzione designata di 100px se possibile,
     * altrimenti termina silenziosamente
     * @param piece pezzo da muovere
     * @param dirIdx direzione in cui muoverlo
     */


    /**
     * Controlla (graficamente) se la configurazione attuale rappresenta una situazione di vittoria
     */
    private void checkWin() {
        //Prende il pezzo piu' grande (che è sempre il primo)
        Node node = blockPane.getChildren().get(0);

        //Se si trova nella posizione di vittoria
        if (node.getLayoutX() == WIN_X && node.getLayoutY() == WIN_Y) {
            gameEnded = true;
            //Lancia alert di vittoria
            reset();
            Utility.setAlert(Alert.AlertType.INFORMATION, "Vittoria", "Hai vinto");
        }
    }

}
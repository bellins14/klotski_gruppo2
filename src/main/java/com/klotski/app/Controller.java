package com.klotski.app;

import com.jfoenix.controls.*;
import javafx.animation.StrokeTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
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
    //Gioco
    private Game game;

    //Pezzo selezionato: un pezzo puo' essere mosso solo con le frecce della tastiera
    // solo dopo essere stato selezionato con il mouse
    private Piece selectedPiece;

    //Necessario per la connessione all'NBM
    private WebEngine webEngine;




    /**
     * Costruttore di default
     */
    public Controller() {
    }


    /**
     * Metodo chiamato di default all'avvio dell'applicazione.
     * Inizializza la pane e gestisce gli input da tastiera e da mouse sui blocchi.
     */
    public void initialize() {

        //Crea un nuovo gioco
        game = new Game();

        //Setta il counter con le mosse
        textCounter.setText("Moves : " + game.getMoveCounter());

        //Setta le dimensioni massime del pane
        blockPane.setMaxHeight(MAX_PANE_HEIGHT);
        blockPane.setMaxWidth(MAX_PANE_WIDTH);

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

            //TODO: sposta più su
            //Setta il bordo della pane
            blockPane.setStyle(PANE_BORDER_COLOR);

            //Aggiunge un gestore eventi per la selezione di un bottone
            piece.setOnMouseClicked(event -> {
                selectedPiece = (Piece) event.getSource();
                for (Piece p : currentPieces) {
                    p.setEffect(null);
                    p.setStrokeWidth(3);
                }

                //Attiva l'illuminazione del pulsante selezionato
                StrokeTransition strokeTransition = new StrokeTransition(Duration.millis(200), piece);
                strokeTransition.setFromValue(Color.grayRgb(3));
                strokeTransition.setToValue(Color.grayRgb(6));
                strokeTransition.setCycleCount(2);
                strokeTransition.setAutoReverse(true);
                strokeTransition.play();
                selectedPiece = piece;

                //Aumenta lo spessore del bordo
                piece.setStrokeWidth(5);
            });
        }

        // Aggiunge un gestore eventi per la pressione dei tasti freccia
        blockPane.setOnKeyPressed(event -> {
            if (selectedPiece != null) {
                //di quanto si deve spostare il mio bottone selezionato
                //tutte le casistiche per evitare che il bottone vada fuori dalla Pane e che non si sovrapponga con altri bottoni
                //getCode mi traduce il comando da tastiera in un codice
                int keyCode = event.getCode().ordinal();

                //Sposta il blocco
                moveBlock(selectedPiece, keyCode);

                //Controlla se il giocatore ha vinto
                if (checkWin(blockPane)) {
                    //Resetta
                    reset();
                }

                //Aggiorna il testo con il counter delle mosse
                textCounter.setText("Moves : " + game.getMoveCounter());

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

        //Cambia la configurazione attuale di game con la configurazione iniziale
        game.setConfigurationToInitialConf(game.getInitialSelectedConf());

        //Aggiorna il testo con il counter delle mosse
        //textCounter.setText("Moves : " + 0);

        //Pulisce il pane
        blockPane.getChildren().clear();

        //Fai ripartire l'inizializzazione
        initialize();
    }


    /**
     * Metodo che si occupa del cambio di configurazione una volta cliccato il bottone apposito.
     *
     * @param event classe FXML che codifica un evento.
     */
    @FXML
    void configurationClicked(ActionEvent event) {

        //Prendi il bottone cliccato
        Button clickedButton = (Button) event.getSource();

        //Prendi il numero della configurazione selezionata
        int configurationNumber = Integer.parseInt(clickedButton.getUserData().toString());

        //Se la configurazione iniziale selezionata è diversa da quella attuale
        if (game.getInitialSelectedConf() != configurationNumber) {

            //Cambia la configurazione attuale di game
            game.setConfigurationToInitialConf(configurationNumber);

            //Aggiorna il testo con il counter delle mosse
            textCounter.setText("Moves : " + game.getMoveCounter());

            //Pulisci il pane
            blockPane.getChildren().clear();

            //Fai ripartire l'inizializzazione
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

            Utility.updateHTMLFile(game.getConfiguration());
            loadHTMLFile();
            this.webEngine.setJavaScriptEnabled(true);
            this.webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == Worker.State.SUCCEEDED) {
                    // Esegui lo script JavaScript nella pagina caricata nella WebView
                    Object result = webEngine.executeScript("JSON.stringify(window.klotski.solve(window.game))");
                    if (result instanceof String jsonString) {
                        jsonString = jsonString.substring(1, 35);
                        //System.out.println(jsonString);
                        int blockIdx = Utility.extractIntValue(jsonString,"blockIdx");
                        int dirIdx =  Utility.extractIntValue(jsonString,"dirIdx");
                        //converto le mosse NBM in valori interi che corrispondo alle frecce della tastiera
                        dirIdx = (dirIdx == 0) ? 19 : ((dirIdx == 1) ? 18 : ((dirIdx == 2) ? 17 : ((dirIdx == 3) ? 16 : -1)));
                        Node node = blockPane.getChildren().get(blockIdx);

                        // essendo ripetizione di codice per lo spostamento, capire se creare metodo unico da inserire
                        // sia qua, sia quando vengono assegnati i comportamenti in base al tasto freccia (su giù dx sx)
                        // quando viene eseguito `initialize()`
                        moveBlock((Piece) node,dirIdx);
                        textCounter.setText("Moves : " + game.getMoveCounter());

                    } // Fine if
                }
                if (newValue == Worker.State.FAILED) {
                    Utility.setAlert(Alert.AlertType.ERROR, "Errore", "Errore nel caricamento dello script");
                }
            });

            //Controlla se ha vinto
            if (checkWin(blockPane)) {
                //Resetta
                reset();
            }

            //Imposta un timer per evitare che il bottone NBM possa essere premuto troppo velocemente
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
     * Metodo che serve per caricare il file per il risolvimento della NBM.
     */
    public  void loadHTMLFile() {
        File prova = new File("src/main/resources/com/klotski/app/solver.html");
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
     * Metodo che gestisce undo.
     */
    @FXML
    void undo() {
        if (game.getMoveCounter() != 0) { //Se il counter è diverso da 0

            //Setta la configurazioen attuale con quella precedente
            game.setConfigurationToPreviousConf();

            //Aggiorna il testo con il counter delle mosse
            textCounter.setText("Moves : " + game.getMoveCounter());

            //Pulisci il pane
            blockPane.getChildren().clear();

            //Fai ripartire l'inizializzazione con il nuovo file di log
            initialize();

        } else {
            Utility.setAlert(Alert.AlertType.WARNING, "Undo", "Non hai spostato nessun blocco!");
        }
    }



    public void moveBlock(Piece block, int dirIdx) {
        double moveAmount = 100;
        switch (dirIdx) {
            //WASD
            //58 su
            //36 sx
            //54 giu
            //39 dx
            //DOWN
            case 19,54 -> {
                if (block.getLayoutY() + moveAmount + block.getHeight() <= 500
                        && isNotOverlapping(block, 0, moveAmount)) {
                    //Muove il blocco in giu di moveAmount
                    game.moveBlockDown(block, moveAmount);
                    //blockMoved = true;
                }
            }
            //RIGHT
            case 18,39 -> {
                if (block.getLayoutX() + moveAmount + block.getWidth() <= 400
                        && isNotOverlapping(block, moveAmount, 0)) {
                    game.moveBlockRight(block, moveAmount);
                }
            }
            //UP
            case 17,58 -> {
                if (block.getLayoutY() - moveAmount >= 0 && isNotOverlapping(block, 0, -moveAmount)) {
                    game.moveBlockUp(block, moveAmount);
                }
            }
            //LEFT
            case 16,36 -> {
                if (block.getLayoutX() - moveAmount >= 0 && isNotOverlapping(block, -moveAmount, 0)) {
                    game.moveBlockLeft(block, moveAmount);
                }
            }
        }
    }

    /**
     * Metodo che gestisce la vittoria.
     */

    private boolean checkWin(Pane blockPane) {
        Node node = blockPane.getChildren().get(0);
        if (node.getLayoutX() == 100 && node.getLayoutY() == 300) {
            Utility.setAlert(Alert.AlertType.INFORMATION, "Vittoria", "Hai vinto");
            return  true;
        }
        return  false;
    }

    /**
     * Metodo che controlla che non ci sia overlapping tra pezzi durante il loro spostamento.
     *
     * @param block pezzo che si vuole.
     * @param deltaX quantità di cui si muove il pezzo orizzontalmente.
     * @param deltaY quantità di cui si muove il pezzo verticalmente.
     * @return false se si overlappa, true se è tutto a posto.
     */
    private boolean isNotOverlapping(Piece block, double deltaX, double deltaY) {
        // Calcola la nuova posizione del bottone
        double newX = block.getLayoutX() + deltaX;
        double newY = block.getLayoutY() + deltaY;

        // Itera su tutti gli elementi figli della Pane
        ObservableList<Node> children = blockPane.getChildren();
        for (Node child : children) {
            // Verifica se l'elemento figlio è un bottone diverso da quello selezionato
            if (child instanceof Piece otherPiece && child != block) {
                // Verifica se il nuovo bottone si sovrappone all'altro bottone
                if (newX + block.getWidth() > otherPiece.getLayoutX() &&
                        newX < otherPiece.getLayoutX() + otherPiece.getWidth() &&
                        newY + block.getHeight() > otherPiece.getLayoutY() &&
                        newY < otherPiece.getLayoutY() + otherPiece.getHeight()) {
                    return false;
                }
            }
        }

        return true;
    }
}
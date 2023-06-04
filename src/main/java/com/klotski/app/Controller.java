package com.klotski.app;

import com.jfoenix.controls.*;
import javafx.animation.StrokeTransition;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import java.io.*;
import java.util.Stack;


public class Controller {

    //Pannello "Pane" dove andrò a inserire i vari Piece
    @FXML
    private Pane blockPane;
    @FXML
    private JFXButton undo;
    private WebView webView;
    private  WebEngine webEngine;
    //Testo per il numero di mosse
    @FXML
    private Text textCounter;
    @FXML
    private JFXButton reset;
    @FXML
    private JFXButton NBM;
    //counter per le mosse
    private int counter = 0;
    protected Configuration _configuration;
    //un bottone posso muoverlo con le frecce solo dopo averlo selezionato con il mouse
    private Piece selectedBlock;
    //configurazione selezionata
    private int selectedConf = 1;
    private Stack<Configuration> log;


    public Controller() {
        log = new Stack<>();
    }


    //primo metodo chiamato di default
    public void initialize() {
        textCounter.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        Piece[] blocks;

        // Controlliamo se il JSON è già inizializzato.
        // Ci devono essere almeno 2 configurazioni inserite
        log = UtilityJackson.deserializeConfigurationLog();
        System.out.println(log);

        if (log.size() == 0){ // Il log è vuoto
            _configuration = new Configuration(1);
            log.push(_configuration);
            UtilityJackson.serializeConfigurationLog(log);
            blocks = _configuration.getBlocks();
            // Debug
            System.out.println("JSON vuoto");

        } else if(log.size() == 1){ // C'è solo una configurazione(che sarebbe iniziale - è stato fatto un reset o partita salvata con 1 conf);
            _configuration = log.peek();
            blocks = _configuration.getBlocks();
            // Debug
            System.out.println("JSON 1 oggetto");

        } else {
            // Ci sono delle configurazioni salvate da una partita precedente.
            _configuration = log.peek();
            blocks = _configuration.getBlocks();
            // Debug
            System.out.println("Più configurazioni");
        }

        blockPane.setMaxWidth(400);
        blockPane.setMaxHeight(500);

        //con questo ciclo for inizializzo la pane
        for (Piece block : blocks) {
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
                block.setStrokeWidth(6);

                // #### DEBUG ####
                //System.out.println(selectedBlock);

                // #### DEBUG ####
            });
        }

        // Stampa stato all'interno del file ConfigurationLog.json
        // Inserisco il primo record relativo alla configurazione iniziale


        // Aggiunge un gestore eventi per la pressione dei tasti freccia
        blockPane.setOnKeyPressed(event -> {
            if (selectedBlock != null) {
                //di quanto si deve spostare il mio bottone selezionato
                double moveAmount = 100;
                //tutte le casistiche per evitare che il bottone vada fuori dalla Pane e che non si sovrapponga con altri bottoni
                //getCode mi traduce il comando da tastiera in un codice
                switch (event.getCode()) {
                    case UP -> {
                        if (selectedBlock.getLayoutY() - moveAmount >= 0 && isNotOverlapping(selectedBlock, 0, -moveAmount)) {
                            selectedBlock.setLayoutY(selectedBlock.getLayoutY() - moveAmount);
                            counter++;
                            /* Aggiorna e stampa stato corrente nel ConfigurationLog. Lo facciamo qui per ogni case perché
                            vengono passati tutti i controlli, soprattutto il notOverlapping. Così non salviamo stati
                            uguali, che sarebbe inutile.*/
                            log.push(_configuration);
                            UtilityJackson.serializeConfigurationLog(log);
                        }
                    }
                    case DOWN -> {
                        if (selectedBlock.getLayoutY() + moveAmount + selectedBlock.getHeight() <= 500
                                && isNotOverlapping(selectedBlock, 0, moveAmount)) {
                            selectedBlock.setLayoutY(selectedBlock.getLayoutY() + moveAmount);
                            counter++;
                            // Aggiorna e stampa stato corrente nel ConfigurationLog
                            log.push(_configuration);
                            UtilityJackson.serializeConfigurationLog(log);
                        }
                    }
                    case LEFT -> {
                        if (selectedBlock.getLayoutX() - moveAmount >= 0 && isNotOverlapping(selectedBlock, -moveAmount, 0)) {
                            selectedBlock.setLayoutX(selectedBlock.getLayoutX() - moveAmount);
                            counter++;
                            // Aggiorna e stampa stato corrente nel ConfigurationLog
                            log.push(_configuration);
                            UtilityJackson.serializeConfigurationLog(log);
                        }
                    }
                    case RIGHT -> {
                        if (selectedBlock.getLayoutX() + moveAmount + selectedBlock.getWidth() <= 400
                                && isNotOverlapping(selectedBlock, moveAmount, 0)) {
                            selectedBlock.setLayoutX(selectedBlock.getLayoutX() + moveAmount);
                            counter++;
                            // Aggiorna e stampa stato corrente nel ConfigurationLog
                            log.push(_configuration);
                            UtilityJackson.serializeConfigurationLog(log);
                        }
                    }
                }

                textCounter.setText("Moves : " + counter);

            }

        });
        // Per consentire il focus della tastiera sul pannello
        blockPane.setFocusTraversable(true);
    }


    //controllo che non ci sia overlapping
    private boolean isNotOverlapping(Piece block, double deltaX, double deltaY) {
        // Calcola la nuova posizione del bottone
        double newX = block.getLayoutX() + deltaX;
        double newY = block.getLayoutY() + deltaY;

        // Itera su tutti gli elementi figli della Pane
        ObservableList<Node> children = blockPane.getChildren();
        for (Node child : children) {
            // Verifica se l'elemento figlio è un bottone diverso da quello selezionato
            if (child instanceof Piece otherBlock && child != block) {
                // Verifica se il nuovo bottone si sovrappone all'altro bottone
                if (newX + block.getWidth() > otherBlock.getLayoutX() &&
                        newX < otherBlock.getLayoutX() + otherBlock.getWidth() &&
                        newY + block.getHeight() > otherBlock.getLayoutY() &&
                        newY < otherBlock.getLayoutY() + otherBlock.getHeight()) {
                    return false;
                }
            }
        }

        return true;
    }


    //reset della configurazione attuale
    @FXML
    void reset() {
        counter = 0;
        textCounter.setText("Moves : " + counter);
        blockPane.getChildren().clear();
        // Resetto il log, e la parte di inizializzazione della pane dovrebbe funzionare in automatico.
        for(int i=0; i < log.size()-1; i++){
            log.pop();
        }
        System.out.println(log);
        UtilityJackson.serializeConfigurationLog(log);
        initialize();
    }


    // cambio configurazione e azzeramento, pulsanti di configurazione 1, 2, 3, 4
    @FXML
    void configurationClicked(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        int configurationIndex = Integer.parseInt(clickedButton.getUserData().toString());
        if(selectedConf != configurationIndex) {
            counter = 0;
            textCounter.setText("Moves : " + counter);
            //conf = configurationIndex;
            selectedConf = configurationIndex;
            blockPane.getChildren().clear();
            // Resetto il log, e la parte di inizializzazione della pane dovrebbe funzionare in automatico, essendo
            // già stata settata la selectedConf
            log.clear();
            log.push(new Configuration(selectedConf));
            UtilityJackson.serializeConfigurationLog(log);
            initialize();
        }
    }


    // Abilita l'esecuzione di JavaScript nella WebView, quindi registra un listener per il cambio di stato del caricamento del worker della WebView.
    // Quando il caricamento è completato con successo (Worker.State.SUCCEEDED), viene eseguito uno script JavaScript nella pagina caricata (NBM).
    // Il risultato della NBM prodotto dallo script viene utilizzato per spostare un nodo nell'interfaccia utente.
    // In caso di errore durante il caricamento (Worker.State.FAILED), viene stampato un messaggio di errore.
    @FXML
    void nextBestMove() throws IOException {
        //aggiorno il file html con la nuova configurazione
        updateHTMLFile();
        //carico il file html
        loadHTMLFile();
        //abilito JavaScript
        webEngine.setJavaScriptEnabled(true);
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                // Esegui lo script JavaScript nella pagina caricata nella WebView
                Object result = webEngine.executeScript("JSON.stringify(window.klotski.solve(window.game))");
                if (result instanceof String jsonString) {
                    if (jsonString.length() == 2) return;
                    jsonString = jsonString.substring(1, 35);
                    //System.out.println(jsonString);

                    int blockIdxStart = jsonString.indexOf("\"blockIdx\":") + "\"blockIdx\":".length();
                    int blockIdxEnd = jsonString.indexOf(",", blockIdxStart);
                    String blockIdxStr = jsonString.substring(blockIdxStart, blockIdxEnd).trim();

                    int dirIdxStart = jsonString.indexOf("\"dirIdx\":") + "\"dirIdx\":".length();
                    int dirIdxEnd = jsonString.indexOf("}", dirIdxStart);
                    String dirIdxStr = jsonString.substring(dirIdxStart, dirIdxEnd).trim();

                    int blockIdx = Integer.parseInt(blockIdxStr);
                    int dirIdx = Integer.parseInt(dirIdxStr);

                    Node node = blockPane.getChildren().get(blockIdx);

                    // essendo ripetizione di codice per lo spostamento, capire se creare metodo unico da inserire
                    // sia qua, sia quando vengono assegnati i comportamenti in base al tasto freccia (su giù dx sx)
                    // quando viene eseguito `initialize()`
                    switch (dirIdx) {
                        //DOWN
                        case 0 -> {
                            node.setLayoutY(node.getLayoutY() + 100);
                            counter++;
                        }
                        //RIGHT
                        case 1 -> {
                            node.setLayoutX(node.getLayoutX() + 100);
                            counter++;
                        }
                        //UP
                        case 2 -> {
                            node.setLayoutY(node.getLayoutY() - 100);
                            counter++;
                        }
                        case 3 -> {
                            node.setLayoutX(node.getLayoutX() - 100);
                            counter++;
                        }
                    }
                    textCounter.setText("Moves : " + counter);
                    checkWin();
                }
            }
            if (newValue == Worker.State.FAILED) {
                System.out.println("Errore");
            }
        });
    }


    private void loadHTMLFile() {
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
                webView = new WebView();
                webEngine = webView.getEngine();
                webEngine.loadContent(htmlContent); // Carica il contenuto HTML nella WebView
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Il file HTML non esiste.");
        }
    }


    void updateHTMLFile() throws IOException {
        String game = "<html>\n" +
                "<head>\n" +
                "  <title>Klotski Game</title>\n" +
                "    <script src=\"https://unpkg.co/klotski/dist/klotski.min.js\"></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<script>\n" +
                "      var klotski = new Klotski();\n" +
                "      var game = {\n" +
                "        blocks: [\n" +
                "          " + _configuration.toString() +
                "],\n" +
                "  boardSize: [5, 4],\n" +
                "  escapePoint: [3, 1],\n" +
                "};\n" +
                "\n" +
                "var result = klotski.solve(game);" +
                "</script>" +
                "</body>\n" +
                "</html>";
        FileWriter file = new FileWriter("src/main/resources/com/klotski/app/solver.html");
        file.write(String.valueOf(game));
        file.close();
    }


    public void checkWin(){
        Node node = blockPane.getChildren().get(0);
        if(node.getLayoutX() == 100 && node.getLayoutY() == 300){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("VITTORIA");
            alert.setHeaderText(null);
            alert.setContentText("HAI VINTO !");
            alert.showAndWait();
        }
    }


    /**
     * Metodo che si occupa di gestire la logica dietro all'undo.
     */
    @FXML
    public void undo() {

    }

}
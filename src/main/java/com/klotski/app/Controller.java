package com.klotski.app;

import com.jfoenix.controls.*;
import javafx.animation.StrokeTransition;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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


    public Controller() {
    }


    //primo metodo chiamato di default
    public void initialize() {
        textCounter.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        //inizializzo _configuration e gli passo come parametro la configurazione iniziale
        _configuration = new Configuration(selectedConf);
        Piece[] blocks = _configuration.getBlocks();

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
                        }
                    }
                    case DOWN -> {
                        if (selectedBlock.getLayoutY() + moveAmount + selectedBlock.getHeight() <= 500
                                && isNotOverlapping(selectedBlock, 0, moveAmount)) {
                            selectedBlock.setLayoutY(selectedBlock.getLayoutY() + moveAmount);
                            counter++;
                            // Aggiorna e stampa stato corrente nel ConfigurationLog
                        }
                    }
                    case LEFT -> {
                        if (selectedBlock.getLayoutX() - moveAmount >= 0 && isNotOverlapping(selectedBlock, -moveAmount, 0)) {
                            selectedBlock.setLayoutX(selectedBlock.getLayoutX() - moveAmount);
                            counter++;
                            // Aggiorna e stampa stato corrente nel ConfigurationLog
                        }
                    }
                    case RIGHT -> {
                        if (selectedBlock.getLayoutX() + moveAmount + selectedBlock.getWidth() <= 400
                                && isNotOverlapping(selectedBlock, moveAmount, 0)) {
                            selectedBlock.setLayoutX(selectedBlock.getLayoutX() + moveAmount);
                            counter++;
                            // Aggiorna e stampa stato corrente nel ConfigurationLog
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
            initialize();
        }
    }

    @FXML
    void nextBestMove(MouseEvent event) throws IOException, InterruptedException {
        StringBuilder game = new StringBuilder("<html>\n" +
                "<head>\n" +
                "  <title>Klotski Game</title>\n" +
                "    <script src=\"https://unpkg.co/klotski/dist/klotski.min.js\"></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<script>\n" +
                "      var klotski = new Klotski();\n" +
                "      var game = {\n" +
                "        blocks: [\n" +
                "          ");
        game.append(_configuration.toString());
        game.append("],\n" +
                "  boardSize: [5, 4],\n" +
                "  escapePoint: [3, 1],\n" +
                "};\n" +
                "\n" +
                "var result = klotski.solve(game);" +
                "</script>"+
                "</body>\n" +
                "</html>");
        FileWriter file = new FileWriter("D:\\JavaFxProjects\\klotski_gruppo2\\src\\main\\resources\\com\\klotski\\app\\prova.html");
        file.write(String.valueOf(game));
        file.close();

        loadHTMLFile();
        webEngine.setJavaScriptEnabled(true);
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                // Esegui lo script JavaScript nella pagina caricata nella WebView
                Object result = webEngine.executeScript("JSON.stringify(window.klotski.solve(window.game))");
                if (result instanceof String) {
                    String jsonString = (String) result;
                    jsonString = jsonString.substring(1,35);
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

                    switch (dirIdx){
                        //DOWN
                        case 0 ->{node.setLayoutY(node.getLayoutY() + 100);
                            counter++;}
                        //RIGHT
                        case 1 -> {node.setLayoutX(node.getLayoutX() + 100);
                            counter++;}
                        //UP
                        case 2->{  node.setLayoutY(node.getLayoutY() - 100);
                            counter++;}
                        case 3 ->{ node.setLayoutX(node.getLayoutX() - 100);
                            counter++;}
                    }
                textCounter.setText("Moves : " + counter);
            }}
            if(newValue == Worker.State.FAILED){
                System.out.println("DIO");
            }
        });
    }


    private void loadHTMLFile() {
        File prova = new File("D:\\JavaFxProjects\\klotski_gruppo2\\src\\main\\resources\\com\\klotski\\app\\prova.html");
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

    @FXML
    void undo(MouseEvent event) {

    }




}
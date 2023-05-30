package com.klotski.app;

import com.jfoenix.controls.*;
import javafx.animation.StrokeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class Controller {


    //Pannello "Pane" dove andrò ad inserire i vari Piece
    @FXML
    private Pane blockPane;

    //Testo per il numero di mosse
    @FXML
    private Text textcounter;

    @FXML
    private JFXButton reset;

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
        textcounter.setFont(Font.font("Arial", FontWeight.BOLD, 16));

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
                System.out.println(_configuration);
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

                textcounter.setText("Moves : " + counter);

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
        textcounter.setText("Moves : " + counter);
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
            textcounter.setText("Moves : " + counter);
            //conf = configurationIndex;
            selectedConf = configurationIndex;
            blockPane.getChildren().clear();
            initialize();
        }
    }




}
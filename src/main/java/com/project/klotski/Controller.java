package com.project.klotski;

import com.jfoenix.controls.*;
import javafx.animation.StrokeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Objects;


public class Controller {


    //Pannello "Pane" dove andrò ad inserire i vari Piece
    @FXML
    private Pane blockGrid;

    //Testo per il numero di mosse
    @FXML
    private Text textcounter;

    @FXML
    private JFXButton reset;

    //counter per le mosse
    private int counter = 0;

    //un bottone posso muoverlo con le frecce solo dopo averlo selezionato con il mouse
    private Piece selectedButton;

    //numero della configurazione
    private int conf = 1;

    //configurazione selezionata
    private int selectedConf = 1;


    public Controller() {
    }


    //primo metodo chiamato di defautl
    public void initialize() {
        textcounter.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        //istanzio un oggetto configuration e gli passo come parametro la configurazione iniziale
        Configuration configuration = new Configuration(conf);
        Piece [] blocks = configuration.getBlocks();
        Tuple [] positions = configuration.getPositions();

        blockGrid.setMaxWidth(400);
        blockGrid.setMaxHeight(500);

        //con questo ciclo for inizializzo la pane
        for (int i = 0; i < blocks.length; i++) {
            Piece block = blocks[i];
            //Color color = blocks[i].getColor();
            //block.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("com/project/klotski/piece0.png"))));
            Image image = new Image(Objects.requireNonNull(getClass().getResource("img/" + block.getImageName())).toString());
            block.setFill(new ImagePattern(image));
            block.setLayoutX(positions[i].getX());
            block.setLayoutY(positions[i].getY());
            //assegno un bordo nero di spessore 3
            block.setStroke(Color.BLACK);
            block.setStrokeWidth(3);

            blockGrid.getChildren().add(block);
            blockGrid.setStyle("-fx-border-color: black");
            // Aggiunge un gestore eventi per la selezione di un bottone
            block.setOnMouseClicked(event -> {
                selectedButton = (Piece) event.getSource();
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
                selectedButton = block;
                // aumenta lo spessore del bordo
                block.setStrokeWidth(6);
            });
        }

        // Aggiunge un gestore eventi per la pressione dei tasti freccia
        blockGrid.setOnKeyPressed(event -> {
            if (selectedButton != null) {
                //di quanto si deve spostare il mio bottone selezionato
                double moveAmount = 100;
                //tutte le casistiche per evitare che il bottone vada fuori dalla Pane e che non si sovrapponnga con altri bottoni
                //getCode mi traduce il comando da tastiera in un codice
                switch (event.getCode()) {
                    case UP -> {
                        if (selectedButton.getLayoutY() - moveAmount >= 0 && isNotOverlapping(selectedButton, 0, -moveAmount)) {
                            selectedButton.setLayoutY(selectedButton.getLayoutY() - moveAmount);
                            counter++;
                        }
                    }
                    case DOWN -> {
                        if (selectedButton.getLayoutY() + moveAmount + selectedButton.getHeight() <= 500
                                && isNotOverlapping(selectedButton, 0, moveAmount)) {
                            selectedButton.setLayoutY(selectedButton.getLayoutY() + moveAmount);
                            counter++;
                        }
                    }
                    case LEFT -> {
                        if (selectedButton.getLayoutX() - moveAmount >= 0 && isNotOverlapping(selectedButton, -moveAmount, 0)) {
                            selectedButton.setLayoutX(selectedButton.getLayoutX() - moveAmount);
                            counter++;
                        }
                    }
                    case RIGHT -> {
                        if (selectedButton.getLayoutX() + moveAmount + selectedButton.getWidth() <= 400
                                && isNotOverlapping(selectedButton, moveAmount, 0)) {
                            selectedButton.setLayoutX(selectedButton.getLayoutX() + moveAmount);
                            counter++;
                        }
                    }
                }

                textcounter.setText("Moves : " + counter);


            }
        });
        // Per consentire il focus della tastiera sul pannello
        blockGrid.setFocusTraversable(true);
    }

    //controllo che non ci sia overlapping
    private boolean isNotOverlapping(Piece block, double deltaX, double deltaY) {
        // Calcola la nuova posizione del bottone
        double newX = block.getLayoutX() + deltaX;
        double newY = block.getLayoutY() + deltaY;

        // Itera su tutti gli elementi figli della Pane
        ObservableList<Node> children = blockGrid.getChildren();
        for (Node child : children) {
            // Verifica se l'elemento figlio è un bottone diverso da quello selezionato
            if (child instanceof Piece otherButton && child != block) {
                // Verifica se il nuovo bottone si sovrappone all'altro bottone
                if (newX + block.getWidth() > otherButton.getLayoutX() &&
                        newX < otherButton.getLayoutX() + otherButton.getWidth() &&
                        newY + block.getHeight() > otherButton.getLayoutY() &&
                        newY < otherButton.getLayoutY() + otherButton.getHeight()) {
                    return false;
                }
            }
        }

        return true;
    }

    //reset della configurazione attuale
    @FXML
    void reset(MouseEvent event) {
     counter = 0;
     textcounter.setText("Moves : " + counter);
     blockGrid.getChildren().clear();
     initialize();
    }

    //cambio configurazione e azzeramento
    @FXML
    void configurationClicked(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        int configurationIndex = Integer.parseInt(clickedButton.getUserData().toString());
        if(selectedConf != configurationIndex) {
            counter = 0;
            textcounter.setText("Moves : " + counter);
            conf = configurationIndex;
            selectedConf = configurationIndex;
            blockGrid.getChildren().clear();
            initialize();
        }
    }




}
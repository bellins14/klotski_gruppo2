package com.klotski.app;

import javafx.concurrent.Worker;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class Game {
    private int counter;
    private WebEngine webEngine;

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getCounter () {return  this.counter;}


    /**
     * Metodo che gestisce la vittoria.
     */

    public boolean checkWin(Pane blockPane) {
        Node node = blockPane.getChildren().get(0);
        if (node.getLayoutX() == 100 && node.getLayoutY() == 300) {
            HelperFunctions.setAlert(Alert.AlertType.INFORMATION, "VITTORIA", "HAI VINTO");
           return  true;
        }
        return  false;
    }


    public void moveBlock(Piece block, Pane blockPane , int dirIdx, Configuration _configuration, Stack<Configuration> log) {
        boolean blockMoved = false;
        double moveAmount = 100;
        switch (dirIdx) {
            //vedere tasto 0
            //DOWN
            case 19,0 -> {
                if (block.getLayoutY() + moveAmount + block.getHeight() <= 500
                        && block.isNotOverlapping(blockPane, 0, moveAmount)) {
                    block.setLayoutY(block.getLayoutY() + moveAmount);
                    this.counter++;
                    blockMoved = true;
                }
            }
            //RIGHT
            case 18,1 -> {
                if (block.getLayoutX() + moveAmount + block.getWidth() <= 400
                        && block.isNotOverlapping(blockPane, moveAmount, 0)) {
                    block.setLayoutX(block.getLayoutX() + moveAmount);
                    this.counter++;
                    blockMoved = true;
                }
            }
            //UP
            case 17,2 -> {
                if (block.getLayoutY() - moveAmount >= 0 && block.isNotOverlapping(blockPane, 0, -moveAmount)) {
                    block.setLayoutY(block.getLayoutY() - moveAmount);
                    this.counter++;
                    blockMoved = true;
                }
            }
            //LEFT
            case 16,3 -> {
                if (block.getLayoutX() - moveAmount >= 0 && block.isNotOverlapping(blockPane, -moveAmount, 0)) {
                    block.setLayoutX(block.getLayoutX() - moveAmount);
                    this.counter++;
                    blockMoved = true;
                }
            }
        }
        if(blockMoved){
            UtilityJackson.serializeConfiguration(_configuration);
            log.push(UtilityJackson.deserializeConfiguration());
            UtilityJackson.serializeConfigurationLog(log);
        }

    }

    /**
     * Metodo che estrapola la configurazione iniziale dal log.
     *
     * @param log da cui estrapolare la configurazione iniziale.
     * @return initConf configurazione iniziale estrapolata.
     */
    public Configuration getInitConfiguration(Stack<Configuration> log) {
        Stack<Configuration> utility = new Stack<>();
        int s = log.size();
        for (int i = 1; i < s; i++) {
            utility.push(log.pop());
        }
        UtilityJackson.serializeConfiguration(log.peek());
        s = utility.size();
        for (int i = 0; i < s; i++) {
            log.push(utility.pop());
        }
        return UtilityJackson.deserializeConfiguration();
    }

    public void NBMDAJE(Pane blockPane, Configuration _configuration, Stack<Configuration>log,Text texcounter) throws IOException {
        HelperFunctions.updateHTMLFile(_configuration);
        loadHTMLFile();
        webEngine.setJavaScriptEnabled(true);
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                // Esegui lo script JavaScript nella pagina caricata nella WebView
                Object result = webEngine.executeScript("JSON.stringify(window.klotski.solve(window.game))");
                if (result instanceof String jsonString) {
                    jsonString = jsonString.substring(1, 35);
                    //System.out.println(jsonString);
                    int blockIdx = HelperFunctions.extractIntValue(jsonString,"blockIdx");
                    int dirIdx =  HelperFunctions.extractIntValue(jsonString,"dirIdx");
                    Node node = blockPane.getChildren().get(blockIdx);

                    // essendo ripetizione di codice per lo spostamento, capire se creare metodo unico da inserire
                    // sia qua, sia quando vengono assegnati i comportamenti in base al tasto freccia (su giù dx sx)
                    // quando viene eseguito `initialize()`
                    moveBlock((Piece) node,blockPane,dirIdx,_configuration,log);
                    texcounter.setText("Moves : "+this.counter);

                } // Fine if
            }
            if (newValue == Worker.State.FAILED) {
                HelperFunctions.setAlert(Alert.AlertType.ERROR, "ERRORE", "ERRORE NEL CARICAMENTO DELLO SCRIPT");
            }
        });
    }


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
                webEngine = webView.getEngine();
                webEngine.loadContent(htmlContent); // Carica il contenuto HTML nella WebView
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Il file HTML non esiste.");
        }
    }




}

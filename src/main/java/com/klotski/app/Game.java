package com.klotski.app;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class Game {
    private int counter;


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


    public void moveBlock(Piece block, Pane blockPane ,int dirIdx, Configuration _configuration, Stack<Configuration> log) {
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





}

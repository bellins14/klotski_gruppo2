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

import static com.klotski.app.Constants.*;


public class Game {

    //Configurazione selezionata tra le 4 iniziali
    private int selectedConf;

    //Configurazione attuale dei pezzi
    private Configuration _configuration;

    //Stack di log
    private Stack<Configuration> log;
    private int counter;


    //Costruttore di default
    public Game(){

        //Creo lo stack log
        log = new Stack<>();

        // Inizializzo lo stack log con il file log
        log = UtilityJackson.deserializeConfigurationLog(LOG_FILE);

        if (log.size() == Utility.EMPTY_LOG_SIZE) { // Se il log è vuoto

            //Inizializzo il gioco con la configurazione default (1)
            selectedConf = 1;
            _configuration = new Configuration(selectedConf);

            //Converti la configurazione attuale in json e inseriscila sia nello stack log che nel file log
            jacksonSerialize();

            //Setta il counter delle mosse a 0
            setCounter(0);

        } else if (log.size() == Utility.SINGLE_LOG_SIZE) { // Nel log c'è solo una configurazione, quella iniziale.

            //Prendi tale configurazione (in json) dal file di log, convertila in un oggetto Configuration
            // e inizializza _configuration
            _configuration = UtilityJackson.deserializeConfiguration(DC_FILE);

            //TODO: gestire la situazione in cui la configurazione nel file di log non è una di quelle iniziali
            //Controlla che sia una delle 4 configurazioni iniziali, prendi il numero e setta selectedConf
            selectedConf = Configuration.isInitialConfiguration(_configuration);

            //Setta il counter delle mosse a 0
            setCounter(0);

        } else { // Se nel log c'è più di una configurazione

            //Recupero dallo stack di log la configurazione iniziale e inizializzo la configurazione attuale
            _configuration = getInitConfiguration();

            //Controlla a quale numero di configurazione iniziale corrisponde e setta selectedConf
            selectedConf = Configuration.isInitialConfiguration(_configuration);

            UtilityJackson.serializeConfiguration(log.peek(), DC_FILE);
            _configuration = UtilityJackson.deserializeConfiguration(DC_FILE);
            setCounter(log.size() - 1);
        }
    }

    public int getSelectedConf() {
        return selectedConf;
    }

    public void setSelectedConf(int i){
        this.selectedConf = i;
        //TODO:aggiungere eccezioni
    }

    public Configuration getConfiguration() {
        return _configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this._configuration = configuration;
    }

    public Stack<Configuration> getLog() {
        return log;
    }

    public void setLog(Stack<Configuration> log) {
        this.log = log;
    }

    public void clearLog(){
        this.log.clear();
    }

    public void popLog(){
        this.log.pop();
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getCounter () {return  this.counter;}



    /**
     * Metodo che estrapola la configurazione iniziale dal log.
     *
     * @return initConf configurazione iniziale estrapolata.
     */
    private Configuration getInitConfiguration() { //TODO: eventualmente togliere il parametro log
        Stack<Configuration> utility = new Stack<>();
        int s = log.size();
        for (int i = 1; i < s; i++) {
            utility.push(log.pop());
        }
        UtilityJackson.serializeConfiguration(log.peek(), DC_FILE);
        s = utility.size();
        for (int i = 0; i < s; i++) {
            log.push(utility.pop());
        }
        return UtilityJackson.deserializeConfiguration(DC_FILE);
    }



    // Prende la configurazione attuale, la converte in json e la inserisce nello stack log e nel file di log
    public void jacksonSerialize(){
        UtilityJackson.serializeConfiguration(_configuration, DC_FILE);
        log.push(UtilityJackson.deserializeConfiguration(DC_FILE));
        UtilityJackson.serializeConfigurationLog(log, LOG_FILE);
    }



}

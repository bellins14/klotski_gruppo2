package com.klotski.app;

import java.util.Stack;

import static com.klotski.app.Constants.*;


public class Game {

    //Configurazione selezionata tra le 4 iniziali
    private int _initialSelectedConf;

    //Configurazione attuale dei pezzi
    private Configuration _configuration;

    //Stack di log
    private Stack<Configuration> log;
    private int counter;


    //Costruttore di default
    public Game(){

        //Crea lo stack log
        log = new Stack<>();

        // Inizializza lo stack log con il file log
        log = UtilityJackson.deserializeConfigurationLog(LOG_FILE);

        if (log.size() == Utility.EMPTY_LOG_SIZE) { // Se il log è vuoto

            //Inizializza il gioco con la configurazione default (1)
            _initialSelectedConf = 1;
            _configuration = new Configuration(_initialSelectedConf);

            //Converte la configurazione attuale in json e inseriscila sia nello stack log che nel file log
            jacksonSerialize();

            //Setta il counter delle mosse a 0
            setCounter(0);

        } else if (log.size() == Utility.SINGLE_LOG_SIZE) { // Nel log c'è solo una configurazione, quella iniziale.

            //Prende tale configurazione (in json) dal file di log, convertila in un oggetto Configuration
            // e inizializza _configuration
            _configuration = UtilityJackson.deserializeConfiguration(DC_FILE);

            //TODO: gestire la situazione in cui la configurazione nel file di log non è una di quelle iniziali
            //Controlla che sia una delle 4 configurazioni iniziali, prendi il numero e setta selectedConf
            _initialSelectedConf = Configuration.isInitialConfiguration(_configuration);

            //Setta il counter delle mosse a 0
            setCounter(0);

        } else { // Se nel log c'è più di una configurazione

            //Recupero dallo stack di log la configurazione iniziale e inizializzo la configurazione attuale
            _configuration = getInitConfiguration();

            //Controlla a quale numero di configurazione iniziale corrisponde e setta selectedConf
            _initialSelectedConf = Configuration.isInitialConfiguration(_configuration);

            UtilityJackson.serializeConfiguration(log.peek(), DC_FILE);
            _configuration = UtilityJackson.deserializeConfiguration(DC_FILE);
            setCounter(log.size() - 1);
        }
    }

    public int getInitialSelectedConf() {
        return _initialSelectedConf;
    }

    public void setInitialSelectedConf(int i){
        this._initialSelectedConf = i;
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

    public void moveBlockDown(Piece block, double moveAmount) {
        //Verifica che il blocco appartenga alla configurazione
        if(!_configuration.doesPieceBelong(block)){
            throw new IllegalArgumentException();
        }
        //Muovi in giù il blocco
        block.setLayoutY(block.getLayoutY() + moveAmount);
        //Incrementa il counter delle mosse
        this.counter++;
        //_configuration si aggiorna automaticamente
        //Aggiorna lo stack di log e il file di log
        jacksonSerialize();
    }

    public void moveBlockUp(Piece block, double moveAmount) {
        //Verifica che il blocco appartenga alla configurazione
        if(!_configuration.doesPieceBelong(block)){
            throw new IllegalArgumentException();
        }
        //Muovi in su il blocco
        block.setLayoutY(block.getLayoutY() - moveAmount);
        //Incrementa il counter delle mosse
        this.counter++;
        //_configuration si aggiorna automaticamente
        //Aggiorna lo stack di log e il file di log
        jacksonSerialize();
    }

    public void moveBlockLeft(Piece block, double moveAmount) {
        //Verifica che il blocco appartenga alla configurazione
        if(!_configuration.doesPieceBelong(block)){
            throw new IllegalArgumentException();
        }
        //Muovi a sx il blocco
        block.setLayoutX(block.getLayoutX() - moveAmount);
        //Incrementa il counter delle mosse
        this.counter++;
        //_configuration si aggiorna automaticamente
        //Aggiorna lo stack di log e il file di log
        jacksonSerialize();
    }

    public void moveBlockRight(Piece block, double moveAmount) {
        //Verifica che il blocco appartenga alla configurazione
        if(!_configuration.doesPieceBelong(block)){
            throw new IllegalArgumentException();
        }
        //Muovi a dx il blocco
        block.setLayoutX(block.getLayoutX() + moveAmount);
        //Incrementa il counter delle mosse
        this.counter++;
        //_configuration si aggiorna automaticamente
        //Aggiorna lo stack di log e il file di log con la nuova configurazione
        jacksonSerialize();
    }

    public void setConfigurationWithInitialConf(int confNumber) throws IllegalArgumentException {

            //Aggiorna la configurazione attuale con la nuova configurazione iniziale
            setConfiguration(new Configuration(confNumber)); //Lancia IllegalArgumentException se confNumber <1 o >4
            //Resetta il counter delle mosse
            setCounter(0);
            //Aggiorna il numero della configurazione iniziale scelta
            setInitialSelectedConf(confNumber);
            //Pulisci lo Stack di log
            clearLog();
            //Pulisci il file di log e inserisci la nuova configurazione
            jacksonSerialize();

    }



    /**
     * Metodo che estrapola la configurazione iniziale dal log.
     *
     * @return initConf configurazione iniziale estrapolata.
     */
    private Configuration getInitConfiguration() {
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
    private void jacksonSerialize(){ //TODO: forse private
        UtilityJackson.serializeConfiguration(_configuration, DC_FILE);
        log.push(UtilityJackson.deserializeConfiguration(DC_FILE));
        UtilityJackson.serializeConfigurationLog(log, LOG_FILE);
    }
}

package com.klotski.app;

import java.util.Stack;

import static com.klotski.app.Constants.*;


/**
 *
 */
public class Game {

    //Configurazione selezionata tra le 4 iniziali
    private int _initialSelectedConf;

    //Configurazione attuale del gioco
    private Configuration _configuration;

    //Stack di configurazioni
    // funge da log (un simil database) per il gioco
    // ed e' sincronizzato con il file di Log ConfigurationLog
    private Stack<Configuration> _stackLog;
    private int _moveCounter;


    //Costruttore di default
    //Crea un gioco a partire dal file di log
    //- se c'è una partita precedente la riprende
    //- altrimenti crea il gioco dalla configurazione iniziale di default (la numero 1)
    public Game(){

        //Crea lo stack log
        _stackLog = new Stack<>();

        //Inizializza lo stack log con il file log
        _stackLog = UtilityJackson.deserializeConfigurationLog(LOG_FILE);

        if (_stackLog.size() == Utility.EMPTY_LOG_SIZE) { // Se il log è vuoto

            //Inizializza il gioco con la configurazione default (la numero 1)
            _initialSelectedConf = 1;
            _configuration = new Configuration(_initialSelectedConf);

            //Aggiorna sia lo Stack che il file di log, inserendo la configurazione corrente
            updateLogsWithCurrentConfiguration();

            //Setta il counter delle mosse a 0
            _moveCounter = 0;

        } else if (_stackLog.size() == Utility.SINGLE_LOG_SIZE) { // Nel log c'è solo una configurazione, quella iniziale
                                                                 // => c'è una partita precedente allo stadio iniziale

            //Prende tale configurazione (in json) dal file di log, la converte in un oggetto Configuration
            // e inizializza _configuration
            _configuration = UtilityJackson.deserializeConfiguration(DC_FILE);

            //TODO: gestire la situazione in cui la configurazione nel file di log non è una di quelle iniziali
            //Controlla che sia una delle 4 configurazioni iniziali, prendi il numero e setta selectedConf
            _initialSelectedConf = Configuration.isInitialConfiguration(_configuration);

            //Setta il counter delle mosse a 0
            _moveCounter = 0;

        } else { //Se nel log c'è più di una configurazione => c'è una partita precedente avanzata

            //Ripristina la partita precedente:
            //Recupera dallo stack di log la configurazione iniziale e inizializza la configurazione attuale
            _configuration = getInitConfiguration();

            //Controlla a quale numero di configurazione iniziale corrisponde e setta selectedConf
            _initialSelectedConf = Configuration.isInitialConfiguration(_configuration);

            //Prende l'ultima configurazione (in formato json) e la salva nel file di supporto (DC_FILE)
            UtilityJackson.serializeConfiguration(_stackLog.peek(), DC_FILE);

            //Prende la configurazione dal file di supporto (json), crea un (oggetto) configurazione e inizializzaù
            // la configurazione corrente
            _configuration = UtilityJackson.deserializeConfiguration(DC_FILE);

            //Ripristina il contatore delle mosse
            _moveCounter = _stackLog.size() - 1;
        }
    }

    /**
     * @return
     */
    public int getInitialSelectedConf() {
        return _initialSelectedConf;
    }

    /**
     * @param i
     */
    public void setInitialSelectedConf(int i){
        if(i<1||i>4) {
            throw new IllegalArgumentException("configurationNumber non compreso tra 1 e 4");
        }

        this._initialSelectedConf = i;
    }

    /**
     * @return
     */
    public Configuration getConfiguration() {
        return _configuration;
    }

    /**
     * @param configuration
     */
    public void setConfiguration(Configuration configuration) {
        this._configuration = configuration;

        //Inserisci la nuova configurazione nello Stack e nel file di log
        updateLogsWithCurrentConfiguration();
    }

    /**
     * @return
     */
    public int getMoveCounter() {return  this._moveCounter;}

    /**
     * @param piece
     * @param moveAmount
     */
    public void movePieceDown(Piece piece, double moveAmount) {

        //Verifica che il pezzo appartenga alla configurazione
        if(!_configuration.doesPieceBelong(piece)){
            throw new IllegalArgumentException("Il pezzo non appartiene alla configurazione attuale del gioco");
        }
        //Muove in giù il pezzo
        piece.setLayoutY(piece.getLayoutY() + moveAmount);

        //Incrementa il counter delle mosse
        this._moveCounter++;

        //La configurazione attuale (_configuration) si aggiorna automaticamente il pezzo spostato

        //Aggiorna lo stack di log e il file di log con la nuova configurazione attuale
        updateLogsWithCurrentConfiguration();
    }

    /**
     * @param piece
     * @param moveAmount
     */
    public void movePieceUp(Piece piece, double moveAmount) {

        //Verifica che il pezzo appartenga alla configurazione
        if(!_configuration.doesPieceBelong(piece)){
            throw new IllegalArgumentException("Il pezzo non appartiene alla configurazione attuale del gioco");
        }
        //Muove in su il pezzo
        piece.setLayoutY(piece.getLayoutY() - moveAmount);

        //Incrementa il counter delle mosse
        this._moveCounter++;

        //La configurazione attuale (_configuration) si aggiorna automaticamente il pezzo spostato

        //Aggiorna lo stack di log e il file di log con la nuova configurazione attuale
        updateLogsWithCurrentConfiguration();
    }

    public void movePieceLeft(Piece piece, double moveAmount) {

        //Verifica che il pezzo appartenga alla configurazione
        if(!_configuration.doesPieceBelong(piece)){
            throw new IllegalArgumentException("Il pezzo non appartiene alla configurazione attuale del gioco");
        }
        //Muovi a sx il pezzo
        piece.setLayoutX(piece.getLayoutX() - moveAmount);

        //Incrementa il counter delle mosse
        this._moveCounter++;

        //La configurazione attuale (_configuration) si aggiorna automaticamente il pezzo spostato

        //Aggiorna lo stack di log e il file di log con la nuova configurazione attuale
        updateLogsWithCurrentConfiguration();
    }

    public void movePieceRight(Piece piece, double moveAmount) {
        //Verifica che il pezzo appartenga alla configurazione
        if(!_configuration.doesPieceBelong(piece)){
            throw new IllegalArgumentException("Il pezzo non appartiene alla configurazione attuale del gioco");
        }
        //Muovi a dx il pezzo
        piece.setLayoutX(piece.getLayoutX() + moveAmount);

        //Incrementa il counter delle mosse
        this._moveCounter++;

        //La configurazione attuale (_configuration) si aggiorna automaticamente il pezzo spostato

        //Aggiorna lo stack di log e il file di log con la nuova configurazione attuale
        updateLogsWithCurrentConfiguration();
    }

    public void setConfigurationToInitialConf(int confNumber) throws IllegalArgumentException {

        //Crea la nuova configurazione iniziale
        Configuration newInitialConfiguration = new Configuration(confNumber); //Lancia IllegalArgumentException se confNumber <1 o >4

        //Se è stata creata la nuova configurazione iniziale
        //Pulisci lo Stack di log
        _stackLog.clear();

        //Aggiorna la configurazione attuale con la nuova configurazione iniziale
        setConfiguration(newInitialConfiguration);

        //Resetta il counter delle mosse
        _moveCounter = 0;

        //Aggiorna il numero della configurazione iniziale scelta
        setInitialSelectedConf(confNumber);

    }

    public void setConfigurationToPreviousConf() throws IllegalArgumentException {

        //Togli temporaneamente la configurazione attuale dallo Stack di log
        Configuration currentConfiguration = this._stackLog.pop();

        if(this._stackLog.isEmpty()){ //Se non ci sono configurazioni precedenti sullo Stack di log
            //Rimetti la configurazione corrente sullo Stack di log
            this._stackLog.push(currentConfiguration);
            return;
        }

        //Altrimenti
        //Aggiorna la configurazione attuale con la configurazione precedente e aggiorna lo Stack di log e il file di log
        setConfiguration(this._stackLog.pop());

        //Diminuisci il counter delle mosse
        _moveCounter--;

    }




    /**
     * Metodo che estrapola la configurazione iniziale, associata alla configurazione corrente, dal log.
     *
     * @return initConf configurazione iniziale estrapolata.
     */
    private Configuration getInitConfiguration() {
        Stack<Configuration> utility = new Stack<>();
        int s = _stackLog.size();
        for (int i = 1; i < s; i++) {
            utility.push(_stackLog.pop());
        }
        UtilityJackson.serializeConfiguration(_stackLog.peek(), DC_FILE);
        s = utility.size();
        for (int i = 0; i < s; i++) {
            _stackLog.push(utility.pop());
        }
        return UtilityJackson.deserializeConfiguration(DC_FILE);
    }

    //Converte la configurazione attuale in json e la inserisce sia nello Stack log che nel file log
    private void updateLogsWithCurrentConfiguration(){
        //Serializza la configurazione corrente nel file di supporto
        UtilityJackson.serializeConfiguration(_configuration, DC_FILE);
        //Deserializza la configurazione corrente dal file di supporto e inseriscila nello Stack log
        _stackLog.push(UtilityJackson.deserializeConfiguration(DC_FILE));
        //Scrivi tutto lo Stack di log sul file di log
        UtilityJackson.serializeConfigurationLog(_stackLog, LOG_FILE);
    }
}

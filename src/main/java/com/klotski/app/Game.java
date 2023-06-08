package com.klotski.app;

import java.util.Stack;


/**
 * Classe che rappresenta il gioco di Klotski. Dispone:
 * - del numero della configurazione di partenza (tra le 4 iniziali),
 * - della configurazione attuale dei pezzi,
 * - di un contatore delle mosse (a partire dalla configurazione iniziale)
 * - del path del file di log (o storico o database) dove è salvata la partita (sottoforma di configurazioni)
 * - del path del file di supporto per la serializzazione/deserializzazione in json della configurazione attuale
 */
public class Game {

    //Indica quale configurazione iniziale e' selezionata tra le 4 disponibili
    private int _initialSelectedConf;

    //Configurazione attuale del gioco
    private Configuration _configuration;

    //Contatore delle mosse
    private int _moveCounter;
    //Stack di configurazioni
    // funge da log (un simil database) per il gioco
    // ed e' sincronizzato con il file di Log ConfigurationLog
    private Stack<Configuration> _stackLog;

    //Path del file di log dove con cui sincronizzare _stackLog
    // e che funge da storico delle mosse
    private final String _logFilePathName;

    //Path del file di supporto per le serializzazione in json delle Configurazioni
    private final String _supportFilePathName;


    /**
     * Costruttore di default
     * Crea un gioco a partire dal file di log, e da un file di supporto per la serializzazione delle configurazioni
     * - se c'è una partita precedente la riprende
     * - altrimenti crea il gioco dalla configurazione iniziale di default (la numero 1)
     * @param logFilePathName file di log (json) (se non esiste già viene creato)
     * @param supportFilePathName file di supporto (json) (se non esiste già viene creato)
     */
    public Game(String logFilePathName, String supportFilePathName){

        //Inizializza i path dei file
        _logFilePathName = logFilePathName;
        _supportFilePathName = supportFilePathName;

        //Crea lo stack log
        _stackLog = new Stack<>();

        //Inizializza lo stack log con il file log
        _stackLog = UtilityJackson.deserializeConfigurationLog(_logFilePathName);

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
            _configuration = UtilityJackson.deserializeConfiguration(_supportFilePathName);

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
            UtilityJackson.serializeConfiguration(_stackLog.peek(), _supportFilePathName);

            //Prende la configurazione dal file di supporto (json), crea un (oggetto) configurazione e inizializzaù
            // la configurazione corrente
            _configuration = UtilityJackson.deserializeConfiguration(_supportFilePathName);

            //Ripristina il contatore delle mosse
            _moveCounter = _stackLog.size() - 1;
        }
    }

    /**
     * Metodo per ritornare il numero della configurazione iniziale associata alla configurazione attuale
     * @return _initialSelectedConf
     */
    public int getInitialSelectedConf() {
        return _initialSelectedConf;
    }

    /**
     * Metodo per ritornare la configurazione attuale
     * @return _configuration
     */
    public Configuration getConfiguration() {
        return _configuration;
    }

    /**
     * Metodo per settare la configurazione attuale con la nuova configurazione passata
     * @param newConfiguration nuova configurazione passata
     */
    public void setConfiguration(Configuration newConfiguration) {
        this._configuration = newConfiguration;

        //Inserisci la nuova configurazione nello Stack e nel file di log
        updateLogsWithCurrentConfiguration();
    }

    /**
     * Metodo per ritornare il counter delle mosse
     * @return _moveCounter
     */
    public int getMoveCounter() {return  this._moveCounter;}

    /**
     * Metodo per muovere un pezzo della configurazione attuale del gioco in basso
     * @param piece pezzo da muovere
     * @param moveAmount di quando muoverlo
     * @throws IllegalArgumentException se il pezzo non appartiene alla configurazione attuale del gioco
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
     * Metodo per muovere un pezzo della configurazione attuale del gioco in alto
     * @param piece pezzo da muovere
     * @param moveAmount di quando muoverlo
     * @throws IllegalArgumentException se il pezzo non appartiene alla configurazione attuale del gioco
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

    /**
     * Metodo per muovere un pezzo della configurazione attuale del gioco a sx
     * @param piece pezzo da muovere
     * @param moveAmount di quando muoverlo
     * @throws IllegalArgumentException se il pezzo non appartiene alla configurazione attuale del gioco
     */
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

    /**
     * Metodo per muovere un pezzo della configurazione attuale del gioco a dx
     * @param piece pezzo da muovere
     * @param moveAmount di quando muoverlo
     * @throws IllegalArgumentException se il pezzo non appartiene alla configurazione attuale del gioco
     */
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

    /**
     * Metodo che setta la configurazione corrente con una configurazione iniziale
     * Utile per fare reset o cambiare configurazione inziale
     * @param confNumber configurazione iniziale
     * @throws IllegalArgumentException se confNumber non è compreso tra 1 e 4
     */
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

    /** Metodo che setta la configurazione corrente con la configurazione precedente, che viene presa dal log
     * Se non è disponibile nessuna configurazione precedente, termina silenziosamente
     * Utile per fare undo
     */
    public void setConfigurationToPreviousConf() {

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
     * Metodo che setta il numero della configurazione iniziale
     * @throws IllegalArgumentException se configurationNumber non è compreso tra 1 e 4
     */
    private void setInitialSelectedConf(int i){
        if(i<1||i>4) {
            throw new IllegalArgumentException("configurationNumber non compreso tra 1 e 4");
        }

        this._initialSelectedConf = i;
    }

    /**
     * Metodo che estrapola la configurazione iniziale, associata alla configurazione corrente, dal log.
     * @return initConf configurazione iniziale estrapolata.
     */
    private Configuration getInitConfiguration() {
        Stack<Configuration> utility = new Stack<>();
        int s = _stackLog.size();
        for (int i = 1; i < s; i++) {
            utility.push(_stackLog.pop());
        }
        UtilityJackson.serializeConfiguration(_stackLog.peek(), _supportFilePathName);
        s = utility.size();
        for (int i = 0; i < s; i++) {
            _stackLog.push(utility.pop());
        }
        return UtilityJackson.deserializeConfiguration(_supportFilePathName);
    }

    /**
     * Metodo che converte la configurazione attuale in json e la inserisce sia nello Stack log che nel file log
     */
    private void updateLogsWithCurrentConfiguration(){
        //Serializza la configurazione corrente nel file di supporto
        UtilityJackson.serializeConfiguration(_configuration, _supportFilePathName);
        //Deserializza la configurazione corrente dal file di supporto e inseriscila nello Stack log
        _stackLog.push(UtilityJackson.deserializeConfiguration(_supportFilePathName));
        //Scrivi tutto lo Stack di log sul file di log
        UtilityJackson.serializeConfigurationLog(_stackLog, _logFilePathName);
    }
}

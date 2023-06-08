package com.klotski.app;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.klotski.jacksonSupport.ConfigurationDeserializer;
import com.klotski.jacksonSupport.ConfigurationSerializer;

import static com.klotski.app.Constants.*;

//Dichiara a Jackson che questa classe ha un Serializer
@JsonSerialize(using = ConfigurationSerializer.class)
//Dichiara a Jackson che questa classe ha un Deserializer
@JsonDeserialize(using = ConfigurationDeserializer.class)


/**
 * Classe che rappresenta una configurazione del gioco del Klotski.
 * Una configurazione è intesa come l'insieme dei pezzi e relative posizioni che formano
 * il layout di gioco prima di una mossa.
 */
public class Configuration {

    //Array di Pieces che rappresenta la configurazione
    //Sarà ordinato dal più grande al più piccolo, per comunicare più agevolmente con l'API BNM
    protected Piece[] _pieces = new Piece[CONF_PIECES_NUM];


    /**
     * Costruttore di default, che costruisce la prima configurazione.
     */
    public Configuration(){

        //Posiziona i pezzi come nella configurazione numero 1
        setPieces(1);
    }


    /**
     * Costruttore che compone la configurazione iniziale designata.
     * @param configurationNumber configurazione designata.
     * @throws IllegalArgumentException se configurationNumber non è valido (minore di 1 o maggiore di 4)
     */
    public Configuration(int configurationNumber) throws IllegalArgumentException{

        //Posiziona i pezzi come nella configurazione numero configurationNumber
        setPieces(configurationNumber);
    }


    /**
     * Costruttore con array di Piece come parametro.
     * Permette (potenzialmente) di creare una configurazione diversa dalle 4 iniziali previste.
     * @param p array di Piece.
     * @throws IllegalArgumentException se p non contiene esattamente 10 pezzi
     */
    public Configuration(Piece[] p) {
        //Se l'array di pezzi non contiene esattamente 10 pezzi lancia eccezione
        if(p.length != CONF_PIECES_NUM){
            throw new IllegalArgumentException("Una configurazione deve avere sempre" + CONF_PIECES_NUM +"pezzi");
        }

        System.arraycopy(p, 0, _pieces, 0, p.length);
    }


    /**
     * Metodo che ritorna l'array di Piece.
     * @return pieces campo di esemplare.
     */
    public Piece[] getPieces() {
        return _pieces;
    }


    /**
     * Metodo che setta i pezzi dell'array pieces.
     * @param confNumber configurazione designata per i pezzi.
     * @throws IllegalArgumentException se confNumber non è valido (minore di 1 o maggiore di 4)
     */
    public void setPieces(int confNumber) throws IllegalArgumentException{
        int[] piecesType = getPiecesType(confNumber);
        Tuple[] positions = getPositions(confNumber);

        for (int j = 0; j < _pieces.length; j++) {
            int pieceType = piecesType[j];
            _pieces[j] = new Piece(pieceType);
            _pieces[j].setLayoutX(positions[j].getX());
            _pieces[j].setLayoutY(positions[j].getY());
        }
    }


    /**
     * Metodo che ritorna i tipi dei pezzi che compongono la configurazione designata.
     * @param configurationNumber configurazione designata.
     * @return types array con il tipo di ciascun pezzo.
     * @throws IllegalArgumentException se configurationNumber non è valido (minore di 1 o maggiore di 4)
     */
    public static int[] getPiecesType(int configurationNumber) {
        int[] types;

        //Inserisce in un array il tipo dei pezzi in base al numero della confiugurazione iniziale
        switch (configurationNumber) {
            case 1 -> types = config1PieceTypes;
            case 2 -> types = config2PieceTypes;
            case 3 -> types = config3PieceTypes;
            case 4 -> types = config4PieceTypes;
            default -> throw new IllegalArgumentException("configurationNumber non compreso tra 1 e 4");
        }

        return types;
    }


    /**
     * Metodo che ritorna un array di tuple che rappresentano le coordinate delle posizioni dei pezzi.
     * @param configurationNumber configurazione designata.
     * @return positions array di tuple.
     * @throws IllegalArgumentException se configurationNumber non è valido (minore di 1 o maggiore di 4)
     */
    public static Tuple[] getPositions(int configurationNumber) {
        int[] positionX;
        int[] positionY;

        // Le misure si riferiscono al pixel in angolo in alto a sinistra, dei pezzi rispettivi
        // scritti in getPieces
        //Inserisce in un array le coordinate dei pezzi in base al numero della confiugurazione iniziale
        switch (configurationNumber) {

            case 1 -> {
                positionX = config1PieceX;
                positionY = config1PieceY;
            }
            case 2 -> {
                positionX = config2PieceX;
                positionY = config2PieceY;
            }
            case 3 -> {
                positionX = config3PieceX;
                positionY = config3PieceY;
            }
            case 4 -> {
                positionX = config4PieceX;
                positionY = config4PieceY;
            }
            default -> throw new IllegalArgumentException("configurationNumber non compreso tra 1 e 4");
        }

        Tuple[] positions = new Tuple[positionX.length];
        for (int i = 0; i < positionX.length; i++) {
            positions[i] = new Tuple(positionX[i], positionY[i]);
        }

        return positions;
    }


    /**
     * Metodo che verifica se la configurazione passata è uguale ad una delle 4 configurazioni iniziali.
     * @param conf configurazione da verificare.
     * @return numero della configurazione corrispondente, altrimenti ritorna 0.
     */
    public static int isInitialConfiguration(Configuration conf){
        Configuration initConf;

        for(int i = 1; i <= 4; i++){
            initConf = new Configuration(i);
            // Controlliamo se la configurazione è uguale alla configurazione iniziale iterata.
            if((conf.toString()).equals(initConf.toString())){
                //System.out.println(i);
                return i;
            }
        }
        // Nessuna corrispondenza trovata.
        return 0;
    }


    /**
     * Metodo per stampare una configurazione.
     * @return sconf stringa che rappresenta la configurazione.
     */
    public String toString(){
        String sconf = "";
        for(Piece p : _pieces){
            sconf = sconf + p.toString() + "\n";
        }
        return sconf;
    }
}


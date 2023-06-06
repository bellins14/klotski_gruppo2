package com.klotski.app;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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

    /* Array di Pieces che rappresenta la configurazione
       Saranno ordinati dal più grande al più piccolo, per comunicare più agevolmente con l'API BNM
     */
    protected Piece[] _pieces = new Piece[10];


    /**
     * Costruttore di default, che costruisce la prima configurazione.
     */
    public Configuration(){

        //Posiziona i pezzi come nella configurazione numero 1
        set_pieces(1);
    }


    /**
     * Costruttore che compone la configurazione iniziale designata.
     * @param configurationNumber configurazione designata.
     * @throws IllegalArgumentException se configurationNumber non è valido (minore di 1 o maggiore di 4)
     */
    public Configuration(int configurationNumber) throws IllegalArgumentException{

        //Posiziona i pezzi come nella configurazione numero @configurationNumber
        set_pieces(configurationNumber);
    }


    /**
     * Costruttore con array di Piece come parametro. Attenzione che non fa la deep copy dell'array passato.
     * @param p array di Piece.
     * @throws IllegalArgumentException se p non contiene esattamente 10 pezzi
     */
    public Configuration(Piece[] p) {
        //Se l'array di pezzi non contiene esattamente 10 pezzi lancia eccezione
        if(p.length != 10){
            throw new IllegalArgumentException("Una configurazione deve avere sempre 10 pezzi");
        }

        System.arraycopy(p, 0, _pieces, 0, p.length);
    }


    /*
    public int getConfiguration() {
        return _configurationNumber;
    }
    */


    /**
     * Metodo che ritorna l'array di Piece.
     * @return pieces campo di esemplare.
     */
    public Piece[] get_pieces() {
        return _pieces;
    }



    /**
     * Metodo che ritorna una deep copy(si spera) dell'array di pieces.
     * @return blks copia profonda dell'array pieces.
     */
    /*
    public Piece[] getCopyBlocks(){
        Piece[] blks = new Piece[blocks.length];
        for(int i = 0; i < blocks.length; i++){
            // Usiamo il costruttore di blocks(h, w) per fare una copia degli oggietti. In questo modo riusciamo ad aggiungere
            // oggetti diversi nello stack.
            blks[i] = new Piece((int)blocks[i].getHeight(), (int)blocks[i].getWidth());
        }
        return blks;
    }*/


    /**
     * Metodo che setta i pezzi dell'array pieces.
     * @param confNumber configurazione designata per i pezzi.
     * @throws IllegalArgumentException se confNumber non è valido (minore di 1 o maggiore di 4)
     */
    public void set_pieces(int confNumber) throws IllegalArgumentException{
        int[] piecesType = getPiecesType(confNumber);
        Tuple[] positions = getPositions(confNumber);

        for (int j = 0; j < _pieces.length; j++) {
            int pieceType = piecesType[j];
            _pieces[j] = new Piece(pieceType); //Cambiato, basta mettere il tipo qui e mette il nome giusto all'immagine
            _pieces[j].setLayoutX(positions[j].getX());
            _pieces[j].setLayoutY(positions[j].getY());

            // #### DEBUG ####
            //System.out.println("Setted piece "+pieces[j]);
        }
    }


    /**
     * Metodo che ritorna i tipi dei pezzi che compongono la configurazione designata.
     * @param configurationNumber configurazione designata.
     * @return types array con il tipo di ciascun pezzo.
     * @throws IllegalArgumentException se configurationNumber non è valido (minore di 1 o maggiore di 4)
     */
    public int[] getPiecesType(int configurationNumber) {
        int[] types;

        // assegna ordine rettangoli per sezione (ordine decrescente)
        switch (configurationNumber) {
            case 1, 4 -> types = new int[]{3, 2, 1, 1, 1, 1, 0, 0, 0, 0};
            case 2, 3 -> types = new int[]{3, 2, 2, 1, 1, 1, 0, 0, 0, 0};
            default -> throw new IllegalArgumentException("configurationNumber non compreso tra 0 e 3");
        }

        return types;
    }


    /**
     * Metodo che ritorna un array di tuple che rappresentano le coordinate delle posizioni dei pezzi.
     * @param configurationNumber configurazione designata.
     * @return positions array di tuple.
     * @throws IllegalArgumentException se configurationNumber non è valido (minore di 1 o maggiore di 4)
     */
    public Tuple[] getPositions(int configurationNumber) {
        int[] positionX;
        int[] positionY;

        // Le misure si riferiscono al pixel in angolo in alto a sinistra, dei pezzi rispettivi
        // scritti in getPieces
        switch (configurationNumber) {
            case 1 -> {
                positionX = new int[]{100, 100, 300, 0, 300, 0, 200, 100, 200, 100};
                positionY = new int[]{0, 400, 200, 200, 0, 0, 300, 300, 200, 200};
            }
            case 2 -> {
                positionX = new int[]{100, 200, 0, 300, 100, 0, 300, 0, 300, 0};
                positionY = new int[]{0, 400, 400, 100, 200, 100, 300, 300, 0, 0};
            }
            case 3 -> {
                positionX = new int[]{200, 200, 100, 0, 100, 0, 300, 300, 200, 100};
                positionY = new int[]{100, 400, 300, 200, 100, 0, 300, 0, 0, 0};
            }
            case 4 -> {
                positionX = new int[]{100, 100, 300, 0, 300, 0, 300, 200, 100, 0};
                positionY = new int[]{0, 200, 200, 200, 0, 0, 400, 300, 300, 400};
            }
            default -> throw new IllegalArgumentException("configurationNumber non compreso tra 0 e 3");
        }

        Tuple[] positions = new Tuple[positionX.length];
        for (int i = 0; i < positionX.length; i++) {
            positions[i] = new Tuple(positionX[i], positionY[i]);
        }

        return positions;
    }


    /**
     * Metodo che verifica se la configurazione passata è uguale ad una delle configurazioni.
     * @param conf configurazione da verificare.
     * @return numero della configurazione corrispondente, altrimenti ritorna 0. //TODO: lanciare eccezione, non 0
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


package com.klotski.app;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

// Dichiara a Jackson che questa classe ha un Serializer
@JsonSerialize(using = ConfigurationSerializer.class)
// Dichiara a Jackson che questa classe ha un Deserializer
@JsonDeserialize(using = ConfigurationDeserializer.class)


/**
 * Classe che rappresenta una configurazione del gioco del Klotski.
 * Una configurazione è intesa come l'insieme dei blocchi e relative posizioni che formano
 * il layout di gioco prima di una mossa.
 */
public class Configuration {

    //Numero della configurazione, da 1 a 4 (tra le 4 disponibili)
    private final int _configurationNumber;

    /* Array di piece che rappresenta la configurazione
       Saranno ordinati dal più grande al più piccolo, per comunicare più agevolmente con l'API BNM
     */
    protected Piece[] blocks = new Piece[10];


    /**
     * Costruttore di default, che costruisce la prima configurazione.
     */
    public Configuration(){
        this._configurationNumber = 1;
        setBlocks(_configurationNumber);
    }


    /**
     * Costruttore che compone la configurazione iniziale designata.
     * @param configuration configurazione designata.
     */
    public Configuration(int configuration){
        this._configurationNumber = configuration;
        setBlocks(_configurationNumber);
    }


    /**
     * Costruttore con array di Piece come parametro. Attenzione che non fa la deep copy dell'array passato.
     * @param b array di Piece.
     */
    public Configuration(Piece[] b) {
        _configurationNumber = 0;
        System.arraycopy(b, 0, blocks, 0, b.length);
    }

    public int getConfiguration() {
        return _configurationNumber;
    }

    /**
     * Metodo che ritorna l'array di blocchi.
     * @return blocks campo di esemplare.
     */
    public Piece[] getBlocks() {
        return blocks;
    }



    /**
     * Metodo che ritorna una deep copy(si spera) dell'array di blocks.
     * @return blks copia profonda dell'array blocks.
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
     * Metodo che setta i blocchi dell'array blocks.
     * @param c configurazione designata per i blocchi.
     */
    public void setBlocks(int c){
        int[] blockTypes = getBlocksType(c);
        Tuple[] positions = getPositions(c);


        for (int j = 0; j < blocks.length; j++) {
            int pieceType = blockTypes[j];
            blocks[j] = new Piece(pieceType); //Cambiato, basta mettere il tipo qui e mette il nome giusto all'immagine
            blocks[j].setLayoutX(positions[j].getX());
            blocks[j].setLayoutY(positions[j].getY());

            // #### DEBUG ####
            //System.out.println("Setted block "+blocks[j]);
        }
    }


    /**
     * Metodo che ritorna i tipi dei blocchi che compongono la configurazione designata.
     * @param c configurazione designata.
     * @return types array con il tipo di ciascun blocco.
     */
    public int[] getBlocksType(int c) {
        int[] types;

        // assegna ordine rettangoli per sezione (ordine decrescente)
        switch (c) {
            case 1, 4 -> types = new int[]{3, 2, 1, 1, 1, 1, 0, 0, 0, 0};
            case 2, 3 -> types = new int[]{3, 2, 2, 1, 1, 1, 0, 0, 0, 0};
            default -> {
                return null;
            }
        }

        return types;
    }


    /**
     * Metodo che ritorna un array di tuple che rappresentano le coordinate delle posizioni dei blocchi.
     * @param c configurazione deisgnata.
     * @return positions array di tuple.
     */
    public Tuple[] getPositions(int c) {
        int[] positionX;
        int[] positionY;

        // Le misure si riferiscono al pixel in angolo in alto a sinistra, dei blocchi rispettivi
        // scritti in getBlocks
        switch (c) {
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
            default -> {
                return null;
            }
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
        for(Piece p : blocks){
            sconf = sconf + p.toString() + "\n";
        }
        return sconf;
    }
}


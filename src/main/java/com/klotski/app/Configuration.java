package com.klotski.app;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.HashMap;
import java.util.Map;


/**
 * Classe che rappresenta una configurazione del gioco del Klotski.
 * Una configurazione è intesa come l'insieme dei blocchi e relative posizioni che formano
 * il layout di gioco prima di una mossa.
 */
@JsonSerialize(using = ConfigurationSerializer.class) // Dichiariamo a Jackson che questa classe ha un Serializer
@JsonDeserialize(using = ConfigurationDeserializer.class) // Dichiariamo a Jackson che questa classe ha un Deserializer
public class Configuration {

    //configurazione scelta, sarebbe da togliere
    private final int _configuration;

    /* Array di piece che rappresenta la configurazione
       Saranno ordinati dal più grande al più piccolo, per comunicare più agevolmente con l'API BNM
     */
    protected Piece[] blocks = new Piece[10];


    /**
     * Costruttore di default, che costruisce la prima configurazione.
     */
    public Configuration(){
        this._configuration = 1;
        setBlocks(_configuration);
    }


    /**
     * Costruttore che compone la configurazione iniziale designata.
     * @param configuration configurazione designata.
     */
    public Configuration(int configuration){
        this._configuration = configuration;
        setBlocks(_configuration);
    }


    /**
     * Costruttore con array di Piece come parametro.
     * @param b array di Piece.
     */
    public Configuration(Piece[] b) {
        _configuration = 0;
        System.arraycopy(b, 0, blocks, 0, b.length);
    }


    /**
     * Metodo che ritorna l'array di blocchi.
     * @return blocks campo di esemplare.
     */
    public Piece[] getBlocks() {
        return blocks;
    }


    /**
     * Metodo che setta i blocchi dell'array blocks.
     * @param c configurazione designata per i blocchi.
     */
    public void setBlocks(int c){
        int[] blockTypes = getBlocksType(c);
        Tuple[] positions = getPositions(c);


        for (int j = 0; j < blocks.length; j++) {
            int pieceType = blockTypes[j];
            blocks[j] = new Piece(pieceType, "piece"+pieceType+".png");
            blocks[j].setLayoutX(positions[j].getX());
            blocks[j].setLayoutY(positions[j].getY());

            // #### DEBUG ####
            //System.out.println("Setted block "+blocks[j]);
        }
    }


    /**
     * Metodo che ritorna i tipi dei blocchi che compondono la configurazione designata.
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


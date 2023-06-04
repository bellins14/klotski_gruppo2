package com.project.klotski;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    //configurazione scelta
    private final int _configuration;

    // Array di piece che rappresenta la configurazione
    // Saranno ordinati dal più grande al più piccolo, per comunicare più agevolmente con l'API BNM
    protected Piece[] _blocks = new Piece[10];


    //==============
    // CONSTRUCTORS
    //==============
    //di default ho scelto la prima
    public Configuration(){
        this._configuration = 1;
        setBlocks(_configuration);
    }

    public Configuration(int configuration){
        this._configuration = configuration;
        setBlocks(_configuration);
    }

    public int getConfiguration() {
        return _configuration;
    }
    public Piece[] getBlocks() {
        return _blocks;
    }

    public void setBlocks(int c){
        int[] blockTypes = getBlocksType(c);
        Tuple[] positions = getPositions(c);


        for (int j = 0; j < _blocks.length; j++) {
            int pieceType = blockTypes[j];
            _blocks[j] = new Piece(pieceType); //Cambiato, basta mettere il tipo qui e mette il nome giusto all'immagine
            _blocks[j].setLayoutX(positions[j].getX());
            _blocks[j].setLayoutY(positions[j].getY());

            // #### DEBUG ####
            //System.out.println("Setted block "+blocks[j]);
        }
    }

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

    //in base alla conf mi ritornano le posizioni su come mettere i vari blocchi
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
}

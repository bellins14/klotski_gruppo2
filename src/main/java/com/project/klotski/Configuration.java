package com.project.klotski;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    //configurazione scelta
    private final int _configuration;

    // Array di piece che rappresenta la configurazione
    // Saranno ordinati dal più grande al più piccolo, per comunicare più agevolmente con l'API BNM
    // private Piece[] pieces

    public Configuration(int configuration){
        this._configuration = configuration;
    }

    //di defautl ho scelto la prima
    public Configuration(){
        this._configuration = 1;
    }


    //in base alla conf, mi ritornano i Piece con i rispettivi colori
    public Piece[] getBlocks() {
        Piece[] blocks = new Piece[10];

        Map<Integer, String> imageMap = new HashMap<>();

        imageMap.put(0, "piece0.png");
        imageMap.put(1, "piece1.png");
        imageMap.put(2, "piece2.png");
        imageMap.put(3, "piece3.png");
        int[] values;

        // assegna ordine rettangoli per sezione (da cambiare l'ordine)
        switch (this._configuration) {
            case 1 -> values = new int[]{1, 3, 1, 1, 0, 0, 0, 0, 1, 2};
            case 2 -> values = new int[]{0, 3, 0, 1, 1, 1, 0, 0, 2, 2};
            case 3 -> values = new int[]{1, 0, 0, 0, 1, 1, 3, 2, 0, 2};
            case 4 -> values = new int[]{1, 3, 1, 1, 2, 0, 0, 1, 0, 0};
            default -> {
                return null;
            }
        }

        // assegna immagine corrispondente al piece*n*
        for (int i = 0; i < blocks.length; i++) {
            int value = values[i];
            String image = imageMap.get(value);
            blocks[i] = new Piece(value, image);
        }

        return blocks;
    }

    //in base alla conf mi ritornano le posizioni su come mettere i vari blocchi
    public Tuple[] getPositions() {
        int[] positionX;
        int[] positionY;

        // Le misure si riferiscono al pixel in angolo in alto a sinistra, dei blocchi rispettivi
        // scritti in getBlocks
        switch (this._configuration) {
            case 1 -> {
                positionX = new int[]{0, 100, 300, 0, 100, 200, 100, 200, 300, 100};
                positionY = new int[]{0, 0, 0, 200, 200, 200, 300, 300, 200, 400,};
            }
            case 2 -> {
                positionX = new int[]{0, 100, 300, 0, 100, 300, 0, 300, 0, 200};
                positionY = new int[]{0, 0, 0, 100, 200, 100, 300, 300, 400, 400};
            }
            case 3 -> {
                positionX = new int[]{0, 100, 200, 300, 0, 100, 200, 100, 300, 200};
                positionY = new int[]{0, 0, 0, 0, 200, 100, 100, 300, 300, 400};
            }
            case 4 -> {
                positionX = new int[]{0, 100, 300, 0, 100, 100, 200, 300, 0, 300};
                positionY = new int[]{0, 0, 0, 200, 200, 300, 300, 200, 400, 400};
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

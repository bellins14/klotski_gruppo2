package com.project.klotski;

import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    //configurazione scelta
    private final int _configuration;

    public Configuration(int configuration){
        this._configuration = configuration;
    }

    //di defautl ho scelto la prima
    public Configuration(){
        this._configuration = 1;
    }


    //in base alla conf, mi ritornano i Piece con i rispettivi colori
    public Piece[] getButtons() {
        Piece[] buttons = new Piece[10];

        Map<Integer, String> imageMap = new HashMap<>();
        /*imageMap.put(0, Color.LIGHTCORAL);
        imageMap.put(1, Color.GREEN);
        imageMap.put(2, Color.LIGHTBLUE);
        imageMap.put(3, Color.BROWN);
*/
        imageMap.put(0, "piece0.png");
        imageMap.put(1, "piece1.png");
        imageMap.put(2, "piece2.png");
        imageMap.put(3, "piece3.png");
        int[] values;
        switch (this._configuration) {
            case 1 -> values = new int[]{1, 3, 1, 1, 0, 0, 0, 0, 1, 2};
            case 2 -> values = new int[]{0, 3, 0, 1, 1, 1, 0, 0, 2, 2};
            case 3 -> values = new int[]{1, 0, 0, 0, 1, 1, 3, 2, 0, 2};
            case 4 -> values = new int[]{1, 3, 1, 1, 2, 0, 0, 1, 0, 0};
            default -> {
                return null;
            }
        }

        for (int i = 0; i < buttons.length; i++) {
            int value = values[i];
            String image = imageMap.get(value);
            buttons[i] = new Piece(value, image);
        }

        return buttons;
    }

    //in base alla conf mi ritornano le posizioni su come mettere i vari blocchi
    public Tuple[] getPositions() {
        int[] positionX;
        int[] positionY;

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

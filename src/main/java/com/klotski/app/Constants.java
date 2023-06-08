package com.klotski.app;

import javafx.scene.paint.Color;

public class Constants {

    //Serializzazione e scrittura su file

    public static final String DC_FILE = "src/main/resources/com/klotski/app/json/ConfigurationDC.json";
    public static final String LOG_FILE = "src/main/resources/com/klotski/app/json/ConfigurationLog.json";

    //JFX Pane:

    //Dimensioni massime
    public static final int MAX_PANE_HEIGHT = 500;
    public static final int MAX_PANE_WIDTH = 400;

    //Colore del bordo
    public static final String PANE_BORDER_COLOR = "-fx-border-color: black";
    //Pezzi

    //Bordo
    public static final Color PIECE_STROKE_COLOR = Color.BLACK; //Colore
    public static final int PIECE_STROKE_WIDTH = 3; //Spessore
    public static final int PIECE_ARC_DIM = 10; //Curvatura spigoli

    //Piece 0
    public static final int PIECE_0_HEIGHT = 100;
    public static final int PIECE_0_WIDTH = 100;
    public static final String PIECE_0_IMAGE_NAME = "img/piece0.png";


    //Piece 1
    public static final int PIECE_1_HEIGHT = 200;
    public static final int PIECE_1_WIDTH = 100;
    public static final String PIECE_1_IMAGE_NAME = "img/piece1.png";

    //Piece 2
    public static final int PIECE_2_HEIGHT = 100;
    public static final int PIECE_2_WIDTH = 200;
    public static final String PIECE_2_IMAGE_NAME = "img/piece2.png";

    //Piece 3
    public static final int PIECE_3_HEIGHT = 200;
    public static final int PIECE_3_WIDTH = 200;
    public static final String PIECE_3_IMAGE_NAME = "img/piece3.png";



    //Configurazioni

    //Numero di pezzi per configurazione
    public static final int CONF_PIECES_NUM = 10;

    //Configurazioni iniziali

    //Configurazione iniziale 1
    public static final int[] config1PieceTypes = {3, 2, 1, 1, 1, 1, 0, 0, 0, 0};
    public static final int[] config1PieceX = {100, 100, 300, 0, 300, 0, 200, 100, 200, 100};
    public static final int[] config1PieceY = {0, 400, 200, 200, 0, 0, 300, 300, 200, 200};

    //Configurazione iniziale 2
    public static final int[] config2PieceTypes = {3, 2, 2, 1, 1, 1, 0, 0, 0, 0};
    public static final int[] config2PieceX = {100, 200, 0, 300, 100, 0, 300, 0, 300, 0};
    public static final int[] config2PieceY = {0, 400, 400, 100, 200, 100, 300, 300, 0, 0};

    //Configurazione iniziale 3
    public static final int[] config3PieceTypes = {3, 2, 2, 1, 1, 1, 0, 0, 0, 0};
    public static final int[] config3PieceX = {200, 200, 100, 0, 100, 0, 300, 300, 200, 100};
    public static final int[] config3PieceY = {100, 400, 300, 200, 100, 0, 300, 0, 0, 0};

    //Configurazione iniziale 4
    public static int[] config4PieceTypes = {3, 2, 1, 1, 1, 1, 0, 0, 0, 0};
    public static int[] config4PieceX = {100, 100, 300, 0, 300, 0, 300, 200, 100, 0};
    public static int[] config4PieceY = {0, 200, 200, 200, 0, 0, 400, 300, 300, 400};

}

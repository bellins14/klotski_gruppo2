package com.klotski.app;

import javafx.scene.paint.Color;

/**
 * Classe contenente tutte le costanti utilizzate.
 */
public class Constants {

    //Dettagli generali:

    /**
     * Path dell'icona del gioco.
     */
    public static final String ICON_PATH_NAME = "img/icons/icon.png";

    /**
     * Nome applicazione.
     */
    public static final String APPLICATION_NAME = "Klotski Game - Gruppo2";

    /**
     * File fxml (per JavaFX).
     */
    public static final String VIEW_FXML_FILENAME = "view.fxml";

    /**
     * File css (per lo stile di JavaFX).
     */
    public static final String STYLE_CSS_FILENAME = "style.css";



    //Serializzazione e scrittura su file:

    /**
     * File di supporto per la serializzazione (json).
     */
    //public static final String DC_FILE = "src/main/resources/com/klotski/app/json/ConfigurationDC.json";

    /**
     * File di log (o storico o database) delle mosse (in forma di stringhe json di configurazioni).
     */
    //public static final String LOG_FILE = "src/main/resources/com/klotski/app/json/ConfigurationLog.json";

    /**
     * Dimensione del log quando è vuoto.
     */
    public static  int EMPTY_LOG_SIZE = 0;

    /**
     * Dimensione del log quando ha una configurazione iniziale.
     */
    public static int SINGLE_LOG_SIZE = 1;


    //JFX Scene:

    /**
     * Larghezza della scena.
     */
    public static final int  SCENE_V = 1000;

    /**
     * Altezza della scena.
     */
    public static final int SCENE_V1 = 700;


    //JFX Pane:

    /**
     * Altezza massima del pane.
     */
    public static final int MAX_PANE_HEIGHT = 500;

    /**
     * Larghezza massima del pane.
     */
    public static final int MAX_PANE_WIDTH = 400;

    /**
     * Colore del bordo.
     */
    public static final String PANE_BORDER_COLOR = "-fx-border-color: black";


    // Bottoni JFX:
    /**
     * Costante per il settaggio dei bottoni.
     */
    public static final int STROKE_TRANSITION_MILLIS = 200; //Tempo transizione del bordo

    /**
     * Costante per il settaggio dei bottoni.
     */
    public static final Color STROKE_START_COLOR = Color.grayRgb(3); //Colore di partenza

    /**
     * Costante per il settaggio dei bottoni.
     */
    public static final Color STROKE_END_COLOR = Color.grayRgb(6); //Colore di destinazione

    /**
     * Costante per il settaggio dei bottoni.
     */
    public static final int CYCLE_COUNT = 2;

    /**
     * Costante per il settaggio dei bottoni.
     */
    public static final boolean TRANSITION_AUTOREVERSE = true;


    //Coordinate per la vittoria:
    /**
     * Coordinata X per la vittoria.
     */
    public static final int WIN_X = 100;

    /**
     * Coordinata Y per la vittoria.
     */
    public static final int WIN_Y = 300;

    /**
     * Di quanti px muovere ciascun pezzo nel gioco.
     */
    public static final double MOVE_AMOUNT = 100;



    //NBM:
    /**
     * Stringa per script NBM.
     */
    public static final String NBM_SCRIPT = "JSON.stringify(window.klotski.solve(window.game))";

    /**
     * Path file HTML per poter risolvere l'NBM.
     */
    //public static final String NBM_SOLVER_HTML_FILE = "src/main/resources/com/klotski/app/solver.html";



    // Indici di direzione:

    // Arrows
    /**
     * Codice identificativo per la corrispondente freccia direzionale.
     */
    public static final int ARROW_DOWN = 19;

    /**
     * Codice identificativo per la corrispondente freccia direzionale.
     */
    public static final int ARROW_RIGHT = 18;

    /**
     * Codice identificativo per la corrispondente freccia direzionale.
     */
    public static final int ARROW_UP = 17;

    /**
     * Codice identificativo per la corrispondente freccia direzionale.
     */
    public static final int ARROW_LEFT = 16;

    // Keyboard buttons
    /**
     * Codice identificativo per il corrispondente tasto.
     */
    public static final int S = 54;

    /**
     * Codice identificativo per il corrispondente tasto.
     */
    public static final int D = 39;

    /**
     * Codice identificativo per il corrispondente tasto.
     */
    public static final int W = 58;

    /**
     * Codice identificativo per il corrispondente tasto.
     */
    public static final int A = 36;

    // Pezzi:

    // Bordo:
    /**
     * Colore del bordo dei pezzi.
     */
    public static final Color PIECE_STROKE_COLOR = Color.BLACK;

    /**
     * Spessore del bordo di un pezzo se non selezionato.
     */
    public static final int UNSELECTED_PIECE_STROKE_WIDTH = 3;

    /**
     * Spessore del bordo di un pezzo se selezionato.
     */
    public static final int SELECTED_PIECE_STROKE_WIDTH = 5;

    /**
     * Curvatura degli spigoli dei pezzi.
     */
    public static final int PIECE_ARC_DIM = 10;

    // Piece 0
    /**
     * Altezza del pezzo unitario.
     */
    public static final int PIECE_0_HEIGHT = 100;

    /**
     * Larghezza del pezzo unitario.
     */
    public static final int PIECE_0_WIDTH = 100;

    /**
     * Nome dell'immagine del pezzo unitario.
     */
    public static final String PIECE_0_IMAGE_NAME = "img/piece0.png";

    // Piece 1
    /**
     * Altezza del pezzo 1x2.
     */
    public static final int PIECE_1_HEIGHT = 200;

    /**
     * Larghezza del pezzo 1x2.
     */
    public static final int PIECE_1_WIDTH = 100;

    /**
     * Nome dell'immagine del pezzo 1x2.
     */
    public static final String PIECE_1_IMAGE_NAME = "img/piece1.png";

    // Piece 2
    /**
     * Altezza del pezzo 2x1.
     */
    public static final int PIECE_2_HEIGHT = 100;

    /**
     * Larghezza del pezzo 2x1.
     */
    public static final int PIECE_2_WIDTH = 200;

    /**
     * Nome dell'immagine del pezzo 2x1.
     */
    public static final String PIECE_2_IMAGE_NAME = "img/piece2.png";

    // Piece 3
    /**
     * Altezza del pezzo speciale.
     */
    public static final int PIECE_3_HEIGHT = 200;

    /**
     * Larghezza del pezzo speciale.
     */
    public static final int PIECE_3_WIDTH = 200;

    /**
     * Nome dell'immagine del pezzo speciale.
     */
    public static final String PIECE_3_IMAGE_NAME = "img/piece3.png";


    // Configurazioni:

    /**
     * Numero di pezzi per configurazione.
     */
    public static final int CONF_PIECES_NUM = 10;


    // Configurazioni iniziali:

    // Configurazione 1
    /**
     * Array contenente i valori dei tipi di blocchi contenuti nella configurazione iniziale 1.
     */
    public static final int[] config1PieceTypes = {3, 2, 1, 1, 1, 1, 0, 0, 0, 0};

    /**
     * Array contenente i valori delle coordinate X dei blocchi contenuti nella configurazione iniziale 1.
     */
    public static final int[] config1PieceX = {100, 100, 300, 0, 300, 0, 200, 100, 200, 100};

    /**
     * Array contenente i valori delle coordinate Y dei blocchi contenuti nella configurazione iniziale 1.
     */
    public static final int[] config1PieceY = {0, 400, 200, 200, 0, 0, 300, 300, 200, 200};

    //Configurazione iniziale 2
    /**
     * Array contenente i valori dei tipi di blocchi contenuti nella configurazione iniziale 2.
     */
    public static final int[] config2PieceTypes = {3, 2, 2, 1, 1, 1, 0, 0, 0, 0};

    /**
     * Array contenente i valori delle coordinate X dei blocchi contenuti nella configurazione iniziale 2.
     */
    public static final int[] config2PieceX = {100, 200, 0, 300, 100, 0, 300, 0, 300, 0};

    /**
     * Array contenente i valori delle coordinate Y dei blocchi contenuti nella configurazione iniziale 2.
     */
    public static final int[] config2PieceY = {0, 400, 400, 100, 200, 100, 300, 300, 0, 0};

    //Configurazione iniziale 3
    /**
     * Array contenente i valori dei tipi di blocchi contenuti nella configurazione iniziale 3.
     */
    public static final int[] config3PieceTypes = {3, 2, 2, 1, 1, 1, 0, 0, 0, 0};

    /**
     * Array contenente i valori delle coordinate X dei blocchi contenuti nella configurazione iniziale 3.
     */
    public static final int[] config3PieceX = {200, 200, 100, 0, 100, 0, 300, 300, 200, 100};

    /**
     * Array contenente i valori delle coordinate Y dei blocchi contenuti nella configurazione iniziale 3.
     */
    public static final int[] config3PieceY = {100, 400, 300, 200, 100, 0, 300, 0, 0, 0};

    //Configurazione iniziale 4
    /**
     * Array contenente i valori dei tipi di blocchi contenuti nella configurazione iniziale 4.
     */
    public static final int[] config4PieceTypes = {3, 2, 1, 1, 1, 1, 0, 0, 0, 0};

    /**
     * Array contenente i valori delle coordinate X dei blocchi contenuti nella configurazione iniziale 4.
     */
    public static final int[] config4PieceX = {100, 100, 300, 0, 300, 0, 300, 200, 100, 0};

    /**
     * Array contenente i valori delle coordinate Y dei blocchi contenuti nella configurazione iniziale 3.
     */
    public static final int[] config4PieceY = {0, 200, 200, 200, 0, 0, 400, 300, 300, 400};

    /**
     * Costruttore vuoto privato per rendere la classe statica.
     */
    private Constants(){
    }
}

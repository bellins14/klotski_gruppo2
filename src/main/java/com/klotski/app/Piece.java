package com.klotski.app;

import javafx.scene.shape.Rectangle;

import static com.klotski.app.Constants.*;

/**
 * Classe che rappresenta un pezzo del Klotski.
 */
public class Piece extends Rectangle {

    //Nome dell'immagine associata al pezzo
    private String _imageName;

    //Tipo del pezzo (può variare da 0 a 3)
    private int _type;


    /**
     * Costruttore di default, che inizializza un pezzo di tipo 0 di dimensioni 100x100.
     */
    public Piece(){
        //Richiama il costruttore di Rectangle
        super();

        //Setta il tipo e, in base a questo, gli attributi del pezzo
        this.setType(0);

    }


    /*  Ricordarsi che 100x100 è 100 di larghezza per 100 di altezza, mentre le coordinate del JSON sono
        invertite.
     */
    /**
     * Costruttore che inizializza un pezzo a partire dal tipo.
     * @param pieceType tipo del pezzo: 0 = 100x100; 1 = 100x200; 2 = 200x100; 3 = 300x300.
     * @throws IllegalArgumentException se pieceType non è valido (minore di 0 o maggiore di 3)
     */

    public Piece(int pieceType) throws IllegalArgumentException{
        //Richiama il costruttore di Rectangle
        super();

        //Setta il tipo e, in base a questo, gli attributi del pezzo
        this.setType(pieceType); //Lancia IllegalArgumentException se pieceType minore di 0 o maggiore di 3

    }



    /**
     * Costruttore con altezza e larghezza.
     * @param h altezza del pezzo.
     * @param w larghezza del pezzo.
     * @throws IllegalArgumentException se le dimensioni non sono valide
     */
    public Piece(int h, int w){
        super();
        if (h == PIECE_0_HEIGHT && w == PIECE_0_WIDTH) {
            this.setType(0);
        } else if (h == PIECE_1_HEIGHT && w == PIECE_1_WIDTH) {
            this.setType(1);
        } else if (h == PIECE_2_HEIGHT && w == PIECE_2_WIDTH) {
            this.setType(2);
        } else if (h == PIECE_3_HEIGHT && w == PIECE_3_WIDTH) {
            this.setType(3);
        } else { //Se le dimensioni non corrispondono ad un pezzo adeguato lancia eccezione
            throw new IllegalArgumentException("Dimensioni non valide");
        }

        //Setta un bordo nero di spessore 3
        this.setStroke(PIECE_STROKE_COLOR);
        this.setStrokeWidth(UNSELECTED_PIECE_STROKE_WIDTH);
    }



    /**
     * Metodo che setta tipo, nomeImmagine, dimensioni, e id del pezzo
     * @param pieceType tipo del pezzo.
     * @throws IllegalArgumentException se pieceType non è valido (minore di 0 o maggiore di 3)
     */

    public void setType(int pieceType){

        //Inizializza il tipo del pezzo
        this._type = pieceType;

        //In base a pieceType decidi quale pezzo creare
        switch (pieceType) {
            case 0 -> {
                this._imageName = PIECE_0_IMAGE_NAME; //Inizializza il nome dell'immagine in base al tipo passato
                this.setHeight(PIECE_0_HEIGHT); //Inizializza l'altezza del pezzo in base al tipo passato
                this.setWidth(PIECE_0_WIDTH); //Inizializza la larghezza del pezzo in base al tipo passato
                this.setId("0"); //Inizializza l'id del pezzo in base al tipo passato


            }
            case 1 -> {
                this._imageName = PIECE_1_IMAGE_NAME;
                this.setHeight(PIECE_1_HEIGHT);
                this.setWidth(PIECE_1_WIDTH);
                this.setId("1");

            }
            case 2 -> {
                this._imageName = PIECE_2_IMAGE_NAME;
                this.setHeight(PIECE_2_HEIGHT);
                this.setWidth(PIECE_2_WIDTH);
                this.setId("2");

            }
            case 3 -> {
                this._imageName = PIECE_3_IMAGE_NAME;
                this.setHeight(PIECE_3_HEIGHT);
                this.setWidth(PIECE_3_WIDTH);
                this.setId("3");

            }

            default -> throw new IllegalArgumentException("pieceType non compreso tra 0 e 3");
        }

        //Setta un bordo con spessore
        this.setStroke(PIECE_STROKE_COLOR);
        this.setStrokeWidth(UNSELECTED_PIECE_STROKE_WIDTH);

        //Setta una curvatura degli angoli
        this.setArcHeight(PIECE_ARC_DIM);
        this.setArcWidth(PIECE_ARC_DIM);
    }


    /**
     * Metodo per ritornare il tipo del pezzo
     * @return _type;
     */
    public int getType(){ return this._type;}


    /**
     * Metodo per ritornare il nome dell'immagine
     * @return _imageName;
     */
    public String getImageName(){
        return this._imageName;
    }



    /**
     * Metodo che ritorna una string che rappresenta un piece in un formato utile per la
     * NBM.
     * @return stringa formattata.
     */
    @Override
    public String toString(){

        return "      {\"shape\": [" + (int) (this.getHeight() / 100) + ", " +
                (int) (this.getWidth() / 100) + "], " +
                "\"position\": [" +
                (int) (this.getLayoutY() / 100) + ", " +
                (int) (this.getLayoutX() / 100) + "] },\n";
    }

}

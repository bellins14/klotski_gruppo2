package com.klotski.app;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


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

        //Setta il tipo del pezzo
        this.setType(0);

        //Setta un bordo nero di spessore 3
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(3);
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

        //Setta il tipo del pezzo
        this.setType(pieceType); //Lancia IllegalArgumentException se pieceType minore di 0 o maggiore di 3

        //Setta un bordo nero di spessore 3
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(3);
    }



    /**
     * Costruttore con altezza e larghezza.
     * @param h altezza del pezzo.
     * @param w larghezza del pezzo.
     * @throws IllegalArgumentException se le dimensioni non sono valide
     */
    public Piece(int h, int w){
        super();
        if (h == 100 && w == 100) {
            this.setType(0);
        } else if (h == 200 && w == 100) {
            this.setType(1);
        } else if (h == 100 && w == 200) {
            this.setType(2);
        } else if (h == 200 && w == 200) {
            this.setType(3);
        } else { //Se le dimensioni non corrispondono ad un pezzo adeguato lancia eccezioni
            throw new IllegalArgumentException("Dimensioni non valide");
        }

        //Setta un bordo nero di spessore 3
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(3);
    }



    /**
     * Metodo che setta tipo, nomeImmagine, dimensioni, e id del pezzo
     * @param pieceType tipo del pezzo.
     * @throws IllegalArgumentException se pieceType non è valido (minore di 0 o maggiore di 3)
     */

    public void setType(int pieceType){

        //Inizializza tipo e nome immagine del pezzo
        this._type = pieceType;
        this._imageName = "img/piece"+ pieceType +".png";

        //In base a pieceType decidi quale pezzo creare
        switch (pieceType) {
            case 0 -> {
                this.setHeight(100);
                this.setWidth(100);
                this.setArcHeight(10); //Aggiunge curvatura agli spigoli
                this.setArcWidth(10); //Aggiunge curvatura agli spigoli
                this.setId("0"); //Setta l'id del pezzo in base al tipo passato

            }
            case 1 -> {
                this.setHeight(200);
                this.setWidth(100);
                this.setArcHeight(10);
                this.setArcWidth(10);
                this.setId("1");

            }
            case 2 -> {
                this.setHeight(100);
                this.setWidth(200);
                this.setArcHeight(10);
                this.setArcWidth(10);
                this.setId("2");

            }
            case 3 -> {
                this.setHeight(200);
                this.setWidth(200);
                this.setArcHeight(10);
                this.setArcWidth(10);
                this.setId("3");

            }

            default -> throw new IllegalArgumentException("pieceType non compreso tra 0 e 3");

        }
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
     * Metodo che ritorna le dimensioni del pezzo in px/100.
     * @return co array con le dimensioni del pezzo [altezza, larghezza].
     */
    public int[] getDimensions(){
        int[] co = new int[2];
        co[0] = (int)this.getHeight()/100;
        co[1] = (int)this.getWidth()/100;
        return co;
    }


    /**
     * Metodo che ritorna la posizione del pezzo in px/100.
     * @return po array con la posizione del pezzo [X, Y].
     */
    public int[] getPosition(){
        int[] po = new int[2];
        po[0] = (int)this.getLayoutX()/100;
        po[1] = (int)this.getLayoutY()/100;
        return po;
    }

    /**
     * Metodo che contolla che non ci sia overlapping tra pezzi durante il loro spostamento.
     *
     * @param pane  pezzo che si vuole.
     * @param deltaX quantità di cui si muove il pezzo orizzontalmente.
     * @param deltaY quantità di cui si muove il pezzo verticalmente.
     * @return false se si overlappa, true se è tutto a posto.
     */
    public boolean isNotOverlapping(Pane pane, double deltaX, double deltaY) {
        // Calcola la nuova posizione del bottone
        double newX = this.getLayoutX() + deltaX;
        double newY = this.getLayoutY() + deltaY;

        // Itera su tutti gli elementi figli della Pane
        ObservableList<Node> children = pane.getChildren();
        for (Node child : children) {
            // Verifica se l'elemento figlio è un bottone diverso da quello selezionato
            if (child instanceof Piece otherPiece && child != this) {
                // Verifica se il nuovo bottone si sovrappone all'altro bottone
                if (newX + this.getWidth() > otherPiece.getLayoutX() &&
                        newX < otherPiece.getLayoutX() + otherPiece.getWidth() &&
                        newY + this.getHeight() > otherPiece.getLayoutY() &&
                        newY < otherPiece.getLayoutY() + otherPiece.getHeight()) {
                    return false;
                }
            }
        }

        return true;
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

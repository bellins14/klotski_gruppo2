package com.klotski.app;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

/**
 * Classe che rappresenta un pezzo del Klotski.
 */
public class Piece extends Rectangle {

    //colore del Piece
    private String _imageName;
    private int _type;


    /**
     * Costruttore di default, che inizializza un blocco di tipo 0 di dimensioni 100x100.
     */
    public Piece(){
        //richiamo il costruttore del rettangolo
        super();

        this.setType(0);

        //this.setImageFill("piece0.png");
        //assegno un bordo nero di spessore 3
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(3);
    }


    public Piece(int pieceType) {
        //richiamo il costruttore del rettangolo
        super();

        this.setType(pieceType);

        //this.setImageFill(imageName);
        //assegno un bordo nero di spessore 3
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(3);
    }



    /*  Ricordarsi che 100x100 è 100 di larghezza per 100 di altezza, mentre le coordinate del JSON sono
        invertite.
     */
    /**
     * Costruttore che inizializza un blocco a partire dal tipo e dalla sua skin.
     * @param pieceType tipo del pezzo: 0 = 100x100; 1 = 100x200; 2 = 200x100; 3 = 300x300.
     * @param imageName nome dell'immagine che fa da skin al blocco.
     */
    public Piece(int pieceType, String imageName) {
        //richiamo il costruttore del rettangolo
        super();

        this.setType(pieceType);
        //this.setImageFill(imageName);
        //assegno un bordo nero di spessore 3
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(3);
    }


    /**
     * Costruttore con altezza e larghezza.
     * @param h altezza del blocco.
     * @param w larghezza del blocco.
     */
    public Piece(int h, int w){
        super();
        if (h == 100 && w == 100) {
            this.setType(0);
            //this.setImageFill("piece0.png");
        } else if (h == 200 && w == 100) {
            this.setType(1);
            //this.setImageFill("piece1.png");
        } else if (h == 100 && w == 200) {
            this.setType(2);
            //this.setImageFill("piece2.png");
        } else if (h == 200 && w == 200) {
            this.setType(3);
            //this.setImageFill("piece3.png");
        }
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(3);
    }



    /**
     * Metodo che setta le dimensioni del blocco (e l'id) in base al suo tipo.
     * @param pieceType tipo del blocco.
     */

    public void setType(int pieceType){

        this._type = pieceType;
        this._imageName = "img/piece"+ pieceType +".png";

        //in base all'argomento passato capisco quale blocco creare
        switch (pieceType) {
            case 0 -> {
                this.setHeight(100);
                this.setWidth(100);
                this.setArcHeight(10);
                this.setArcWidth(10);
                this.setId("0");
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
        }
    }

    public int getType(){ return this._type;}


    /**
     * Metodo per l'impostazione della skin del blocco.
     * @param imageName nome dell'immagine che fa da skin.
     */

        /*
    public void setImageFill(String imageName) {
        setImageName(imageName);
        Image pieceImage = new Image(Objects.requireNonNull(getClass().getResource(_image)).toString());
        ImagePattern piecePattern = new ImagePattern(pieceImage);
        this.setFill(piecePattern);
    }
    */


    //public Color getColor() {return  this._color;}


    /**
     * Metodo per la composizione del path dell'immagine.
     * @param imageName nome dell'immagine.
     */
    /*
    public void setImageName(String imageName) {
        this._imageName += imageName;
    }
    */

    /**
     * Metodo per ritornare l'immagine, non so a cosa serva.
     * @return _image;
     */
    public String getImageName(){
        return this._imageName;
    }


    /**
     * Metodo che ritorna le dimensioni del blocco in px/100.
     * @return co array con le dimensioni del blocco [altezza, larghezza].
     */
    public int[] getDimensions(){
        int[] co = new int[2];
        co[0] = (int)this.getHeight()/100;
        co[1] = (int)this.getWidth()/100;
        return co;
    }


    /**
     * Metodo che ritorna la posizione del blocco in px/100.
     * @return po array con la posizione del blocco [X, Y].
     */
    public int[] getPosition(){
        int[] po = new int[2];
        po[0] = (int)this.getLayoutX()/100;
        po[1] = (int)this.getLayoutY()/100;
        return po;
    }

    /**
     * Metodo che contolla che non ci sia overlapping tra blocchi durante il loro spostamento.
     *
     * @param pane  blocco che si vuove.
     * @param deltaX quantità di cui si muove il blocco orizzontalmente.
     * @param deltaY quantità di cui si muove il blocco verticalmente.
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
            if (child instanceof Piece otherBlock && child != this) {
                // Verifica se il nuovo bottone si sovrappone all'altro bottone
                if (newX + this.getWidth() > otherBlock.getLayoutX() &&
                        newX < otherBlock.getLayoutX() + otherBlock.getWidth() &&
                        newY + this.getHeight() > otherBlock.getLayoutY() &&
                        newY < otherBlock.getLayoutY() + otherBlock.getHeight()) {
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

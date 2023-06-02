package com.klotski.app;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class Piece extends Rectangle {

    //
    private String _image = "img/";

    //==============
    // CONSTRUCTORS
    //==============
    //default
    public Piece(){
        //richiamo il costruttore del rettangolo
        super();
        //decido che il piece di default è un quadrato (100x100)
        this.setType(0);
        //immagine predefinta per il quadrato
        this.setImageFill("piece0.png");
        //assegno un bordo nero di spessore 3
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(3);
    }
    //con parametri
    public Piece(int pieceType, String imageName) {
        //richiamo il costruttore del rettangolo
        super();
        //in base al numero del pieceType va a creare il piece scelto
        this.setType(pieceType);
        //immagine predefinita per il piece scelto
        this.setImageFill(imageName);
        //assegno un bordo nero di spessore 3
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(3);
    }

    public void setType(int pieceType){
        //in base all'argomento passato capisco quale blocco creare
        switch (pieceType) {
            case 0 -> {
                this.setHeight(100);
                this.setWidth(100);
                //this.setId("a");
            }
            case 1 -> {
                this.setHeight(200);
                this.setWidth(100);
                //this.setId("b");
            }
            case 2 -> {
                this.setHeight(100);
                this.setWidth(200);
                //this.setId("c");
            }
            case 3 -> {
                this.setHeight(200);
                this.setWidth(200);
               // this.setId("d");
            }
        }
    }

    // controllo eccezione?
    public void setImageFill(String imageName) {
        setImageName(imageName);
        Image pieceImage = new Image(Objects.requireNonNull(getClass().getResource(_image)).toString());
        ImagePattern piecePattern = new ImagePattern(pieceImage);
        this.setFill(piecePattern);
    }

    public void setImageName(String imageName) {
        this._image += imageName;
    }


    //Eseguo l'override del metodo toString, stampo per ogni piece una configurazione che mi servirà per
    //la NBM.
    @Override
    public String toString(){

        return "      {\"shape\": [" + (int) (this.getHeight() / 100) + ", " +
                (int) (this.getWidth() / 100) + "], " +
                "\"position\": [" +
                (int) (this.getLayoutY() / 100) + ", " +
                (int) (this.getLayoutX() / 100) + "] },\n";
    }

}

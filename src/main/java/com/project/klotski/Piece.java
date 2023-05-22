package com.project.klotski;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class Piece extends Rectangle {

    //colore del Piece
    //private final Color _color;
    private String _image = "img/";

    public Piece(){
        //richiamo il costruttore del rettangolo
        super();

        this.setType(0);
        this.setImageFill("piece0.png");
        //assegno un bordo nero di spessore 3
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(3);
    }

    public Piece(int pieceType, String imageName) {
        //richiamo il costruttore del rettangolo
        super();

        this.setType(pieceType);
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
                this.setId("a");
            }
            case 1 -> {
                this.setHeight(200);
                this.setWidth(100);
                this.setId("b");
            }
            case 2 -> {
                this.setHeight(100);
                this.setWidth(200);
                this.setId("c");
            }
            case 3 -> {
                this.setHeight(200);
                this.setWidth(200);
                this.setId("d");
            }
        }
    }

    public void setImageFill(String imageName) {
        setImageName(imageName);
        Image pieceImage = new Image(Objects.requireNonNull(getClass().getResource(_image)).toString());
        ImagePattern piecePattern = new ImagePattern(pieceImage);
        this.setFill(piecePattern);
    }

    //public Color getColor() {return  this._color;}

    public void setImageName(String imageName) {
        this._image = imageName;
    }

    //public String getImageName(){ return this._image;}

    // rende stringa il Piece, sempre in 3 cifre (zeri aggiunti all'inizio se serve)
    // da usare per data base, richiesta next best move (bisogna fare metodo che printa la tupla dell'attuale stato in cui si richiede)
    // tipo di output: _idxxxyyy
    // esempio: a000100 = Piece 0 (che come _id ha 'a') in posizione x = 0 px, y = 100 px a partire da angolo in alto a sx
    @Override
    public String toString(){
        StringBuilder coords = new StringBuilder(this.getId());
        int x_length = Integer.toString((int) getLayoutX()).length();
        int y_length = Integer.toString((int) getLayoutY()).length();

        if(x_length < 3){
            coords.append("0".repeat(3 - x_length));
        }
        coords.append((int) getLayoutX());

        if(y_length < 3){
            coords.append("0".repeat(3 - y_length));
        }
        coords.append((int) getLayoutY());

        return coords.toString();
    }

}

package com.klotski.app;

import javafx.scene.shape.Rectangle;

public class Piece extends Rectangle {

    //colore del Piece
    //private final Color _color;
    private String _image = "";
    /*public Piece(int a, Color color) {
        //richiamo il costruttore del rettangolo
        super();
        this._color = color;

        //in base all'argomento passato capisco quale blocco creare
        switch (a) {
            case 0 -> {
                this.setHeight(100);
                this.setWidth(100);
            }
            case 1 -> {
                this.setHeight(200);
                this.setWidth(100);
            }
            case 2 -> {
                this.setHeight(100);
                this.setWidth(200);
            }
            case 3 -> {
                this.setHeight(200);
                this.setWidth(200);
            }
        }

    }*/

    public Piece(int a, String imageName) {
        //richiamo il costruttore del rettangolo
        super();
        this.setImage(imageName);
        //in base all'argomento passato capisco quale blocco creare
        switch (a) {
            case 0 -> {
                this.setHeight(100);
                this.setWidth(100);
            }
            case 1 -> {
                this.setHeight(200);
                this.setWidth(100);
            }
            case 2 -> {
                this.setHeight(100);
                this.setWidth(200);
            }
            case 3 -> {
                this.setHeight(200);
                this.setWidth(200);
            }
        }

    }

    //public Color getColor() {return  this._color;}

    public void setImage(String imageName) {
        this._image = imageName;
    }

    public String getImageName(){ return this._image;}


}

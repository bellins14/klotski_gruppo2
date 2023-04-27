package com.example.progetto;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Piece extends Rectangle {

    //colore del Piece
    private final Color _color;
    public Piece(int a, Color color) {
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

    }

    public Color getColor() {return  this._color;}


}

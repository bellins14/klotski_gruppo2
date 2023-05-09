package com.project.klotski;

import javafx.scene.shape.Rectangle;

public class Piece extends Rectangle {

    //colore del Piece
    //private final Color _color;
    private String _image = "";
    private String _id = "";

    public Piece(int a, String imageName) {
        //richiamo il costruttore del rettangolo
        super();
        this.setImage(imageName);
        //in base all'argomento passato capisco quale blocco creare
        switch (a) {
            case 0 -> {
                this.setHeight(100);
                this.setWidth(100);
                this._id = "a";
            }
            case 1 -> {
                this.setHeight(200);
                this.setWidth(100);
                this._id = "b";
            }
            case 2 -> {
                this.setHeight(100);
                this.setWidth(200);
                this._id = "c";
            }
            case 3 -> {
                this.setHeight(200);
                this.setWidth(200);
                this._id = "d";
            }
        }

    }

    //public Color getColor() {return  this._color;}

    public void setImage(String imageName) {
        this._image = imageName;
    }

    public String getImageName(){ return this._image;}

    // rende stringa il Piece, sempre in 3 cifre (zeri aggiunti all'inizio se serve)
    // da usare per data base, richiesta next best move (bisogna fare metodo che printa la tupla dell'attuale stato in cui si richiede)
    // tipo di output: _idxxxyyy
    // esempio: a000100 = Piece 0 (che come _id ha 'a') in posizione x = 0 px, y = 100 px a partire da angolo in alto a sx
    @Override
    public String toString(){
        StringBuilder coords = new StringBuilder(_id);
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

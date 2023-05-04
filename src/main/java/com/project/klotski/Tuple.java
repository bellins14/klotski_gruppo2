package com.project.klotski;

import java.lang.*;

public class Tuple {
    //coordinata X del Piece
    public final int x;

    //coordinata Y del Piece
    public final int y;

    public Tuple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {return  this.x;}
    public int getY() {return this.y;}

    // rende stringa la Tuple, sempre in 3 cifre (zeri aggiunti all'inizio se serve)
    // da usare per data base, richiesta next best move (bisogna fare metodo che printa la tupla dell'attuale stato in cui si richiede)
    @Override
    public String toString(){
        StringBuilder tuple = new StringBuilder();
        int x_length = Integer.toString(x).length();
        int y_length = Integer.toString(y).length();

        if(x_length < 3){
            tuple.append("0".repeat(3 - x_length));
        }
        tuple.append(x);

        if(y_length < 3){
            tuple.append("0".repeat(3 - y_length));
        }
        tuple.append(y);

        return tuple.toString();
    }
}

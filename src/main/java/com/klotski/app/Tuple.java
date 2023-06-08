package com.klotski.app;


/**
 * Classe che rappresenta una Tupla (struttura dati) per gestire meglio
 * le coordinate dei Piece
 */
public class Tuple {

    //Coordinata X
    public final int _x;

    //Coordinata Y
    public final int _y;


    /**
     * Costruttore con parametri che inzializza coordinata "X" e coordinata "Y"
     */
    public Tuple(int x, int y) {
        this._x = x;
        this._y = y;
    }
    /**
     * Metodo che ritorna la posizione "X"
     */
    public int getX() {return  this._x;}

    /**
     * Metodo che ritorna la posizone "Y"
     */
    public int getY() {return this._y;}
}

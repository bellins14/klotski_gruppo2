package com.klotski.app;

/**
 * Classe che rappresenta una Tupla (struttura dati) per gestire meglio
 * le coordinate dei Piece.
 */
public class Tuple {
    /**
     * Coordinata X
     */
    public final int _x;
    /**
     * Coordinata Y
     */
    public final int _y;


    /**
     * Costruttore con parametri che inizializza coordinata "X" e coordinata "Y"
     * @param x del pezzo
     * @param y del pezzo
     */
    public Tuple(int x, int y) {
        this._x = x;
        this._y = y;
    }
    /**
     * Metodo che ritorna la posizione "X"
     * @return x
     */
    public int getX() {return  this._x;}

    /**
     * Metodo che ritorna la posizione "Y"
     * @return  y
     */
    public int getY() {return this._y;}
}

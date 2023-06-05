package com.klotski.app;


/**
 * Classe che rappresenta una Tupla (struttura dati) per gestire meglio
 * le coordinate dei Piece
 */
public class Tuple {
    //coordinata X del Piece
    public final int _x;

    //coordinata Y del Piece
    public final int _y;

    //==============
    // CONSTRUCTOR
    //==============

    /**
     * Costruttore con parametri che inzializza coordinata "X" e coordinata "Y" del piece
     */
    public Tuple(int x, int y) {
        this._x = x;
        this._y = y;
    }
    /**
     * Metodo che ritorna la posizione "X" del piece
     */
    public int getX() {return  this._x;}

    /**
     * Metodo che ritorna la posizone "Y" del piece
     */
    public int getY() {return this._y;}
}

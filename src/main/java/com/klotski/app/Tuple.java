package com.klotski.app;


//Java non dispone di pair/tuple quindi l'ho creata per gestire meglio
//le posizoni dei vari piece
public class Tuple {
    //coordinata X del Piece
    public final int _x;

    //coordinata Y del Piece
    public final int _y;

    //==============
    // CONSTRUCTOR
    //==============
    public Tuple(int x, int y) {
        this._x = x;
        this._y = y;
    }

    public int getX() {return  this._x;}
    public int getY() {return this._y;}
}

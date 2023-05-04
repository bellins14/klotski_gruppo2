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
}

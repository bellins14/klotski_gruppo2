package com.klotski.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TupleTest {
    private Tuple tuple;

    // Array di coppie di posizioni
    int[][] low_pairs = new int[][]{{1, 3}, {-2, -1}, {-10, 3}, {2, -1}, {0, -1}, {-1,0}};
    int[][] high_pairs = new int[][]{{100000000, 300000000}, {-200000000, -100000000}, {-100000000, 300000000}, {200000000, -100000000}, {0, -100000000}, {-100000000,0}};
    int[][] generic_pairs = new int[][]{{1, 2140}, {-685, -504}, {-3, 44}, {546, -21}, {0, -29409}, {-6758,0}};

    @Test
    public void getX() {
        System.out.println("test getX");

        //Test per ogni coppia dell'array
        for (int[] pair : generic_pairs) {
            //Crea un'istanza di Tuple
            tuple = new Tuple(pair[0], pair[1]);

            // Chiamata del metodo da testare
            int result = tuple.getX();

            // Verifica del risultato
            assertEquals(pair[0], result);
        }
    }

    @Test
    public void getY() {
        System.out.println("test getY");

        //Test per ogni coppia dell'array
        for (int[] pair : generic_pairs) {
            //Crea un'istanza di Tuple
            tuple = new Tuple(pair[0], pair[1]);

            // Chiamata del metodo da testare
            int result = tuple.getY();

            // Verifica del risultato
            assertEquals(pair[1], result);
        }
    }

    @Test
    public void getXLow() {
        System.out.println("test getXLow");

        //Test per ogni coppia dell'array
        for (int[] pair : low_pairs) {
            //Crea un'istanza di Tuple
            tuple = new Tuple(pair[0], pair[1]);

            // Chiamata del metodo da testare
            int result = tuple.getX();

            // Verifica del risultato
            assertEquals(pair[0], result);
        }
    }

    @Test
    public void getYLow() {
        System.out.println("test getYLow");

        //Test per ogni coppia dell'array
        for (int[] pair : low_pairs) {
            //Crea un'istanza di Tuple
            tuple = new Tuple(pair[0], pair[1]);

            // Chiamata del metodo da testare
            int result = tuple.getY();

            // Verifica del risultato
            assertEquals(pair[1], result);
        }
    }

    @Test
    public void getXHigh() {
        System.out.println("test getXHigh");

        //Test per ogni coppia dell'array
        for (int[] pair : high_pairs) {
            //Crea un'istanza di Tuple
            tuple = new Tuple(pair[0], pair[1]);

            // Chiamata del metodo da testare
            int result = tuple.getX();

            // Verifica del risultato
            assertEquals(pair[0], result);
        }
    }

    @Test
    public void getYHigh() {
        System.out.println("test getYHigh");

        //Test per ogni coppia dell'array
        for (int[] pair : high_pairs) {
            //Crea un'istanza di Tuple
            tuple = new Tuple(pair[0], pair[1]);

            // Chiamata del metodo da testare
            int result = tuple.getY();

            // Verifica del risultato
            assertEquals(pair[1], result);
        }
    }

    @Test
    void getXZero() {
        System.out.println("test getXZero");

        //Test dei vari casi di tuple con X=0
        tuple = new Tuple(0,0);
        assertEquals(0, tuple.getX());

        tuple = new Tuple(0,-1);
        assertEquals(0, tuple.getX());

        tuple = new Tuple(0,1);
        assertEquals(0, tuple.getX());

        tuple = new Tuple(0,-100000000);
        assertEquals(0, tuple.getX());

        tuple = new Tuple(0,100000000);
        assertEquals(0, tuple.getX());

    }

    @Test
    void getYZero() {
        System.out.println("test getYZero");

        //Test dei vari casi di tuple con Y=0
        tuple = new Tuple(0,0);
        assertEquals(0, tuple.getY());

        tuple = new Tuple(-1,0);
        assertEquals(0, tuple.getY());

        tuple = new Tuple(1,0);
        assertEquals(0, tuple.getY());

        tuple = new Tuple(-100000000,0);
        assertEquals(0, tuple.getY());

        tuple = new Tuple(100000000,0);
        assertEquals(0, tuple.getY());
    }
}
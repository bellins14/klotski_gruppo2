package com.klotski.app;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {
    private Piece piece;

    //Test del costruttore di default
    @Test
    public void testDefaultConstructor(){
        System.out.println("test defaultConstructor");

        piece = new Piece();

        //Controlla gli attributi settati
        assertEquals(3, piece.getStrokeWidth());
        assertEquals(Color.BLACK, piece.getStroke());

        //Controlla che non ritorni null
        assertNotNull(piece);
    }

    //Test del costruttore con parametro
    @Test
    public void testParamConstructor(){
        System.out.println("test paramConstructor");

        //Testa tutti e 4 i possibili tipi
        for(int i=0; i<=3; i++) {
            piece = new Piece(i);

            //Controlla gli attributi settati
            assertEquals(3, piece.getStrokeWidth());
            assertEquals(Color.BLACK, piece.getStroke());

            //Controlla che non ritorni null
            assertNotNull(piece);
        }

    }

    //Test input illegali del costruttore con parametro
    @Test
    @Disabled //NumberFormatException da aggiungere in Piece
    public void testParamConstructorIllegal(){
        System.out.println("test paramConstructorIllegal");

        assertThrows(NumberFormatException.class, ()->{piece = new Piece(4);});
        assertThrows(NumberFormatException.class, ()->{piece = new Piece(-1);});

    }

    //Test del metodo setType
    @Test
    void setType() {
        System.out.println("test setType");

        //Testa tutti e 4 i possibili tipi
        for(int i=0; i<=3; i++) {
            piece = new Piece(i);

            //In base al tipo controlla che gli attributi height, width e id siano inizializzati al valore corretto
            switch (i) {
                case 0 -> {
                    assertEquals("img/piece0.png", piece.getImageName()); //imageName
                    assertEquals(100, piece.getHeight()); //height
                    assertEquals(100, piece.getWidth()); //width
                    assertEquals("a", piece.getId()); //id

                }
                case 1 -> {
                    assertEquals("img/piece1.png", piece.getImageName()); //imageName
                    assertEquals(200, piece.getHeight()); //height
                    assertEquals(100, piece.getWidth()); //width
                    assertEquals("b", piece.getId()); //id
                }
                case 2 -> {
                    assertEquals("img/piece2.png", piece.getImageName()); //imageName
                    assertEquals(100, piece.getHeight()); //height
                    assertEquals(200, piece.getWidth()); //width
                    assertEquals("c", piece.getId()); //id
                }
                case 3 -> {
                    assertEquals("img/piece3.png", piece.getImageName()); //imageName
                    assertEquals(200, piece.getHeight()); //height
                    assertEquals(200, piece.getWidth()); //width
                    assertEquals("d", piece.getId()); //id
                }
            }
        }
    }

    //Test input illegali del metodo setType
    @Test
    @Disabled //NumberFormatException da aggiungere in Piece
    void setTypeIllegal() {
        System.out.println("test setTypeIllegal");

        assertThrows(NumberFormatException.class, ()->{piece.setType(4);});
        assertThrows(NumberFormatException.class, ()->{piece.setType(-1);});
    }

    //Test del metodo getImageName()
    @Test
    void getImageName() {
        System.out.println("test getImageName");

        //Testa tutti e 4 i possibili tipi
        for(int i=0; i<=3; i++) {
            piece = new Piece(i);

            switch (i) {
                case 0 -> assertEquals("img/piece0.png", piece.getImageName());
                case 1 -> assertEquals("img/piece1.png", piece.getImageName());
                case 2 -> assertEquals("img/piece2.png", piece.getImageName());
                case 3 -> assertEquals("img/piece3.png", piece.getImageName());
            }
        }
    }

    @Test
    void testToString() {
        System.out.println("testToString");

        //devo fare dei test settando delle posizioni
        //a caso e controllare che vengano printate correttamente facendo il to string

    }
}
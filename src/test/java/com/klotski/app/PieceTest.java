package com.klotski.app;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.klotski.app.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

class PieceTest {
    private Piece piece;

    //Test del costruttore di default
    @Test
    public void testDefaultConstructor(){
        System.out.println("test defaultConstructor");

        piece = new Piece();

        //Controlla che venga creato un pezzo del tipo corretto
        assertEquals(0, piece.getType());

        //Il test degli attributi inizializzati viene fatto su setType

        //Controlla che non ritorni null
        assertNotNull(piece);
    }

    //Test del costruttore con parametro
    @Test
    public void testParamConstructor(){
        System.out.println("test paramConstructor");

        //Testa tutti e 4 i possibili tipi
        for(int pieceType=0; pieceType<=3; pieceType++) {
            piece = new Piece(pieceType);

            //Controlla che venga creato un pezzo del tipo corretto
            assertEquals(pieceType, piece.getType());

            //Il test degli attributi inizializzati viene fatto su setType

            //Controlla che non ritorni null
            assertNotNull(piece);
        }
    }

    //Test input illegali del costruttore con parametro
    @Test
    public void testParamConstructorIllegal(){
        System.out.println("test paramConstructorIllegal");

        assertThrows(IllegalArgumentException.class, ()->{piece = new Piece(4);});
        assertThrows(IllegalArgumentException.class, ()->{piece = new Piece(-1);});
    }

    //Test del costruttore con 2 parametri
    @Test
    public void testTwoParamConstructor(){
        System.out.println("test twoParamConstructor");

        //Testa tutti e 4 i possibili tipi
        for(int pieceType=0; pieceType<=3; pieceType++) {

            switch (pieceType) {
                case 0 -> piece = new Piece(PIECE_0_HEIGHT, PIECE_0_WIDTH);
                case 1 -> piece = new Piece(PIECE_1_HEIGHT, PIECE_1_WIDTH);
                case 2 -> piece = new Piece(PIECE_2_HEIGHT, PIECE_2_WIDTH);
                case 3 -> piece = new Piece(PIECE_3_HEIGHT, PIECE_3_WIDTH);
            }

            //Controlla che venga creato un pezzo del tipo corretto
            assertEquals(pieceType, piece.getType());

            //Il test degli altri attributi inizializzati viene fatto su setType

            //Controlla che non ritorni null
            assertNotNull(piece);
        }
    }

    //Test input illegali del costruttore con 2 parametri
    @Test
    public void testTwoParamConstructorIllegal(){
        System.out.println("test twoParamConstructorIllegal");

        assertThrows(IllegalArgumentException.class, ()->{piece = new Piece(4);});
        assertThrows(IllegalArgumentException.class, ()->{piece = new Piece(-1);});
    }

    //Test del metodo setType()
    @Test
    void setType() {
        System.out.println("test setType");

        //Testa tutti e 4 i possibili tipi
        for(int pieceType=0; pieceType<=3; pieceType++) {
            piece = new Piece(pieceType);

            //In base al tipo controlla che gli attributi siano inizializzati al valore corretto
            switch (pieceType) {
                case 0 -> {
                    assertEquals(0, piece.getType()); //type
                    assertEquals(PIECE_0_IMAGE_NAME, piece.getImageName()); //imageName
                    assertEquals(PIECE_0_HEIGHT, piece.getHeight()); //height
                    assertEquals(PIECE_0_WIDTH, piece.getWidth()); //width
                    assertEquals("0", piece.getId()); //id

                }
                case 1 -> {
                    assertEquals(1, piece.getType());
                    assertEquals(PIECE_1_IMAGE_NAME, piece.getImageName());
                    assertEquals(PIECE_1_HEIGHT, piece.getHeight());
                    assertEquals(PIECE_1_WIDTH, piece.getWidth());
                    assertEquals("1", piece.getId());
                }
                case 2 -> {
                    assertEquals(2, piece.getType());
                    assertEquals(PIECE_2_IMAGE_NAME, piece.getImageName());
                    assertEquals(PIECE_2_HEIGHT, piece.getHeight());
                    assertEquals(PIECE_2_WIDTH, piece.getWidth());
                    assertEquals("2", piece.getId());
                }
                case 3 -> {
                    assertEquals(3, piece.getType());
                    assertEquals(PIECE_3_IMAGE_NAME, piece.getImageName());
                    assertEquals(PIECE_3_HEIGHT, piece.getHeight());
                    assertEquals(PIECE_3_WIDTH, piece.getWidth());
                    assertEquals("3", piece.getId());
                }
            }

            //Controlla il bordo con spessore
            assertEquals(PIECE_STROKE_COLOR, piece.getStroke());
            assertEquals(UNSELECTED_PIECE_STROKE_WIDTH, piece.getStrokeWidth());

            //Controlla la curvatura degli angoli
            assertEquals(PIECE_ARC_DIM, piece.getArcHeight());
            assertEquals(PIECE_ARC_DIM, piece.getArcWidth());
        }
    }

    //Test input illegali del metodo setType()
    @Test
    void setTypeIllegal() {
        System.out.println("test setTypeIllegal");

        piece = new Piece();
        assertThrows(IllegalArgumentException.class, ()->{piece.setType(4);});
        assertThrows(IllegalArgumentException.class, ()->{piece.setType(-1);});
    }

    //Test del metodo getType()
    @Test
    void getType() {
        System.out.println("test getType");

        //Testa tutti e 4 i possibili tipi
        for(int pieceType=0; pieceType<=3; pieceType++) {

            piece = new Piece(pieceType);

            //Controlla che venga ritornato il tipo corretto del pezzo
            assertEquals(pieceType, piece.getType());
            }
    }

    //Test del metodo getImageName()
    @Test
    void getImageName() {
        System.out.println("test getImageName");

        //Testa tutti e 4 i possibili tipi
        for(int pieceType=0; pieceType<=3; pieceType++) {

            piece = new Piece(pieceType);

            //Controlla che venga ritornato l'imageName corretto del pezzo
            switch (pieceType) {
                case 0 -> {
                    piece = new Piece(0);
                    assertEquals(PIECE_0_IMAGE_NAME, piece.getImageName());
                }
                case 1 -> {
                    piece = new Piece(1);
                    assertEquals(PIECE_1_IMAGE_NAME, piece.getImageName());
                }
                case 2 -> {
                    piece = new Piece(2);
                    assertEquals(PIECE_2_IMAGE_NAME, piece.getImageName());
                }
                case 3 -> {
                    piece = new Piece(3);
                    assertEquals(PIECE_3_IMAGE_NAME, piece.getImageName());
                }
            }

        }
    }

    //Test del metodo toString()
    @Test
    void testToString() {
        System.out.println("test toString");

        //Testa coordinate specifiche (dei pezzi), con ogni tipo di pezzo
        for (int pieceType = 0; pieceType <= 3; pieceType++) {

            //Crea dei pezzi da testare
            ArrayList<Piece> testingPieces = new ArrayList<>();

            //Crea le rispettive stringhe attese
            ArrayList<String> expectedStrings = new ArrayList<>();


            //Pezzo di utilizzo usuale
            Piece usualPiece = new Piece(pieceType);
            usualPiece.setLayoutX(500);
            usualPiece.setLayoutY(600);

            testingPieces.add(usualPiece);
            expectedStrings.add("      {\"shape\": ["+ (int) usualPiece.getHeight()/100 + ", "+ (int) usualPiece.getWidth()/100 +"], " + "\"position\": [6, 5] },\n");

            //Pezzo con coordinate alte
            Piece highPiece = new Piece(pieceType);
            highPiece.setLayoutX(10000000);
            highPiece.setLayoutY(10000000);

            testingPieces.add(highPiece);
            expectedStrings.add("      {\"shape\": ["+ (int) highPiece.getHeight()/100 + ", "+ (int)highPiece.getWidth()/100 +"], " + "\"position\": [100000, 100000] },\n");
            //Pezzo con coordinate basse
            Piece lowPiece = new Piece(pieceType);
            lowPiece.setLayoutX(-10000000);
            lowPiece.setLayoutY(-10000000);

            testingPieces.add(lowPiece);
            expectedStrings.add("      {\"shape\": ["+ (int) lowPiece.getHeight()/100 + ", "+ (int) lowPiece.getWidth()/100 +"], " + "\"position\": [-100000, -100000] },\n");

            //Pezzo di con coordinate miste
            Piece mixPiece = new Piece(pieceType);
            mixPiece.setLayoutX(+400);
            mixPiece.setLayoutY(-600);

            testingPieces.add(mixPiece);
            expectedStrings.add("      {\"shape\": ["+ (int) mixPiece.getHeight()/100 + ", "+ (int) mixPiece.getWidth()/100 +"], " + "\"position\": [-6, 4] },\n");

            //Pezzo di con coordinate miste invertite di segno
            Piece invertedMixPiece = new Piece(pieceType);
            invertedMixPiece.setLayoutX(-400);
            invertedMixPiece.setLayoutY(+600);


            testingPieces.add(invertedMixPiece);
            expectedStrings.add("      {\"shape\": ["+ (int) invertedMixPiece.getHeight()/100 + ", "+ (int) invertedMixPiece.getWidth()/100 +"], " + "\"position\": [6, -4] },\n");

            //Pezzo di con coordinate miste elevate
            Piece elevateMixPiece = new Piece(pieceType);
            elevateMixPiece.setLayoutX(+10000000);
            elevateMixPiece.setLayoutY(-10000000);

            testingPieces.add(elevateMixPiece);
            expectedStrings.add("      {\"shape\": ["+ (int) elevateMixPiece.getHeight()/100 + ", "+ (int) elevateMixPiece.getWidth()/100 +"], " + "\"position\": [-100000, 100000] },\n");


            //Pezzo di con coordinate miste elevate invertite di segno
            Piece invertedElevateMixPiece = new Piece(pieceType);
            invertedElevateMixPiece.setLayoutX(-10000000);
            invertedElevateMixPiece.setLayoutY(+10000000);

            testingPieces.add(invertedElevateMixPiece);
            expectedStrings.add("      {\"shape\": ["+ (int) invertedElevateMixPiece.getHeight()/100 + ", "+ (int) invertedElevateMixPiece.getWidth()/100 +"], " + "\"position\": [100000, -100000] },\n");

            //Pezzo di con coordinate nulle
            Piece zeroPiece = new Piece(pieceType);
            zeroPiece.setLayoutX(0);
            zeroPiece.setLayoutY(0);

            testingPieces.add(zeroPiece);
            expectedStrings.add("      {\"shape\": ["+ (int) zeroPiece.getHeight()/100 + ", "+ (int) zeroPiece.getWidth()/100 +"], " + "\"position\": [0, 0] },\n");

            //Per ogni pezzo dell'array di testing
            for (int i=0; i<testingPieces.size(); i++) {

                Piece testingPiece = testingPieces.get(i);
                String expectedString = expectedStrings.get(i);

                //Controlla che toString ritorni la stringa corretta
                assertEquals(expectedString, testingPiece.toString());

            }
        }
    }
}
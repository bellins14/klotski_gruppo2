package com.klotski.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static com.klotski.app.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    //File di test 1
    private final String testDC1 = "src/test/testFiles/json/TestDC1.json";
    private final String testLog1 = "src/test/testFiles/json/TestLog1.json";


    //File di test 2
    private final String testDC2 = "src/test/testFiles/json/TestDC2.json";
    private final String testLog2 = "src/test/testFiles/json/TestLog2.json";

    //File di test 3
    private final String testDC3 = "src/test/testFiles/json/TestDC3.json";
    private final String testLog3 = "src/test/testFiles/json/TestLog3.json";

    //File di test 4
    private final String testDC4 = "src/test/testFiles/json/TestDC4.json";
    private final String testLog4 = "src/test/testFiles/json/TestLog4.json";

    //File di test 5
    private final String testDC5 = "src/test/testFiles/json/TestDC5.json";
    private final String testLog5 = "src/test/testFiles/json/TestLog5.json";

    //File di test 6
    private final String testDC6 = "src/test/testFiles/json/TestDC6.json";
    private final String testLog6 = "src/test/testFiles/json/TestLog6.json";

    //File di test 7
    private final String testDC7 = "src/test/testFiles/json/TestDC7.json";
    private final String testLog7 = "src/test/testFiles/json/TestLog7.json";

    //File di test 8
    private final String testDC8 = "src/test/testFiles/json/TestDC8.json";
    private final String testLog8 = "src/test/testFiles/json/TestLog8.json";

    //File di test 9
    private final String testDC9 = "src/test/testFiles/json/TestDC9.json";
    private final String testLog9 = "src/test/testFiles/json/TestLog9.json";

    //File di test 10
    private final String testDC10 = "src/test/testFiles/json/TestDC10.json";
    private final String testLog10 = "src/test/testFiles/json/TestLog10.json";


    //Eseguito una volta prima di ogni test
    @BeforeEach
    public void setUpBeforeEach() {
        System.out.println("setUpBeforeEach");

        //Crea un gioco con i due file di log e di supporto
        game = new Game(LOG_FILE, DC_FILE);

    }

    //Test del costruttore con 2 parametri
    @Test
    public void testTwoParamConstructor() {
        System.out.println("test twoParamConstructor");

        //Controlla che non ritorni null
        assertNotNull(game);
    }


    //Test del metodo getInitialSelectedConf()
    @Test
    void getInitialSelectedConf() {
        System.out.println("test getInitialSelectedConf");

        //Esegui il test con 4 file json di test

        //Crea un gioco con il primo file log di test
        game = new Game(testLog1, testDC1);

        //Controlla che il primo file log di test abbia come configurazione iniziale la numero 1
        assertEquals(game.getInitialSelectedConf(), 1);

        //Crea un gioco con il secondo file log di test
        game = new Game(testLog2, testDC2);

        //Controlla che il secondo file log di test abbia come configurazione iniziale la numero 2
        assertEquals(game.getInitialSelectedConf(), 2);

        //Crea un gioco con il terzo file log di test
        game = new Game(testLog3, testDC3);

        //Controlla che il terzo file log di test abbia come configurazione iniziale la numero 3
        assertEquals(game.getInitialSelectedConf(), 3);

        //Crea un gioco con il quarto file log di test
        game = new Game(testLog4, testDC4);

        //Controlla che il quarto file log di test abbia come configurazione iniziale la numero 4
        assertEquals(game.getInitialSelectedConf(), 4);
    }

    //Test del metodo getConfiguration()
    @Test
    void getConfiguration() {
        System.out.println("test getConfiguration");

        //Esegui il test con 4 file json di test

        //Crea un gioco con il primo file log di test
        game = new Game(testLog1, testDC1);

        //Configurazione attuale attesa di TestLog1
        String testLog1ExpectedActualConfig = "      {\"shape\": [2, 2], \"position\": [0, 1] },\n" +
                "\n" +
                "      {\"shape\": [1, 2], \"position\": [4, 2] },\n" +
                "\n" +
                "      {\"shape\": [2, 1], \"position\": [2, 3] },\n" +
                "\n" +
                "      {\"shape\": [2, 1], \"position\": [2, 0] },\n" +
                "\n" +
                "      {\"shape\": [2, 1], \"position\": [0, 3] },\n" +
                "\n" +
                "      {\"shape\": [2, 1], \"position\": [0, 0] },\n" +
                "\n" +
                "      {\"shape\": [1, 1], \"position\": [3, 2] },\n" +
                "\n" +
                "      {\"shape\": [1, 1], \"position\": [4, 1] },\n" +
                "\n" +
                "      {\"shape\": [1, 1], \"position\": [2, 2] },\n" +
                "\n" +
                "      {\"shape\": [1, 1], \"position\": [2, 1] },\n" +
                "\n";

        //Controlla che il primo file log di test abbia come configurazione attuale quella attesa
        assertEquals(game.getConfiguration().toString(), testLog1ExpectedActualConfig);

        //Crea un gioco con il secondo file log di test
        game = new Game(testLog2, testDC2);

        //Configurazione attuale attesa di TestLog2
        String testLog2ExpectedActualConfig = "      {\"shape\": [2, 2], \"position\": [0, 1] },\n" +
                "\n" +
                "      {\"shape\": [1, 2], \"position\": [3, 2] },\n" +
                "\n" +
                "      {\"shape\": [1, 2], \"position\": [4, 2] },\n" +
                "\n" +
                "      {\"shape\": [2, 1], \"position\": [1, 3] },\n" +
                "\n" +
                "      {\"shape\": [2, 1], \"position\": [2, 1] },\n" +
                "\n" +
                "      {\"shape\": [2, 1], \"position\": [1, 0] },\n" +
                "\n" +
                "      {\"shape\": [1, 1], \"position\": [2, 2] },\n" +
                "\n" +
                "      {\"shape\": [1, 1], \"position\": [3, 0] },\n" +
                "\n" +
                "      {\"shape\": [1, 1], \"position\": [0, 3] },\n" +
                "\n" +
                "      {\"shape\": [1, 1], \"position\": [0, 0] },\n" +
                "\n";

        //Controlla che il secondo file log di test abbia come configurazione attuale quella attesa
        assertEquals(game.getConfiguration().toString(), testLog2ExpectedActualConfig);

        //Crea un gioco con il terzo file log di test
        game = new Game(testLog3, testDC3);

        //Configurazione attuale attesa di TestLog3
        String testLog3ExpectedActualConfig = "      {\"shape\": [2, 2], \"position\": [1, 2] },\n" +
                "\n" +
                "      {\"shape\": [1, 2], \"position\": [4, 1] },\n" +
                "\n" +
                "      {\"shape\": [1, 2], \"position\": [3, 2] },\n" +
                "\n" +
                "      {\"shape\": [2, 1], \"position\": [3, 0] },\n" +
                "\n" +
                "      {\"shape\": [2, 1], \"position\": [2, 1] },\n" +
                "\n" +
                "      {\"shape\": [2, 1], \"position\": [1, 0] },\n" +
                "\n" +
                "      {\"shape\": [1, 1], \"position\": [4, 3] },\n" +
                "\n" +
                "      {\"shape\": [1, 1], \"position\": [0, 3] },\n" +
                "\n" +
                "      {\"shape\": [1, 1], \"position\": [0, 2] },\n" +
                "\n" +
                "      {\"shape\": [1, 1], \"position\": [0, 1] },\n" +
                "\n";

        //Controlla che il secondo file log di test abbia come configurazione attuale quella attesa
        assertEquals(game.getConfiguration().toString(), testLog3ExpectedActualConfig);

        //Crea un gioco con il quarto file log di test
        game = new Game(testLog4, testDC4);

        //Configurazione attuale attesa di TestLog4
        String testLog4ExpectedActualConfig = "      {\"shape\": [2, 2], \"position\": [0, 1] },\n" +
                "\n" +
                "      {\"shape\": [1, 2], \"position\": [2, 1] },\n" +
                "\n" +
                "      {\"shape\": [2, 1], \"position\": [3, 3] },\n" +
                "\n" +
                "      {\"shape\": [2, 1], \"position\": [3, 1] },\n" +
                "\n" +
                "      {\"shape\": [2, 1], \"position\": [0, 3] },\n" +
                "\n" +
                "      {\"shape\": [2, 1], \"position\": [0, 0] },\n" +
                "\n" +
                "      {\"shape\": [1, 1], \"position\": [4, 2] },\n" +
                "\n" +
                "      {\"shape\": [1, 1], \"position\": [3, 2] },\n" +
                "\n" +
                "      {\"shape\": [1, 1], \"position\": [4, 0] },\n" +
                "\n" +
                "      {\"shape\": [1, 1], \"position\": [3, 0] },\n" +
                "\n";

        //Controlla che il quarto file log di test abbia come configurazione attuale quella attesa
        assertEquals(game.getConfiguration().toString(), testLog4ExpectedActualConfig);
    }

    //Test del metodo setConfiguration()
    @Test
    void setConfiguration() {
        System.out.println("test setConfiguration");

        //Esegui il test con 2 file json di test

        //Crea un gioco con il quinto file log di test
        game = new Game(testLog5, testDC5);

        //Setta la configurazione corrente con una configurazione iniziale 1
        game.setConfiguration(new Configuration(1));

        //Controlla che la configurazione attuale (stampata sul file di log)
        // siano quella attesa (configurazione 1)
        assertEquals(game.getConfiguration().toString(), UtilityJackson.deserializeConfiguration(testDC5).toString());

        //Crea un gioco con il sesto file log di test
        game = new Game(testLog6, testDC6);

        //Setta la configurazione corrente con una configurazione iniziale 4
        game.setConfiguration(new Configuration(4));

        //Controlla che la configurazione attuale (stampata sul file di log)
        // sia quella attesa (configurazione 4)
        assertEquals(game.getConfiguration().toString(), UtilityJackson.deserializeConfiguration(testDC6).toString());



    }

    //Test del metodo getMoveCounter()
    @Test
    void getMoveCounter() {
        System.out.println("test getMoveCounter");

        //Esegui il test con 4 file json di test

        //Crea un gioco con il primo file log di test
        game = new Game(testLog1, testDC1);

        //Controlla che il primo file log di test abbia come moveCounter quello atteso
        assertEquals(2, game.getMoveCounter());

        //Crea un gioco con il secondo file log di test
        game = new Game(testLog2, testDC2);

        //Controlla che il secondo file log di test abbia come moveCounter quello atteso
        assertEquals(5, game.getMoveCounter());

        //Crea un gioco con il terzo file log di test
        game = new Game(testLog3, testDC3);

        //Controlla che il primo file log di test abbia come moveCounter quello atteso
        assertEquals(6, game.getMoveCounter());

        //Crea un gioco con il quarto file log di test
        game = new Game(testLog4, testDC4);

        //Controlla che il quarto file log di test abbia come moveCounter quello atteso
        assertEquals(9, game.getMoveCounter());
    }

    //Test del metodo movePieceDown()
    @Test
    void movePieceDown() {
        System.out.println("test movePieceDown");

        //Esegui il test con 2 file json di test

        //Crea un gioco con il settimo file log di test
        game = new Game(testLog7, testDC7);

        //Muovi il primo pezzo della configurazione corrente in basso di 100
        Configuration currentConfig1 = game.getConfiguration();
        Piece pieceOfcurrentConfig1 = currentConfig1.getPieces()[0];
        double currentPiece1Y = pieceOfcurrentConfig1.getLayoutY();
        game.movePieceDown(pieceOfcurrentConfig1, 100);

        //Controlla che si sia spostato di 100
        assertEquals(100, pieceOfcurrentConfig1.getLayoutY()-currentPiece1Y);

        //Crea un gioco con l'ottavo file log di test
        game = new Game(testLog8, testDC8);

        //Muovi il primo pezzo della configurazione corrente in basso di 200
        Configuration currentConfig2 = game.getConfiguration();
        Piece pieceOfcurrentConfig2 = currentConfig2.getPieces()[0];
        double currentPiece2Y = pieceOfcurrentConfig2.getLayoutY();
        game.movePieceDown(pieceOfcurrentConfig2, 200);

        //Controlla che si sia spostato di 200
        assertEquals(200, pieceOfcurrentConfig2.getLayoutY()-currentPiece2Y);
    }

    //Test del metodo movePieceDown() con input illegali
    @Test
    void movePieceDownIllegal() {
        System.out.println("test movePieceDownIllegal");

        //Esegui il test con 2 file json di test

        //Crea un gioco con il settimo file log di test
        game = new Game(testLog7, testDC7);

        //Pezzo che non appartiene al gioco
        Piece notBleongingPiece1 = new Piece();

        //Controlla che venga lanciata eccezione
        assertThrows(IllegalArgumentException.class, ()->{game.movePieceDown(notBleongingPiece1, 100);});

        //Crea un gioco con l'ottavo file log di test
        game = new Game(testLog8, testDC8);

        //Pezzo che non appartiene al gioco
        Piece notBleongingPiece2 = new Piece();

        //Controlla che venga lanciata eccezione
        assertThrows(IllegalArgumentException.class, ()->{game.movePieceDown(notBleongingPiece2, 100);});
    }

    //Test del metodo movePieceUp()
    @Test
    void movePieceUp() {
        System.out.println("test movePieceUp");

        //Esegui il test con 2 file json di test

        //Crea un gioco con il settimo file log di test
        game = new Game(testLog7, testDC7);

        //Muovi il primo pezzo della configurazione corrente in alto di 100
        Configuration currentConfig1 = game.getConfiguration();
        Piece pieceOfcurrentConfig1 = currentConfig1.getPieces()[0];
        double currentPiece1Y = pieceOfcurrentConfig1.getLayoutY();
        game.movePieceUp(pieceOfcurrentConfig1, 100);

        //Controlla che si sia spostato di 100
        assertEquals(100, currentPiece1Y-pieceOfcurrentConfig1.getLayoutY());

        //Crea un gioco con l'ottavo file log di test
        game = new Game(testLog8, testDC8);

        //Muovi il primo pezzo della configurazione corrente in basso di 200
        Configuration currentConfig2 = game.getConfiguration();
        Piece pieceOfcurrentConfig2 = currentConfig2.getPieces()[0];
        double currentPiece2Y = pieceOfcurrentConfig2.getLayoutY();
        game.movePieceUp(pieceOfcurrentConfig2, 200);

        //Controlla che si sia spostato di 200
        assertEquals(200, currentPiece2Y-pieceOfcurrentConfig2.getLayoutY());

    }

    //Test del metodo movePieceDown() con input illegali
    @Test
    void movePieceUpIllegal() {
        System.out.println("test movePieceUpIllegal");

        //Esegui il test con 2 file json di test

        //Crea un gioco con il settimo file log di test
        game = new Game(testLog7, testDC7);

        //Pezzo che non appartiene al gioco
        Piece notBleongingPiece1 = new Piece();

        //Controlla che venga lanciata eccezione
        assertThrows(IllegalArgumentException.class, ()->{game.movePieceUp(notBleongingPiece1, 100);});

        //Crea un gioco con l'ottavo file log di test
        game = new Game(testLog8, testDC8);

        //Pezzo che non appartiene al gioco
        Piece notBleongingPiece2 = new Piece();

        //Controlla che venga lanciata eccezione
        assertThrows(IllegalArgumentException.class, ()->{game.movePieceUp(notBleongingPiece2, 100);});
    }

    //Test del metodo movePieceLeft()
    @Test
    void movePieceLeft() {
        System.out.println("test movePieceLeft");

        //Esegui il test con 2 file json di test

        //Crea un gioco con il settimo file log di test
        game = new Game(testLog7, testDC7);

        //Muovi il primo pezzo della configurazione corrente a dx di 100
        Configuration currentConfig1 = game.getConfiguration();
        Piece pieceOfcurrentConfig1 = currentConfig1.getPieces()[0];
        double currentPiece1X = pieceOfcurrentConfig1.getLayoutX();
        game.movePieceRight(pieceOfcurrentConfig1, 100);

        //Controlla che si sia spostato di 100
        assertEquals(100, pieceOfcurrentConfig1.getLayoutX()-currentPiece1X);

        //Crea un gioco con l'ottavo file log di test
        game = new Game(testLog8, testDC8);

        //Muovi il primo pezzo della configurazione corrente in basso di 200
        Configuration currentConfig2 = game.getConfiguration();
        Piece pieceOfcurrentConfig2 = currentConfig2.getPieces()[0];
        double currentPiece2X = pieceOfcurrentConfig2.getLayoutX();
        game.movePieceLeft(pieceOfcurrentConfig2, 200);

        //Controlla che si sia spostato di 200
        assertEquals(200, currentPiece2X-pieceOfcurrentConfig2.getLayoutX());

    }

    //Test del metodo movePieceLeft() con input illegali
    @Test
    void movePieceLeftIllegal() {
        System.out.println("test movePieceLeftIllegal");

        //Esegui il test con 2 file json di test

        //Crea un gioco con il settimo file log di test
        game = new Game(testLog7, testDC7);

        //Pezzo che non appartiene al gioco
        Piece notBleongingPiece1 = new Piece();

        //Controlla che venga lanciata eccezione
        assertThrows(IllegalArgumentException.class, ()->{game.movePieceLeft(notBleongingPiece1, 100);});

        //Crea un gioco con l'ottavo file log di test
        game = new Game(testLog8, testDC8);

        //Pezzo che non appartiene al gioco
        Piece notBleongingPiece2 = new Piece();

        //Controlla che venga lanciata eccezione
        assertThrows(IllegalArgumentException.class, ()->{game.movePieceLeft(notBleongingPiece2, 100);});
    }

    //Test del metodo movePieceRight()
    @Test
    void movePieceRight() {
        System.out.println("test movePieceRight");

        //Esegui il test con 2 file json di test

        //Crea un gioco con il settimo file log di test
        game = new Game(testLog7, testDC7);

        //Muovi il primo pezzo della configurazione corrente a dx di 100
        Configuration currentConfig1 = game.getConfiguration();
        Piece pieceOfcurrentConfig1 = currentConfig1.getPieces()[0];
        double currentPiece1X = pieceOfcurrentConfig1.getLayoutX();
        game.movePieceRight(pieceOfcurrentConfig1, 100);

        //Controlla che si sia spostato di 100
        assertEquals(100, pieceOfcurrentConfig1.getLayoutX()- currentPiece1X);

        //Crea un gioco con l'ottavo file log di test
        game = new Game(testLog8, testDC8);

        //Muovi il primo pezzo della configurazione corrente in basso di 200
        Configuration currentConfig2 = game.getConfiguration();
        Piece pieceOfcurrentConfig2 = currentConfig2.getPieces()[0];
        double currentPiece2X = pieceOfcurrentConfig2.getLayoutX();
        game.movePieceRight(pieceOfcurrentConfig2, 200);

        //Controlla che si sia spostato di 200
        assertEquals(200, pieceOfcurrentConfig2.getLayoutX()-currentPiece2X);
    }

    //Test del metodo movePieceRight() con input illegali
    @Test
    void movePieceRightIllegal() {
        System.out.println("test movePieceRightIllegal");

        //Esegui il test con 2 file json di test

        //Crea un gioco con il settimo file log di test
        game = new Game(testLog7, testDC7);

        //Pezzo che non appartiene al gioco
        Piece notBleongingPiece1 = new Piece();

        //Controlla che venga lanciata eccezione
        assertThrows(IllegalArgumentException.class, ()->{game.movePieceRight(notBleongingPiece1, 100);});

        //Crea un gioco con l'ottavo file log di test
        game = new Game(testLog8, testDC8);

        //Pezzo che non appartiene al gioco
        Piece notBleongingPiece2 = new Piece();

        //Controlla che venga lanciata eccezione
        assertThrows(IllegalArgumentException.class, ()->{game.movePieceRight(notBleongingPiece2, 100);});
    }


    //Test del metodo setConfigurationToInitialConf()
    @Test
    void setConfigurationToInitialConf() {
        System.out.println("test setConfigurationToInitialConf");

        //Esegui il test con 2 file json di test

        //Crea un gioco con il nono file log di test
        game = new Game(testLog9, testDC9);

        //Testa con ognuna delle 4 configurazioni iniziali
        for(int i=1; i<=4; i++){

            //Setta la configurazione attuale alla configurazione iniziale numero i
            game.reset(i);

            //Configurazione attesa
            Configuration expectedConfiguration = new Configuration(i);

            //Controlla che la config attuale sia effettivamente la corrispettiva conf iniziale
            assertEquals(expectedConfiguration.toString(),game.getConfiguration().toString());

        }

        //Crea un gioco con il decimo file log di test
        game = new Game(testLog10, testDC10);

        //Testa con ognuna delle 4 configurazioni iniziali
        for(int i=1; i<=4; i++){

            //Setta la configurazione attuale alla configurazione iniziale numero i
            game.reset(i);

            //Configurazione attesa
            Configuration expectedConfiguration = new Configuration(i);

            //Controlla che la config attuale sia effettivamente la corrispettiva conf iniziale
            assertEquals(expectedConfiguration.toString(),game.getConfiguration().toString());

        }

    }

    //Test del metodo setConfigurationToInitialConf() con input illegali
    @Test
    void setConfigurationToInitialConfIllegal() {
        System.out.println("test setConfigurationToInitialConfIllegal");

        //Esegui il test con 2 file json di test

        //Crea un gioco con il nono file log di test
        game = new Game(testLog9, testDC9);

        //Controlla che venga lanciata eccezione
        assertThrows(IllegalArgumentException.class, ()->{game.reset(0);});

        //Crea un gioco con il decimo file log di test
        game = new Game(testLog10, testDC10);

        //Controlla che venga lanciata eccezione
        assertThrows(IllegalArgumentException.class, ()->{game.reset(0);});
    }



    //Test del metodo setConfigurationToPreviousConf()
    @Test
    void setConfigurationToPreviousConf() {
        System.out.println("test setConfigurationToPreviousConf");

        //Crea un gioco con il primo file log di test
        game = new Game(testLog1, testDC1);

        //Salva la configurazione attuale
        Configuration actualConfig = game.getConfiguration();

        //Setta la configurazione attuale con quella precedente
        game.undo();

        //Configurazione precedente attesa
        String testLog1ExpectedPreviousConfig = "      {\"shape\": [2, 2], \"position\": [0, 1] },\n" +
                "\n" +
                "      {\"shape\": [1, 2], \"position\": [4, 2] },\n" +
                "\n" +
                "      {\"shape\": [2, 1], \"position\": [2, 3] },\n" +
                "\n" +
                "      {\"shape\": [2, 1], \"position\": [2, 0] },\n" +
                "\n" +
                "      {\"shape\": [2, 1], \"position\": [0, 3] },\n" +
                "\n" +
                "      {\"shape\": [2, 1], \"position\": [0, 0] },\n" +
                "\n" +
                "      {\"shape\": [1, 1], \"position\": [3, 2] },\n" +
                "\n" +
                "      {\"shape\": [1, 1], \"position\": [3, 1] },\n" +
                "\n" +
                "      {\"shape\": [1, 1], \"position\": [2, 2] },\n" +
                "\n" +
                "      {\"shape\": [1, 1], \"position\": [2, 1] },\n" +
                "\n";

        //Controlla che la nuova configurazione attuale sia quella precedente
        assertEquals(testLog1ExpectedPreviousConfig, game.getConfiguration().toString());

        //Rimetti la config attuale nel file di log, per poter ripetere il test
        game.setConfiguration(actualConfig);
    }


}





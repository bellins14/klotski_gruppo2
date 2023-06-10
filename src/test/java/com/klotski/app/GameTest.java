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


    //Test del costruttore con 2 parametri
    @Test
    public void testTwoParamConstructor() {
        System.out.println("test twoParamConstructor");

        //Crea un gioco con il primo file log di test
        game = new Game(testLog1, testDC1);

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

    //Test del metodo movePiece()
    @Test
    void movePiece() throws Exception {
        System.out.println("test movePiece");

        //Esegui il test con 2 file json di test

        //Crea un gioco con il settimo file log di test
        game = new Game(testLog7, testDC7);

        //Sistema il file di log 7 con la prima configurazione iniziale
        game.reset();

        //Prendi l'ultimo pezzo della configurazione corrente
        Configuration currentConfig1 = game.getConfiguration();
        Piece pieceOfcurrentConfig1 = currentConfig1.getPieces()[1];
        double currentPiece1X = pieceOfcurrentConfig1.getLayoutX();


        //Muovilo a sinistra con ARROW_LEFT
        game.movePiece(pieceOfcurrentConfig1, ARROW_LEFT);

        //Controlla che si sia spostato di MOVE_AMOUNT px a sx
        assertEquals(-MOVE_AMOUNT, pieceOfcurrentConfig1.getLayoutX()-currentPiece1X);

        //Sistema il file di log 7
        game.reset();

        //Prendi di nuovo quel pezzo
        pieceOfcurrentConfig1 = game.getConfiguration().getPieces()[9];
        currentPiece1X = pieceOfcurrentConfig1.getLayoutX();

        //Prova a spostarlo dove non è possibile
        game.movePiece(pieceOfcurrentConfig1, ARROW_RIGHT);

        //Controlla che non lo abbia spostato
        assertEquals(0, pieceOfcurrentConfig1.getLayoutX()-currentPiece1X);

    }

    //Test del metodo movePieceDown() con input illegali
    @Test
    void movePieceIllegal() {
        System.out.println("test movePieceDownIllegal");

        //Esegui il test con 2 file json di test

        //Crea un gioco con il settimo file log di test
        game = new Game(testLog7, testDC7);

        //Pezzo che non appartiene al gioco
        Piece notBleongingPiece1 = new Piece();

        //Controlla che venga lanciata eccezione
        assertThrows(IllegalArgumentException.class, ()->{game.movePiece(notBleongingPiece1, ARROW_LEFT);});
    }

    //Test del metodo resetToAnotherInitialConf()
    @Test
    void resetToAnotherInitialConf() throws Exception{
        System.out.println("test resetToAnotherInitialConf");

        //Esegui il test con 2 file json di test

        //Crea un gioco con il nono file log di test
        game = new Game(testLog9, testDC9);

        //Testa con ognuna delle 4 configurazioni iniziali
        for(int i=1; i<=4; i++){

            //Setta la configurazione attuale alla configurazione iniziale numero i
            game.resetToAnotherInitialConf(i);

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
            game.resetToAnotherInitialConf(i);

            //Configurazione attesa
            Configuration expectedConfiguration = new Configuration(i);

            //Controlla che la config attuale sia effettivamente la corrispettiva conf iniziale
            assertEquals(expectedConfiguration.toString(),game.getConfiguration().toString());

        }

    }

    //Test del metodo resetToAnotherInitialConfConf() con input illegali
    @Test
    void resetToAnotherInitialConfIllegal() throws Exception{
        System.out.println("test setConfigurationToInitialConfIllegal");

        //Esegui il test con 2 file json di test

        //Crea un gioco con il nono file log di test
        game = new Game(testLog9, testDC9);

        //Controlla che venga lanciata eccezione
        assertThrows(Exception.class, ()->{game.resetToAnotherInitialConf(0);});


        //Crea un gioco con il decimo file log di test
        game = new Game(testLog10, testDC10);

        //Controlla che venga lanciata eccezione
        assertThrows(Exception.class, ()->{game.resetToAnotherInitialConf(0);});

        //Controlla che venga lanciata eccezione
        game.resetToAnotherInitialConf(2);
        assertThrows(Exception.class, ()->{game.resetToAnotherInitialConf(2);});


    }
    //Test del metodo reset()
    @Test
    void reset(){
        System.out.println("test reset");

        //Esegui il test con 2 file json di test

        //Crea un gioco con l'ottavo file log di test
        game = new Game(testLog8, testDC8);

        //Effettua un reset del gioco
            game.reset();

        //Configurazione attesa
        Configuration expectedConfiguration = new Configuration(1);

        //Controlla che la config attuale sia effettivamente la corrispettiva conf iniziale
        assertEquals(expectedConfiguration.toString(),game.getConfiguration().toString());
        }


    //Test del metodo undo()
    @Test
    void undo() {
        System.out.println("test undo");

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





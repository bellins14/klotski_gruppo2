package com.klotski.app;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.klotski.app.Constants.*;
import static com.klotski.app.Constants.config4PieceY;
import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {
    private Configuration configuration;


    //Test del costruttore di default
    @Test
    public void testDefaultConstructor() {
        System.out.println("test defaultConstructor");

        configuration = new Configuration();

        //Il test dei pezzi viene fatto su setPieces

        //Controlla che non ritorni null
        assertNotNull(configuration);
    }

    //Test del costruttore con parametro configurationNumber
    @Test
    public void testConfigurationNumberParamConstructor() {
        System.out.println("test configurationNumberParamConstructor");

        //Testa tutte e 4 le possibili configurazioni iniziali
        for (int confNumber = 1; confNumber <= 4; confNumber++) {
            configuration = new Configuration(confNumber);

            //Il test degli attributi inizializzati viene fatto su setPieces

            //Controlla che non ritorni null
            assertNotNull(configuration);
        }
    }

    //Test input illegali del costruttore con parametro configurationNumber
    @Test
    public void testConfigurationNumberParamConstructorIllegal() {
        System.out.println("test configurationNumberParamConstructorIllegal");

        assertThrows(IllegalArgumentException.class, () -> {
            configuration = new Configuration(5);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            configuration = new Configuration(0);
        });

    }

    //Test del costruttore con parametro p
    //TODO: Test con configurazione diversa
    @Test
    public void testPParamConstructor() {
        System.out.println("test pParamConstructor");

        //Testa la creazione di una configurazione dall'array di pezzi di tutte
        // e 4 le possibili configurazioni iniziali
        for (int confNumber = 1; confNumber <= 4; confNumber++) {

            //Crea un array dei rispettivi pezzi da testare
            Piece[] testingPieces = new Piece[10];

            int[] piecesType = Configuration.getPiecesType(confNumber);
            Tuple[] positions = Configuration.getPositions(confNumber);

            for (int j = 0; j < testingPieces.length; j++) {
                int pieceType = piecesType[j];
                testingPieces[j] = new Piece(pieceType);
                testingPieces[j].setLayoutX(positions[j].getX());
                testingPieces[j].setLayoutY(positions[j].getY());
            }

            //Crea una configurazione dall'array di pezzi
            configuration = new Configuration(testingPieces);

            //Salva su una nuova variabile l'array di pezzi della configurazione creata
            Piece[] createdConfPieces = configuration.getPieces();

            //Per ogni pezzo della configurazione creata
            for (int i = 0; i < createdConfPieces.length; i++) {

                //Controlla che sia uguale al corrispettivo pezzo dell'array di pezzi da testare
                assertEquals(testingPieces[i], createdConfPieces[i]);

            }

            //Controlla che non ritorni null
            assertNotNull(configuration);
        }
    }

    //Test input illegali del costruttore con parametro p
    @Test
    public void testPParamConstructorIllegal() {
        System.out.println("test pParamConstructorIllegal");

        //Crea un array di 11 pezzi
        Piece[] testingPieces11 = new Piece[11];
        assertThrows(IllegalArgumentException.class, () -> {
            configuration = new Configuration(testingPieces11);
        });

        //Crea un array di 9 pezzi
        Piece[] testingPieces9 = new Piece[9];
        assertThrows(IllegalArgumentException.class, () -> {
            configuration = new Configuration(testingPieces9);
        });

    }

    //Test del metodo getPieces
    @Test
    void getPieces() {
        System.out.println("test getPieces");

        //Testa tutte e 4 le possibili configurazioni iniziali
        for (int i = 1; i <= 4; i++) {
            configuration = new Configuration(i);
            Piece[] pieces = configuration.getPieces();

            //In base alla configurazione testa i pezzi di quella configurazione
            switch (i) {
                case 1 -> {
                    //Per ogni pezzo della configurazione
                    for (int j = 0; j < pieces.length; j++) {

                        //Controlla che il tipo sia corretto
                        assertEquals(config1PieceTypes[j], pieces[j].getType());

                        //Controlla che la posizione sia corretta
                        assertEquals(config1PieceX[j], pieces[j].getLayoutX());
                        assertEquals(config1PieceY[j], pieces[j].getLayoutY());
                    }
                }
                case 2 -> {
                    //Per ogni blocco della configurazione
                    for (int j = 0; j < pieces.length; j++) {

                        //Controlla che il tipo sia corretto
                        assertEquals(config2PieceTypes[j], pieces[j].getType());

                        //Controlla che la posizione sia corretta
                        assertEquals(config2PieceX[j], pieces[j].getLayoutX());
                        assertEquals(config2PieceY[j], pieces[j].getLayoutY());
                    }
                }
                case 3 -> {
                    //Per ogni blocco della configurazione
                    for (int j = 0; j < pieces.length; j++) {

                        //Controlla che il tipo sia corretto
                        assertEquals(config3PieceTypes[j], pieces[j].getType());

                        //Controlla che la posizione sia corretta
                        assertEquals(config3PieceX[j], pieces[j].getLayoutX());
                        assertEquals(config3PieceY[j], pieces[j].getLayoutY());
                    }
                }
                case 4 -> {
                    //Per ogni blocco della configurazione
                    for (int j = 0; j < pieces.length; j++) {

                        //Controlla che il tipo sia corretto
                        assertEquals(config4PieceTypes[j], pieces[j].getType());

                        //Controlla che la posizione sia corretta
                        assertEquals(config4PieceX[j], pieces[j].getLayoutX());
                        assertEquals(config4PieceY[j], pieces[j].getLayoutY());
                    }
                }
            }
        }
    }

    //Test del metodo setPieces
    @Test
    void setPieces() {
        System.out.println("test setPieces");

        //Test con le 4 configurazioni iniziali:

        //Test configuration1.setBlocks(configuration2)

        //Crea configuration1
        configuration = new Configuration(1);

        //Setta i blocchi di configuration1 con i blocchi di configuration2
        configuration.setPieces(2);

        //Controlla che i blocchi di configuration1 siano gli stessi di configuration2
        //Per ogni blocco di configuration1
        for (int i = 0; i < configuration.getPieces().length; i++) {

            //Controlla che il tipo sia corretto
            assertEquals(config2PieceTypes[i], configuration.getPieces()[i].getType());

            //Controlla che la posizione sia corretta
            assertEquals(config2PieceX[i], configuration.getPieces()[i].getLayoutX());
            assertEquals(config2PieceY[i], configuration.getPieces()[i].getLayoutY());
        }


        //Test configuration4.setBlocks(configuration3)

        //Crea configuration4
        configuration = new Configuration(4);

        //Setta i blocchi di configuration4 con i blocchi di configuration3
        configuration.setPieces(3);

        //Controlla che i blocchi di configuration4 siano gli stessi di configuration3
        //Per ogni blocco di configuration4
        for (int i = 0; i < configuration.getPieces().length; i++) {

            //Controlla che il tipo sia corretto
            assertEquals(config3PieceTypes[i], configuration.getPieces()[i].getType());

            //Controlla che la posizione sia corretta
            assertEquals(config3PieceX[i], configuration.getPieces()[i].getLayoutX());
            assertEquals(config3PieceY[i], configuration.getPieces()[i].getLayoutY());
        }


        //Test configuration2.setBlocks(configuration4)

        //Crea configuration2
        configuration = new Configuration(2);

        //Setta i blocchi di configuration4 con i blocchi di configuration2
        configuration.setPieces(4);

        //Controlla che i blocchi di configuration2 siano gli stessi di configuration4
        //Per ogni blocco di configuration2
        for (int i = 0; i < configuration.getPieces().length; i++) {

            //Controlla che il tipo sia corretto
            assertEquals(config4PieceTypes[i], configuration.getPieces()[i].getType());

            //Controlla che la posizione sia corretta
            assertEquals(config4PieceX[i], configuration.getPieces()[i].getLayoutX());
            assertEquals(config4PieceY[i], configuration.getPieces()[i].getLayoutY());
        }


        //Test configuration3.setBlocks(configuration1)

        //Crea configuration3
        configuration = new Configuration(3);

        //Setta i blocchi di configuration3 con i blocchi di configuration1
        configuration.setPieces(1);

        //Controlla che i blocchi di configuration3 siano gli stessi di configuration1
        //Per ogni blocco di configuration3
        for (int i = 0; i < configuration.getPieces().length; i++) {

            //Controlla che il tipo sia corretto
            assertEquals(config1PieceTypes[i], configuration.getPieces()[i].getType());

            //Controlla che la posizione sia corretta
            assertEquals(config1PieceX[i], configuration.getPieces()[i].getLayoutX());
            assertEquals(config1PieceY[i], configuration.getPieces()[i].getLayoutY());
        }

        //Test configuration3.setBlocks(configuration2)

        //Crea configuration3
        configuration = new Configuration(3);

        //Setta i blocchi di configuration3 con i blocchi di configuration2
        configuration.setPieces(2);

        //Controlla che i blocchi di configuration3 siano gli stessi di configuration2
        //Per ogni blocco di configuration3
        for (int i = 0; i < configuration.getPieces().length; i++) {

            //Controlla che il tipo sia corretto
            assertEquals(config2PieceTypes[i], configuration.getPieces()[i].getType());

            //Controlla che la posizione sia corretta
            assertEquals(config2PieceX[i], configuration.getPieces()[i].getLayoutX());
            assertEquals(config2PieceY[i], configuration.getPieces()[i].getLayoutY());
        }
    }

    //Test input illegali di setPieces
    @Test
    public void testSetPiecesIllegal() {
        System.out.println("test setPiecesIllegal");

        configuration = new Configuration();
        assertThrows(IllegalArgumentException.class, () -> {
            configuration.setPieces(5);
        });

        configuration = new Configuration(4);
        assertThrows(IllegalArgumentException.class, () -> {
            configuration.setPieces(0);
        });

    }

    //Test del metodo getPiecesType
    @Test
    void getPiecesType() {
        System.out.println("test getPiecesType");

        //Testa tutte e 4 le possibili configurazioni
        for (int i = 1; i <= 4; i++) {

            //Prendi i tipi di pezzi della configurazione i
            int[] piecesType = Configuration.getPiecesType(i);

            //In base alla configurazione testa i blocchi di quella configurazione
            switch (i) {
                case 1 -> {
                    //Per ogni blocco della configurazione
                    for (int j = 0; j < piecesType.length; j++) {

                        //Controlla che il tipo sia corretto
                        assertEquals(config1PieceTypes[j], piecesType[j]);
                    }
                }
                case 2 -> {
                    //Per ogni blocco della configurazione
                    for (int j = 0; j < piecesType.length; j++) {

                        //Controlla che il tipo sia corretto
                        assertEquals(config2PieceTypes[j], piecesType[j]);
                    }
                }
                case 3 -> {
                    //Per ogni blocco della configurazione
                    for (int j = 0; j < piecesType.length; j++) {

                        //Controlla che il tipo sia corretto
                        assertEquals(config3PieceTypes[j], piecesType[j]);
                    }
                }
                case 4 -> {
                    //Per ogni blocco della configurazione
                    for (int j = 0; j < piecesType.length; j++) {

                        //Controlla che il tipo sia corretto
                        assertEquals(config4PieceTypes[j], piecesType[j]);
                    }
                }
            }
        }
    }

    //Test input illegali di getPiecesType
    @Test
    public void testGetPiecesTypeIllegal() {
        System.out.println("test getPiecesTypeIllegal");

        assertThrows(IllegalArgumentException.class, () -> {
            Configuration.getPiecesType(5);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Configuration.getPiecesType(0);
        });
    }

    //Test del metodo getPositions
    @Test
    void getPositions() {
        System.out.println("test getPositions");

        //Testa tutte e 4 le possibili configurazioni iniziali
        for (int i = 1; i <= 4; i++) {

            //Prendi le posizioni dei pezzi della configurazione i
            Tuple[] piecesPositions = Configuration.getPositions(i);

            //In base alla configurazione testa i blocchi di quella configurazione
            switch (i) {
                case 1 -> {
                    //Per ogni blocco della configurazione
                    for (int j = 0; j < piecesPositions.length; j++) {

                        //Controlla che la posizione sia corretta
                        assertEquals(config1PieceX[j], piecesPositions[j].getX());
                        assertEquals(config1PieceY[j], piecesPositions[j].getY());
                    }
                }
                case 2 -> {
                    //Per ogni blocco della configurazione
                    for (int j = 0; j < piecesPositions.length; j++) {

                        //Controlla che la posizione sia corretta
                        assertEquals(config2PieceX[j], piecesPositions[j].getX());
                        assertEquals(config2PieceY[j], piecesPositions[j].getY());
                    }
                }
                case 3 -> {
                    //Per ogni blocco della configurazione
                    for (int j = 0; j < piecesPositions.length; j++) {

                        //Controlla che la posizione sia corretta
                        assertEquals(config3PieceX[j], piecesPositions[j].getX());
                        assertEquals(config3PieceY[j], piecesPositions[j].getY());
                    }
                }
                case 4 -> {
                    //Per ogni blocco della configurazione
                    for (int j = 0; j < piecesPositions.length; j++) {

                        //Controlla che la posizione sia corretta
                        assertEquals(config4PieceX[j], piecesPositions[j].getX());
                        assertEquals(config4PieceY[j], piecesPositions[j].getY());
                    }
                }
            }
        }
    }

    //Test input illegali di getPositions
    @Test
    public void testGetPositionsIllegal() {
        System.out.println("test getPositionsIllegal");

        assertThrows(IllegalArgumentException.class, () -> {
            Configuration.getPositions(5);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Configuration.getPositions(0);
        });
    }

    //Test del metodo isInitialConfiguration
    @Test
    void isInitialConfiguration() {
        System.out.println("test isInitialConfiguration");

        //Test con le 4 configurazioni iniziali (per ognuna deve restituire il corrispettivo numero)
        for(int i=1; i<=4; i++) {
            configuration = new Configuration(i);
            assertEquals(i, Configuration.isInitialConfiguration(configuration));
        }

        //TODO: Test con configurazione diversa
        //Test con configurazione diversa da quelle iniziali (per ognuna deve restituire il corrispettivo numero)
           // configuration = new Configuration();
           // assertEquals(0, Configuration.isInitialConfiguration(configuration));
    }

    //Test del metodo toString()
    @Test
    void testToString() {
        System.out.println("test toString");

        //Test delle 4 configurazioni iniziali
        for (int confNumber = 1; confNumber <= 3; confNumber++) {

            //Crea configurazioni da testare
            ArrayList<Configuration> testingConfigurations = new ArrayList<>();

            //Crea le rispettive stringhe attese
            ArrayList<String> expectedStrings = new ArrayList<>();

            //Conf1
            Configuration conf1 = new Configuration(1);

            testingConfigurations.add(conf1);
            expectedStrings.add("      {\"shape\": [2, 2], \"position\": [0, 1] },\n" +
                    "\n" +
                    "      {\"shape\": [1, 2], \"position\": [4, 1] },\n" +
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
                    "\n");

            //Conf2
            Configuration conf2 = new Configuration(2);

            testingConfigurations.add(conf2);
            expectedStrings.add("      {\"shape\": [2, 2], \"position\": [0, 1] },\n" +
                    "\n" +
                    "      {\"shape\": [1, 2], \"position\": [4, 2] },\n" +
                    "\n" +
                    "      {\"shape\": [1, 2], \"position\": [4, 0] },\n" +
                    "\n" +
                    "      {\"shape\": [2, 1], \"position\": [1, 3] },\n" +
                    "\n" +
                    "      {\"shape\": [2, 1], \"position\": [2, 1] },\n" +
                    "\n" +
                    "      {\"shape\": [2, 1], \"position\": [1, 0] },\n" +
                    "\n" +
                    "      {\"shape\": [1, 1], \"position\": [3, 3] },\n" +
                    "\n" +
                    "      {\"shape\": [1, 1], \"position\": [3, 0] },\n" +
                    "\n" +
                    "      {\"shape\": [1, 1], \"position\": [0, 3] },\n" +
                    "\n" +
                    "      {\"shape\": [1, 1], \"position\": [0, 0] },\n" +
                    "\n");

            //Conf3
            Configuration conf3 = new Configuration(3);

            testingConfigurations.add(conf3);
            expectedStrings.add("      {\"shape\": [2, 2], \"position\": [1, 2] },\n" +
                    "\n" +
                    "      {\"shape\": [1, 2], \"position\": [4, 2] },\n" +
                    "\n" +
                    "      {\"shape\": [1, 2], \"position\": [3, 1] },\n" +
                    "\n" +
                    "      {\"shape\": [2, 1], \"position\": [2, 0] },\n" +
                    "\n" +
                    "      {\"shape\": [2, 1], \"position\": [1, 1] },\n" +
                    "\n" +
                    "      {\"shape\": [2, 1], \"position\": [0, 0] },\n" +
                    "\n" +
                    "      {\"shape\": [1, 1], \"position\": [3, 3] },\n" +
                    "\n" +
                    "      {\"shape\": [1, 1], \"position\": [0, 3] },\n" +
                    "\n" +
                    "      {\"shape\": [1, 1], \"position\": [0, 2] },\n" +
                    "\n" +
                    "      {\"shape\": [1, 1], \"position\": [0, 1] },\n" +
                    "\n");

            //Conf4
            Configuration conf4 = new Configuration(4);

            testingConfigurations.add(conf4);
            expectedStrings.add("      {\"shape\": [2, 2], \"position\": [0, 1] },\n" +
                    "\n" +
                    "      {\"shape\": [1, 2], \"position\": [2, 1] },\n" +
                    "\n" +
                    "      {\"shape\": [2, 1], \"position\": [2, 3] },\n" +
                    "\n" +
                    "      {\"shape\": [2, 1], \"position\": [2, 0] },\n" +
                    "\n" +
                    "      {\"shape\": [2, 1], \"position\": [0, 3] },\n" +
                    "\n" +
                    "      {\"shape\": [2, 1], \"position\": [0, 0] },\n" +
                    "\n" +
                    "      {\"shape\": [1, 1], \"position\": [4, 3] },\n" +
                    "\n" +
                    "      {\"shape\": [1, 1], \"position\": [3, 2] },\n" +
                    "\n" +
                    "      {\"shape\": [1, 1], \"position\": [3, 1] },\n" +
                    "\n" +
                    "      {\"shape\": [1, 1], \"position\": [4, 0] },\n" +
                    "\n");

            //TODO: inserire configurazione di testing alternativa

            //Per ogni configurazione dell'array di testing
            for (int i=0; i<testingConfigurations.size(); i++) {

                Configuration testingConfiguration = testingConfigurations.get(i);
                String expectedString = expectedStrings.get(i);

                //Controlla che toString ritorni la stringa corretta
                assertEquals(expectedString, testingConfiguration.toString());

            }
        }
    }

}


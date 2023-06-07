package com.klotski.app;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {
    private Configuration configuration;


    //Test del costruttore di default
    @Test
    public void testDefaultConstructor(){
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

        //Testa tutte e 4 le possibili configurazioni
        for (int i = 1; i <= 4; i++) {
            configuration = new Configuration(i);

            //Il test degli attributi inizializzati viene fatto su setPieces

            //Controlla che non ritorni null
            assertNotNull(configuration);
        }
    }

    //Test input illegali del costruttore con parametro configurationNumber
    @Test
    public void testConfigurationNumberParamConstructorIllegal(){
        System.out.println("test configurationNumberParamConstructorIllegal");

        assertThrows(IllegalArgumentException.class, ()->{configuration = new Configuration(5);});
        assertThrows(IllegalArgumentException.class, ()->{configuration = new Configuration(0);});

    }

    //Test del costruttore con parametro p
    @Test
    public void testPParamConstructor() {
        System.out.println("test pParamConstructor");

        //Testa tutte e 4 le attuali configurazioni iniziali
        for (int i = 1; i <= 4; i++) {

            Piece [] configPieces = new Piece[10];
            configuration = new Configuration(i);

            //Il test degli attributi inizializzati viene fatto su setPieces

            //Controlla che non ritorni null
            assertNotNull(configuration);
        }
    }

    //Test input illegali del costruttore con parametro p
    @Test
    public void testPParamConstructorIllegal(){
        System.out.println("test pParamConstructorIllegal");

        assertThrows(IllegalArgumentException.class, ()->{configuration = new Configuration(5);});
        assertThrows(IllegalArgumentException.class, ()->{configuration = new Configuration(0);});

    }

    //Test del metodo getConfiguration
    @Test
    void getConfiguration(){
        System.out.println("test getConfiguration");

        //Testa tutte e 4 le possibili configurazioni
        for (int i = 1; i <= 4; i++) {
            configuration = new Configuration(i);
            assertEquals(i, configuration.getConfiguration());
        }
    }


    //Test del metodo getBlocksType
    @Test
    void getBlocksType() {
        System.out.println("test getBlocksType");

        configuration = new Configuration();

        //Testa tutte e 4 le possibili configurazioni
        for (int i = 1; i <= 4; i++) {

            //Prendi i tipi di blocchi della configurazione i
            int[] blocksType = configuration.getPiecesType(i);

            //In base alla configurazione testa i blocchi di quella configurazione
            switch (i) {
                case 1 -> {
                    //Per ogni blocco della configurazione
                    for (int j = 0; j < blocksType.length; j++) {

                        //Controlla che il tipo sia corretto
                        assertEquals(config1BlockTypes[j], blocksType[j]);
                    }
                }
                case 2 -> {
                    //Per ogni blocco della configurazione
                    for (int j = 0; j < blocksType.length; j++) {

                        //Controlla che il tipo sia corretto
                        assertEquals(config2BlockTypes[j], blocksType[j]);
                    }
                }
                case 3 -> {
                    //Per ogni blocco della configurazione
                    for (int j = 0; j < blocksType.length; j++) {

                        //Controlla che il tipo sia corretto
                        assertEquals(config3BlockTypes[j], blocksType[j]);
                    }
                }
                case 4 -> {
                    //Per ogni blocco della configurazione
                    for (int j = 0; j < blocksType.length; j++) {

                        //Controlla che il tipo sia corretto
                        assertEquals(config4BlockTypes[j], blocksType[j]);
                    }
                }
            }
        }
    }

    //Test input illegali di getBlocksType
    @Test
    @Disabled //NumberFormatException da aggiungere in Configuration
    public void testGetBlocksTypeIllegal(){
        System.out.println("test getBlocksTypeIllegal");

        configuration = new Configuration();
        assertThrows(NumberFormatException.class, ()->{configuration.getPiecesType(5);});

        configuration = new Configuration(3);
        assertThrows(NumberFormatException.class, ()->{configuration.getPiecesType(0);});
    }

    //Test del metodo getPositions
    @Test
    void getPositions() {
        System.out.println("test getPositions");

        configuration = new Configuration();

        //Testa tutte e 4 le possibili configurazioni
        for (int i = 1; i <= 4; i++) {

            //Prendi le posizioni dei blocchi della configurazione i
            Tuple[] blocksPositions = configuration.getPositions(i);

            //In base alla configurazione testa i blocchi di quella configurazione
            switch (i) {
                case 1 -> {
                    //Per ogni blocco della configurazione
                    for (int j = 0; j < blocksPositions.length; j++) {

                        //Controlla che la posizione sia corretta
                        assertEquals(config1BlockX[j], blocksPositions[j].getX());
                        assertEquals(config1BlockY[j], blocksPositions[j].getY());
                    }
                }
                case 2 -> {
                    //Per ogni blocco della configurazione
                    for (int j = 0; j < blocksPositions.length; j++) {

                        //Controlla che la posizione sia corretta
                        assertEquals(config2BlockX[j], blocksPositions[j].getX());
                        assertEquals(config2BlockY[j], blocksPositions[j].getY());
                    }
                }
                case 3 -> {
                    //Per ogni blocco della configurazione
                    for (int j = 0; j < blocksPositions.length; j++) {

                        //Controlla che la posizione sia corretta
                        assertEquals(config3BlockX[j], blocksPositions[j].getX());
                        assertEquals(config3BlockY[j], blocksPositions[j].getY());
                    }
                }
                case 4 -> {
                    //Per ogni blocco della configurazione
                    for (int j = 0; j < blocksPositions.length; j++) {

                        //Controlla che la posizione sia corretta
                        assertEquals(config4BlockX[j], blocksPositions[j].getX());
                        assertEquals(config4BlockY[j], blocksPositions[j].getY());
                    }
                }
            }
        }
    }

    //Test input illegali di getPotisions
    @Test
    @Disabled //NumberFormatException da aggiungere in Configuration
    public void testGetPositionsIllegal(){
        System.out.println("test getPositionsIllegal");

        configuration = new Configuration();
        assertThrows(NumberFormatException.class, ()->{configuration.getPositions(5);});

        configuration = new Configuration(2);
        assertThrows(NumberFormatException.class, ()->{configuration.getPositions(0);});
    }

    //Test del metodo getBlocks
    @Test
    void getBlocks() {
        System.out.println("test getBlocks");

        //Testa tutte e 4 le possibili configurazioni
        for (int i = 1; i <= 4; i++) {
            configuration = new Configuration(i);
            Piece[] blocks = configuration.getPieces();

            //In base alla configurazione testa i blocchi di quella configurazione
            switch (i) {
                case 1 -> {
                    //Per ogni blocco della configurazione
                    for (int j = 0; j < blocks.length; j++) {

                        //Controlla che il tipo sia corretto
                        assertEquals(config1BlockTypes[j], blocks[j].getType());

                        //Controlla che la posizione sia corretta
                        assertEquals(config1BlockX[j], blocks[j].getLayoutX());
                        assertEquals(config1BlockY[j], blocks[j].getLayoutY());
                    }
                }
                case 2 -> {
                    //Per ogni blocco della configurazione
                    for (int j = 0; j < blocks.length; j++) {

                        //Controlla che il tipo sia corretto
                        assertEquals(config2BlockTypes[j], blocks[j].getType());

                        //Controlla che la posizione sia corretta
                        assertEquals(config2BlockX[j], blocks[j].getLayoutX());
                        assertEquals(config2BlockY[j], blocks[j].getLayoutY());
                    }
                }
                case 3 -> {
                    //Per ogni blocco della configurazione
                    for (int j = 0; j < blocks.length; j++) {

                        //Controlla che il tipo sia corretto
                        assertEquals(config3BlockTypes[j], blocks[j].getType());

                        //Controlla che la posizione sia corretta
                        assertEquals(config3BlockX[j], blocks[j].getLayoutX());
                        assertEquals(config3BlockY[j], blocks[j].getLayoutY());
                    }
                }
                case 4 -> {
                    //Per ogni blocco della configurazione
                    for (int j = 0; j < blocks.length; j++) {

                        //Controlla che il tipo sia corretto
                        assertEquals(config4BlockTypes[j], blocks[j].getType());

                        //Controlla che la posizione sia corretta
                        assertEquals(config4BlockX[j], blocks[j].getLayoutX());
                        assertEquals(config4BlockY[j], blocks[j].getLayoutY());
                    }
                }
            }
        }
    }

    //Test del metodo setBlocks con configurazioni previste
    @Test
    void setBlocks() {
        System.out.println("test setBlocks");

        //Effettua 4 test con configurazioni standard:

        //Test configuration1.setBlocks(configuration2)

        //Crea configuration1
        configuration = new Configuration(1);

        //Setta i blocchi di configuration1 con i blocchi di configuration2
        configuration.setPieces(2);

        //Controlla che i blocchi di configuration1 siano gli stessi di configuration2
        //Per ogni blocco di configuration1
        for (int i = 0; i < configuration.getPieces().length; i++) {

            //Controlla che il tipo sia corretto
            assertEquals(config2BlockTypes[i], configuration.getPieces()[i].getType());

            //Controlla che la posizione sia corretta
            assertEquals(config2BlockX[i], configuration.getPieces()[i].getLayoutX());
            assertEquals(config2BlockY[i], configuration.getPieces()[i].getLayoutY());
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
            assertEquals(config3BlockTypes[i], configuration.getPieces()[i].getType());

            //Controlla che la posizione sia corretta
            assertEquals(config3BlockX[i], configuration.getPieces()[i].getLayoutX());
            assertEquals(config3BlockY[i], configuration.getPieces()[i].getLayoutY());
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
            assertEquals(config4BlockTypes[i], configuration.getPieces()[i].getType());

            //Controlla che la posizione sia corretta
            assertEquals(config4BlockX[i], configuration.getPieces()[i].getLayoutX());
            assertEquals(config4BlockY[i], configuration.getPieces()[i].getLayoutY());
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
            assertEquals(config1BlockTypes[i], configuration.getPieces()[i].getType());

            //Controlla che la posizione sia corretta
            assertEquals(config1BlockX[i], configuration.getPieces()[i].getLayoutX());
            assertEquals(config1BlockY[i], configuration.getPieces()[i].getLayoutY());
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
            assertEquals(config2BlockTypes[i], configuration.getPieces()[i].getType());

            //Controlla che la posizione sia corretta
            assertEquals(config2BlockX[i], configuration.getPieces()[i].getLayoutX());
            assertEquals(config2BlockY[i], configuration.getPieces()[i].getLayoutY());
        }
    }

    //Test input illegali di setBlocks
    @Test
    @Disabled //NumberFormatException da aggiungere in Configuration
    public void testSetBlocksIllegal(){
        System.out.println("test setBlocksIllegal");

        configuration = new Configuration();
        assertThrows(NumberFormatException.class, ()->{configuration.setPieces(5);});

        configuration = new Configuration(4);
        assertThrows(NumberFormatException.class, ()->{configuration.setPieces(0);});

    }
}




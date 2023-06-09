package com.klotski.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

import static com.klotski.app.Constants.*;


class UtilityJacksonTest {

    //File di test 12
    private final String testDC12 = "src/test/testFiles/json/TestDC12.json";
    private final String testLog12 = "src/test/testFiles/json/TestLog12.json";


    //Test del metodo serializeConfiguration
    @Test
    void serializeConfiguration() {
        System.out.println("test serializeConfiguration");

        Configuration configuration;

        //Testa tutte e 4 le configurazioni iniziali
        for (int i = 1; i <= 4; i++) {
            configuration = new Configuration(i);

            UtilityJackson.serializeConfiguration(configuration, testDC12);

            Configuration serializedConfiguration = null;

            File f;
            FileReader fr = null;

            try {
                ObjectMapper om = new ObjectMapper(); // Oggetto per mappare un oggetto in JSON
                f = new File(testDC12);
                fr = new FileReader(f); // Classe per la lettura da file
                serializedConfiguration = om.readValue(fr, Configuration.class);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fr != null) {
                    try {
                        fr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            String expectedString = configuration.toString();

            //Controlla che le due stringhe coincidano
            assertEquals(expectedString, serializedConfiguration.toString());
        }


    }

    //Test del metodo deserializeConfiguration
    @Test
    void deserializeConfiguration() {
        System.out.println("test deserializeConfiguration");

        Configuration configuration;

        //Testa tutte e 4 le configurazioni iniziali
        for (int i = 1; i <= 4; i++) {
            configuration = new Configuration(i);

            UtilityJackson.serializeConfiguration(configuration, testDC12); //cambia con un file di test

            Configuration deserializedConfiguration = UtilityJackson.deserializeConfiguration(testDC12);

            String expectedString = configuration.toString();

            //Controlla che le due stringhe coincidano
            assertEquals(expectedString, deserializedConfiguration.toString());
        }

        //TODO:test con configurazione alternativa

    }

    //Test del metodo serializeConfigurationLog
    @Test
    void serializeConfigurationLog() {
        System.out.println("test serializeConfigurationLog");

        //Crea uno stack di configurazioni da testare
        Stack<Configuration> testingLog = new Stack<>();

        //Inserisci tutte e 4 le configurazioni iniziali
        for (int i = 1; i <= 4; i++) {
            testingLog.push(new Configuration(i));
        }

        //Utilizza il metodo per serializzare le configurazioni del log e salvarle su file
        UtilityJackson.serializeConfigurationLog(testingLog, testLog12);

        //Crea il log e riempilo con gli elementi del log serializzato
        Stack<Configuration> serializedConfigurationLog = new Stack<>();
        File f;
        FileReader fr = null;

        try {
            ObjectMapper om = new ObjectMapper(); // Oggetto per mappare un oggetto in JSON
            f = new File(testLog12);
            fr = new FileReader(f); // Classe per la lettura da file
            JsonNode rootNode = om.readTree(fr);

            ArrayNode configurationsNode = (ArrayNode) rootNode;
            List<Configuration> configurationList = new ArrayList<>();

            for (JsonNode configurationNode : configurationsNode) {
                Configuration configuration = om.treeToValue(configurationNode, Configuration.class);
                configurationList.add(configuration);
            }

            for (Configuration c : configurationList) {
                serializedConfigurationLog.push(c);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        //Controlla che i due log corrispondano
        // Per ogni configurazione dello stack di log
        while (!testingLog.isEmpty()) {
            String expectedString = testingLog.pop().toString();

            //Controlla che la configurazione del log testato e la rispettiva configurazione di quello ottenuto corrispondano
            assertEquals(expectedString, serializedConfigurationLog.pop().toString());
        }
        
    }



    //Test del metodo deserializeConfigurationLog
    @Test
    void deserializeConfigurationLog() {
        System.out.println("test deserializeConfigurationLog");

        //Crea uno stack di configurazioni da inserire sul file
        Stack<Configuration> testingLog = new Stack<>();

        //Inserisci tutte e 4 le configurazioni iniziali
        for (int i = 1; i <= 4; i++) {
            testingLog.push(new Configuration(i));
        }
        
        //Salva lo stack su file
        UtilityJackson.serializeConfigurationLog(testingLog, testLog12);
        
        //Usa il metodo per deserializzare le configurazioni dal file e passali ad un nuovo log
        Stack<Configuration> deserializedConfigurationLog = UtilityJackson.deserializeConfigurationLog(testLog12);

        //Controlla che i due log corrispondano
        // Per ogni configurazione dello stack di log
        while (!testingLog.isEmpty()) {
            String expectedString = testingLog.pop().toString();

            //Controlla che la configurazione del log testato e la rispettiva configurazione di quello ottenuto corrispondano
            assertEquals(expectedString, deserializedConfigurationLog.pop().toString());
        }
        
    }
}
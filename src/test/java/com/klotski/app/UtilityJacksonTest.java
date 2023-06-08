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

    //Test del metodo serializeConfiguration
    @Test
    void serializeConfiguration() {
        System.out.println("test serializeConfiguration");

        Configuration configuration;

        //Testa tutte e 4 le configurazioni iniziali
        for (int i = 1; i <= 4; i++) {
            configuration = new Configuration(i);

            UtilityJackson.serializeConfiguration(configuration, DC_FILE); //TODO: cambia con un file di test

            Configuration serializedConfiguration = null;

            File f;
            FileReader fr = null;

            try {
                ObjectMapper om = new ObjectMapper(); // Oggetto per mappare un oggetto in JSON
                f = new File(DC_FILE);
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

        //TODO:test con configurazione alternativa

    }

    //Test del metodo deserializeConfiguration
    @Test
    void deserializeConfiguration() {
        System.out.println("test deserializeConfiguration");

        Configuration configuration;

        //Testa tutte e 4 le configurazioni iniziali
        for (int i = 1; i <= 4; i++) {
            configuration = new Configuration(i);

            UtilityJackson.serializeConfiguration(configuration, DC_FILE); //cambia con un file di test

            Configuration deserializedConfiguration = UtilityJackson.deserializeConfiguration(DC_FILE);

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
        UtilityJackson.serializeConfigurationLog(testingLog, LOG_FILE); //TODO: cambia con file di test

        //Crea il log e riempilo con gli elementi del log serializzato
        Stack<Configuration> serializedConfigurationLog = new Stack<>();
        File f;
        FileReader fr = null;

        try {
            ObjectMapper om = new ObjectMapper(); // Oggetto per mappare un oggetto in JSON
            f = new File("src/main/resources/com/klotski/app/json/ConfigurationLog.json");
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
        
        //TODO: testare con configurazione diversa da quelle iniziali
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
        UtilityJackson.serializeConfigurationLog(testingLog, LOG_FILE); //TODO: cambia con file di test
        
        //Usa il metodo per deserializzare le configurazioni dal file e passali ad un nuovo log
        Stack<Configuration> deserializedConfigurationLog = UtilityJackson.deserializeConfigurationLog(LOG_FILE);

        //Controlla che i due log corrispondano
        // Per ogni configurazione dello stack di log
        while (!testingLog.isEmpty()) {
            String expectedString = testingLog.pop().toString();

            //Controlla che la configurazione del log testato e la rispettiva configurazione di quello ottenuto corrispondano
            assertEquals(expectedString, deserializedConfigurationLog.pop().toString());
        }

        //TODO: testare con configurazione diversa da quelle iniziali
        
        
    }
}
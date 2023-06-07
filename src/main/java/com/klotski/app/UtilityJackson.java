package com.klotski.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Classe che fornisce dei metodi di utility per la scrittura fu file JSON.
 */
public class UtilityJackson {

    /**
     * Metodo che serializza una configurazione in un file JSON.
     * Utile per la deep copy.
     * @param conf configurazione da serializzare.
     */
    public static void serializeConfiguration(Configuration conf){
        File f;
        FileWriter fw = null;
        try {
            ObjectMapper om = new ObjectMapper(); // Oggetto per mappare un oggetto in JSON
            // File usato come appoggio per la deep copy
            f = new File("src/main/resources/com/klotski/app/json/ConfigurationDC.json");
            fw = new FileWriter(f); // Classe per la scrittura su file
            om.writeValue(fw, conf);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } // Fine if
        } // Fine try-catch-finally
    }


    /**
     * Metodo che deserializza di un file JSON in una configurazione.
     * Utile per la deep copy.
     * @return
     */
    public static Configuration deserializeConfiguration(){
        Configuration c = null;
        File f;
        FileReader fr = null;

        try {
            ObjectMapper om = new ObjectMapper(); // Oggetto per mappare un oggetto in JSON
            f = new File("src/main/resources/com/klotski/app/json/ConfigurationDC.json");
            fr = new FileReader(f); // Classe per la lettura da file
            c = om.readValue(fr, Configuration.class);

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
        return c;
    }


    /**
     * Metodo che serve per scrivere lo stack con il log delle configurazioni
     * su file JSON.
     * @param l stack di configuration.
     */
    public static void  serializeConfigurationLog (Stack<Configuration> l){
        File f;
        FileWriter fw = null;

        try {
            ObjectMapper om = new ObjectMapper(); // Oggetto per mappare un oggetto in JSON
            f = new File("src/main/resources/com/klotski/app/json/ConfigurationLog.json");
            fw = new FileWriter(f); // Classe per la scrittura su file
            om.writeValue(fw, l);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Metodo di utility per ritornare uno stack già deserializzato di configurazioni.
     * @return log stack deserializzato.
     */
    public static Stack<Configuration> deserializeConfigurationLog (){
        Stack<Configuration> log = new Stack<>();
        File f;
        FileReader fr = null;

        try {
            ObjectMapper om = new ObjectMapper(); // Oggetto per mappare un oggetto in JSON
            f = new File("src/main/resources/com/klotski/app/json/ConfigurationLog.json");
            fr= new FileReader(f); // Classe per la scrittura su file
            JsonNode rootNode = om.readTree(fr);

            ArrayNode configurationsNode = (ArrayNode) rootNode;
            List<Configuration> configurationList = new ArrayList<>();

            for (JsonNode configurationNode : configurationsNode) {
                Configuration configuration = om.treeToValue(configurationNode, Configuration.class);
                configurationList.add(configuration);
            }

            for(Configuration c : configurationList){
                log.push(c);
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

        return log;
    }

}

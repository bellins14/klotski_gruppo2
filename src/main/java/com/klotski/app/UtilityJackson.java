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
 * Classe che fornisce dei metodi di utilità per la scrittura/lettura delle configurazioni su/da file JSON.
 */
public class UtilityJackson {

    private UtilityJackson() {
        // Costruttore privato per evitare l'istanziazione della classe
    }

    /**
     * Metodo che serializza un oggetto configurazione in una stringa JSON e la salva su un file JSON di supporto.
     * Necessario per effettuare una deep copy della configurazione
     * @param conf oggetto configurazione da serializzare.
     * @param supportFilePathName path del file di supporto (Necessario per effettuare una deep copy della configurazione)
     */
    public static void serializeConfiguration(Configuration conf, String supportFilePathName){
        File f;
        FileWriter fw = null;
        try {
            // Oggetto per mappare un oggetto in JSON
            ObjectMapper om = new ObjectMapper();

            // File usato come appoggio per la deep copy
            f = new File(supportFilePathName);

            // Per scrivere sul file
            fw = new FileWriter(f);

            // Scrittura sul file
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
     * Metodo che deserializza una configurazione in formato stringa JSON dal file di supporto
     * e la converte in un oggetto configurazione.
     * @param supportFilePathName path del file di supporto (Necessario per effettuare una deep copy della configurazione)
     * @return oggetto configurazione deserializzata
     */
    public static Configuration deserializeConfiguration(String supportFilePathName){
        Configuration c = null;
        File f;
        FileReader fr = null;

        try {
            // Oggetto per mappare un oggetto in JSON
            ObjectMapper om = new ObjectMapper();

            // File usato come appoggio per la deep copy
            f = new File(supportFilePathName);

            // Classe per la lettura da file
            fr = new FileReader(f);

            // Lettura da file
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
     * Metodo per trascrivere uno Stack di oggetti configurazioni in un file di log (o storico o database)
     * in formato JSON
     * @param stack Stack di oggetti Configuration.
     */
    public static void  serializeConfigurationLog (Stack<Configuration> stack, String logFilePathName){
        File f;
        FileWriter fw = null;

        try {
            // Oggetto per mappare un oggetto in JSON
            ObjectMapper om = new ObjectMapper();

            // File di log
            f = new File(logFilePathName);

            // Classe per la scrittura su file
            fw = new FileWriter(f);

            // Scrive su file
            om.writeValue(fw, stack);

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
     * Metodo per tradurre un file di log (o storico o database) di configurazioni (in formato JSON)
     * in uno Stack di oggetti Confiugration
     * @param logPathName path del file di log.
     * @return Stack di oggetti Configurazioni.
     */
    public static Stack<Configuration> deserializeConfigurationLog (String logPathName){
        Stack<Configuration> log = new Stack<>();
        File f;
        FileReader fr = null;

        try {
            // Oggetto per mappare un oggetto in JSON
            ObjectMapper om = new ObjectMapper();
            f = new File(logPathName);

            // Classe per la scrittura su file
            fr= new FileReader(f);
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

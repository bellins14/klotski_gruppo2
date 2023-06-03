package com.klotski.app;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class UtilityJackson {

    /**
     * Metodo che serve per scrivere lo stack con il log delle configurazioni
     * su file JSON.
     * @param l stack di configuration.
     */
    public static void  serializeConfigurationLog (Stack<Configuration> l){
        try {
            ObjectMapper om = new ObjectMapper(); // Oggetto per mappare un oggetto in JSON
            File f = new File("src/main/resources/com/klotski/app/json/ConfigurationLog.json");
            FileWriter fw = new FileWriter(f); // Classe per la scrittura su file
            om.writeValue(fw, l);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

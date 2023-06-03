package com.project.klotski;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.klotski.app.Configuration;
import com.klotski.app.Piece;

import java.io.IOException;

/**
 * Classe che implementa il serializzatore Jackson per tradurre un oggetto della classe Configuration
 * in un formato JSON compatibile con l'applicazione
 */
public class ConfigurationSerializer extends JsonSerializer<Configuration> {

    /**
     * Metodo che permette l'effettiva serializzazione dell'oggetto config.
     * @param config configurazione da serializzare.
     * @param jsonGenerator oggetto Jackson
     * @param serializerProvider oggetto Jackson
     */
    @Override
    public void serialize(Configuration config, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        // Estrapoliamo i blocchi da config in modo da poterli serializzare
        Piece[] blocks = config.getBlocks();

        // Inizia l'oggetto JSON Configurazione
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("blocks");
        jsonGenerator.writeStartArray();
        for(int i=0; i<blocks.length; i++){
            // Inizia l'oggetto JSON Block
            jsonGenerator.writeStartObject();
            // Campo shape dell'oggetto Block
            jsonGenerator.writeFieldName("shape");
            jsonGenerator.writeStartArray();
            jsonGenerator.writeNumber((int)blocks[i].getHeight()/100);
            jsonGenerator.writeNumber((int)blocks[i].getWidth()/100);
            jsonGenerator.writeEndArray();
            // Campo position dell'oggetto Block
            jsonGenerator.writeFieldName("position");
            jsonGenerator.writeStartArray();
            jsonGenerator.writeNumber((int)blocks[i].getLayoutX()/100);
            jsonGenerator.writeNumber((int)blocks[i].getLayoutY()/100);
            jsonGenerator.writeEndArray();
            jsonGenerator.writeEndObject();
            // Chiudi l'oggetto JSON Block
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject(); // Chiudi l'oggetto JSON
    }
}

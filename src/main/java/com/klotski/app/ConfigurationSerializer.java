package com.klotski.app;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

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
        // Estrapoliamo i pezzi da config in modo da poterli serializzare - Per riferimento
        Piece[] pieces = config.getPieces();

        // Inizia l'oggetto JSON Configurazione
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("blocks");
        jsonGenerator.writeStartArray();
        for(int i=0; i<pieces.length; i++){
            // Inizia l'oggetto JSON Block
            jsonGenerator.writeStartObject();
            // Campo shape dell'oggetto Block
            jsonGenerator.writeFieldName("shape");
            jsonGenerator.writeStartArray();
            jsonGenerator.writeNumber((int)pieces[i].getHeight()/100);
            jsonGenerator.writeNumber((int)pieces[i].getWidth()/100);
            jsonGenerator.writeEndArray();
            // Campo position dell'oggetto Block
            jsonGenerator.writeFieldName("position");
            jsonGenerator.writeStartArray();
            jsonGenerator.writeNumber((int)pieces[i].getLayoutY()/100);
            jsonGenerator.writeNumber((int)pieces[i].getLayoutX()/100);
            jsonGenerator.writeEndArray();
            jsonGenerator.writeEndObject();
            // Chiudi l'oggetto JSON Block
        }
        jsonGenerator.writeEndArray();

        // Campi comuni
        jsonGenerator.writeFieldName("boardSize");
        jsonGenerator.writeStartArray();
        jsonGenerator.writeNumber(5);
        jsonGenerator.writeNumber(4);
        jsonGenerator.writeEndArray();

        jsonGenerator.writeFieldName("escapePoint");
        jsonGenerator.writeStartArray();
        jsonGenerator.writeNumber(3);
        jsonGenerator.writeNumber(1);
        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject(); // Chiudi l'oggetto JSON
    }
    // Siccome le informazioni le stampiamo, perdiamo per forza di cose il riferimento agli oggetti.
}

package com.klotski.jacksonSupport;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.klotski.app.Configuration;
import com.klotski.app.Piece;

import java.io.IOException;

/**
 * Classe che implementa un deserializzatore da JSON a Configuration.
 */
public class ConfigurationDeserializer extends JsonDeserializer<Configuration> {

    /**
     * Metodo che permette l'effettiva deserializzazione di un JSON in un oggetto Configuration.
     * @param jsonParser oggetto Jackson.
     * @param deserializationContext oggetto Jackson.
     * @return config configurazione ottenuta dalla deserializzazione dell'oggetto JSON.
     */
    @Override
    public Configuration deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        // Supporto al Parsing
        int closed_objects = 0;
        JsonToken token;
        // Creazione Blocchi
        Piece p;
        Piece[] blocks = new Piece[10];
        int i = 0;
        // Coordinate
        int x = 0;
        int y = 0;
        boolean c_check = false;
        // Dimensioni
        int h = 0;
        int w = 0;
        boolean d_check = false;

        while (i < 10) {
            jsonParser.nextToken();
            token = jsonParser.currentToken();
            if (token == JsonToken.START_OBJECT) {
                jsonParser.nextToken();
                jsonParser.nextToken();
                jsonParser.nextToken();

                // Arrivo al primo VALUE_NUMBER_INT di shape
                h = jsonParser.getValueAsInt();
                jsonParser.nextToken();
                w = jsonParser.getValueAsInt();

                jsonParser.nextToken();
                jsonParser.nextToken();
                jsonParser.nextToken();
                jsonParser.nextToken();

                // Arrivo al primo VALUE_NUMBER_INT di position
                y = jsonParser.getValueAsInt();
                jsonParser.nextToken();
                x = jsonParser.getValueAsInt();

                // Creiamo il block
                p = new Piece(h * 100, w * 100);
                p.setLayoutX(x * 100);
                p.setLayoutY(y * 100);
                blocks[i] = p;
                i++;

                jsonParser.nextToken();
                jsonParser.nextToken();
            }
        }

        // Creazione e ritorno dell'oggetto Configuration deserializzato
        return new Configuration(blocks);
    }
}
// Ovviamente creiamo una nuova configurazione da 0, quindi non dovrebbero esserci problemi di riferimenti.

// Descrizione del funzionamento dei token
/*
    JsonToken.START_OBJECT: Indica l'inizio di un oggetto JSON (parentesi graffa {).
    JsonToken.END_OBJECT: Indica la fine di un oggetto JSON (parentesi graffa }).
    JsonToken.START_ARRAY: Indica l'inizio di un array JSON (parentesi quadra [).
    JsonToken.END_ARRAY: Indica la fine di un array JSON (parentesi quadra ]).
    JsonToken.FIELD_NAME: Indica il nome di un campo all'interno di un oggetto JSON.
    JsonToken.VALUE_STRING: Indica un valore di tipo stringa.
    JsonToken.VALUE_NUMBER_INT: Indica un valore numerico intero.
    JsonToken.VALUE_NUMBER_FLOAT: Indica un valore numerico a virgola mobile.
    JsonToken.VALUE_TRUE: Indica il valore booleano true.
    JsonToken.VALUE_FALSE: Indica il valore booleano false.
    JsonToken.VALUE_NULL: Indica un valore nullo.
 */

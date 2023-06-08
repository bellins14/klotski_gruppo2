# System Sequence Diagram

![SystemSequenceDiagram.png](img/diagrams/SystemSequenceDiagram.png)

```plantuml
!theme materia-outline

skinparam ArrowColor #00B4D8

actor Giocatore
participant Sistema

Giocatore -> Sistema: inizia_partita()
Sistema --> Giocatore: mostra(configurazione_corrente,\ncounter)

par
    Giocatore -> Sistema: muovi(blocco)
    
    critical 
      alt vittoria
        Sistema --> Giocatore: messaggio("hai vinto")

      else altrimenti
        Sistema --> Giocatore: mostra(configurazione_aggiornata,\ncounter++)
      end
    end
    
else 
    Giocatore -> Sistema: cambia_configurazione(configurazione_alternativa)
    
    critical 
      Sistema --> Giocatore: mostra(configurazione_alternativa,\ncounter_azzerato)
    end

else 
    Giocatore -> Sistema: undo()
    
    critical 
      alt counter > 0 
        Sistema --> Giocatore: mostra(configurazione_aggiornata,\ncounter--)
        
      else counter == 0
        Sistema --> Giocatore: messaggio("Impossibile tornare indietro")
      end
    end
    
else 
    Giocatore -> Sistema: reset()
    
    critical 
      Sistema --> Giocatore: mostra(configurazione_iniziale,\ncounter_azzerato)
    end

else 
    Giocatore -> Sistema: richiedi_NBM()
    
    critical 
      alt connessione ad internet
        Sistema --> Giocatore: mostra(NBM)
        
      else altrimenti
        Sistema --> Giocatore: messaggio("Next Best Move non disponibile")
    end
end
@enduml
```


# Internal Sequence Diagrams

## Internal Sequence Diagram: inizia_partita()

![InternalSequenceDiagram.png](img/diagrams/InternalSequenceDiagram1.png)

```plantuml
@startuml
!theme materia-outline

skinparam ArrowColor #00B4D8
skinparam ActorBorderColor #03045E
skinparam ActorFontColor #03045E
skinparam ActorBackgroundColor #CAF0F8
skinparam ParticipantFontColor #03045E
skinparam ParticipantBorderColor #03045E
skinparam ParticipantBackgroundColor #90E0EF
skinparam DatabaseBorderColor #03045E
skinparam DatabaseBackgroundColor #00B4D8
skinparam DatabaseFontColor #03045E

actor Giocatore 
participant Partita
database Log

Giocatore -> Partita: inizia_partita()

Partita -> Log: richiedi_storico_stati()
Log -> Log: controlla()


alt storico disponibile
    Log --> Partita: restituisci(storico_stati)
    Partita -> Partita: preleva(ultima_configurazione, counter);

else storico non disponibile o vuoto
    Log --> Partita: messaggio(nessuno_storico)
    Partita -> Partita: configurazione_iniziale(1)
    Partita -> Partita: aggiungi_storico(configurazione_iniziale)
    Partita -> Partita: counter = 0
    Partita --> Log: scrivi(storico)
end

Partita --> Giocatore: mostra(configurazione,\ncounter)

@enduml
```


## Internal Sequence Diagram - Completo(da Eliminare)

![InternalSequenceDiagram.png](img/diagrams/InternalSequenceDiagram.png)

```plantuml
@startuml
!theme materia-outline

actor Giocatore 
participant Partita #ff00ff
database Database #99FF99
participant API #ff0000


Giocatore -> Partita: inizia_partita()

Partita -> Database: richiedi_storico_stati()
Database -> Database: controlla()


alt storico disponibile
    Database --> Partita: restituisci(storico_stati)

else storico non disponibile
    Database --> Partita: eccezione(nessuno_storico)
    Partita -> Database: richiedi_configurazioni()
    Database --> Partita: restituisci(configurazioni)
    Partita -> Partita: scegli_configurazione_iniziale(configurazioni)

end

Partita -> API: richiedi_NBM(configurazione_corrente)
API --> Partita: restituisci(NBM)

Partita --> Giocatore: mostra(configurazione_corrente,\ncounter)

par 
    Giocatore -> Partita: muovi(blocco)
    
    critical 
    alt vittoria
      Partita --> Giocatore:messaggio("hai vinto")

      else altrimenti
      Partita -> Partita: aggiorna_configurazione_corrente()\ncounter++
      Partita -> Partita: aggiorna_storico_stati(nuovo_stato)
      Partita -> Database: upload(storico_stati)
    
      Partita -> API: richiedi_NBM(configurazione_corrente)
      API --> Partita: restituisci(NBM)
      Partita --> Giocatore: mostra(configurazione_corrente,\ncounter)

    end

    end

else 
    Giocatore -> Partita: cambia_configurazione(configurazione_alternativa)
    
    critical
      Partita -> Partita: aggiorna_configuazione_corrente(configurazione_alternativa)\nreset_counter()\nreset_storico_stati()
      Partita -> Partita: aggiorna_storico_stati(nuovo_stato)
      Partita -> Database: upload(storico_stati)
      
      Partita -> API: richiedi_NBM(configurazione_corrente)
      API --> Partita: restituisci(NBM)
      
      Partita-->Giocatore: mostra(configurazione_corrente,\ncounter)
    end
    
else 
    Giocatore -> Partita: undo()

    critical
      Partita -> Partita: aggiorna_configurazione_corrente(stato_precedente[configurazione_corrente])\ncounter--
      Partita -> Partita: rimuovi_ultimo_stato(storico_stati)
      Partita -> Database: upload(storico_stati)
      
      Partita -> API: richiedi_NBM(configurazione_corrente)
      API --> Partita: restituisci(NBM)
      
      Partita --> Giocatore: mostra(configurazione_corrente,\ncounter)

    end
    
else 
    Giocatore -> Partita: reset()
    critical
      Partita -> Partita: aggiorna_configurazione_corrente(configurazione_iniziale)\nreset_counter()
      Partita -> Partita: reset_storico_stati()
      Partita -> Database: upload(storico_stati)
      
      Partita -> API: richiedi_NBM(configurazione_corrente)
      API --> Partita: restituisci(NBM)    
      
      Partita --> Giocatore: mostra(configurazione_corrente,\ncounter)

    end
    
Giocatore -> Partita: esegui_NBM()
    
    critical 
      Partita -> Partita: aggiorna_configurazione_corrente()\ncounter++
      Partita -> Partita: aggiorna_storico_stati(nuovo_stato)
      Partita -> Database: upload(storico_stati)
    
      Partita -> API: richiedi_NBM(configurazione_corrente)
      API --> Partita: restituisci(NBM)
      Partita --> Giocatore: mostra(configurazione_corrente,\ncounter)


    end
end
@enduml
```

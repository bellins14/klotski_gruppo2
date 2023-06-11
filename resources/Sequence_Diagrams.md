# System Sequence Diagram

![SystemSequenceDiagram.png](img/diagrams/SystemSequenceDiagram.png)

```plantuml
@startuml
!theme materia-outline

skinparam ArrowColor #00B4D8
skinparam BackgroundColor #FFFFFF
skinparam ArrowColor #00B4D8
skinparam ActorBorderColor #03045E
skinparam ActorFontColor #03045E
skinparam ActorBackgroundColor #CAF0F8
skinparam ParticipantFontColor #03045E
skinparam ParticipantBorderColor #03045E
skinparam ParticipantBackgroundColor #90E0EF

actor Giocatore
participant Sistema

Sistema --> Giocatore: mostra(configurazione_corrente,\ncounter)

par
    Giocatore -> Sistema: muovi(pezzo)
    
    critical 
      alt vittoria
        Sistema --> Giocatore: alert "Hai vinto", \nconfigurazione_iniziale \ncounter_azzerato

      else altrimenti
        Sistema --> Giocatore: configurazione_aggiornata,\n++counter
      end
    end
    
else 
    Giocatore -> Sistema: cambia_configurazione(configurazione_alternativa)
    
    critical 
      Sistema --> Giocatore: configurazione_alternativa,\ncounter_azzerato
    end

else 
    Giocatore -> Sistema: undo()
    
    critical 
      alt counter > 0 
        Sistema --> Giocatore: configurazione_precedente,\n--counter
        
      else counter == 0
        Sistema --> Giocatore: alert "Non hai mosso nessun blocco"
      end
    end
    
else 
    Giocatore -> Sistema: reset()
    
    critical 
      Sistema --> Giocatore: configurazione_iniziale,\ncounter_azzerato
    end

else 
    Giocatore -> Sistema: richiedi_NBM()
    
    critical 
    alt NBM precedente in caricamento
      Sistema --> Giocatore: alert "NBM in caricamento"
      
      else altrimenti
      
      alt connessione ad internet funzionante
        Sistema --> Giocatore: configurazione_aggiornata_con_NBM, \n++counter
        
      else connessione ad internet non funzionante
        Sistema --> Giocatore: alert "NBM non disponibile, connetti a internet" 
    end
end
@enduml
```



# Internal Sequence Diagrams

## inizia_partita()

![InternalSequenceDiagram1.png](img/diagrams/InternalSequenceDiagram1.png)

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
skinparam BackgroundColor #FFFFFF

actor Giocatore 
participant Partita
database Log

Giocatore -> Partita: inizia_partita()

Partita -> Log: leggi_storico_configurazioni()
Log -> Log: controlla()


alt storico disponibile
    Log --> Partita: restituisci(storico_configurazioni)
    Partita -> Partita: preleva_ultima_configurazione(storico_configurazioni) \ninizializza_configurazione_corrente(ultima_configurazione) \ncalcola_counter(storico_configurazioni)

else storico non disponibile o vuoto
    Log --> Partita: eccezione(nessuno_storico)
    Partita -> Partita: ininizializza_configurazione_corrente(configurazione_iniziale) \n inizializza_counter(0) \naggiorna_storico_configurazioni(configurazione_iniziale)
    Partita -> Log: scrivi(storico_configurazioni)
end

Partita --> Giocatore: mostra(configurazione_corrente,\ncounter)

@enduml
```


## muovi(blocco)

![InternalSequenceDiagram2.png](img/diagrams/InternalSequenceDiagram2.png)

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
skinparam BackgroundColor #FFFFFF

actor Giocatore 
participant Partita
database Log

Giocatore -> Partita: muovi(blocco)
 
critical 
  alt vittoria
    Partita --> Giocatore:messaggio("hai vinto")

  else altrimenti
    Partita -> Partita: aggiorna_configurazione_corrente()\ncounter++
    Partita -> Partita: aggiorna_storico_configurazioni(configurazione_corrente)
    Partita -> Log: scrivi(storico_configurazioni)
    Partita --> Giocatore: mostra(configurazione_corrente,\ncounter)

end
    
@enduml
```


## cambia_configurazione(configurazione_alternativa)

![InternalSequenceDiagram3.png](img/diagrams/InternalSequenceDiagram3.png)

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
skinparam BackgroundColor #FFFFFF

actor Giocatore 
participant Partita
database Log

Giocatore -> Partita: cambia_configurazione(configurazione_alternativa)
    

Partita -> Partita: aggiorna_configurazione_corrente(configurazione_alternativa)\nreset_counter()
Partita -> Partita: reset_storico_configurazioni() \naggiorna_storico_configurazioni(configurazione_corrente)
Partita -> Log: scrivi(storico_configurazioni)


Partita-->Giocatore: mostra(configurazione_corrente,\ncounter)

@enduml
```


## undo()

![InternalSequenceDiagram4.png](img/diagrams/InternalSequenceDiagram4.png)

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
skinparam BackgroundColor #FFFFFF

actor Giocatore 
participant Partita
database Log

Giocatore -> Partita: undo()

alt counter > 0
  Partita -> Partita: rimuovi_ultima_configurazione(storico_configurazioni)
  Partita -> Partita: aggiorna_configurazione_corrente(ultima_configurazione(storico_configurazioni)\ncounter--
  Partita -> Log: scrivi(storico_configurazioni)
else counter == 0
  Partita --> Giocatore: messaggio("Impossibile tornare indietro")
end

Partita --> Giocatore: mostra(configurazione_corrente,\ncounter)

@enduml
```


## reset()

![InternalSequenceDiagram5.png](img/diagrams/InternalSequenceDiagram5.png)

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
skinparam BackgroundColor #FFFFFF

actor Giocatore 
participant Partita
database Log

Giocatore -> Partita: reset()

Partita -> Partita: aggiorna_configurazione_corrente(configurazione_iniziale)\nreset_counter()
Partita -> Partita: reset_storico_configurazioni() \naggiorna_storico_configurazioni(configurazione_corrente)
Partita -> Log: scrivi(storico_configurazioni)

Partita --> Giocatore: mostra(configurazione_corrente,\ncounter)

@enduml
```


## richiedi_NBM()

![InternalSequenceDiagram6.png](img/diagrams/InternalSequenceDiagram6.png)

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
skinparam BackgroundColor #FFFFFF

actor Giocatore 
participant Partita
database Log
participant NBM_Script

Giocatore -> Partita: esegui_NBM()

Partita -> NBM_Script: richiedi_NBM(configurazione_corrente)
NBM_Script -> NBM_Script: calcola_NBM()
NBM_Script --> Partita: restituisci(configurazione_NBM)
    
Partita -> Partita: aggiorna_configurazione_corrente(configurazione_NBM)\ncounter++
Partita -> Partita: aggiorna_storico_configurazioni(configurazione_corrente)
Partita -> Log: scrivi(storico_configurazioni)

Partita --> Giocatore: mostra(configurazione_corrente,\ncounter)

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
skinparam BackgroundColor #FFFFFF


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

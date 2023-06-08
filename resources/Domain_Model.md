# Grafi Domain Model

* Il giocatore inizia la partita.
* Il giocatore sceglie tra 4 configurazioni.
* Le configurazioni sono formate da 10 blocchi ciascuna.
* Il giocatore muove un blocco e si aggiorna il counter delle mosse.
* Il giocatore può tornare indietro tramite una funzione undo.
* Il giocatore può decidere di farsi consigliare la nbm da un sistema esterno.
* Il giocatore può resettare la partita.
* Il giocatore può uscire dalla partita.
* La partita viene salvata all interno del DB.
* Il giocatore deve ricominciare da dove aveva lasciato una volta riaperto il gioco.
* Il giocatore deve sapere quando ha vinto
* Il giovatore deve cominciare una altra partita dopo che ha concluso quella precedente.
* Il DB viene resettato.

## Corrente
Grafo senza la classe concettuale Klotski

![Domain Model](img/diagrams/DomainModel.png)

```plantuml
@startuml

left to right direction
object Giocatore
object Configurazione
object Blocco{
  colore
  tipo
}
object Partita{
  contatoreMosse
}
object Database
object API
object NBM
object Reset
object Undo

Giocatore "1" -- "1"Partita : Gioca
Giocatore "1" -- "1"Configurazione : Sceglie
Giocatore "1" -- "1"Blocco : Muove
Partita "1" *-down "4" Configurazione : Contiene
Configurazione "1" *-down "10" Blocco : Contiene
Database "1" -up-> "1" Partita : Contiene
API -up-> NBM : Fornisce
Partita "1" o-left "0..1" NBM : Ha
Giocatore "1" -- "0.." Reset : Richiede
Giocatore "1" -- "0.." Undo : Richiede
Giocatore "1" -- "0.." NBM : Richiede
Reset -- Partita : Modifica
Undo -- Partita : Modifica

@enduml
```

## Deprecato(?)
Grafico con la classe concettuale "Klotski"

```plantuml
# PlantUML Editor

@startuml

left to right direction
object Giocatore
object Configurazione
object Blocco{
  colore
  tipo
}
object Klotski
object Partita{
  contatoreMosse
}
object Database
object API
object NBM

Giocatore "1" --> "1" Klotski : Gioca
Klotski "1" *-down "1" Partita : Ha
Partita "1" *-down "4" Configurazione : Contiene
Configurazione "1" *-down "10" Blocco : Contiene
Database "1" --> "1" Partita : Contiene
Klotski -up-> Database : Si interfaccia con
/'Klotski --> SistemaEsterno : Si interfaccia con'/
API -up-> NBM : Fornisce
Klotski --> API : Si interfaccia con
Partita "1" o- "0..1" NBM : Ha

@enduml
```

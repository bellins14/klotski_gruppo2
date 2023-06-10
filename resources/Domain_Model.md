# Domain Model

![Domain Model](img/diagrams/DomainModel.png)

```plantuml
@startuml

left to right direction
object Giocatore
object Configurazione

object Blocco{
  dimensione
  posizione
}

object Partita
object StoricoConfigurazioni
object NBM_Script
object NBM
object Reset
object Undo
object ConfigurazioneIniziale
object ConfigurazioneCorrente
object ContatoreMosse


Giocatore "1" --> "1" ConfigurazioneIniziale : Sceglie

ConfigurazioneIniziale "1" --> "1" Configurazione : E'

ConfigurazioneCorrente "1" --> "1" Configurazione : E'

Giocatore "1" --> "1" Blocco : Muove

Giocatore "1" --> "1" ConfigurazioneCorrente : Visualizza

StoricoConfigurazioni "1" *-right- "1..n" Configurazione : \nContiene\n

Configurazione "1" *-right- "10" Blocco : \nContiene\n

StoricoConfigurazioni "1" -left-* "1" Partita : \nContiene\n

NBM_Script "1" -up-> "1" NBM : Fornisce

Giocatore "1" --> "1" Reset : Richiede

Giocatore "1" --> "0.." Undo : Richiede

Giocatore "1" --> "1" NBM : Richiede

Giocatore "1" --> "1" ContatoreMosse : Visualizza

ContatoreMosse "1" -down-* "1" Partita : Contiene

Reset "1" --> "1" Partita : Modifica

Undo "1" --> "1" Partita : Modifica

@enduml
```

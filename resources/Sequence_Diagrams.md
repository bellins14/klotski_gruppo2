# System Sequence Diagram

![SystemSequenceDiagram.png](img/diagrams/SystemSequenceDiagram.png)

```plantuml
@startuml
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
    Giocatore -> Sistema: muovi(pezzo, direzione)
    
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
      
      alt connessione ad internet funzionante
        Sistema --> Giocatore: configurazione_aggiornata_con_NBM, \n++counter
        
      else connessione ad internet non funzionante
        Sistema --> Giocatore: alert "NBM non disponibile, connetti a internet"
      end
    end
end
@enduml
```



# Internal Sequence Diagrams


## muovi(pezzo, keyCode)

![InternalSequenceDiagram1.png](img/diagrams/InternalSequenceDiagram1.png)

```plantuml
@startuml
!theme materia-outline
autonumber 

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
participant Controller
participant Game
participant Piece


Giocatore -> Controller: muovi(pezzo, keyCode)
activate Controller
Controller -> Game: movePiece(piece, keyCode)
activate Game

alt keyCode == UP
Game -> Game: movePieceUp(piece)

Game -> Piece: setLayoutY(piece.getLayoutY - MOVE_AMOUNT)
activate Piece
Piece --> Game
deactivate Piece
Game -> Game: _moveCounter++ \nupdateLogsWithCurrentConfiguration()

else keyCode == DOWN 
Game -> Game: movePieceDown(piece)

Game -> Piece: setLayoutY(piece.getLayoutY + MOVE_AMOUNT)
activate Piece
Piece --> Game
deactivate Piece
Game -> Game: _moveCounter++ \nupdateLogsWithCurrentConfiguration()

else keyCode == RIGHT 
Game -> Game: movePieceRight(piece)

Game -> Piece: setLayoutX(piece.getLayoutX + MOVE_AMOUNT)
activate Piece
Piece --> Game
deactivate Piece
Game -> Game: _moveCounter++ \nupdateLogsWithCurrentConfiguration()

else keyCode == LEFT 
Game -> Game: movePieceLeft(piece)

Game -> Piece: setLayoutX(piece.getLayoutX - MOVE_AMOUNT)
activate Piece
Piece --> Game
deactivate Piece
Game -> Game: _moveCounter++ \nupdateLogsWithCurrentConfiguration()

end

Game -> Game: checkNotWin()
Game -> Configuration: pieceToCheck = _configuration.getPieces()[0]
activate Configuration
Configuration --> Game: pieceToCheck
deactivate Configuration

alt pieceToCheck in posizione di vittoria
Game -> Game: reset();
Game --> Controller: Exception()
deactivate Game
Controller -> Controller: updateBlockPaneAndCounter();
Controller -> Utility: setAlert("Hai vinto")
activate Utility
Utility --> Giocatore: alert "Hai vinto"
deactivate Utility
Controller-->Giocatore: configurazione_iniziale \ncounter_azzerato



else altrimenti
Controller -> Controller: updateCounter();
Controller-->Giocatore: configurazione_aggiornata,\n++counter
deactivate Controller
end

@enduml
```


## cambia_configurazione(configurazione_alternativa)

![InternalSequenceDiagram2.png](img/diagrams/InternalSequenceDiagram2.png)

```plantuml
@startuml
!theme materia-outline

autonumber
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
participant Controller
participant Game


Giocatore -> Controller: configurationClicked()
activate Controller
Controller -> Game: resetToAnotherInitialConf(configurationNumber)
activate Game

alt configurationNumber == _initialSelectedConf
Game --> Controller: Exception

else configurationNumber != _initialSelectedConf
Game -> Configuration: newInitialConfiguration = new Configuration(configurationNumber)
activate Configuration
Configuration --> Game: newInitialConfiguration
deactivate Configuration

Game -> Game: _stackLog.clear() \nsetConfiguration(newInitialConfiguration)
Game -> Game: updateLogsWithCurrentConfiguration();
Game -> Game: _moveCounter = 0 \nsetInitialSelectedConf(confNumber)
Game --> Controller
deactivate Game

Controller -> Controller: updateBlockPaneAndCounter()
Controller --> Giocatore: configurazione_alternativa \ncounter_azzerato
deactivate Controller

end
@enduml
```


## undo()

![InternalSequenceDiagram3.png](img/diagrams/InternalSequenceDiagram3.png)

```plantuml
@startuml
!theme materia-outline
autonumber

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
participant Controller
participant Game

Giocatore -> Controller: undo()
activate Controller
Controller -> Game: undo()
activate Game

alt _stackLog.isEmpty()
Game --> Controller: Exception()
Controller -> Utility: setAlert("Non hai mosso nessun blocco")
activate Utility
Utility --> Giocatore: alert "Non hai mosso nessun blocco"
deactivate Utility


else altrimenti
Game -> Game: setConfiguration(_stackLog.pop()) 
Game -> Game: updateLogsWithCurrentConfiguration()
Game -> Game:_moveCounter--
Game --> Controller
deactivate Game

Controller -> Controller: updateBlockPaneAndCounter()
Controller --> Giocatore: configurazione_precedente,\n--counter
deactivate Controller

end

@enduml
```


## reset()

![InternalSequenceDiagram4.png](img/diagrams/InternalSequenceDiagram4.png)

```plantuml
@startuml
!theme materia-outline
autonumber
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
participant Controller
participant Game


Giocatore -> Controller: reset()
activate Controller
Controller -> Game: reset()
activate Game

Game -> Configuration: newInitialConfiguration = new Configuration(_initialSelectedConf)
activate Configuration
Configuration --> Game: newInitialConfiguration
deactivate Configuration
Game -> Game: _stackLog.clear() \nsetConfiguration(newInitialConfiguration)
Game -> Game: updateLogsWithCurrentConfiguration();
Game -> Game: _moveCounter = 0 
Game --> Controller
deactivate Game

Controller -> Controller: updateBlockPaneAndCounter()
Controller --> Giocatore: configurazione_iniziale \ncounter_azzerato
deactivate Controller


@enduml
```


## richiedi_NBM()

![InternalSequenceDiagram5.png](img/diagrams/InternalSequenceDiagram5.png)

```plantuml
@startuml
!theme materia-outline
autonumber
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
participant Controller
participant Game
participant Piece
participant Utility
actor NBM_Script


Giocatore -> Controller: nextBestMove()
activate Controller

Controller -> Utility : isInternetConnected()
activate Utility

alt connessione non internet funzionante
  Utility --> Controller: false
  deactivate Utility

  Controller -> Utility: setAlert("NBM non disponibile, connetti a internet")
  activate Utility

  Utility --> Giocatore : alert "NBM non disponibile, connetti a internet" 


else connessione ad internet funzionante

Utility --> Controller : true
deactivate Utility


  Controller -> Game: getConfiguration()
  activate Game

  Game --> Controller: configuration
  deactivate Game
  Controller -> Utility : updateHTMLFile(configuration)
  activate Utility

  Utility --> Controller : 
  deactivate Utility

  
  Controller -> Controller : loadHTMLFile()
  
  Controller -> NBM_Script : Richiedi NBM
  activate NBM_Script
  NBM_Script -> NBM_Script : Calcola NBM
  
  NBM_Script --> Controller : NBM
    deactivate NBM_Script

  
Controller -> Game: movePiece(piece, keyCode)
activate Game

alt keyCode == UP
Game -> Game: movePieceUp(piece)

Game -> Piece: setLayoutY(piece.getLayoutY - MOVE_AMOUNT)
activate Piece
Piece --> Game
deactivate Piece
Game -> Game: _moveCounter++ \nupdateLogsWithCurrentConfiguration()

else keyCode == DOWN 
Game -> Game: movePieceDown(piece)

Game -> Piece: setLayoutY(piece.getLayoutY + MOVE_AMOUNT)
activate Piece
Piece --> Game
deactivate Piece
Game -> Game: _moveCounter++ \nupdateLogsWithCurrentConfiguration()

else keyCode == RIGHT 
Game -> Game: movePieceRight(piece)

Game -> Piece: setLayoutX(piece.getLayoutX + MOVE_AMOUNT)
activate Piece
Piece --> Game
deactivate Piece
Game -> Game: _moveCounter++ \nupdateLogsWithCurrentConfiguration()

else keyCode == LEFT 
Game -> Game: movePieceLeft(piece)

Game -> Piece: setLayoutX(piece.getLayoutX - MOVE_AMOUNT)
activate Piece
Piece --> Game
deactivate Piece
Game -> Game: _moveCounter++ \nupdateLogsWithCurrentConfiguration()

end

Game -> Game: checkNotWin()
Game -> Configuration: pieceToCheck = _configuration.getPieces()[0]
activate Configuration
Configuration --> Game: pieceToCheck
deactivate Configuration

alt pieceToCheck in posizione di vittoria
Game -> Game: reset();
Game --> Controller: Exception()
deactivate Game
Controller -> Controller: updateBlockPaneAndCounter();
Controller -> Utility: setAlert("Hai vinto")
activate Utility
Utility --> Giocatore: alert "Hai vinto"
deactivate Utility
Controller-->Giocatore: configurazione_iniziale \ncounter_azzerato



else altrimenti
Controller -> Controller: updateCounter();
Controller-->Giocatore: configurazione_aggiornata,\n++counter
deactivate Controller
end

end




@enduml
```

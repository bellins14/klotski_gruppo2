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


## muovi(pezzo, keyCode)

![InternalSequenceDiagram2.png](img/diagrams/InternalSequenceDiagram1.png)

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
participant Controller
participant Game
participant Piece


Giocatore -> Controller: muovi(pezzo, keyCode)
Controller -> Game: movePiece(piece, keyCode)
alt keyCode == up
Game -> Game: movePieceUp(piece)

Game -> Piece: setLayoutY(piece.getLayoutY - MOVE_AMOUNT)
Game -> Game: _moveCounter++ \nupdateLogsWithCurrentConfiguration()

else keyCode == down 
Game -> Game: movePieceDown(piece)

Game -> Piece: setLayoutY(piece.getLayoutY + MOVE_AMOUNT)
Game -> Game: _moveCounter++ \nupdateLogsWithCurrentConfiguration()

else keyCode == right 
Game -> Game: movePieceRight(piece)

Game -> Piece: setLayoutX(piece.getLayoutX + MOVE_AMOUNT)
Game -> Game: _moveCounter++ \nupdateLogsWithCurrentConfiguration()

else keyCode == left 
Game -> Game: movePieceLeft(piece)

Game -> Piece: setLayoutX(piece.getLayoutX - MOVE_AMOUNT)
Game -> Game: _moveCounter++ \nupdateLogsWithCurrentConfiguration()

end

Game -> Game: checkNotWin()

alt vittoria
Game -> Game: reset();
Game -> Controller: Exception()
Controller -> Controller: updateBlockPaneAndCounter();
Controller -> Utility: setAlert("Hai vinto")
Utility --> Giocatore: alert "Hai vinto"
Controller-->Giocatore: configurazione_iniziale \ncounter_azzerato



else altrimenti
Controller-->Giocatore: configurazione_aggiornata,\n++counter
end



@enduml
```


## cambia_configurazione(configurazione_alternativa)

![InternalSequenceDiagram3.png](img/diagrams/InternalSequenceDiagram2.png)

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
participant Controller
participant Game
participant Piece


Giocatore -> Controller: configurationClicked()
Controller -> Game: resetToAnotherInitialConf(configurationNumber)

alt configurationNumber == _initialSelectedConf
Game --> Controller: Exception

else configurationNumber != _initialSelectedConf
Game->Game: _initialSelectedConf = configurationNumber
Game -> Configuration: newInitialConfiguration = new Configuration(_initialSelectedConf)
Configuration --> Game: newInitialConfiguration
Game -> Game: _stackLog.clear() \nsetConfiguration(newInitialConfiguration)
Game -> Game: updateLogsWithCurrentConfiguration();
Game -> Game: _moveCounter = 0 \nsetInitialSelectedConf(confNumber)
Controller -> Controller: updateBlockPaneAndCounter()
Controller --> Giocatore: configurazione_alternativa \ncounter_azzerato

end
@enduml
```


## undo()

![InternalSequenceDiagram4.png](img/diagrams/InternalSequenceDiagram3.png)

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
participant Controller
participant Game
participant Piece


Giocatore -> Controller: undo()
Controller -> Game: undo()

alt _stackLog.isEmpty()
Game --> Controller: Exception()
Utility --> Giocatore: alert "Non hai mosso nessun blocco"

else altrimenti
Game -> Game: setConfiguration(_stackLog.pop()) \n_moveCounter--;
Controller -> Controller: updateBlockPaneAndCounter();
Controller --> Giocatore: configurazione_precedente,\n--counter

end

@enduml
```


## reset()

![InternalSequenceDiagram5.png](img/diagrams/InternalSequenceDiagram4.png)

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
participant Controller
participant Game
participant Piece


Giocatore -> Controller: reset()
Controller -> Game: reset()

Game -> Configuration: newInitialConfiguration = new Configuration(_initialSelectedConf)
Configuration --> Game: newInitialConfiguration
Game -> Game: _stackLog.clear() \nsetConfiguration(newInitialConfiguration)
Game -> Game: updateLogsWithCurrentConfiguration();
Game -> Game: _moveCounter = 0 \nsetInitialSelectedConf(confNumber)
Controller -> Controller: updateBlockPaneAndCounter()
Controller --> Giocatore: configurazione_iniziale \ncounter_azzerato

@enduml
```


## richiedi_NBM()

![InternalSequenceDiagram6.png](img/diagrams/InternalSequenceDiagram5.png)

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
participant Controller
participant Game
participant Piece
participant Utility
actor NBM_Script


Giocatore -> Controller: nextBestMove()

Controller -> Utility : isInternetConnected()

alt connessione non internet funzionante
  
  Controller --> Giocatore : alert "NBM non disponibile, connetti a internet" 
  
else connessione ad internet funzionante

  Controller -> Utility : updateHTMLFile()
  
  Controller -> Controller : loadHTMLFile()
  
  Controller -> NBM_Script : Richiedi NBM
  
  NBM_Script -> NBM_Script : Calcola NBM
  
  NBM_Script --> Controller : NBM
  
  Controller -> Game: movePiece(piece, keyCode)
  alt keyCode == up
  Game -> Game: movePieceUp(piece)
  
  Game -> Piece: setLayoutY(piece.getLayoutY - MOVE_AMOUNT)
  Game -> Game: _moveCounter++ \nupdateLogsWithCurrentConfiguration()
  
  else keyCode == down 
  Game -> Game: movePieceDown(piece)
  
  Game -> Piece: setLayoutY(piece.getLayoutY + MOVE_AMOUNT)
  Game -> Game: _moveCounter++ \nupdateLogsWithCurrentConfiguration()
  
  else keyCode == right 
  Game -> Game: movePieceRight(piece)
  
  Game -> Piece: setLayoutX(piece.getLayoutX + MOVE_AMOUNT)
  Game -> Game: _moveCounter++ \nupdateLogsWithCurrentConfiguration()
  
  else keyCode == left 
  Game -> Game: movePieceLeft(piece)
  
  Game -> Piece: setLayoutX(piece.getLayoutX - MOVE_AMOUNT)
  Game -> Game: _moveCounter++ \nupdateLogsWithCurrentConfiguration()
  
  end
  
  Game -> Game: checkNotWin()
  
  alt vittoria
  Game -> Game: reset();
  Game --> Controller: Exception()
  Controller -> Controller: updateBlockPaneAndCounter();
  Controller -> Utility: setAlert("Hai vinto")
  Utility --> Giocatore: alert "Hai vinto"
  Controller --> Giocatore: configurazione_iniziale \ncounter_azzerato
  
  
  
  else altrimenti
  Controller-->Giocatore: configurazione_aggiornata,\n++counter
  end
end


@enduml
@enduml
```

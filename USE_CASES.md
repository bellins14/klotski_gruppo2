# Use Cases
1. Scegliere configurazione iniziale
2. Muovere i blocchi nelle posizioni consentite
3. Utilizzare funzione di reset
4. Utilizzare funzione di undo
5. Richiedere "next best move"
6. Visualizzare counter delle mosse effettuate
7. Salvare lo stato corrente
8. Ripristinare partita salvata

### Use Case 1
<table>
  <tr>
    <td><b>Nome dello Use Case</b>
    <td>Scegliere configurazione iniziale</td>
  </tr>
  <tr>
    <td><b>Attori</b></td>
    <td>Giocatore</td>
  </tr>
  <tr>
    <td><b>Descrizione</b></td>
    <td>Il giocatore può selezionare una tra 4 configurazioni iniziali da cui partire con il gioco</td>
  </tr>
  <tr>
    <td><b>Precondizioni</b></td>
    <td>Nessuna mossa deve essere stata effettuata, oppure deve essere stato appena indotto un reset della partita</td>
  </tr>
  <tr>
    <td><b>Scenario Principale</b></td>
    <td>Viene impostata dal programma la disposizione iniziale dei blocchi scelta dal giocatore</td>
  </tr>
  <tr>
    <td><b>Scenario Alternativo</b></td>
    <td>La partita può iniziare con la configurazione fornita dal programma</td>
  </tr>
  <tr>
    <td><b>Post-Condizioni</b></td>
    <td>Resettare il counter delle mosse, salvare lo stato corrente, elaborare "next best move"</td>
  </tr>
    <tr>
    <td><b>Note</b></td>
    <td>Valutare se è sempre possibile determinare il "next best move" dalla condizione iniziale</td>
  </tr>
</table>

# Grafo Use Cases
```UML
# PlantUML Editor

@startuml

left to right direction

actor Giocatore
actor ExternalSystem

Giocatore --> (Scegliere configurazione iniziale)
Giocatore --> (Muovere i blocchi nelle posizioni consentite)
Giocatore --> (Utilizzare funzione di reset)
Giocatore --> (Utilizzare funzione di undo)
Giocatore --> (Richiedere «next best move»)
Giocatore --> (Visualizzare counter delle mosse effettuate)
Giocatore --> (Salvare lo stato corrente)

@enduml
```

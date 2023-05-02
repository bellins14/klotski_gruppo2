### Use Case 4
<table>
  <tr>
    <td><b>Nome dello Use Case</b>
    <td>Utilizzare funzione di undo</td>
  </tr>
  <tr>
    <td><b>Attori</b></td>
    <td>Giocatore</td>
  </tr>
  <tr>
    <td><b>Descrizione</b></td>
    <td>Il giocatore può scegliere di annullare una o più mosse, in ordine cronologico dalla più recente alla meno recente, fino alla situazione di partenza</td>
  </tr>
  <tr>
    <td><b>Precondizioni</b></td>
    <td>Deve essere stata eseguita almeno una mossa</td>
  </tr>
  <tr>
    <td><b>Scenario Principale</b></td>
    <td>La disposizione dei blocchi ritorna allo stato precedente alla mossa eseguita che si vuole annullare</td>
  </tr>
  <tr>
    <td><b>Scenario Alternativo</b></td>
    <td>Non viene applicata alcuna modifica allo stato di gioco</td>
  </tr>
  <tr>
    <td><b>Post-Condizioni</b></td>
    <td>Decrementare il counter delle mosse, salvare lo stato corrente, elaborare "next best move"</td>
  </tr>
    <tr>
    <td><b>Note</b></td>
    <td>-</td>
  </tr>
</table>
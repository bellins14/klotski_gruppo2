# Link Utili
* [Cartella google drive](https://drive.google.com/drive/folders/1akJS9H8smdbRZ23X_Sjkz1RkiikFJabz?usp=sharing)
* [File pdf del progetto](https://stem.elearning.unipd.it/pluginfile.php/512482/mod_resource/content/1/SE_ExamProject.pdf)
* [PlantUML - Nuova Versione](https://plantuml-editor.kkeisuke.dev/)
* [PlantUML - Vecchia Versione](https://plantuml-editor.kkeisuke.com/)
* [PlanText](https://www.planttext.com/)
* [Documentazione PlantUML](https://plantuml.com/)
* [Diritti Mojang](https://www.minecraft.net/it-it/terms#commercial)
* [Link repo github per API klotski solver](https://github.com/jeantimex/Klotski)

---

# TODO list

- [X] Aggiungere condizione di vittoria
- [ ] Fare in modo che la partita riparta (resettata) dopo la vittoria
- [ ] Aggiungere messaggio assenza possibilità NBM
- [ ] Distanziare i bottoni NBM, Reset, Undo
- [ ] Mettere un caricamento per l'nbm (se possibile)
- [ ] Design Patterns (verificare quali abbiamo utilizzato)
- [X] DB
- [ ] JUNIT
- [ ] Test (con documentazione)
- [ ] Aggiungere eccezioni
- [ ] Sito
- [ ] Rivedere design class model (aggiustare quello di Damiano con quello di INTELLIJ)
- [ ] Decidere se separare i Sequence diagrams
- [ ] Cambiare nome a file `prova.html`
- [ ] "Pulire" file da codice commentato inutile
- [ ] JavaDoc
- [ ] Scrivere le Licenze e il codice altrui utilizzato
- [ ] System Test (degli use cases a mano + report)
- [ ] Manuale

# Step da fare:
* Documento di specifiche (4-5 pagine)
  * Use cases (diagrammi)
  * Descrizione tabellare di ciascun use case

* Documento di design (4-5 pagine)
  * Domain model (se è utile con una descrizione testuale)
  * System sequence diagram
  * Design class model
  * Internal sequence diagrams
 
* Codice (su github)
  * Codice, files di compilazione, etc.
  * opportunamente commentato
  * leggibile e compilabile con un IDE
  * All’interno del codice ci devono essere anche le classi di test (junit)

* Documento di system test à 4-5 pagine
  * Definizione dei system test case (di solito corrispondono 1-1 agli use cases. Vengono testati
    manualmente dall’interfaccia utente)
  * System test report: è un doc scritto a mano. Template suggerito https://www.ibm.com/docs/en/elm/7.0.3?topic=sections-testcase-template-reference – Colonna SAFe

* Unit test report
  * Gli unit test case sono già nel codice, come classi di test. Di
solito corrispondono 1-1 alle classi software significative.
  * Il report è quello generato automaticamente da junit

* Un manuale (2-3 pagine)
  * Una descrizione ad alto livello del Progetto (1 pag max)
  * le istruzioni su come installare e lanciare il software.
  * Indicazioni su ambienti di esecuzione, vincoli su version java, etc.
  * Un’indicazione delle principali funzioni riutilizzate da librerie esistenti (secluse quella banali, log4j, java.utils....)


# Domande per il Professore
* Partendo dagli use cases, e creando il domain model, è possibile e/o opportuno inserire le funzioni in qualche modo?
* Che differenza c'è tra internal e system sequence diagram? cosa si intende con l'uno e con l'altro? 
  

# Struttura 
* Stato corrente:
  * configurazione_corrente
  * configurazione_iniziale (per fare il reset)
  * counter (numero delle mosse effettuate)

* Database:
  * storico_stati
  * configurazioni_iniziali

* Configurazione: 
  * tipo blocchi + disposizione blocchi per ogni stato
 
* Configurazione iniziale = una delle 4 config di partenza
  * c'è sempre possibilità di sapere da quale si è partiti (per il `reset()`)
  * appena viene eseguita una mossa, il counter aumenta, si entra in una nuova `configurazione_corrente` != `configurazione_iniziale`
  * se si fa `reset()` si ritorna a `configurazione_iniziale` scelta
  * se si preme un pulsante di una qualsiasi delle 4 configurazioni possibili di partenza, il gioco si resetta alla config scelta e riparte da zero, ergo la `configurazione_iniziale` cambia (a meno che non si sia scelta la stessa di prima)

* DATABASE: 
  * il database contiene lo storico degli stati, da cui la partita richiede, ogni volta che lo necessita, richiede l'ultimo stato, e alla     fine di ogni mossa, il nuovo stato.
  * (eventuale: la partita tiene uno storico degli stati, che a cui vien aggiunto l'ultimo stato e poi inviato su db alla fine di ogni       
  mossa) 
# Note sul codice
L'oggetto configuration viene creato nuovo ogni volta che viene cambiata una configurazione

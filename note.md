# Annotazioni
* [Cartella google drive](https://drive.google.com/drive/folders/1akJS9H8smdbRZ23X_Sjkz1RkiikFJabz?usp=sharing)
* [File pdf del progetto](https://stem.elearning.unipd.it/pluginfile.php/512482/mod_resource/content/1/SE_ExamProject.pdf)
* [PlantUML - Nuova Versione](https://plantuml-editor.kkeisuke.dev/)
* [PlantUML - Vecchia Versione](https://plantuml-editor.kkeisuke.com/)
* [PlanText](https://www.planttext.com/)
* [Documentazione PlantUML](https://plantuml.com/)
* [Diritti Mojang](https://www.minecraft.net/it-it/terms#commercial)

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

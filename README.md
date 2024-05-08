# Database per la gestione di un ristorante cinese con formula all-you-can-eat
L'obiettivo del progetto è realizzare un sistema informativo dedicato alla gestione di un ristorante cinese con formula all-you-can-eat da parte del personale ed il gestore del locale. 

L’amministratore (ed il personale qualificato) del locale può espandere il proprio menù aggiungendo e rimuovendo piatti o bevande, creando categorie per una facile navigazione del menù da parte dei clienti. 
Inoltre, l’amministratore ed i dipendenti sono in grado di modificare l’inventario del locale aggiungendo e togliendo le materie prime che vengono usate durante il servizio al fine di facilitare l’ordine delle materie prime che scarseggiano; a questo proposito il sistema può segnalare se una quantità supera una soglia critica sia in termini di quantità troppo bassa sia se la scadenza di certi prodotti si avvicina. 
Il sistema conosce le materie prime necessarie alla realizzazione di un piatto e permette di conoscere quali piatti non potranno essere cucinati durante il servizio. 

Il personale di sala è in grado di creare nuove comande (termine per identificare un insieme di clienti seduti presso lo stesso tavolo) contenenti più ordini ognuno dei quali è composto dai piatti ed eventualmente le bevande, che non sono incluse nel menù fisso. La comanda può essere di tipo menu fisso o alla carta, in base alla scelta del cliente. 

E’ fornita anche una sezione per la gestione delle prenotazioni che vengono inserite dallo staff in seguito ad una richiesta dei clienti che non viene modellata dal sistema (una chiamata o un messaggio che il ristorante dovrà gestire in modo manuale). Le prenotazioni vengono gestite in base alla capienza massima del locale, che viene inserita in fase di inizializzazione del sistema.

Infine il sistema conserva tutti i ricavi per un dato servizio, per facilitare la chiusura dei conti a fine giornata.

NOTA: Per chi non fosse pratico con i ristoranti all-you-can-eat, ogni tavolo sceglie quali piatti ordinare ed esegue molteplici ordini finché non è soddisfatto, ma alla fine nella comanda vi figura unicamente il prezzo del menù fisso e le bevande eventualmente consumate. In questi ristoranti vi è anche l’opzione di consumare alla carta, questo sistema informativo modella anche questo caso, che è molto simile alla formula a.y.c.e. con la differenza che alla chiusura del conto vengono calcolati i prezzi per tutti i piatti. 

L’amministratore potrà:
- Aggiungere o rimuovere piatti, bevande e categorie al menù
- Aggiungere o rimuovere materie prime all’inventario
- Visualizzare la quantità di materie prime presenti e tracciare quelle in scadenza
- Modificare la capienza massima del locale 
- Visualizzare il numero di prenotazioni, divise per fasce orarie
- Visualizzare i ricavi totali o parziali per la giornata lavorativa

Il personale (incluso l’amministratore) potrà:
- Aggiornare la quantità di materie prime quando ne fa utilizzo 
- Aggiungere, rimuovere o modificare le comande con piatti e bevande
- Visualizzare quali sono i piatti che non è possibile cucinare in quel momento 
- Chiudere una comanda, calcolando il conto finale per i clienti del tavolo richiesto, calcolando opportunamente i prezzi se una comanda è di tipo alla carta o menù fisso
- Aggiungere, modificare o rimuovere una prenotazione per un certo numero di persone per una certa ora 

Dotboard_backend
---
Progetto di gruppo - Collaboratori : Dela Reyna Chellee Mae, Gaudino Francesco, Napoleone Marco.

Abbiamo utilizzato il servizio di [AWS](https://docs.aws.amazon.com/ec2/index.html?nc2=h_ql_doc_ec2) per il deploy

Casi d'uso
---
**UC1: Creazione Board - Attore Primario: Utente Registrato**
1. L'utente fa l'autenticazione
2. Il sistema mostra la homepage con le sue board
3. L'utente seleziona l'operazione di aggiunta di una board
4. L'utente inserisce nome e descrizione della board
5. Il sistema mostra la nuova board creata

**UC2: Modifica di una board - Attore Primario: Utente Registrato**
1. L'utente fa l'autenticazione
2. Il sistema mostra la homepage con le sue board
3. L'utente seleziona la board che vuole modificare
4. Il sistema mostra la board selezionata
5. L'utente seleziona l'operazione "Modifica"
6. L'utente seleziona "Modifica del nome e della descrizione"
7. L'utente inserisce nome e descrizione
8. L'utente conferma
9. Il sistema mostra la board appena modificata

**UC3: Aggiunta delle boardItem su board - Attore Primario: Utente Registrato**
1. L'utente fa l'autenticazione
2. Il sistema mostra la homepage con le sue board
3. L'utente seleziona la board che vuole modificare
4. Il sistema mostra la board selezionata
5. L'utente seleziona l'operazione "Modifica"
6. L'utente seleziona il tipo di boardIteam da aggiungere
7. L'utente inserisce le informazioni
8. L'utente conferma
8a. L'utente annulla ed il sistema non registra la modifica
9. Il sistema mostra la board appena modificata

**UC4: Spostamento delle boardItem su board - Attore Primario: Utente Registrato**
1. L'utente fa l'autenticazione
2. Il sistema mostra la homepage con le sue board
3. L'utente seleziona la board che vuole modificare
4. Il sistema mostra la board selezionata
5. L'utente seleziona l'operazione "Modifica"
6. L'utente sposta le boardItem nella posizione desiderata
*L'utente ripete l'operazione 6 fino ad esser soddisfatto*
7. L'utente conferma
7a. L'utente annulla ed il sistema non registra le modifiche
8. Il sistema mostra la board appena modificata

**UC5: Eliminazione di una board - Attore Primario: Utente Registrato**
1. L'utente fa l'autenticazione
2. Il sistema mostra la homepage con le sue board
3. L'utente seleziona la board che vuole eliminare
4. Il sistema mostra la board selezionata
5. L'utente seleziona l'operazione "Elimina"
6. Il sistema mostra le board appartenenti all'utente

**UC6: Eliminazione di una boardItem - Attore Primario: Utente Registrato**
1. L'utente fa l'autenticazione
2. Il sistema mostra la homepage con le sue board
3. L'utente seleziona la board che vuole modificare
4. Il sistema mostra la board selezionata
5. L'utente seleziona l'operazione "Modifica"
6. L'utente seleziona la boardItem da eliminare
7. L'utente sceglie l'operazione "Elimina"
8. Il sistema mostra la board modificata

**UC7: Modifica di una boardItem - Attore Primario: Utente Registrato**
1. L'utente fa l'autenticazione
2. Il sistema mostra la homepage con le sue board
3. L'utente seleziona la board che vuole modificare
4. Il sistema mostra la board selezionata
5. L'utente seleziona l'operazione "Modifica"
6. L'utente seleziona la boardItem da modificare
7. L'utente inserisce le informazioni
8. L'utente conferma
8a. L'utente annulla e il sistema non registra le modifiche
9. Il sistema mostra la board modificata

**UC8: Visualizzazzione di una board - Attore Primario: Utente Registrato**
1. L'utente fa l'autenticazione
2. Il sistema mostra la homepage con le sue board
2a. Se la board è pubblica, basta mettere il link della board pubblica
3. L'utente seleziona la board che vuole visualizzare
4. Il sistema mostra la board

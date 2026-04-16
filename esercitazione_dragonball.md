# Esercitazione Android — Dragon Ball App

Con l'aiuto dell'IA (ChatGPT o Claude), completa un'app Android per l'esplorazione dei personaggi Dragon Ball.

Il progetto base è già fornito e contiene:
- `MainActivity` con toolbar e navigazione tra due fragment
- `CharacterListFragment` con RecyclerView, campo di ricerca (non collegato) e chiamata API già impostata
- `CharacterDetailFragment` con nome e descrizione del personaggio
- `MainViewModel` condiviso tra i due fragment
- `CharacterAdapter` per la lista
- Retrofit configurato sull'API pubblica `https://dragonball-api.com/api/`

Il tuo compito è **estendere** il progetto base completando le richieste elencate di seguito.

---

## API di riferimento

| Endpoint | Descrizione |
|---|---|
| `GET /characters` | Lista paginata di personaggi (10 per pagina) |
| `GET /characters/{id}` | Dettaglio di un singolo personaggio |

Parametri utili: `?race=Saiyan` per filtrare in base alla razza, `?name=goku` per la ricerca per nome.

---

## Richieste

### 1. Gestione degli errori *(2 pt)*

Quando si verifica un errore durante il caricamento dei dati (es. nessuna connessione di rete), mostra un messaggio `Toast` all'utente con il testo dell'errore.

> Suggerimento: il `MainViewModel` espone già il LiveData `errore`. Osservalo dal fragment e mostra il Toast quando il valore non è null.

---

### 2. Ricerca locale *(2 pt)*

Collega il campo di ricerca già presente in `fragment_character_list.xml` alla lista dei personaggi.
Mentre l'utente digita, la lista deve aggiornarsi mostrando solo i personaggi il cui nome contiene il testo inserito (senza effettuare nuove chiamate API).

> Suggerimento: usa l'extension `addTextChangedListener` sull'`EditText` e filtra la lista già caricata prima di passarla all'adapter.

---

### 3. Modifica del view holder *(2 pt)*

Il view holder della lista attualmente mostra solo immagine, nome e razza. Estendila aggiungendo:
- affiliazione (`affiliation`)

> Suggerimento: tutti questi campi sono già presenti nel modello `Character`. Aggiorna il layout `item_character.xml` e il codice del view holder nell'adapter.

---

### 4. Pagina di dettaglio completa *(3 pt)*

La pagina di dettaglio attualmente mostra solo nome e descrizione. Estendila aggiungendo:
- immagine grande del personaggio (usa: `imageView.load(url)`)
- razza (`race`)
- genere (`gender`)
- affiliazione (`affiliation`)
- ki base (`ki`) e ki massimo (`maxKi`)

> Suggerimento: tutti questi campi sono già presenti nel modello `Character`. Aggiorna il layout `fragment_character_detail.xml` e il codice del fragment.

---

### 5. Condivisione del personaggio *(2 pt)*

Nella pagina di dettaglio, aggiungi un pulsante "Condividi" che permette di condividere nome e descrizione del personaggio tramite un `Intent` implicito (Whatsapp, Note, Email, ecc.).

> Suggerimento: usa `Intent(Intent.ACTION_SEND)` con `type = "text/plain"` e `putExtra(Intent.EXTRA_TEXT, ...)`.

---

### 6. Ordinamento della lista *(2 pt)*

Aggiungi un pulsante o un'icona nella toolbar della lista che permetta di ordinare i personaggi in ordine alfabetico (A → Z). Una seconda pressione deve ripristinare l'ordine originale (Z → A o ordine API).

> Suggerimento: mantieni la lista originale nel ViewModel e ordina una copia prima di passarla all'adapter.

---

### 7. Salva ricerca in locale *(3 pt)*

Salva la ricerca utente in locale tramite SharedPreferences.
Ripristina il campo di ricerca in caso di riavvio dell'app.

> Suggerimento: salva nel `addTextChangedListener` e carica il valore salvato nel `onViewCreated` del fragment. Usa `getSharedPreferences("DragonBallApp", Context.MODE_PRIVATE)` per accedere alle SharedPreferences.
---

### 8. Trasformazioni del personaggio *(4 pt)*

Nella pagina di dettaglio, dopo la descrizione, aggiungi una sezione "Trasformazioni" che mostra la lista delle trasformazioni del personaggio.
Ogni trasformazione deve mostrare almeno il nome e l'immagine.

> Suggerimento: aggiungi `RecyclerView` nel layout del dettaglio con layout manager orizzontale `LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)` e crea un nuovo adapter `TransformationAdapter`.

---

### 9. Filtro per razza *(4 pt)*

Quando l'utente seleziona "Saiyan", chiama l'api `getCharactersByRace` passandogli la razza per ottenere i personaggi filtrati.  
Quando seleziona "Tutti", richiama l'api `getCharacters` senza parametri per ottenere tutti i personaggi.

> Suggerimento: sfrutta le funzioni e le variabili esistenti, aggiungi nel view model `caricaPersonaggiPerRazza(val razza: String)` 

---

## Punteggio riepilogativo

| #          | Richiesta                      | Punti  |
|------------|--------------------------------|--------|
| 1          | Gestione degli errori          | 2      |
| 2          | Ricerca locale                 | 2      |
| 3          | Modifica del view holder       | 2      |
| 4          | Pagina di dettaglio completa   | 3      |
| 5          | Condivisione del personaggio   | 2      |
| 6          | Ordinamento della lista        | 2      |
| 7          | Salva ricerca in locale        | 3      |
| 8          | Trasformazioni del personaggio | 4      |
| 9          | Filtro per razza               | 4      |
| **Totale** |                                | **24** |

---

## Consegna
1) Crea un nuovo branch con questa sintassi: esercizi/dragonball/[NOME-COGNOME]
2) Salva il lavoro svolto con uno o più commit inserendo una breve descrizione di cosa hai fatto.
3) Pusha il branch quando hai finito!


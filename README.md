# MiniChat AI — App Android didattica

App di chat con un assistente AI che interagisce con le API di OpenRouter.
Progetto sviluppato a fini didattici per il corso Android.

---

## Branch: `minichat/1-recyclerview` — Lezione 2

### Obiettivo
Visualizzare i messaggi nella chat con una RecyclerView, usando due layout diversi per utente e bot.

### Cosa è stato aggiunto

- **`Message.kt`** — Data class che rappresenta un messaggio (`testo` + `TipoMessaggio`)
- **`ChatAdapter.kt`** — Adapter con due ViewHolder: uno per UTENTE, uno per BOT
- **`item_message_user.xml`** — Bolla messaggio allineata a destra (colore primario)
- **`item_message_bot.xml`** — Bolla messaggio allineata a sinistra (grigio chiaro)
- **`bg_bubble_user.xml`** / **`bg_bubble_bot.xml`** — Shape drawable per le bolle arrotondate
- **`MainViewModel`** — Aggiunto `LiveData<List<Message>>` con messaggi hardcoded di esempio
- **`ChatFragment`** — Collegato alla RecyclerView, osserva il ViewModel

### Concetti introdotti

| Concetto | Dove si vede |
|---|---|
| RecyclerView + Adapter | `ChatAdapter`, `fragment_chat.xml` |
| Due layout per item | `getItemViewType()` in `ChatAdapter` |
| ViewHolder pattern | `UtenteViewHolder`, `BotViewHolder` |
| LiveData + observe | `ChatFragment.onViewCreated()` |
| LinearLayoutManager | `stackFromEnd = true` per scrollare al fondo |

### Struttura dei messaggi

```
MainViewModel
└── messaggi: LiveData<List<Message>>
        │
        └── ChatFragment osserva e aggiorna ChatAdapter
                ├── item layout UTENTE  (bolla destra)
                └── item layout BOT     (bolla sinistra)
```

---

## Lezione precedente

- **Lezione 1** (`minichat/0-struttura-base`) — `MainActivity` + `BottomNavigationView` + due fragment vuoti

---

## Prossimi step

- **Lezione 3** — Gestione eventi: click sul bottone aggiunge messaggio, longClick sul bot mostra Toast
- **Lezione 4** — Impostazioni con SharedPreferences (tono del bot, lunghezza risposte)
- **Lezione 5** — Intent `ACTION_SEND` per condividere i messaggi del bot
- **Lezione 6** — Coroutines + chiamata reale alle API OpenRouter

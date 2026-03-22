# MiniChat AI — App Android didattica

App di chat con un assistente AI che interagisce con le API di OpenRouter.
Progetto sviluppato a fini didattici per il corso Android.

---

## Branch: `minichat/2-eventi` — Lezione 3

### Obiettivo
Rendere la chat interattiva gestendo i due eventi principali: click e longClick.

### Cosa è stato modificato

- **`ChatAdapter.kt`** — Aggiunto parametro `onLongClickBot: (Message) -> Unit` (callback) e `setOnLongClickListener` sul ViewHolder del bot
- **`ChatFragment.kt`** — Aggiunto `setOnClickListener` sul bottone "Invia" e gestione del Toast al longClick

### Concetti introdotti

| Concetto | Dove si vede |
|---|---|
| `setOnClickListener` | `ChatFragment.impostaBottoneInvia()` |
| `setOnLongClickListener` | `ChatAdapter.onBindViewHolder()` sul BotViewHolder |
| Callback (lambda) | `onLongClickBot: (Message) -> Unit` passata dal fragment all'adapter |
| Toast | `ChatFragment.mostraToastCondividi()` |

### Come funzionano gli eventi

```
Utente preme "Invia"
  → setOnClickListener in ChatFragment
  → viewModel.aggiungiMessaggio()
  → LiveData notifica → RecyclerView si aggiorna

Utente tiene premuto su messaggio bot
  → setOnLongClickListener in ChatAdapter
  → chiama onLongClickBot (callback definita in ChatFragment)
  → mostra Toast "Condividi!"       ← nella Lezione 5 diventerà un Intent
```

---

## Lezioni precedenti

- **Lezione 2** (`minichat/1-recyclerview`) — RecyclerView con due layout diversi (utente / bot)
- **Lezione 1** (`minichat/0-struttura-base`) — `MainActivity` + `BottomNavigationView` + due fragment vuoti

---

## Prossimi step

- **Lezione 4** — `SettingsFragment` con SharedPreferences (tono del bot, lunghezza risposte)
- **Lezione 5** — Intent `ACTION_SEND` per condividere i messaggi del bot
- **Lezione 6** — Coroutines + chiamata reale alle API OpenRouter

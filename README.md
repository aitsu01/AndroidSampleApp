# MiniChat AI — App Android didattica

App di chat con un assistente AI che interagisce con le API di OpenRouter.
Progetto sviluppato a fini didattici per il corso Android.

---

## Branch: `minichat/4-intent` — Lezione 5

### Obiettivo
Sostituire il Toast del longClick con un vero `Intent.ACTION_SEND` per condividere il testo del messaggio con qualsiasi app del dispositivo.

### Cosa è stato modificato

- **`ChatFragment.kt`** — `mostraToastCondividi()` sostituito con `condividiMessaggio()` che lancia un `Intent.ACTION_SEND`

### Concetti introdotti

| Concetto | Dove si vede |
|---|---|
| Intent implicito | `Intent(Intent.ACTION_SEND)` — Android sceglie le app compatibili |
| `Intent.EXTRA_TEXT` | Passa il testo del messaggio all'app destinataria |
| `Intent.createChooser` | Mostra il selettore di app con un titolo personalizzato |

### Come funziona l'Intent implicito

```
Utente tiene premuto su messaggio bot
  → setOnLongClickListener in ChatAdapter
  → callback condividiMessaggio() in ChatFragment
  → Intent(ACTION_SEND) con EXTRA_TEXT = testo del messaggio
  → createChooser → Android mostra le app compatibili (WhatsApp, Gmail, ecc.)
```

La differenza rispetto a un **Intent esplicito** (es. `Intent(context, SettingsActivity::class.java)`)
è che qui non specifichiamo l'app destinataria: lo decide Android in base al tipo di contenuto (`text/plain`).

---

## Lezioni precedenti

- **Lezione 4** (`minichat/3-settings`) — `SettingsFragment` con SharedPreferences (system prompt + lunghezza)
- **Lezione 3** (`minichat/2-eventi`) — Click su "Invia" e longClick per il Toast
- **Lezione 2** (`minichat/1-recyclerview`) — RecyclerView con due layout diversi
- **Lezione 1** (`minichat/0-struttura-base`) — `MainActivity` + `BottomNavigationView` + due fragment vuoti

---

## Prossimi step

- **Lezione 6** — Coroutines + chiamata reale alle API OpenRouter (usa `systemPrompt` e `lunghezza` dal ViewModel)

# MiniChat AI — App Android didattica

App di chat con un assistente AI che interagisce con le API di OpenRouter.
Progetto sviluppato a fini didattici per il corso Android.

---

## Branch: `minichat/3-settings` — Lezione 4

### Obiettivo
Implementare il `SettingsFragment` con i controlli per personalizzare il bot, salvando le preferenze con `SharedPreferences`.

### Cosa è stato modificato

- **`fragment_settings.xml`** — Layout con `EditText` multiriga (system prompt) + `Slider` (lunghezza)
- **`SettingsFragment.kt`** — Legge/scrive `SharedPreferences`, aggiorna il ViewModel
- **`MainViewModel.kt`** — Aggiunti `LiveData<String>` per il system prompt e `LiveData<Int>` per la lunghezza

### Concetti introdotti

| Concetto | Dove si vede |
|---|---|
| SharedPreferences (lettura) | `SettingsFragment.caricaPreferenze()` |
| SharedPreferences (scrittura) | `SettingsFragment.salvaPreferenza()` con `.edit().apply()` |
| EditText multiriga | `inputType="textMultiLine"`, `minLines="3"` in `fragment_settings.xml` |
| TextWatcher | `addTextChangedListener` — salva ad ogni modifica del testo |
| Material Slider + listener | `addOnChangeListener` |
| ViewModel come ponte | Le impostazioni scritte da `SettingsFragment` sono leggibili da `ChatFragment` |

### Flusso dati delle impostazioni

```
SettingsFragment
  → legge SharedPreferences al caricamento
  → aggiorna UI (EditText system prompt, Slider)
  → aggiorna MainViewModel (systemPrompt, lunghezza)

Quando l'utente modifica un'impostazione:
  → salva in SharedPreferences  (persiste dopo la chiusura dell'app)
  → aggiorna MainViewModel      (disponibile subito agli altri fragment)
```

---

## Lezioni precedenti

- **Lezione 3** (`minichat/2-eventi`) — Click su "Invia" e longClick per il Toast
- **Lezione 2** (`minichat/1-recyclerview`) — RecyclerView con due layout diversi
- **Lezione 1** (`minichat/0-struttura-base`) — `MainActivity` + `BottomNavigationView` + due fragment vuoti

---

## Prossimi step

- **Lezione 5** — Intent `ACTION_SEND` per condividere i messaggi del bot (sostituisce il Toast)
- **Lezione 6** — Coroutines + chiamata reale alle API OpenRouter (usa tono e lunghezza dal ViewModel)

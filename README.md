# MiniChat AI — App Android didattica

App di chat con un assistente AI che interagisce con le API di OpenRouter.
Progetto sviluppato a fini didattici per il corso Android.

---

## Branch: `minichat/0-struttura-base` — Lezione 1

### Obiettivo
Creare lo scheletro dell'app con la navigazione tra due schermate.

### Cosa è stato aggiunto

- **`MainActivity`** — Activity principale che ospita i fragment e gestisce la navigazione
- **`BottomNavigationView`** — Barra di navigazione in basso per passare tra Chat e Impostazioni
- **`ChatFragment`** — Schermata principale della chat (per ora con testo segnaposto)
- **`SettingsFragment`** — Schermata delle impostazioni (per ora con testo segnaposto)
- **`MainViewModel`** — ViewModel condiviso tra i due fragment tramite `activityViewModels()`

### Concetti introdotti

| Concetto | Dove si vede |
|---|---|
| Fragment | `ChatFragment`, `SettingsFragment` |
| FragmentManager | `MainActivity` — `supportFragmentManager.beginTransaction()` |
| BottomNavigationView | `activity_main.xml` + `MainActivity` |
| ViewModel condiviso | `activityViewModels()` in entrambi i fragment |
| View Binding | Tutti i file Kotlin con `_binding` |

### Struttura dell'app

```
MainActivity
├── ChatFragment          ← schermata principale (testo segnaposto)
└── SettingsFragment      ← impostazioni (testo segnaposto)
```

---

## Prossimi step

- **Lezione 2** — RecyclerView con messaggi hardcoded e due layout diversi (utente / bot)
- **Lezione 3** — Gestione eventi: click per inviare, longClick per condividere
- **Lezione 4** — Impostazioni con SharedPreferences (tono del bot, lunghezza risposte)
- **Lezione 5** — Intent `ACTION_SEND` per condividere i messaggi del bot
- **Lezione 6** — Coroutines + chiamata reale alle API OpenRouter

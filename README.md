# MiniChat AI — App Android didattica

App di chat con un assistente AI che interagisce con le API di OpenRouter.
Progetto sviluppato a fini didattici per il corso Android.

---

## Branch: `minichat/6-personalizzazione` — Lezione 7

### Obiettivo
Personalizzare l'aspetto dell'app con colori, stili e temi. Aggiungere un toggle per passare tra light e dark mode.

### Cosa è stato aggiunto / modificato

- **`res/values/colors.xml`** — Colori con nome (`colore_primario`, `colore_secondario`) invece di hex inline
- **`res/values/themes.xml`** — Tema light personalizzato con `colorPrimary` e `colorSecondary`
- **`res/values-night/themes.xml`** — Override dark mode con tinte più chiare (leggibili su sfondo scuro)
- **`res/values/styles.xml`** — Stili riutilizzabili `Stile.TitoloSezione` e `Stile.DescrizioneSezione`
- **`fragment_settings.xml`** — Applica gli stili + aggiunge sezione "Modalità scura" con `MaterialSwitch`
- **`SettingsFragment.kt`** — Listener sullo switch: chiama `AppCompatDelegate.setDefaultNightMode()`
- **`MainActivity.kt`** — `applicaTemaSalvato()` chiamata prima di `super.onCreate()` per evitare il flash

### Concetti introdotti

| Concetto | Dove si vede |
|---|---|
| `colors.xml` | Nomi semantici ai colori invece di hex diretti |
| `colorPrimary` nel tema | `values/themes.xml` — si propaga a toolbar, bottoni, slider |
| `values-night/` | Android carica automaticamente questi file in dark mode |
| `styles.xml` | Stili riutilizzabili applicati con `style="@style/..."` |
| `?attr/colorOnSurfaceVariant` | Attributo del tema corrente — si adatta automaticamente a light/dark |
| `AppCompatDelegate.setDefaultNightMode()` | Cambia il tema a runtime, provoca recreate() dell'Activity |
| `applicaTemaSalvato()` prima di `super.onCreate()` | Evita il flash visivo al riavvio dell'app |

### Come funziona la gerarchia Colori → Tema → Stile → Layout

```
colors.xml          → definisce i valori (#1565C0 = "colore_primario")
    ↓
themes.xml          → assegna i colori agli attributi del tema (colorPrimary = @color/colore_primario)
    ↓
styles.xml          → raggruppa attributi riutilizzabili per tipo di view
    ↓
fragment_*.xml      → le view applicano stili (style="@style/Stile.TitoloSezione")
                      o leggono direttamente il tema (?attr/colorPrimary)
```

### Toggle dark mode — flusso completo

```
Avvio app
  → MainActivity.applicaTemaSalvato()   legge SharedPreferences
  → AppCompatDelegate.setDefaultNightMode()  applica prima del disegno
  → Android legge values/ o values-night/ in base al modo impostato

Utente attiva lo switch
  → SettingsFragment salva preferenza in SharedPreferences
  → AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
  → Activity.recreate() automatico → tutto il tema si aggiorna
```

---

## Tutte le lezioni

| Branch | Lezione | Contenuto |
|---|---|---|
| `minichat/0-struttura-base` | 1 | `MainActivity` + `BottomNavigationView` + due fragment vuoti |
| `minichat/1-recyclerview` | 2 | RecyclerView con due layout diversi (utente / bot) |
| `minichat/2-eventi` | 3 | Click su "Invia" e longClick con Toast |
| `minichat/3-settings` | 4 | `SettingsFragment` con SharedPreferences |
| `minichat/4-intent` | 5 | `Intent.ACTION_SEND` per condividere i messaggi |
| `minichat/5-openrouter` | 6 | Coroutines + chiamata reale alle API OpenRouter |
| `minichat/6-personalizzazione` | 7 | Colori, stili, temi e toggle dark/light mode |

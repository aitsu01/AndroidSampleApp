# AndroidSampleApp - Corso Android Base

Progetto didattico progressivo per imparare lo sviluppo Android, dall'interfaccia base alle chiamate API REST.

---

## Indice Lezioni
- [Lezione 0: Ciclo di vita Activity](#lezione-0-ciclo-vita-activity) (branch: `lezione-0`)
- [Lezione 1: TextView, EditText e Button](#lezione-1-textview-edittext-e-button) (branch: `lezione-1-textview-edittext-button`)
- [Lezione 2: ViewModel e LiveData](#lezione-2-viewmodel-e-livedata) (branch: `lezione-2-viewmodel`)

---

## Lezione 1: TextView, EditText e Button

### Obiettivo
Comprendere i widget base di Android per input e output di testo.

### Cosa abbiamo imparato

#### 1. **EditText** - Campo di input testo
L'`EditText` permette all'utente di inserire del testo. Nel nostro esempio:
```xml
<EditText
    android:id="@+id/editTextPlayerName"
    android:hint="@string/player_name_hint"
    android:inputType="textPersonName" />
```

- `android:id` - identificatore univoco per accedere alla view dal codice
- `android:hint` - testo suggerimento che appare quando il campo è vuoto
- `android:inputType` - tipo di input (in questo caso, nome di persona)

#### 2. **Button** - Pulsante cliccabile
Il `Button` è un elemento cliccabile che esegue un'azione:
```xml
<Button
    android:id="@+id/buttonStartGame"
    android:text="@string/start_game_button" />
```

Nel codice Kotlin gestiamo il click con un listener:
```kotlin
binding.buttonStartGame.setOnClickListener {
    // Codice da eseguire al click
}
```

#### 3. **TextView** - Visualizzazione testo
La `TextView` mostra del testo a schermo:
```xml
<TextView
    android:id="@+id/textViewWelcome"
    android:text="@string/insert_name_message"
    android:textSize="18sp" />
```

Possiamo aggiornare il testo dinamicamente dal codice:
```kotlin
binding.textViewWelcome.text = "Nuovo messaggio"
```

### ViewBinding

Utilizziamo **ViewBinding** per accedere alle view in modo type-safe:

```kotlin
// Dichiarazione del binding
private var _binding: FragmentFirstBinding? = null
private val binding get() = _binding!!

// Inizializzazione in onCreateView
_binding = FragmentFirstBinding.inflate(inflater, container, false)

// Utilizzo delle view
binding.buttonStartGame.setOnClickListener { ... }

// Pulizia in onDestroyView per evitare memory leak
_binding = null
```

### Flusso dell'applicazione

1. L'utente inserisce il proprio nome nell'`EditText`
2. L'utente clicca sul `Button` "Inizia Partita"
3. Il codice legge il testo dall'`EditText`
4. Viene verificato che il nome non sia vuoto
5. Il messaggio di benvenuto viene mostrato nella `TextView`

### Concetti chiave

- **View**: Ogni elemento visibile nell'interfaccia (Button, TextView, EditText, ecc.)
- **ViewBinding**: Sistema per accedere alle view in modo sicuro e performante
- **Listener**: Funzione che viene eseguita quando avviene un evento (es. click)
- **getString()**: Metodo per recuperare stringhe dalle risorse (res/values/strings.xml)
- **String formatting**: Uso di `%s` per inserire valori dinamici nelle stringhe

### Struttura file

```
app/src/main/
├── java/.../FirstFragment.kt      # Logica del fragment
├── res/
│   ├── layout/
│   │   └── fragment_first.xml     # Layout dell'interfaccia
│   └── values/
│       └── strings.xml            # Stringhe tradotte
```

### Prossimi passi

Nella prossima lezione introdurremo il **ViewModel** per separare la logica UI dai dati e gestire meglio il ciclo di vita dell'app.

---

## Lezione 2: ViewModel e LiveData

### Obiettivo
Introdurre l'architettura MVVM (Model-View-ViewModel) per separare la logica UI dai dati e gestire correttamente il ciclo di vita.

### Perché usare ViewModel?

Nella lezione precedente, tutta la logica era nel Fragment. Questo crea problemi:

1. **Perdita di dati**: Se ruoti lo schermo, il Fragment viene distrutto e ricreato, perdendo tutti i dati
2. **Codice difficile da testare**: La logica mista con l'UI è difficile da testare unitariamente
3. **Violazione del principio di singola responsabilità**: Il Fragment fa troppo

Il **ViewModel** risolve questi problemi:
- Sopravvive ai cambi di configurazione (rotazione schermo)
- Separa la logica di business dall'UI
- È facile da testare
- Permette di condividere dati tra Fragment

### Cosa abbiamo imparato

#### 1. **ViewModel** - Gestione dello stato

Il ViewModel contiene i dati e la logica di business:

```kotlin
class MainViewModel : ViewModel() {
    private val _welcomeMessage = MutableLiveData<String>()
    val welcomeMessage: LiveData<String> = _welcomeMessage

    fun onStartGameClicked(playerName: String, welcomeTemplate: String, emptyMessage: String) {
        if (playerName.isNotBlank()) {
            _welcomeMessage.value = String.format(welcomeTemplate, playerName)
        } else {
            _welcomeMessage.value = emptyMessage
        }
    }
}
```

**Caratteristiche importanti:**
- Estende `ViewModel`
- Non ha riferimenti all'UI (View, Context, Activity)
- Contiene `MutableLiveData` privato per modifiche interne
- Espone `LiveData` pubblico in sola lettura

#### 2. **LiveData** - Osservazione dei dati

LiveData è un contenitore di dati osservabile, lifecycle-aware:

```kotlin
// Nel ViewModel - MutableLiveData (può essere modificato)
private val _welcomeMessage = MutableLiveData<String>()
val welcomeMessage: LiveData<String> = _welcomeMessage

// Nel Fragment - Osservazione
viewModel.welcomeMessage.observe(viewLifecycleOwner) { message ->
    binding.textViewWelcome.text = message
}
```

**Vantaggi di LiveData:**
- **Lifecycle-aware**: Si ferma automaticamente quando il Fragment non è attivo
- **No memory leak**: L'observer viene rimosso automaticamente
- **No crash**: Non aggiorna l'UI se il Fragment è distrutto
- **Dati sempre aggiornati**: Ricevi automaticamente l'ultimo valore quando torni attivo

#### 3. **Pattern MVVM** - Architettura

```
┌─────────────┐
│    View     │ ← Fragment con ViewBinding
│ (Fragment)  │   Solo UI, nessuna logica
└──────┬──────┘
       │ osserva LiveData
       │ chiama metodi
┌──────▼──────┐
│  ViewModel  │ ← Logica e stato
│             │   Espone LiveData
└──────┬──────┘
       │ manipola
┌──────▼──────┐
│    Model    │ ← Dati (prossime lezioni)
│  (Data)     │
└─────────────┘
```

#### 4. **Inizializzazione del ViewModel**

Nel Fragment, usiamo il delegate `by viewModels()`:

```kotlin
private val viewModel: MainViewModel by viewModels()
```

Questo:
- Crea il ViewModel automaticamente
- Lo associa al ciclo di vita del Fragment
- Restituisce lo stesso ViewModel anche dopo ricreazione del Fragment
- Gestisce la pulizia automatica quando il Fragment è definitivamente distrutto

### Confronto: Prima vs Dopo

**Prima (Lezione 1 - senza ViewModel):**
```kotlin
binding.buttonStartGame.setOnClickListener {
    val playerName = binding.editTextPlayerName.text.toString()
    if (playerName.isNotBlank()) {
        binding.textViewWelcome.text = getString(R.string.welcome_message, playerName)
    } else {
        binding.textViewWelcome.text = getString(R.string.insert_name_message)
    }
}
```
❌ Logica mista con UI
❌ Dati persi alla rotazione
❌ Difficile da testare

**Dopo (Lezione 2 - con ViewModel):**
```kotlin
// Osservazione
viewModel.welcomeMessage.observe(viewLifecycleOwner) { message ->
    binding.textViewWelcome.text = message
}

// Azione
binding.buttonStartGame.setOnClickListener {
    val playerName = binding.editTextPlayerName.text.toString()
    viewModel.onStartGameClicked(playerName, getString(R.string.welcome_message), getString(R.string.insert_name_message))
}
```
✅ Logica separata
✅ Dati sopravvivono alla rotazione
✅ Facile da testare

### Ciclo di vita ViewModel

```
Fragment creato → ViewModel creato
    ↓
Fragment ricreato (rotazione) → ViewModel RIUTILIZZATO (stessi dati!)
    ↓
Fragment definitivamente distrutto → ViewModel distrutto
```

### Dipendenze aggiunte

```kotlin
// ViewModel
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
// LiveData
implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
// Fragment KTX (per by viewModels())
implementation("androidx.fragment:fragment-ktx:1.6.2")
```

### Test pratico

**Prova questo:**
1. Inserisci il tuo nome e clicca "Inizia Partita"
2. Ruota lo schermo (Ctrl+F11/F12 su emulatore)
3. Il messaggio di benvenuto è ancora lì! 🎉

**Perché?** Il ViewModel sopravvive alla rotazione e LiveData riemette automaticamente l'ultimo valore al nuovo Fragment.

### Concetti chiave

- **ViewModel**: Contiene dati e logica, sopravvive ai configuration changes
- **LiveData**: Contenitore dati osservabile, lifecycle-aware
- **MutableLiveData**: Versione modificabile di LiveData (uso interno)
- **Observer**: Lambda che reagisce ai cambiamenti dei dati
- **viewLifecycleOwner**: Collega l'observer al ciclo di vita del Fragment
- **MVVM**: Pattern architetturale (Model-View-ViewModel)

### Struttura file

```
app/src/main/
├── java/.../
│   ├── FirstFragment.kt           # View: UI e osservazione
│   └── MainViewModel.kt           # ViewModel: logica e stato
└── res/...                        # Layout e risorse (invariati)
```

### Prossimi passi

Nella prossima lezione introdurremo **RecyclerView** per mostrare liste scrollabili di elementi, usando il ViewModel per gestire la lista di dati.

---

## Setup Progetto

**Requisiti:**
- Android Studio Hedgehog o superiore
- SDK Android 24+ (Android 7.0)
- Kotlin 1.9+

**Come eseguire:**
1. Clona il repository
2. Apri il progetto in Android Studio
3. Fai checkout del branch della lezione desiderata
4. Avvia l'app su emulatore o dispositivo fisico

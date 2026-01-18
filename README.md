# AndroidSampleApp - Corso Android Base

Progetto didattico progressivo per imparare lo sviluppo Android, dall'interfaccia base alle chiamate API REST.

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

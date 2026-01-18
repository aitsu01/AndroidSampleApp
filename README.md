# AndroidSampleApp - Corso Android Base

Progetto didattico progressivo per imparare lo sviluppo Android, dall'interfaccia base alle chiamate API REST.

---

## Indice Lezioni
- [Lezione 0: Ciclo di vita Activity](#lezione-0-ciclo-vita-activity) (branch: `lezione-0`)
- [Lezione 1: TextView, EditText e Button](#lezione-1-textview-edittext-e-button) (branch: `lezione-1-textview-edittext-button`)
- [Lezione 2: ViewModel e LiveData](#lezione-2-viewmodel-e-livedata) (branch: `lezione-2-viewmodel`)
- [Lezione 3: RecyclerView e Liste](#lezione-3-recyclerview-e-liste) (branch: `lezione-3-liste-semplici`)
- [Lezione 4: Data Class e Modelli Dati](#lezione-4-data-class-e-modelli-dati) (branch: `lezione-4-modelli-dati`)

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

## Lezione 3: RecyclerView e Liste

### Obiettivo
Imparare a mostrare liste scrollabili di dati usando RecyclerView, uno dei componenti più importanti di Android.

### Perché RecyclerView?

Per mostrare liste di dati, potremmo pensare di usare tante TextView in un ScrollView. Ma questo ha problemi:

1. **Memoria**: Se hai 1000 elementi, crei 1000 view = app lenta e crash
2. **Performance**: Creare view è costoso, scorrere diventa lento
3. **Codice**: Devi gestire manualmente ogni elemento

**RecyclerView risolve tutto questo**:
- Riutilizza le view invece di crearle ogni volta (pattern ViewHolder)
- Mostra solo gli elementi visibili a schermo
- Performance eccellenti anche con milioni di elementi
- Codice pulito con pattern Adapter

### Cosa abbiamo imparato

#### 1. **RecyclerView** - Lista scrollabile efficiente

RecyclerView è un componente che mostra liste in modo ottimizzato:

```xml
<androidx.recyclerview.widget.RecyclerView
    android:id="@+id/recyclerViewCards"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

Per funzionare ha bisogno di:
- **Adapter**: Collega i dati alle view
- **LayoutManager**: Definisce come disporre gli item (lista, griglia, etc.)
- **ViewHolder**: Contiene i riferimenti alle view di ogni item

#### 2. **Adapter** - Collegamento tra dati e view

L'Adapter è il ponte tra i tuoi dati e la RecyclerView:

```kotlin
class CardAdapter(private var cards: List<String>) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    // 1. Crea la view per un item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = ItemCardSimpleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    // 2. Collega i dati alla view
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    // 3. Dice quanti elementi ci sono
    override fun getItemCount(): Int = cards.size
}
```

**I tre metodi fondamentali:**
- `onCreateViewHolder()`: Crea una nuova view (chiamato poche volte)
- `onBindViewHolder()`: Riempie una view con i dati (chiamato per ogni item visibile)
- `getItemCount()`: Dice quanti elementi totali ci sono

#### 3. **ViewHolder** - Pattern per performance

Il ViewHolder conserva i riferimenti alle view per riutilizzarle:

```kotlin
class CardViewHolder(private val binding: ItemCardSimpleBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cardName: String) {
        binding.textViewCardName.text = cardName
    }
}
```

**Perché è importante?**
- Senza ViewHolder: Chiami `findViewById()` mille volte = LENTO ❌
- Con ViewHolder: Salvi i riferimenti e li riusi = VELOCE ✅

#### 4. **LayoutManager** - Disposizione degli item

Il LayoutManager decide come disporre gli elementi:

```kotlin
binding.recyclerViewCards.layoutManager = LinearLayoutManager(requireContext())
```

**Tipi disponibili:**
- `LinearLayoutManager`: Lista verticale o orizzontale
- `GridLayoutManager`: Griglia (es. galleria foto)
- `StaggeredGridLayoutManager`: Griglia con altezze variabili (es. Pinterest)

#### 5. **Integrazione con ViewModel e LiveData**

La lista di carte è gestita dal ViewModel:

```kotlin
// Nel ViewModel
private val _cards = MutableLiveData<List<String>>()
val cards: LiveData<List<String>> = _cards

// Nel Fragment
viewModel.cards.observe(viewLifecycleOwner) { cards ->
    cardAdapter.updateCards(cards)
}
```

Quando i dati cambiano nel ViewModel, la RecyclerView si aggiorna automaticamente! ✨

### Come funziona il riciclo delle view

```
┌─────────────────────────┐
│  Elementi visibili: 5   │
│  ┌─────┐               │
│  │ Item 1 │  ← View creata
│  ├─────┤               │
│  │ Item 2 │  ← View creata
│  ├─────┤               │
│  │ Item 3 │  ← View creata
│  ├─────┤               │
│  │ Item 4 │  ← View creata
│  ├─────┤               │
│  │ Item 5 │  ← View creata
│  └─────┘               │
└─────────────────────────┘
        ↓ scroll giù
┌─────────────────────────┐
│  ┌─────┐               │
│  │ Item 2 │             │
│  ├─────┤               │
│  │ Item 3 │             │
│  ├─────┤               │
│  │ Item 4 │             │
│  ├─────┤               │
│  │ Item 5 │             │
│  ├─────┤               │
│  │ Item 6 │  ← View RIUSATA (era Item 1!)
│  └─────┘               │
└─────────────────────────┘

RecyclerView ha creato solo 5 view, anche se ci sono 13 elementi!
```

### Struttura completa

```
┌──────────────┐
│  ViewModel   │ ← Gestisce lista di carte (LiveData)
└──────┬───────┘
       │ osserva
┌──────▼───────┐
│  Fragment    │ ← Configura RecyclerView e Adapter
└──────┬───────┘
       │ fornisce dati
┌──────▼───────┐
│   Adapter    │ ← Collega dati a view
└──────┬───────┘
       │ crea e riusa
┌──────▼───────┐
│  ViewHolder  │ ← Contiene riferimenti alle view
└──────────────┘
```

### Vantaggi di questa architettura

✅ **Separazione responsabilità**:
- ViewModel: gestisce i dati
- Fragment: configura l'UI
- Adapter: collega dati a view
- ViewHolder: ottimizza performance

✅ **Performance**:
- Solo le view visibili sono create
- Le view sono riutilizzate quando scrolli
- Nessun lag anche con migliaia di elementi

✅ **Manutenibilità**:
- Ogni classe ha un compito preciso
- Facile da modificare e testare
- Codice organizzato e leggibile

### File creati/modificati

```
app/src/main/
├── java/.../
│   ├── FirstFragment.kt           # Configura RecyclerView
│   ├── MainViewModel.kt           # Gestisce lista carte con LiveData
│   └── CardAdapter.kt             # Adapter per RecyclerView (NUOVO)
└── res/
    └── layout/
        ├── fragment_first.xml     # Aggiunta RecyclerView
        └── item_card_simple.xml   # Layout singolo item (NUOVO)
```

### Test pratico

**Prova questo:**
1. Avvia l'app
2. Vedrai una lista scrollabile di 13 carte
3. Scorri su e giù: fluido e veloce!
4. Ruota lo schermo: la lista rimane (grazie al ViewModel)

### Concetti chiave

- **RecyclerView**: Componente per liste scrollabili efficienti
- **Adapter**: Collega i dati alle view
- **ViewHolder**: Pattern per riutilizzare view e migliorare performance
- **LayoutManager**: Definisce disposizione degli item
- **notifyDataSetChanged()**: Notifica cambiamenti nei dati
- **View Recycling**: Riutilizzo delle view invece di crearne sempre di nuove

### Prossimi passi

Nella prossima lezione creeremo una **data class Card** per rappresentare le carte come oggetti con più proprietà (valore, seme) invece di semplici stringhe.

---

## Lezione 4: Data Class e Modelli Dati

### Obiettivo
Imparare a usare le data class di Kotlin per creare modelli dati strutturati e type-safe.

### Problema: Usare stringhe semplici

Nella lezione precedente usavamo stringhe per rappresentare le carte:

```kotlin
val cards = listOf("Asso di Cuori", "Re di Picche", ...)
```

**Problemi:**
- ❌ Dati non strutturati: valore e seme sono mischiati
- ❌ Difficile da estendere: come aggiungiamo codice carta, immagine, ecc?
- ❌ Errori facili: "Asso Cuori" o "Asso di Cuori"? Formato inconsistente
- ❌ No type safety: possiamo passare qualsiasi stringa

### Soluzione: Data Class

Le **data class** in Kotlin sono classi speciali per contenere dati strutturati:

```kotlin
data class Card(
    val value: String,
    val suit: String
)
```

**Il compilatore genera automaticamente:**
- `equals()` - Confronta oggetti per valore
- `hashCode()` - Per usarli in Set/Map
- `toString()` - Stampa leggibile dell'oggetto
- `copy()` - Crea copie modificate
- `componentN()` - Per destructuring

### Cosa abbiamo imparato

#### 1. **Creare una data class**

```kotlin
data class Card(
    val value: String,  // "Asso", "Re", "10", ecc.
    val suit: String    // "Cuori", "Picche", ecc.
) {
    // Metodo custom per ottenere il nome completo
    fun getFullName(): String {
        return "$value di $suit"
    }
}
```

**Caratteristiche:**
- `data` keyword: dice al compilatore di generare metodi automaticamente
- Proprietà nel constructor: parametri primari della classe
- `val`: proprietà immutabili (meglio per sicurezza)
- Metodi custom: possiamo aggiungerne altri se serve

#### 2. **Creare istanze**

```kotlin
// Prima (stringhe)
val card = "Asso di Cuori"

// Dopo (data class)
val card = Card("Asso", "Cuori")
val card2 = Card(value = "Re", suit = "Picche")  // Named parameters
```

#### 3. **Metodi generati automaticamente**

```kotlin
val card1 = Card("Asso", "Cuori")
val card2 = Card("Asso", "Cuori")
val card3 = Card("Re", "Picche")

// equals() - confronto per valore
card1 == card2  // true (stessi dati)
card1 == card3  // false (dati diversi)

// toString() - rappresentazione leggibile
println(card1)  // Output: "Card(value=Asso, suit=Cuori)"

// copy() - creare copie modificate
val card4 = card1.copy(suit = "Quadri")  // Card("Asso", "Quadri")

// destructuring - estrarre proprietà
val (value, suit) = card1
println("$value di $suit")  // "Asso di Cuori"
```

#### 4. **Type Safety**

```kotlin
// Prima (stringhe)
fun showCard(card: String) {
    // Cosa contiene? Chi lo sa! 🤷‍♂️
}
showCard("qualsiasi cosa")  // Compila!

// Dopo (data class)
fun showCard(card: Card) {
    // Sicuro! Posso accedere a card.value e card.suit
    println("${card.value} di ${card.suit}")
}
showCard("stringa")  // ERRORE di compilazione! ✅
showCard(Card("Asso", "Cuori"))  // OK! ✅
```

#### 5. **Aggiornamento Adapter**

L'adapter ora lavora con oggetti Card invece di stringhe:

```kotlin
// Prima
class CardAdapter(private var cards: List<String>) { ... }

fun bind(cardName: String) {
    binding.textViewCardName.text = cardName
}

// Dopo
class CardAdapter(private var cards: List<Card>) { ... }

fun bind(card: Card) {
    binding.textViewCardName.text = card.getFullName()
}
```

#### 6. **Aggiornamento ViewModel**

Il ViewModel crea e gestisce oggetti Card:

```kotlin
// Prima
private val _cards = MutableLiveData<List<String>>()

private fun loadCards() {
    val cardList = listOf("Asso di Cuori", "Re di Picche", ...)
    _cards.value = cardList
}

// Dopo
private val _cards = MutableLiveData<List<Card>>()

private fun loadCards() {
    val cardList = listOf(
        Card("Asso", "Cuori"),
        Card("Re", "Picche"),
        ...
    )
    _cards.value = cardList
}
```

### Vantaggi delle Data Class

✅ **Dati strutturati**:
```kotlin
card.value  // "Asso"
card.suit   // "Cuori"
// Invece di parsing di stringhe!
```

✅ **Facile da estendere**:
```kotlin
data class Card(
    val value: String,
    val suit: String,
    val code: String? = null,      // Aggiungi facilmente
    val image: String? = null      // nuove proprietà
)
```

✅ **Type safety**:
```kotlin
// Il compilatore ti aiuta!
val cards: List<Card> = ...  // Solo oggetti Card, nient'altro
```

✅ **Code leggibile**:
```kotlin
Card("Asso", "Cuori")  // Chiaro e conciso
// vs
"Asso di Cuori"  // Formato? Parsing?
```

✅ **Testing facile**:
```kotlin
// Confronto oggetti è semplice
val expected = Card("Asso", "Cuori")
val actual = getCard()
assertEquals(expected, actual)  // Usa equals() generato automaticamente
```

### Quando usare data class?

**✅ Usa data class quando:**
- Hai dati strutturati da rappresentare
- Vuoi type safety
- Devi confrontare oggetti per valore
- I dati verranno da/verso API/Database

**❌ Non usare data class quando:**
- La classe ha logica complessa (usa class normale)
- Servono ereditarietà complesse
- La classe rappresenta comportamento, non dati

### Confronto: Prima vs Dopo

| Aspetto | Stringhe | Data Class |
|---------|----------|------------|
| Struttura | ❌ Nessuna | ✅ value + suit separati |
| Type Safety | ❌ Accetta qualsiasi stringa | ✅ Solo oggetti Card validi |
| Estensibilità | ❌ Difficile | ✅ Aggiungi proprietà facilmente |
| Confronto | ❌ Confronto stringhe | ✅ Confronto per valore automatico |
| Leggibilità | ❌ "Asso di Cuori" | ✅ Card("Asso", "Cuori") |
| Preparazione API | ❌ Devi rifare tutto | ✅ Aggiungi campi API |

### Package Structure

Abbiamo creato un package `models` per organizzare i modelli dati:

```
app/src/main/java/.../
├── models/
│   └── Card.kt           # Data class per carta
├── CardAdapter.kt        # Usa Card
├── MainViewModel.kt      # Crea lista di Card
└── FirstFragment.kt      # Osserva List<Card>
```

**Best practice:**
- Modelli dati in package `models`
- UI in package `ui` (futuro)
- Network in package `network` (futuro)

### Preparazione per il futuro

Questa data class è pronta per l'API! In futuro aggiungeremo:

```kotlin
data class Card(
    val value: String,
    val suit: String,
    val code: String,        // "AS", "KH", ecc. (da API)
    val image: String        // URL immagine (da API)
)
```

Senza riscrivere tutto! 🎉

### Concetti chiave

- **Data class**: Classe Kotlin per contenere dati strutturati
- **Metodi generati**: equals, hashCode, toString, copy, componentN
- **Type safety**: Il compilatore controlla i tipi e previene errori
- **Immutabilità**: Usare `val` per proprietà che non cambiano
- **Separazione responsabilità**: Models per dati, Adapter per UI
- **Package organization**: Struttura codice in package logici

### Prossimi passi

Nella prossima lezione introdurremo **Retrofit** per configurare le chiamate API REST e preparare l'integrazione con l'API Deck of Cards.

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

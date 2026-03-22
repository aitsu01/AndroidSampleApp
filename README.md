# MiniChat AI — App Android didattica

App di chat con un assistente AI che interagisce con le API di OpenRouter.
Progetto sviluppato a fini didattici per il corso Android.

---

## Branch: `minichat/5-openrouter` — Lezione 6

### Obiettivo
Collegare l'app alle API di OpenRouter con coroutine, inviare la cronologia dei messaggi e il system prompt, e mostrare la risposta reale del bot.

### Cosa è stato aggiunto / modificato

- **`network/OpenRouterModels.kt`** — Data class per richiesta (`ChatRequest`, `ChatMessage`) e risposta (`ChatResponse`, `Choice`)
- **`network/OpenRouterService.kt`** — Interfaccia Retrofit con la funzione `suspend` per il POST
- **`network/ApiClient.kt`** — Aggiunto interceptor per l'header `Authorization` e `service: OpenRouterService`
- **`MainViewModel.kt`** — Aggiunta `inviaMessaggio()` con `viewModelScope.launch` + `withContext(Dispatchers.IO)`, aggiunto `caricamento: LiveData<Boolean>`
- **`ChatFragment.kt`** — Chiama `viewModel.inviaMessaggio()`, osserva `caricamento` per disabilitare il bottone

### Concetti introdotti

| Concetto | Dove si vede |
|---|---|
| Coroutine (`viewModelScope.launch`) | `MainViewModel.inviaMessaggio()` |
| `withContext(Dispatchers.IO)` | Sposta la chiamata di rete su thread di background |
| `suspend fun` | `OpenRouterService.inviaMessaggio()` |
| OkHttp Interceptor | `ApiClient` — aggiunge `Authorization` a ogni richiesta |
| `LiveData<Boolean>` caricamento | Disabilita il bottone mentre l'API risponde |

### Flusso completo di una chiamata

```
Utente preme "Invia"
  → ChatFragment chiama viewModel.inviaMessaggio(testo)

MainViewModel
  1. Aggiunge il messaggio utente → RecyclerView si aggiorna
  2. _caricamento = true → bottone disabilitato
  3. viewModelScope.launch {
       withContext(Dispatchers.IO) {
           ApiClient.service.inviaMessaggio(ChatRequest)  ← chiamata HTTP
       }
       Aggiunge risposta bot → RecyclerView si aggiorna
     }
  4. _caricamento = false → bottone riabilitato
```

### Struttura della richiesta API

```json
{
  "model": "openai/gpt-4o-mini",
  "max_tokens": 300,
  "messages": [
    { "role": "system",    "content": "<system prompt da impostazioni>" },
    { "role": "user",      "content": "primo messaggio" },
    { "role": "assistant", "content": "prima risposta" },
    { "role": "user",      "content": "nuovo messaggio" }
  ]
}
```

> **Nota:** prima di testare, sostituire `YOUR_API_KEY_HERE` in `ApiClient.kt` con una chiave valida da [openrouter.ai/keys](https://openrouter.ai/keys).

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

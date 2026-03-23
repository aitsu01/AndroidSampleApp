package it.zakantonio.androidsampleapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.zakantonio.androidsampleapp.model.Message
import it.zakantonio.androidsampleapp.model.TipoMessaggio
import it.zakantonio.androidsampleapp.network.ApiClient
import it.zakantonio.androidsampleapp.network.ChatMessage
import it.zakantonio.androidsampleapp.network.ChatRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// ViewModel condiviso tra ChatFragment e SettingsFragment.
// Contiene i messaggi, le impostazioni e la logica per chiamare l'API.
class MainViewModel : ViewModel() {

    // ── Messaggi ──────────────────────────────────────────────────────────────

    private val _messaggi = MutableLiveData<List<Message>>()
    val messaggi: LiveData<List<Message>> = _messaggi

    private val listaMessaggi = mutableListOf<Message>()

    private fun aggiungiMessaggio(messaggio: Message) {
        listaMessaggi.add(messaggio)
        _messaggi.value = listaMessaggi.toList()
    }

    // ── Stato di caricamento ──────────────────────────────────────────────────

    // true mentre l'app aspetta la risposta dall'API
    private val _caricamento = MutableLiveData<Boolean>(false)
    val caricamento: LiveData<Boolean> = _caricamento

    // ── Impostazioni ──────────────────────────────────────────────────────────

    private val _systemPrompt = MutableLiveData<String>(SYSTEM_PROMPT_DEFAULT)
    val systemPrompt: LiveData<String> = _systemPrompt

    private val _lunghezza = MutableLiveData<Int>(LUNGHEZZA_DEFAULT)
    val lunghezza: LiveData<Int> = _lunghezza

    fun aggiornaSystemPrompt(nuovoPrompt: String) { _systemPrompt.value = nuovoPrompt }
    fun aggiornaLunghezza(nuovaLunghezza: Int) { _lunghezza.value = nuovaLunghezza }

    // ── Chiamata API ──────────────────────────────────────────────────────────

    // Funzione chiamata da ChatFragment quando l'utente preme "Invia".
    // Aggiunge il messaggio utente, chiama l'API e aggiunge la risposta del bot.
    fun inviaMessaggio(testo: String) {
        // 1. Aggiunge subito il messaggio dell'utente alla lista (UI reattiva)
        aggiungiMessaggio(Message(testo, TipoMessaggio.UTENTE))

        _caricamento.value = true

        // 2. viewModelScope.launch avvia una coroutine legata al ciclo di vita del ViewModel.
        //    Se il ViewModel viene distrutto, la coroutine viene cancellata automaticamente.
        viewModelScope.launch {
            try {
                val richiesta = ChatRequest(
                    model = MODELLO,
                    messages = costruisciMessaggiApi(),
                    maxTokens = (lunghezza.value ?: LUNGHEZZA_DEFAULT) * 100
                )

                // 3. withContext(Dispatchers.IO) sposta l'esecuzione su un thread di background.
                //    La chiamata di rete non blocca il thread dell'UI.
                val risposta = withContext(Dispatchers.IO) {
                    ApiClient.service.inviaMessaggio(richiesta)
                }

                // 4. Dopo withContext siamo di nuovo sul thread principale: possiamo aggiornare la UI
                val testoRisposta = risposta.choices.firstOrNull()?.message?.content
                    ?: "Nessuna risposta ricevuta."

                aggiungiMessaggio(Message(testoRisposta, TipoMessaggio.BOT))

            } catch (e: Exception) {
                // In caso di errore (rete assente, API key errata, ecc.) mostra un messaggio di errore
                aggiungiMessaggio(Message("Errore: ${e.message}", TipoMessaggio.BOT))
            } finally {
                // finally viene eseguito sempre, sia in caso di successo che di errore
                _caricamento.value = false
            }
        }
    }

    // Costruisce la lista di messaggi da inviare all'API nel formato richiesto.
    // Include il system prompt come primo messaggio e tutta la cronologia della chat.
    private fun costruisciMessaggiApi(): List<ChatMessage> {
        val messaggiApi = mutableListOf<ChatMessage>()

        // Il system prompt va sempre per primo, con role "system"
        messaggiApi.add(
            ChatMessage(
                role = "system",
                content = systemPrompt.value ?: SYSTEM_PROMPT_DEFAULT
            )
        )

        // Converte ogni messaggio della chat nel formato API
        listaMessaggi.forEach { msg ->
            messaggiApi.add(
                ChatMessage(
                    role = if (msg.tipo == TipoMessaggio.UTENTE) "user" else "assistant",
                    content = msg.testo
                )
            )
        }

        return messaggiApi
    }

    companion object {
        const val SYSTEM_PROMPT_DEFAULT = "Sei un assistente utile e gentile."
        const val LUNGHEZZA_DEFAULT = 3
        const val MODELLO = "stepfun/step-3.5-flash:free"
    }
}

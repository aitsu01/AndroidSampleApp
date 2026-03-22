package it.zakantonio.androidsampleapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import it.zakantonio.androidsampleapp.model.Message
import it.zakantonio.androidsampleapp.model.TipoMessaggio

// ViewModel condiviso tra ChatFragment e SettingsFragment.
// Contiene sia i messaggi della chat sia le impostazioni del bot.
// Sopravvive alle rotazioni dello schermo grazie all'architettura ViewModel.
class MainViewModel : ViewModel() {

    // ── Messaggi ──────────────────────────────────────────────────────────────

    private val _messaggi = MutableLiveData<List<Message>>()
    val messaggi: LiveData<List<Message>> = _messaggi

    private val listaMessaggi = mutableListOf<Message>()

    init {
        // Dati hardcoded per la demo — saranno rimossi nella lezione delle API
        listaMessaggi.addAll(
            listOf(
                Message("Ciao! Come posso aiutarti?", TipoMessaggio.BOT),
                Message("Qual è la capitale della Francia?", TipoMessaggio.UTENTE),
                Message("La capitale della Francia è Parigi.", TipoMessaggio.BOT),
                Message("Grazie mille!", TipoMessaggio.UTENTE),
                Message("Prego! Hai altre domande?", TipoMessaggio.BOT)
            )
        )
        _messaggi.value = listaMessaggi.toList()
    }

    fun aggiungiMessaggio(messaggio: Message) {
        listaMessaggi.add(messaggio)
        _messaggi.value = listaMessaggi.toList()
    }

    // ── Impostazioni ──────────────────────────────────────────────────────────
    // Queste proprietà sono scritte da SettingsFragment (tramite SharedPreferences)
    // e saranno lette da ChatFragment nella Lezione 6 per costruire la richiesta API.

    // System prompt: istruzioni iniziali che definiscono il comportamento del bot
    private val _systemPrompt = MutableLiveData<String>(SYSTEM_PROMPT_DEFAULT)
    val systemPrompt: LiveData<String> = _systemPrompt

    // Lunghezza risposta: valore da 1 (breve) a 5 (molto lunga)
    private val _lunghezza = MutableLiveData<Int>(LUNGHEZZA_DEFAULT)
    val lunghezza: LiveData<Int> = _lunghezza

    fun aggiornaSystemPrompt(nuovoPrompt: String) {
        _systemPrompt.value = nuovoPrompt
    }

    fun aggiornaLunghezza(nuovaLunghezza: Int) {
        _lunghezza.value = nuovaLunghezza
    }

    companion object {
        const val SYSTEM_PROMPT_DEFAULT = "Sei un assistente utile e gentile."
        const val LUNGHEZZA_DEFAULT = 3
    }
}

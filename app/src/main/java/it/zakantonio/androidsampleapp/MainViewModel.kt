package it.zakantonio.androidsampleapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import it.zakantonio.androidsampleapp.model.Message
import it.zakantonio.androidsampleapp.model.TipoMessaggio

// ViewModel condiviso tra ChatFragment e SettingsFragment.
// Contiene la lista dei messaggi della chat e la logica per aggiungerli.
// Sopravvive alle rotazioni dello schermo grazie all'architettura ViewModel.
class MainViewModel : ViewModel() {

    // _messaggi è privato e modificabile solo dall'interno del ViewModel
    private val _messaggi = MutableLiveData<List<Message>>()

    // messaggi è pubblico e osservabile dai fragment (sola lettura)
    val messaggi: LiveData<List<Message>> = _messaggi

    // Lista interna mutabile che accumula i messaggi
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
        // Pubblica la lista iniziale
        _messaggi.value = listaMessaggi.toList()
    }

    // Aggiunge un messaggio alla lista e notifica gli osservatori
    fun aggiungiMessaggio(messaggio: Message) {
        listaMessaggi.add(messaggio)
        _messaggi.value = listaMessaggi.toList()
    }
}

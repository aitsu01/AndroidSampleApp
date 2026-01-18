package it.zakantonio.androidsampleapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import it.zakantonio.androidsampleapp.models.Card

/**
 * ViewModel per gestire lo stato UI del FirstFragment.
 *
 * Il ViewModel serve a:
 * - Separare la logica di business dall'UI
 * - Mantenere i dati durante i cambi di configurazione (es. rotazione schermo)
 * - Esporre i dati all'UI tramite LiveData (pattern Observable)
 *
 * Importante: Il ViewModel sopravvive ai cambi di configurazione,
 * mentre il Fragment viene distrutto e ricreato.
 */
class MainViewModel : ViewModel() {

    // LiveData per il messaggio di benvenuto
    private val _welcomeMessage = MutableLiveData<String>()
    val welcomeMessage: LiveData<String> = _welcomeMessage

    // LiveData per la lista di carte
    // In questa lezione usiamo oggetti Card con proprietà value e suit
    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> = _cards

    init {
        // Inizializziamo la lista con alcune carte di esempio
        // Questa lista statica simula i dati che in futuro arriveranno dall'API
        loadCards()
    }

    /**
     * Elabora il nome del giocatore e aggiorna il messaggio di benvenuto.
     *
     * Questa logica è ora nel ViewModel invece che nel Fragment,
     * così è più facile da testare e riutilizzare.
     *
     * @param playerName nome inserito dall'utente
     * @param welcomeTemplate template del messaggio di benvenuto (dalla stringa resource)
     * @param emptyMessage messaggio da mostrare se il campo è vuoto
     */
    fun onStartGameClicked(playerName: String, welcomeTemplate: String, emptyMessage: String) {
        if (playerName.isNotBlank()) {
            // Se il nome è valido, creiamo il messaggio personalizzato
            val message = String.format(welcomeTemplate, playerName)
            _welcomeMessage.value = message
        } else {
            // Se il campo è vuoto, mostriamo il messaggio di errore
            _welcomeMessage.value = emptyMessage
        }
    }

    /**
     * Carica la lista di carte di esempio.
     *
     * Ora creiamo oggetti Card con value, suit e icon separati.
     * L'icon è una risorsa Drawable che rappresenta visualmente il seme.
     * Nelle prossime lezioni, queste carte arriveranno dall'API.
     */
    private fun loadCards() {
        val cardList = listOf(
            Card("Asso", "Cuori", R.drawable.ic_hearts),
            Card("Re", "Picche", R.drawable.ic_spades),
            Card("Regina", "Quadri", R.drawable.ic_diamond),
            Card("Jack", "Fiori", R.drawable.ic_clubs),
            Card("10", "Cuori", R.drawable.ic_hearts),
            Card("9", "Picche", R.drawable.ic_spades),
            Card("8", "Quadri", R.drawable.ic_diamond),
            Card("7", "Fiori", R.drawable.ic_clubs),
            Card("6", "Cuori", R.drawable.ic_hearts),
            Card("5", "Picche", R.drawable.ic_spades),
            Card("4", "Quadri", R.drawable.ic_diamond),
            Card("3", "Fiori", R.drawable.ic_clubs),
            Card("2", "Cuori", R.drawable.ic_hearts)
        )
        _cards.value = cardList
    }
}

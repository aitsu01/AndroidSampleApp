package it.zakantonio.androidsampleapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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

    // MutableLiveData: può essere modificato internamente al ViewModel
    private val _welcomeMessage = MutableLiveData<String>()

    // LiveData: esposto al Fragment in sola lettura (immutabile dall'esterno)
    val welcomeMessage: LiveData<String> = _welcomeMessage

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
}

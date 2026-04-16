package it.zakantonio.androidsampleapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.zakantonio.androidsampleapp.model.Character
import it.zakantonio.androidsampleapp.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// ViewModel condiviso tra CharacterListFragment e CharacterDetailFragment.
// Contiene i dati dei personaggi e la logica per chiamare l'API.
class MainViewModel : ViewModel() {

    // ── Lista personaggi ──────────────────────────────────────────────────────

    private val _personaggi = MutableLiveData<List<Character>>()
    val personaggi: LiveData<List<Character>> = _personaggi

    // ── Personaggio selezionato (dettaglio) ───────────────────────────────────

    private val _personaggioSelezionato = MutableLiveData<Character?>()
    val personaggioSelezionato: LiveData<Character?> = _personaggioSelezionato

    // ── Stato di caricamento ──────────────────────────────────────────────────

    // true mentre l'app aspetta la risposta dall'API
    private val _caricamento = MutableLiveData<Boolean>(false)
    val caricamento: LiveData<Boolean> = _caricamento

    // ── Errori ────────────────────────────────────────────────────────────────

    private val _errore = MutableLiveData<String?>()
    val errore: LiveData<String?> = _errore

    // ── Chiamate API ──────────────────────────────────────────────────────────

    // Carica la lista dei personaggi dalla prima pagina dell'API.
    // Chiamata da CharacterListFragment al suo avvio.
    fun caricaPersonaggi() {
        viewModelScope.launch {
            _caricamento.value = true
            try {
                val risposta = withContext(Dispatchers.IO) {
                    ApiClient.service.getCharacters()
                }
                _personaggi.value = risposta.items
            } catch (e: Exception) {
                _errore.value = e.message
            } finally {
                // finally viene eseguito sempre, sia in caso di successo che di errore
                _caricamento.value = false
            }
        }
    }

    // Carica il dettaglio di un singolo personaggio per ID.
    // Chiamata da CharacterDetailFragment.
    fun caricaDettaglio(id: Int) {
        viewModelScope.launch {
            _caricamento.value = true
            try {
                val personaggio = withContext(Dispatchers.IO) {
                    ApiClient.service.getCharacter(id)
                }
                _personaggioSelezionato.value = personaggio
            } catch (e: Exception) {
                _errore.value = e.message
            } finally {
                _caricamento.value = false
            }
        }
    }

    fun resetDettaglio() {
        _personaggioSelezionato.value = null
    }
}

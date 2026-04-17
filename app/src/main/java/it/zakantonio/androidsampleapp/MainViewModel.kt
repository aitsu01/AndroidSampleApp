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

class MainViewModel : ViewModel() {

    private val _personaggi = MutableLiveData<List<Character>>()
    val personaggi: LiveData<List<Character>> = _personaggi

    private val _personaggioSelezionato = MutableLiveData<Character?>()
    val personaggioSelezionato: LiveData<Character?> = _personaggioSelezionato

    private val _caricamento = MutableLiveData(false)
    val caricamento: LiveData<Boolean> = _caricamento

    private val _errore = MutableLiveData<String?>()
    val errore: LiveData<String?> = _errore

    private var listaCompleta: List<Character> = emptyList()
    private var ordinamentoCorrente: String = "A-Z"
    private var filtroRazzaCorrente: String = "Tutti"

    fun caricaPersonaggi() {
        viewModelScope.launch {
            _caricamento.value = true
            try {
                val risposta = withContext(Dispatchers.IO) {
                    ApiClient.service.getCharacters()
                }
                listaCompleta = risposta.items
                applicaOrdinamento()
            } catch (e: Exception) {
                _errore.value = e.message
            } finally {
                _caricamento.value = false
            }
        }
    }

    fun aggiornaFiltroRazza(filtro: String) {
        filtroRazzaCorrente = filtro

        viewModelScope.launch {
            _caricamento.value = true
            try {
                if (filtroRazzaCorrente == "Saiyan") {
                    listaCompleta = withContext(Dispatchers.IO) {
                        ApiClient.service.getCharactersByRace("Saiyan")
                    }
                } else if (filtroRazzaCorrente == "Android") {
                    listaCompleta = withContext(Dispatchers.IO) {
                        ApiClient.service.getCharactersByRace("Android")
                    }
                } else {
                    val risposta = withContext(Dispatchers.IO) {
                        ApiClient.service.getCharacters()
                    }
                    listaCompleta = risposta.items
                }

                applicaOrdinamento()

            } catch (e: Exception) {
                _errore.value = e.message
            } finally {
                _caricamento.value = false
            }
        }
    }

    fun aggiornaRicercaNome(nome: String) {
    viewModelScope.launch {
        _caricamento.value = true
        try {
            if (nome.isBlank()) {
                if (filtroRazzaCorrente == "Saiyan") {
                    listaCompleta = withContext(Dispatchers.IO) {
                        ApiClient.service.getCharactersByRace("Saiyan")
                    }
                } else if (filtroRazzaCorrente == "Android") {
                    listaCompleta = withContext(Dispatchers.IO) {
                        ApiClient.service.getCharactersByRace("Android")
                    }
                } else {
                    val risposta = withContext(Dispatchers.IO) {
                        ApiClient.service.getCharacters()
                    }
                    listaCompleta = risposta.items
                }
            } else {
                listaCompleta = withContext(Dispatchers.IO) {
                    ApiClient.service.getCharactersByName(nome)
                }
            }

            applicaOrdinamento()

        } catch (e: Exception) {
            _errore.value = e.message
        } finally {
            _caricamento.value = false
        }
    }
}



    fun aggiornaOrdinamento(ordinamento: String) {
        ordinamentoCorrente = ordinamento
        applicaOrdinamento()
    }

    private fun applicaOrdinamento() {
        val listaOrdinata = when (ordinamentoCorrente) {
            "Z-A" -> listaCompleta.sortedByDescending { personaggio -> personaggio.name.lowercase() }
            else -> listaCompleta.sortedBy { personaggio -> personaggio.name.lowercase() }
        }
        _personaggi.value = listaOrdinata
    }

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
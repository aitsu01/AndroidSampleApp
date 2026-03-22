package it.zakantonio.androidsampleapp

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import it.zakantonio.androidsampleapp.core.BaseFragment
import it.zakantonio.androidsampleapp.databinding.FragmentSettingsBinding

// Fragment che gestisce la schermata delle impostazioni.
// Le preferenze vengono salvate con SharedPreferences: persistono anche dopo la chiusura dell'app.
class SettingsFragment : BaseFragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    // Chiavi usate per leggere e scrivere le preferenze nel file SharedPreferences
    companion object {
        const val PREFS_NAME = "minichat_prefs"
        const val KEY_SYSTEM_PROMPT = "system_prompt"
        const val KEY_LUNGHEZZA = "lunghezza"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        caricaPreferenze()
        impostaListenerSystemPrompt()
        impostaListenerLunghezza()
    }

    // Legge i valori salvati da SharedPreferences e aggiorna sia la UI sia il ViewModel
    private fun caricaPreferenze() {
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Carica il system prompt salvato (o usa il default se non è mai stato salvato)
        val promptSalvato = prefs.getString(KEY_SYSTEM_PROMPT, MainViewModel.SYSTEM_PROMPT_DEFAULT)!!
        binding.campoSystemPrompt.setText(promptSalvato)

        // Carica la lunghezza salvata (o usa il default)
        val lunghezzaSalvata = prefs.getInt(KEY_LUNGHEZZA, MainViewModel.LUNGHEZZA_DEFAULT)
        binding.sliderLunghezza.value = lunghezzaSalvata.toFloat()
        binding.testoLunghezza.text = descrizioneSlider(lunghezzaSalvata)

        // Sincronizza il ViewModel con i valori caricati
        viewModel.aggiornaSystemPrompt(promptSalvato)
        viewModel.aggiornaLunghezza(lunghezzaSalvata)
    }

    // TextWatcher ascolta ogni modifica al testo e salva in SharedPreferences
    private fun impostaListenerSystemPrompt() {
        binding.campoSystemPrompt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            // afterTextChanged viene chiamato ogni volta che il testo cambia
            override fun afterTextChanged(s: Editable?) {
                val testo = s.toString()
                salvaPreferenza(KEY_SYSTEM_PROMPT, testo)
                viewModel.aggiornaSystemPrompt(testo)
            }
        })
    }

    // Ascolta i cambiamenti dello Slider e salva il valore in SharedPreferences
    private fun impostaListenerLunghezza() {
        binding.sliderLunghezza.addOnChangeListener { _, value, _ ->
            val valore = value.toInt()
            binding.testoLunghezza.text = descrizioneSlider(valore)
            salvaPreferenza(KEY_LUNGHEZZA, valore)
            viewModel.aggiornaLunghezza(valore)
        }
    }

    // Converte il valore numerico dello slider in un'etichetta leggibile
    private fun descrizioneSlider(valore: Int): String {
        return when (valore) {
            1    -> "Breve"
            2    -> "Corta"
            3    -> "Media"
            4    -> "Lunga"
            5    -> "Molto lunga"
            else -> "Media"
        }
    }

    // Funzioni di supporto per scrivere in SharedPreferences
    // Due versioni dello stesso metodo (overload) per String e Int
    private fun salvaPreferenza(chiave: String, valore: String) {
        requireContext()
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(chiave, valore)
            .apply() // apply() è asincrono e non blocca l'UI, a differenza di commit()
    }

    private fun salvaPreferenza(chiave: String, valore: Int) {
        requireContext()
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putInt(chiave, valore)
            .apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

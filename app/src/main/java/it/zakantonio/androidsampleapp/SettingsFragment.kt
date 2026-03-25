package it.zakantonio.androidsampleapp

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import it.zakantonio.androidsampleapp.core.BaseFragment
import it.zakantonio.androidsampleapp.databinding.FragmentSettingsBinding

// Fragment che gestisce la schermata delle impostazioni.
// Le preferenze vengono salvate con SharedPreferences: persistono anche dopo la chiusura dell'app.
class SettingsFragment : BaseFragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val PREFS_NAME = "minichat_prefs"
        const val KEY_SYSTEM_PROMPT = "system_prompt"
        const val KEY_LUNGHEZZA = "lunghezza"
        const val KEY_MODALITA_SCURA = "modalita_scura"
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
        impostaListenerModalitaScura()
    }

    private fun caricaPreferenze() {
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val promptSalvato = prefs.getString(KEY_SYSTEM_PROMPT, MainViewModel.SYSTEM_PROMPT_DEFAULT)!!
        binding.campoSystemPrompt.setText(promptSalvato)

        val lunghezzaSalvata = prefs.getInt(KEY_LUNGHEZZA, MainViewModel.LUNGHEZZA_DEFAULT)
        binding.sliderLunghezza.value = lunghezzaSalvata.toFloat()
        binding.testoLunghezza.text = descrizioneSlider(lunghezzaSalvata)

        // Riflette nella UI lo stato attuale dello switch senza attivare il listener.
        // setOnCheckedChangeListener va impostato DOPO questa riga per evitare
        // che il ripristino del valore triggeri inutilmente il cambio tema.
        val modalitaScura = prefs.getBoolean(KEY_MODALITA_SCURA, false)
        binding.switchModalitaScura.isChecked = modalitaScura

        viewModel.aggiornaSystemPrompt(promptSalvato)
        viewModel.aggiornaLunghezza(lunghezzaSalvata)
    }

    private fun impostaListenerSystemPrompt() {
        binding.campoSystemPrompt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val testo = s.toString()
                salvaPreferenza(KEY_SYSTEM_PROMPT, testo)
                viewModel.aggiornaSystemPrompt(testo)
            }
        })
    }

    private fun impostaListenerLunghezza() {
        binding.sliderLunghezza.addOnChangeListener { _, value, _ ->
            val valore = value.toInt()
            binding.testoLunghezza.text = descrizioneSlider(valore)
            salvaPreferenza(KEY_LUNGHEZZA, valore)
            viewModel.aggiornaLunghezza(valore)
        }
    }

    // Cambia il tema dell'intera app a runtime.
    // AppCompatDelegate.setDefaultNightMode() è il metodo ufficiale AndroidX:
    // forza il tema scuro o chiaro indipendentemente dalle impostazioni di sistema.
    // Il cambio provoca il recreate() dell'Activity — comportamento normale e atteso.
    private fun impostaListenerModalitaScura() {
        binding.switchModalitaScura.setOnCheckedChangeListener { _, isChecked ->
            salvaPreferenza(KEY_MODALITA_SCURA, isChecked)

            val modalita = if (isChecked) {
                AppCompatDelegate.MODE_NIGHT_YES  // forza dark
            } else {
                AppCompatDelegate.MODE_NIGHT_NO   // forza light
            }
            AppCompatDelegate.setDefaultNightMode(modalita)
            // Dopo questa riga l'Activity si riavvia automaticamente
            // per applicare il nuovo tema — non serve fare altro.
        }
    }

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

    private fun salvaPreferenza(chiave: String, valore: String) {
        requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().putString(chiave, valore).apply()
    }

    private fun salvaPreferenza(chiave: String, valore: Int) {
        requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().putInt(chiave, valore).apply()
    }

    private fun salvaPreferenza(chiave: String, valore: Boolean) {
        requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().putBoolean(chiave, valore).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

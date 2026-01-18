package it.zakantonio.androidsampleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import it.zakantonio.androidsampleapp.core.BaseFragment
import it.zakantonio.androidsampleapp.databinding.FragmentFirstBinding

/**
 * Fragment principale che dimostra l'uso di ViewModel e LiveData.
 *
 * In questa lezione impariamo a:
 * - Usare ViewModel per separare logica UI dai dati
 * - Usare LiveData per osservare cambiamenti nei dati
 * - Pattern MVVM (Model-View-ViewModel)
 * - Gestire il ciclo di vita in modo corretto
 */
class FirstFragment : BaseFragment() {

    // ViewBinding per accedere alle view del layout
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    // ViewModel: gestisce lo stato e la logica, sopravvive ai cambi di configurazione
    // Il delegate 'by viewModels()' crea automaticamente il ViewModel
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Carichiamo il layout usando ViewBinding
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Osserviamo il LiveData del ViewModel
        // Ogni volta che il valore cambia, il lambda viene eseguito automaticamente
        viewModel.welcomeMessage.observe(viewLifecycleOwner) { message ->
            // Aggiorniamo la TextView con il nuovo messaggio
            binding.textViewWelcome.text = message
        }

        // Impostiamo il listener per il click del bottone
        binding.buttonStartGame.setOnClickListener {
            // Leggiamo il testo dall'EditText
            val playerName = binding.editTextPlayerName.text.toString()

            // Deleghiamo la logica al ViewModel
            // Il ViewModel elabora i dati e aggiorna il LiveData
            viewModel.onStartGameClicked(
                playerName = playerName,
                welcomeTemplate = getString(R.string.welcome_message),
                emptyMessage = getString(R.string.insert_name_message)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Puliamo il binding per evitare memory leak
        _binding = null
    }
}
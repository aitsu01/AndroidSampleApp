package it.zakantonio.androidsampleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import it.zakantonio.androidsampleapp.core.BaseFragment
import it.zakantonio.androidsampleapp.databinding.FragmentFirstBinding

/**
 * Fragment principale che dimostra l'uso di EditText, Button e TextView.
 *
 * In questa lezione impariamo a:
 * - Usare ViewBinding per accedere alle view
 * - Gestire il click di un Button
 * - Leggere il testo da un EditText
 * - Aggiornare il contenuto di una TextView
 */
class FirstFragment : BaseFragment() {

    // ViewBinding per accedere alle view del layout
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

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

        // Impostiamo il listener per il click del bottone
        binding.buttonStartGame.setOnClickListener {
            // Leggiamo il testo inserito dall'utente nell'EditText
            val playerName = binding.editTextPlayerName.text.toString()

            // Verifichiamo che il nome non sia vuoto
            if (playerName.isNotBlank()) {
                // Creiamo il messaggio di benvenuto usando la stringa formattata
                val welcomeMessage = getString(R.string.welcome_message, playerName)

                // Aggiorniamo il TextView con il messaggio
                binding.textViewWelcome.text = welcomeMessage
            } else {
                // Se il campo è vuoto, mostriamo un messaggio di errore
                binding.textViewWelcome.text = getString(R.string.insert_name_message)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Puliamo il binding per evitare memory leak
        _binding = null
    }
}
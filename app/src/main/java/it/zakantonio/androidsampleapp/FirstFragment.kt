package it.zakantonio.androidsampleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import it.zakantonio.androidsampleapp.core.BaseFragment
import it.zakantonio.androidsampleapp.databinding.FragmentFirstBinding

/**
 * Fragment principale che dimostra l'uso di RecyclerView con ViewModel e LiveData.
 *
 * In questa lezione impariamo a:
 * - Usare RecyclerView per mostrare liste scrollabili
 * - Creare un Adapter personalizzato
 * - Osservare liste di dati con LiveData
 * - Gestire il LayoutManager per definire come gli item sono disposti
 */
class FirstFragment : BaseFragment() {

    // ViewBinding per accedere alle view del layout
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    // ViewModel: gestisce lo stato e la logica, sopravvive ai cambi di configurazione
    private val viewModel: MainViewModel by viewModels()

    // Adapter per la RecyclerView
    private lateinit var cardAdapter: CardAdapter

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

        // Configuriamo la RecyclerView
        setupRecyclerView()

        // Osserviamo il LiveData del messaggio di benvenuto
        viewModel.welcomeMessage.observe(viewLifecycleOwner) { message ->
            binding.textViewWelcome.text = message
        }

        // Osserviamo il LiveData della lista di carte
        // Quando la lista cambia, aggiorniamo l'adapter
        viewModel.cards.observe(viewLifecycleOwner) { cards ->
            cardAdapter.updateCards(cards)
        }

        // Impostiamo il listener per il click del bottone
        binding.buttonStartGame.setOnClickListener {
            val playerName = binding.editTextPlayerName.text.toString()
            viewModel.onStartGameClicked(
                playerName = playerName,
                welcomeTemplate = getString(R.string.welcome_message),
                emptyMessage = getString(R.string.insert_name_message)
            )
        }
    }

    /**
     * Configura la RecyclerView con adapter e layout manager.
     *
     * Il LayoutManager definisce come gli item sono disposti:
     * - LinearLayoutManager: lista verticale o orizzontale
     * - GridLayoutManager: griglia
     * - StaggeredGridLayoutManager: griglia con altezze variabili
     */
    private fun setupRecyclerView() {
        // Creiamo l'adapter
        cardAdapter = CardAdapter()

        // Configuriamo la RecyclerView
        binding.recyclerViewCards.apply {
            // Impostiamo l'adapter
            adapter = cardAdapter

            // Impostiamo il LayoutManager (lista verticale)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Puliamo il binding per evitare memory leak
        _binding = null
    }
}
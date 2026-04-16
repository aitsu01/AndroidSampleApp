package it.zakantonio.androidsampleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import it.zakantonio.androidsampleapp.core.BaseFragment
import it.zakantonio.androidsampleapp.databinding.FragmentCharacterDetailBinding
import it.zakantonio.androidsampleapp.model.Character

// Fragment che mostra il dettaglio di un personaggio Dragon Ball.
// Riceve l'ID del personaggio tramite arguments e carica i dati dal ViewModel.
class CharacterDetailFragment : BaseFragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_ID = "character_id"

        // Factory method: crea un'istanza con l'ID già inserito negli arguments
        fun newInstance(id: Int): CharacterDetailFragment {
            return CharacterDetailFragment().apply {
                arguments = Bundle().apply { putInt(ARG_ID, id) }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mostra il tasto "indietro" nella toolbar
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Svuoto subito il titolo della toolbar
        (activity as AppCompatActivity).supportActionBar?.title = null

        val id = arguments?.getInt(ARG_ID) ?: return
        viewModel.caricaDettaglio(id)

        viewModel.personaggioSelezionato.observe(viewLifecycleOwner) { personaggio ->
            impostaDettaglio(personaggio)
        }

        viewModel.caricamento.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    fun impostaDettaglio(personaggio: Character?) {
        if (personaggio == null) {
            // resetto la view
            (activity as AppCompatActivity).supportActionBar?.title = null
            binding.textNome.text = null
            binding.textDescrizione.text = null
            return
        }

        // Aggiorna il titolo della toolbar con il nome del personaggio
        (activity as AppCompatActivity).supportActionBar?.title = personaggio.name
        binding.textNome.text = personaggio.name
        binding.textDescrizione.text = personaggio.description
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.resetDettaglio()
    }
}

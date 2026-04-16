package it.zakantonio.androidsampleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.zakantonio.androidsampleapp.core.BaseFragment
import it.zakantonio.androidsampleapp.databinding.FragmentCharacterListBinding

// Fragment che mostra la lista dei personaggi Dragon Ball.
// Contiene un campo di ricerca (da implementare) e una RecyclerView.
class CharacterListFragment : BaseFragment() {

    // ViewModel condiviso con CharacterDetailFragment
    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    // Al tap su un personaggio, chiede a MainActivity di aprire il dettaglio
    private val adapter = CharacterAdapter { personaggio ->
        (activity as MainActivity).apriDettaglio(personaggio.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.titolo_lista)
            setDisplayHomeAsUpEnabled(false)
        }

        binding.recyclerPersonaggi.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerPersonaggi.adapter = adapter

        // Osserva la lista: ogni volta che cambia, aggiorna l'adapter
        viewModel.personaggi.observe(viewLifecycleOwner) { lista ->
            adapter.submitList(lista)
        }

        // Mostra o nasconde la ProgressBar durante il caricamento
        viewModel.caricamento.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Avvia il caricamento della lista dall'API
        viewModel.caricaPersonaggi()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Rilascia il binding per evitare memory leak
        _binding = null
    }
}

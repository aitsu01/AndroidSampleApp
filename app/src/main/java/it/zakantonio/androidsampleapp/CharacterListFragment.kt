package it.zakantonio.androidsampleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import it.zakantonio.androidsampleapp.core.BaseFragment
import it.zakantonio.androidsampleapp.databinding.FragmentCharacterListBinding

class CharacterListFragment : BaseFragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

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

        binding.tastoTutti.setOnClickListener {
            viewModel.aggiornaFiltroRazza("Tutti")
        }

        binding.tastoSayan.setOnClickListener {
            viewModel.aggiornaFiltroRazza("Saiyan")
        }

        binding.tastoAndroid.setOnClickListener {
            viewModel.aggiornaFiltroRazza("Android")
        }

        binding.buttonOrdinaAZ.setOnClickListener {
            viewModel.aggiornaOrdinamento("A-Z")
        }

        binding.buttonOrdinaZA.setOnClickListener {
            viewModel.aggiornaOrdinamento("Z-A")
        }

        binding.campoRicerca.doOnTextChanged { text, _, _, _ ->
            viewModel.aggiornaRicercaNome(text?.toString().orEmpty())
        }

        viewModel.personaggi.observe(viewLifecycleOwner) { lista ->
            adapter.submitList(lista)
        }

        viewModel.caricamento.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.caricaPersonaggi()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
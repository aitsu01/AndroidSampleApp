package it.zakantonio.androidsampleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import it.zakantonio.androidsampleapp.core.BaseFragment
import it.zakantonio.androidsampleapp.databinding.FragmentCharacterDetailBinding
import it.zakantonio.androidsampleapp.model.Character

class CharacterDetailFragment : BaseFragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private val transformationAdapter = TransformationAdapter()

    companion object {
        private const val ARG_ID = "character_id"

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

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = null

        binding.recyclerTrasformazioni.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerTrasformazioni.adapter = transformationAdapter

        val id = arguments?.getInt(ARG_ID) ?: return
        viewModel.caricaDettaglio(id)

        viewModel.personaggioSelezionato.observe(viewLifecycleOwner) { personaggio ->
            impostaDettaglio(personaggio)
        }

        viewModel.caricamento.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun impostaDettaglio(personaggio: Character?) {
        if (personaggio == null) {
            (activity as AppCompatActivity).supportActionBar?.title = null
            binding.textNome.text = null
            binding.textRazza.text = null
            binding.textGenere.text = null
            binding.textKi.text = null
            binding.textMaxKi.text = null
            binding.textAffiliazione.text = null
            binding.textDescrizione.text = null
            binding.imagePersonaggio.setImageDrawable(null)

            transformationAdapter.submitList(emptyList())
            binding.textTitoloTrasformazioni.visibility = View.GONE
            binding.recyclerTrasformazioni.visibility = View.GONE
            return
        }

        (activity as AppCompatActivity).supportActionBar?.title = personaggio.name

        binding.textNome.text = personaggio.name
        binding.textRazza.text = "Razza: ${personaggio.race}"
        binding.textGenere.text = "Genere: ${personaggio.gender}"
        binding.textKi.text = "Ki: ${personaggio.ki}"
        binding.textMaxKi.text = "Max Ki: ${personaggio.maxKi}"
        binding.textAffiliazione.text = "Affiliazione: ${personaggio.affiliation}"
        binding.textDescrizione.text = personaggio.description

        binding.imagePersonaggio.load(personaggio.image)

        transformationAdapter.submitList(personaggio.transformations)

        if (personaggio.transformations.isEmpty()) {
            binding.textTitoloTrasformazioni.visibility = View.GONE
            binding.recyclerTrasformazioni.visibility = View.GONE
        } else {
            binding.textTitoloTrasformazioni.visibility = View.VISIBLE
            binding.recyclerTrasformazioni.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.resetDettaglio()
    }
}
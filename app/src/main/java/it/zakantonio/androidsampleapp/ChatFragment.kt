package it.zakantonio.androidsampleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import it.zakantonio.androidsampleapp.core.BaseFragment
import it.zakantonio.androidsampleapp.databinding.FragmentChatBinding

// Fragment che gestisce la schermata principale della chat.
// Osserva il ViewModel per aggiornare la lista dei messaggi.
class ChatFragment : BaseFragment() {

    // ViewModel condiviso con SettingsFragment tramite activityViewModels()
    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        impostaRecyclerView()
    }

    private fun impostaRecyclerView() {
        // LinearLayoutManager dispone gli item in verticale, uno sotto l'altro
        val layoutManager = LinearLayoutManager(requireContext())

        // stackFromEnd = true: la lista parte dal basso, come nelle app di chat
        layoutManager.stackFromEnd = true

        binding.recyclerMessaggi.layoutManager = layoutManager

        // Osserva la lista messaggi nel ViewModel.
        // Ogni volta che la lista cambia, l'adapter viene aggiornato con i nuovi dati.
        viewModel.messaggi.observe(viewLifecycleOwner) { messaggi ->
            binding.recyclerMessaggi.adapter = ChatAdapter(messaggi)
            // Scrolla sempre all'ultimo messaggio quando la lista si aggiorna
            if (messaggi.isNotEmpty()) {
                binding.recyclerMessaggi.scrollToPosition(messaggi.size - 1)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

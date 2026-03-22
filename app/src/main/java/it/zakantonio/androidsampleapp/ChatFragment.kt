package it.zakantonio.androidsampleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import it.zakantonio.androidsampleapp.core.BaseFragment
import it.zakantonio.androidsampleapp.databinding.FragmentChatBinding
import it.zakantonio.androidsampleapp.model.Message
import it.zakantonio.androidsampleapp.model.TipoMessaggio

// Fragment che gestisce la schermata principale della chat.
// Gestisce i due eventi principali: click su "Invia" e longClick sui messaggi bot.
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
        impostaBottoneInvia()
    }

    private fun impostaRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.stackFromEnd = true
        binding.recyclerMessaggi.layoutManager = layoutManager

        viewModel.messaggi.observe(viewLifecycleOwner) { messaggi ->
            // Passa il callback onLongClickBot all'adapter.
            // Il fragment decide cosa fare (mostrare il Toast), l'adapter si limita a segnalare l'evento.
            binding.recyclerMessaggi.adapter = ChatAdapter(messaggi) { messaggio ->
                mostraToastCondividi(messaggio)
            }
            if (messaggi.isNotEmpty()) {
                binding.recyclerMessaggi.scrollToPosition(messaggi.size - 1)
            }
        }
    }

    // Evento 1 — Click: il bottone "Invia" aggiunge un messaggio utente alla chat
    private fun impostaBottoneInvia() {
        binding.bottoneInvia.setOnClickListener {
            val testo = binding.campoTesto.text.toString().trim()

            // Ignora il tap se il campo è vuoto
            if (testo.isEmpty()) return@setOnClickListener

            // Aggiunge il messaggio dell'utente tramite il ViewModel
            viewModel.aggiungiMessaggio(Message(testo, TipoMessaggio.UTENTE))

            // Pulisce il campo di testo dopo l'invio
            binding.campoTesto.text.clear()
        }
    }

    // Evento 2 — LongClick: mostra un Toast con "Condividi!" (per ora)
    // Nella Lezione 5 questo Toast sarà sostituito da un vero Intent di condivisione
    private fun mostraToastCondividi(messaggio: Message) {
        Toast.makeText(requireContext(), "Condividi!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

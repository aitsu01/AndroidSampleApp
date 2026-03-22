package it.zakantonio.androidsampleapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import it.zakantonio.androidsampleapp.core.BaseFragment
import it.zakantonio.androidsampleapp.databinding.FragmentChatBinding
import it.zakantonio.androidsampleapp.model.Message

// Fragment che gestisce la schermata principale della chat.
class ChatFragment : BaseFragment() {

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
        osservaCaricamento()
    }

    private fun impostaRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.stackFromEnd = true
        binding.recyclerMessaggi.layoutManager = layoutManager

        viewModel.messaggi.observe(viewLifecycleOwner) { messaggi ->
            binding.recyclerMessaggi.adapter = ChatAdapter(messaggi) { messaggio ->
                condividiMessaggio(messaggio)
            }
            if (messaggi.isNotEmpty()) {
                binding.recyclerMessaggi.scrollToPosition(messaggi.size - 1)
            }
        }
    }

    // Evento 1 — Click: delega al ViewModel che aggiunge il messaggio E chiama l'API
    private fun impostaBottoneInvia() {
        binding.bottoneInvia.setOnClickListener {
            val testo = binding.campoTesto.text.toString().trim()
            if (testo.isEmpty()) return@setOnClickListener

            viewModel.inviaMessaggio(testo)
            binding.campoTesto.text.clear()
        }
    }

    // Osserva lo stato di caricamento: disabilita il bottone mentre l'API risponde
    // così l'utente non può inviare più messaggi contemporaneamente
    private fun osservaCaricamento() {
        viewModel.caricamento.observe(viewLifecycleOwner) { staCaricando ->
            binding.bottoneInvia.isEnabled = !staCaricando
            binding.bottoneInvia.text = if (staCaricando) "..." else "Invia"
        }
    }

    // Evento 2 — LongClick: condivide il testo del messaggio bot con Intent implicito
    private fun condividiMessaggio(messaggio: Message) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, messaggio.testo)
        }
        startActivity(Intent.createChooser(intent, "Condividi messaggio"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package it.zakantonio.androidsampleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import it.zakantonio.androidsampleapp.core.BaseFragment
import it.zakantonio.androidsampleapp.databinding.FragmentChatBinding

// Fragment che gestisce la schermata principale della chat.
// Nelle prossime lezioni conterrà la RecyclerView dei messaggi e il campo di input.
class ChatFragment : BaseFragment() {

    // activityViewModels() restituisce il ViewModel condiviso con la MainActivity
    // e con gli altri fragment. In questo modo ChatFragment e SettingsFragment
    // possono comunicare attraverso lo stesso ViewModel.
    private val viewModel: MainViewModel by activityViewModels()

    // View Binding: _binding è nullable perché il binding viene annullato in onDestroyView
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla il layout del fragment e inizializza il binding
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Annulla il binding per evitare memory leak quando la view viene distrutta
        _binding = null
    }
}

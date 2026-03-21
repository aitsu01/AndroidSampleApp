package it.zakantonio.androidsampleapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.zakantonio.androidsampleapp.databinding.ItemMessageBotBinding
import it.zakantonio.androidsampleapp.databinding.ItemMessageUserBinding
import it.zakantonio.androidsampleapp.model.Message
import it.zakantonio.androidsampleapp.model.TipoMessaggio

// Adapter per la RecyclerView della chat.
// Gestisce DUE tipi di item diversi: uno per i messaggi utente, uno per i messaggi bot.
// Il metodo getItemViewType() è il meccanismo chiave che permette di usare layout diversi.
class ChatAdapter(private val messaggi: List<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Costanti che identificano i due tipi di item nella lista
    companion object {
        const val TIPO_UTENTE = 0
        const val TIPO_BOT = 1
    }

    // ViewHolder per i messaggi dell'utente
    class UtenteViewHolder(binding: ItemMessageUserBinding) : RecyclerView.ViewHolder(binding.root) {
        val testoMessaggio: TextView = binding.testoMessaggio
    }

    // ViewHolder per i messaggi del bot
    class BotViewHolder(binding: ItemMessageBotBinding) : RecyclerView.ViewHolder(binding.root) {
        val testoMessaggio: TextView = binding.testoMessaggio
    }

    // Restituisce il tipo di item in base alla posizione.
    // La RecyclerView usa questo valore per decidere quale layout gonfiare in onCreateViewHolder.
    override fun getItemViewType(position: Int): Int {
        return when (messaggi[position].tipo) {
            TipoMessaggio.UTENTE -> TIPO_UTENTE
            TipoMessaggio.BOT -> TIPO_BOT
        }
    }

    // Crea il ViewHolder giusto in base al viewType restituito da getItemViewType()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TIPO_UTENTE -> {
                val binding = ItemMessageUserBinding.inflate(inflater, parent, false)
                UtenteViewHolder(binding)
            }
            else -> {
                val binding = ItemMessageBotBinding.inflate(inflater, parent, false)
                BotViewHolder(binding)
            }
        }
    }

    // Popola il ViewHolder con i dati del messaggio nella posizione corrente
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val messaggio = messaggi[position]
        when (holder) {
            is UtenteViewHolder -> holder.testoMessaggio.text = messaggio.testo
            is BotViewHolder -> holder.testoMessaggio.text = messaggio.testo
        }
    }

    override fun getItemCount(): Int = messaggi.size
}

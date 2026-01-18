package it.zakantonio.androidsampleapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.zakantonio.androidsampleapp.databinding.ItemCardSimpleBinding

/**
 * Adapter per la RecyclerView che mostra una lista di carte.
 *
 * RecyclerView è un componente che mostra liste scrollabili in modo efficiente:
 * - Riutilizza le view (pattern ViewHolder) invece di crearle ogni volta
 * - Gestisce automaticamente lo scroll e il riciclo delle view
 * - Permette di mostrare grandi quantità di dati con performance ottime
 *
 * @param cards Lista di stringhe contenente i nomi delle carte da mostrare
 */
class CardAdapter(
    private var cards: List<String> = emptyList()
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    /**
     * ViewHolder che contiene i riferimenti alle view di un singolo item.
     *
     * Il pattern ViewHolder serve per:
     * - Evitare di chiamare findViewById ripetutamente (costoso)
     * - Mantenere i riferimenti alle view per riutilizzarle
     * - Migliorare le performance della lista
     */
    class CardViewHolder(
        private val binding: ItemCardSimpleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Collega i dati di una carta alle view dell'item.
         *
         * @param cardName nome della carta da mostrare
         */
        fun bind(cardName: String) {
            binding.textViewCardName.text = cardName
        }
    }

    /**
     * Chiamato quando RecyclerView ha bisogno di un nuovo ViewHolder.
     * Qui creiamo la view per un singolo item della lista.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        // Creiamo il binding per il layout dell'item
        val binding = ItemCardSimpleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CardViewHolder(binding)
    }

    /**
     * Chiamato quando RecyclerView vuole mostrare un item in una posizione specifica.
     * Qui colleghiamo i dati alla view esistente (binding dei dati).
     */
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        // Prendiamo la carta nella posizione corrente
        val card = cards[position]
        // Colleghiamo i dati alla view
        holder.bind(card)
    }

    /**
     * Ritorna il numero totale di item nella lista.
     * RecyclerView usa questo per sapere quanti item deve mostrare.
     */
    override fun getItemCount(): Int = cards.size

    /**
     * Aggiorna la lista di carte e notifica la RecyclerView del cambiamento.
     *
     * @param newCards nuova lista di carte da mostrare
     */
    fun updateCards(newCards: List<String>) {
        cards = newCards
        // Notifichiamo che i dati sono cambiati, così RecyclerView si aggiorna
        notifyDataSetChanged()
    }
}

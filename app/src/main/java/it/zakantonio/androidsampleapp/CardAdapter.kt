package it.zakantonio.androidsampleapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.zakantonio.androidsampleapp.databinding.ItemCardSimpleBinding
import it.zakantonio.androidsampleapp.models.Card

/**
 * Adapter per la RecyclerView che mostra una lista di carte.
 *
 * In questa lezione abbiamo migliorato l'adapter per usare oggetti Card
 * invece di semplici stringhe. Questo ci permette di:
 * - Avere dati strutturati (value e suit separati)
 * - Aggiungere facilmente nuove proprietà in futuro
 * - Usare type safety (il compilatore controlla i tipi)
 *
 * @param cards Lista di oggetti Card da mostrare
 */
class CardAdapter(
    private var cards: List<Card> = emptyList()
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
         * Ora usiamo un oggetto Card che contiene value, suit e icon.
         * Mostriamo sia l'icona che il nome completo.
         *
         * @param card oggetto Card da mostrare
         */
        fun bind(card: Card) {
            // Mostriamo l'icona del seme (Drawable)
            binding.imageViewCardIcon.setImageResource(card.icon)
            // Mostriamo il nome completo della carta
            binding.textViewCardName.text = card.getFullName()
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
     * @param newCards nuova lista di oggetti Card da mostrare
     */
    fun updateCards(newCards: List<Card>) {
        cards = newCards
        // Notifichiamo che i dati sono cambiati, così RecyclerView si aggiorna
        notifyDataSetChanged()
    }
}

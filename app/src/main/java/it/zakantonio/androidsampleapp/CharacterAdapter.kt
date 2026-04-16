package it.zakantonio.androidsampleapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import it.zakantonio.androidsampleapp.databinding.ItemCharacterBinding
import it.zakantonio.androidsampleapp.model.Character

// Adapter per la lista dei personaggi.
class CharacterAdapter(
    private val onClick: (Character) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    private val items = mutableListOf<Character>()

    class ViewHolder(val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(personaggio: Character) {
            with(binding) {
                textNome.text = personaggio.name
                textRazza.text = personaggio.race
                // Coil carica l'immagine in modo asincrono dall'URL
                imagePersonaggio.load(personaggio.image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val personaggio = getItem(position)
        holder.bind(personaggio)
        holder.binding.root.setOnClickListener { onClick(personaggio) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(lista: List<Character>) {
        items.clear()
        items.addAll(lista)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): Character {
        return items[position]
    }
}

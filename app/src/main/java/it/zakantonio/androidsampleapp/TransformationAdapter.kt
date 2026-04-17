package it.zakantonio.androidsampleapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import it.zakantonio.androidsampleapp.databinding.ItemTransformationBinding
import it.zakantonio.androidsampleapp.model.Transformation

class TransformationAdapter : RecyclerView.Adapter<TransformationAdapter.ViewHolder>() {

    private val items = mutableListOf<Transformation>()

    class ViewHolder(private val binding: ItemTransformationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transformation: Transformation) {
            binding.textNomeTrasformazione.text = transformation.name
            binding.textKiTrasformazione.text = "Ki: ${transformation.ki}"
            binding.imageTrasformazione.load(transformation.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTransformationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(lista: List<Transformation>) {
        items.clear()
        items.addAll(lista)
        notifyDataSetChanged()
    }
}
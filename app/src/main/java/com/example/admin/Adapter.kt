package com.example.admin

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(
    private var data: List<Data>,
    private val onItemClick: (Data) -> Unit // Callback to handle item click
) : RecyclerView.Adapter<Adapter.ItemViewHolder>() {

    inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val creator: TextView = view.findViewById(R.id.creator)

        // Binding click listener to the item view
        fun bind(note: Data) {
            title.text = note.title
            creator.text = note.creator

            // Set click listener on the entire item
            itemView.setOnClickListener {
                onItemClick(note) // Trigger the click callback
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflatedView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_row, parent, false)
        return ItemViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val note = data[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int = data.size
}

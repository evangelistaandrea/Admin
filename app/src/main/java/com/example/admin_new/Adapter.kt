package com.example.admin_new

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(
    private var data: List<Notes>
) : RecyclerView.Adapter<Adapter.ItemViewHolder>() {

    inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val creator: TextView = view.findViewById(R.id.creator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflatedView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_row, parent, false)
        return ItemViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val notes = data[position]
        holder.title.text = notes.title
        holder.creator.text = notes.creator
    }

    override fun getItemCount(): Int = data.size

}

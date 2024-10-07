package com.example.admin

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.api.requests_responses.admin.getAdminData

class Adapter(
    private var adminData: List<getAdminData>,
) : RecyclerView.Adapter<Adapter.ItemViewHolder>() {

    inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val contents: ConstraintLayout = view.findViewById(R.id.layout_contents)
        val creator: TextView = view.findViewById(R.id.creator)

       /* Binding click listener to the item view
        fun bind(note: Data) {
            title.text = note.title
            creator.text = note.creator

            // Set click listener on the entire item
            itemView.setOnClickListener {
                onItemClick(note) // Trigger the click callback
            }
        }*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflatedView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_row, parent, false)
        return ItemViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val note = adminData[position]
        holder.title.text = note.title
        holder.creator.text = note.creator_username

        holder.contents.setOnClickListener {
            val intent = Intent(it.context, View_Admin::class.java)
            intent.putExtra("title", note.title)
            intent.putExtra("creator", note.creator_username)  // Assuming contents are in `creator`
            intent.putExtra("contents", note.contents)
            intent.putExtra("public", note.public)
            intent.putExtra("notesId", note.notes_id)

            it.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = adminData.size
}

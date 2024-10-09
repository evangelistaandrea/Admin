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
        val creator: TextView = view.findViewById(R.id.tvEmail)

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
        val pendingData = adminData[position]
        holder.title.text = pendingData.title
        holder.creator.text = pendingData.creatorUsername

        holder.contents.setOnClickListener {
            val intent = Intent(it.context, View_Admin::class.java)
            val updatedAtDate = pendingData.createdAt.substringBefore("t")
            intent.putExtra("title", pendingData.title)
            intent.putExtra("creator_username", pendingData.creatorUsername)
            intent.putExtra("creator_email", pendingData.creatorEmail)
            intent.putExtra("contents", pendingData.contents)
            intent.putExtra("created_at", updatedAtDate)
            intent.putExtra("public", pendingData.public)
            intent.putExtra("notes_id", pendingData.notesId)
            intent.putExtra("to_public", pendingData.toPublic)
            intent.putExtra("user_id", pendingData.userId)
            it.context.startActivity(intent)
        }

    }
    override fun getItemCount(): Int = adminData.size
}

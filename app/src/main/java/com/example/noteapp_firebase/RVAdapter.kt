package com.example.noteapp_firebase

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*


//import kotlinx.android.synthetic.main.single_item.view.*

class RVAdapter(val activity: MainActivity, val notes: List<Note>) : RecyclerView.Adapter<RVAdapter.recyclerViewHolder>() {
    class recyclerViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recyclerViewHolder {
        return recyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item,
                parent,
                false))
    }

    override fun onBindViewHolder(holder: recyclerViewHolder, position: Int) {
        val note = notes[position]

        holder.itemView.apply {

            textview2.text = note.note

           imageView.setOnClickListener {
               activity.update(note)
           }
            imageView2.setOnClickListener {
                activity.confirm(note)
            }
        }}


    override fun getItemCount()=notes.size
}
package com.ksas.notebook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ksas.notebook.R
import com.ksas.notebook.models.Note
import kotlin.collections.ArrayList
import kotlin.random.Random

class NoteAdapter(private val context: Context, private val listener: NotesItemClickListener) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private val noteList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notesLayout: CardView = itemView.findViewById(R.id.card_layout)
        val titleRv: TextView = itemView.findViewById(R.id.title_rv)
        val noteRv: TextView = itemView.findViewById(R.id.note_rv)
        val dateRv: TextView = itemView.findViewById(R.id.date_rv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

        )
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = noteList[position]
        holder.titleRv.text = currentNote.title
        holder.titleRv.isSelected = true
        holder.noteRv.text = currentNote.note
        holder.dateRv.text = currentNote.date
        holder.dateRv.isSelected = true

        holder.notesLayout.setCardBackgroundColor(
            holder.itemView.resources.getColor(
                generateRandomColor()
            )
        )

        holder.notesLayout.setOnClickListener {
            listener.onItemClicked(noteList[holder.adapterPosition])
        }

        holder.notesLayout.setOnLongClickListener {
            listener.onLongItemClicked(noteList[holder.adapterPosition], holder.notesLayout)
            true
        }
    }

    private fun generateRandomColor(): Int {

        val list = ArrayList<Int>()
        list.add(R.color.blue)
        list.add(R.color.blue_1)
        list.add(R.color.green)
        list.add(R.color.orange)
        list.add(R.color.peach)
        list.add(R.color.yellow)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)


        return list[randomIndex]
    }

    fun updateList(newList: List<Note>) {
        fullList.clear()
        fullList.addAll(newList)

        noteList.clear()
        noteList.addAll(fullList)
        notifyDataSetChanged()
    }

    fun filterList(search: String) {
        noteList.clear()
        for (item in fullList) {
            if (item.title?.lowercase()
                    ?.contains(search.lowercase()) == true || item.note?.lowercase()
                    ?.contains(search.lowercase()) == true
            ) {
                noteList.add(item)

            }
        }

        notifyDataSetChanged()
    }


    interface NotesItemClickListener {
        fun onItemClicked(note: Note)

        fun onLongItemClicked(note: Note, cardView: CardView)

    }
}
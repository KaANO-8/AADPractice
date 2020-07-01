package com.kaano8.androidsamples.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.database.Note

class HomeListAdapter(private val listItemClickListener: NoteListItemClickListener) : ListAdapter<Note, RecyclerView.ViewHolder>(NoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val row = inflater.inflate(R.layout.note_list_item, parent, false)
        return NoteViewHolder(row)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
         with(holder as NoteViewHolder) {
             recipientText.text = recipientText.context.getString(R.string.recipient_text_format, item.recipientName)
             noteText.text = item.note
             senderText.text = recipientText.context.getString(R.string.sender_text_format, item.senderName)
             deleteActionButton.setOnClickListener { listItemClickListener.onDeleteItem(item) }
             editActionButton.setOnClickListener { listItemClickListener.onEditItem(item) }
         }
    }

    class NoteViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val recipientText: TextView = view.findViewById(R.id.recipientTextView)
        val noteText: TextView = view.findViewById(R.id.noteTextView)
        val senderText: TextView = view.findViewById(R.id.senderTextView)
        val deleteActionButton: Button = view.findViewById(R.id.deleteAction)
        val editActionButton: Button = view.findViewById(R.id.editAction)
    }

    class NoteDiffCallback: DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem.noteId == newItem.noteId

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem == newItem
    }
}
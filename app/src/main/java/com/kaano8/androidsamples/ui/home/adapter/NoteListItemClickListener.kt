package com.kaano8.androidsamples.ui.home.adapter

import com.kaano8.androidsamples.models.note.Note

interface NoteListItemClickListener {
    fun onDeleteItem(note: Note)
    fun onEditItem(note: Note)
}
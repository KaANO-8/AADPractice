package com.kaano8.androidsamples.ui.home.adapter

import com.kaano8.androidsamples.database.Note

interface NoteListItemClickListener {
    fun onDeleteItem(note: Note)
    fun onEditItem(note: Note)
}
package com.kaano8.androidsamples.ui.addnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kaano8.androidsamples.database.Note
import com.kaano8.androidsamples.database.NoteDatabase
import com.kaano8.androidsamples.database.NoteDatabaseDao
import kotlinx.coroutines.*

class AddNoteViewModel(private val noteDatabase: NoteDatabaseDao) : ViewModel() {

    private val _blankNoteError = MutableLiveData<Boolean>()

    val blackNoteError: LiveData<Boolean>
        get() = _blankNoteError

    private val job = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


    fun submitNote(recipient: String, sender: String, note: String) {
        if (note.isBlank()) {
            _blankNoteError.value = true
        }

        uiScope.launch {
            withContext(Dispatchers.IO) {
                noteDatabase.insert(Note(
                    recipientName = recipient,
                    senderName = sender,
                    note = note
                ))
            }
        }
    }
}
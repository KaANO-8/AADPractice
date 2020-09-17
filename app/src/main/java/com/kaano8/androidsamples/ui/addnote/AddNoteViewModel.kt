package com.kaano8.androidsamples.ui.addnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaano8.androidsamples.database.Gift
import com.kaano8.androidsamples.database.Note
import com.kaano8.androidsamples.repository.NoteRepository
import kotlinx.coroutines.*

class AddNoteViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    private val _blankFieldError = MutableLiveData<Boolean>()

    val blankFieldError: LiveData<Boolean>
        get() = _blankFieldError

    private val _insertionSuccess = MutableLiveData<Boolean>()

    val insertionSuccess: LiveData<Boolean>
        get() = _insertionSuccess

    private val _navigateToHome = MutableLiveData<Boolean?>()

    val navigateToHome: LiveData<Boolean?>
        get() = _navigateToHome

    // Property to get a single note
    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note>
        get() = _note

    val gifts: LiveData<List<Gift>> = noteRepository.gifts

    fun insertOrUpdateNote(noteId: Long, recipient: String, sender: String, note: String, gift: Gift) {
        if (recipient.isBlank() || sender.isBlank() || note.isBlank()) {
            _blankFieldError.value = true
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            if(noteId > -1)
                noteRepository.update(Note(noteId = noteId, recipientName = recipient, senderName = sender, note = note, gift = gift))
            else
                noteRepository.insert(Note(recipientName = recipient, senderName = sender, note = note, gift = gift))
        }
        _insertionSuccess.value = true
        _navigateToHome.value = true
    }

    fun getNoteById(noteId: Long): LiveData<Note> = noteRepository.getNoteById(noteId)

    fun doneNavigateToHome() {
        _navigateToHome.value = null
    }

}
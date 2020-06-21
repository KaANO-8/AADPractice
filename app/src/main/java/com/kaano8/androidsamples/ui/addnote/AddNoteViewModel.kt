package com.kaano8.androidsamples.ui.addnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    fun insertNewNote(recipient: String, sender: String, note: String) {
        if (recipient.isBlank() || sender.isBlank() || note.isBlank()) {
            _blankFieldError.value = true
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insert(Note(recipientName = recipient, senderName = sender, note = note))
        }
        _insertionSuccess.value = true
        _navigateToHome.value = true
    }

    fun doneNavigateToHome() {
        _navigateToHome.value = null
    }

}
package com.kaano8.androidsamples.ui.addnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class AddNoteViewModel : ViewModel() {

    private val _blankNoteError = MutableLiveData<Boolean>()

    val blackNoteError: LiveData<Boolean>
        get() = _blankNoteError

    private val job = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


    fun submitNote(recipient: String?, sender: String?, note: String?) {
        if (note?.isBlank() == true) {
            _blankNoteError.value = true
        }

    }
}
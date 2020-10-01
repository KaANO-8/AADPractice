package com.kaano8.androidsamples.ui.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kaano8.androidsamples.repository.coroutines.TitleRepository

class CoroutineViewModelFactory(private val titleRepository: TitleRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoroutineViewModel::class.java)) {
            return CoroutineViewModel(titleRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

package com.kaano8.androidsamples.ui.paging2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kaano8.androidsamples.database.gift.GiftDatabaseDao

class GiftListViewModelFactory(private val giftDatabaseDao: GiftDatabaseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GiftListViewModel::class.java)) {
            return GiftListViewModel(giftDatabaseDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
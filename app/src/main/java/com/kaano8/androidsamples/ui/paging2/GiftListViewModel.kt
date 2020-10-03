package com.kaano8.androidsamples.ui.paging2

import androidx.lifecycle.ViewModel
import androidx.paging.toLiveData
import com.kaano8.androidsamples.database.gift.GiftDatabaseDao

class GiftListViewModel(private val giftDatabaseDao: GiftDatabaseDao) : ViewModel() {

    val giftList = giftDatabaseDao.getGiftDataSource().toLiveData(pageSize = 10)
}
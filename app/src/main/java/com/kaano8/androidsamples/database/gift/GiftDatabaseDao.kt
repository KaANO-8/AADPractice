package com.kaano8.androidsamples.database.gift

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kaano8.androidsamples.models.gift.Gift

@Dao
interface GiftDatabaseDao {
    @Query("select * from gift_table ORDER BY name")
    fun getGifts(): LiveData<List<Gift>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(gifts: List<Gift>)
}
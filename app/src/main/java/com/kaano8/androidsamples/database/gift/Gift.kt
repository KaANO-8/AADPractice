package com.kaano8.androidsamples.database.gift

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gift_table")
data class Gift(
    @PrimaryKey var id: String,
    var name: String,
    var amount: Double,
    var remark: String
)
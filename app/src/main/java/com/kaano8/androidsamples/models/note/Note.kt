package com.kaano8.androidsamples.models.note

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kaano8.androidsamples.models.gift.Gift

@Entity(tableName = "thank_you_note_table")
data class Note(
    @PrimaryKey(autoGenerate = true) var noteId: Long = 0L,
    @ColumnInfo(name = "recipient_name") var recipientName: String,
    @ColumnInfo(name = "sender_name") var senderName: String,
    var note: String,
    @Embedded val gift: Gift
)
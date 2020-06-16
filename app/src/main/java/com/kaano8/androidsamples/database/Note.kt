package com.kaano8.androidsamples.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "thank_you_note_table")
data class Note(
    @PrimaryKey(autoGenerate = true) var noteId: Long = 0L,
    @ColumnInfo(name = "recipient_name") var recipientName: String,
    @ColumnInfo(name = "sender_name") var senderName: String,
    var note: String
)
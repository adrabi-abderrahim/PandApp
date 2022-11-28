package com.andela.edutream17.pandapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "virtual_peer")
data class ChatHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "model_name")
    val modelName: String,

    val message: String,

    @ColumnInfo(name = "message_date")
    val messageDate: LocalDateTime,

    @ColumnInfo(name = "is_user_message")
    val isUserMessage: Boolean = false
)

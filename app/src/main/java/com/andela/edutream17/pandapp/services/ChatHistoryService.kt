package com.andela.edutream17.pandapp.services

import android.content.Context
import com.andela.edutream17.pandapp.database.PandAppDatabase
import com.andela.edutream17.pandapp.database.entities.ChatHistoryEntity
import java.time.LocalDateTime

class ChatHistoryService private constructor(context: Context) {
    private val db = PandAppDatabase.build(context)

    suspend fun getAll() = db.virtualPeer().getAll()

    suspend fun getLastMessage(): ChatHistoryEntity? = db.virtualPeer().getLastMessage()

    suspend fun addMessage(modelName: String, message: String, isUserMessage: Boolean = false) =
        db.virtualPeer().insert(
            ChatHistoryEntity(
                modelName = modelName,
                message = message,
                messageDate = LocalDateTime.now(),
                isUserMessage = isUserMessage
            )
        )

    suspend fun deleteByModel(modelName: String) = db.virtualPeer().deleteByModel(modelName)


    companion object {
        fun build(context: Context) = ChatHistoryService(context)
    }
}

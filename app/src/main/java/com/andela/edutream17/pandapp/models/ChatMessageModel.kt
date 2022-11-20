package com.andela.edutream17.pandapp.models

import java.time.LocalDateTime

data class ChatMessageModel(
    val message: String,
    val isUserMessage: Boolean,
    val messageDateTime: LocalDateTime = LocalDateTime.now()
)

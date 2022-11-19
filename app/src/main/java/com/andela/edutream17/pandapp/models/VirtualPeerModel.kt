package com.andela.edutream17.pandapp.models

import androidx.compose.ui.graphics.vector.ImageVector
import java.time.LocalDateTime

data class VirtualPeerModel(
    val name: String,
    val label: String,
    val lastMessage: String,
    val dateOfLastMessage: LocalDateTime,
    val icon: ImageVector
)
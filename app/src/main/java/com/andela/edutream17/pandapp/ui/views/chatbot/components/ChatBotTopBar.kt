package com.andela.edutream17.pandapp.ui.views.chatbot.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun ChatBotTopBar(onClick: () -> Unit) {
    TopAppBar(
        title = { Text("<ChatBot Name>") },
        elevation = 0.5.dp,
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        }
    )
}
package com.andela.edutream17.pandapp.ui.views.chatbot.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.andela.edutream17.pandapp.database.entities.CustomModelEntity

@Composable
fun ChatBotTopBar(customModelEntity: CustomModelEntity, onClick: () -> Unit) {
    TopAppBar(
        title = { Text(customModelEntity.metadata?.label!!) },
        elevation = 0.5.dp,
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        }
    )
}
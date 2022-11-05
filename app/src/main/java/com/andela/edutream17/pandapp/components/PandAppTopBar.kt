package com.andela.edutream17.pandapp.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier


@Composable
fun PandAppTopBar(content: @Composable () -> Unit) {
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
    topBar = {
        TopAppBar(
            title = { Text(text = "PandApp") },
            actions = {
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false })
                {
                    DropdownMenuItem(onClick = {  }) {
                        Text(text = "Settings")
                    }
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Search, contentDescription = null)
                }

                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(Icons.Default.MoreVert, contentDescription = null)
                }
            }
        )
    },
    modifier = Modifier.fillMaxSize()
    ) {
        content()
    }
}
package com.andela.edutream17.pandapp.ui.views.home.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp


@Composable
fun PandAppTopBar() {
    var showMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = "PandApp") },
        elevation = 0.5.dp,
        actions = {
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false })
            {

                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text("Update")
                }

                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text(text = "Settings")
                }
            }

            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(Icons.Default.MoreVert, contentDescription = null)
            }
        }
    )
}
package com.andela.edutream17.pandapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun VirtualPeerDownloadScreen(navController: NavController) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Add a virtual peer") },
            elevation = 0.5.dp,
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )
    }) {
        Column(modifier = Modifier.padding(it)) {

        }
    }
}
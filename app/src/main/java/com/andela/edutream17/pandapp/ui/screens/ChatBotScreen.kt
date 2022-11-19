package com.andela.edutream17.pandapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.andela.edutream17.pandapp.ui.views.chatbot.components.ChatBotBottomBar
import com.andela.edutream17.pandapp.ui.views.chatbot.components.ChatBotTopBar


@Composable
fun ChatBotScreen(navController: NavController){
    Scaffold(topBar = {
        ChatBotTopBar {
            navController.popBackStack()
        }
    },
    bottomBar = {
        ChatBotBottomBar()
    }
        ) {
        Column(modifier = Modifier.padding(it)) {

        }
    }
}
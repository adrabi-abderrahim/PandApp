package com.andela.edutream17.pandapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.andela.edutream17.pandapp.models.ChatMessageModel
import com.andela.edutream17.pandapp.ui.views.chatbot.components.ChatBotBottomBar
import com.andela.edutream17.pandapp.ui.views.chatbot.components.ChatBotTopBar
import com.andela.edutream17.pandapp.ui.views.chatbot.components.ChatBubble


class ChatBotViewModel {
    private val maxLength = 255 * 2
    val messages: MutableList<ChatMessageModel> = mutableStateListOf()
    var currentMessage: MutableState<String> = mutableStateOf("")

    fun addMessage(message: ChatMessageModel) {
        this.messages.add(message)
    }

    fun updateCurrentMessage(text: String) {
        if (text.length < this.maxLength) this.currentMessage.value = text
    }
}


@Composable
fun ChatBotScreen(navController: NavController, vm: ChatBotViewModel = ChatBotViewModel()) {

    val messages = vm.messages
    val currentMessage by vm.currentMessage

    val focusManager = LocalFocusManager.current
    Scaffold(
        backgroundColor = Color(0xFFEAFAE2),
        topBar = {
            ChatBotTopBar {
                navController.popBackStack()
            }
        },
        bottomBar = {
            ChatBotBottomBar(messageValue = currentMessage, onValueChange = {
                vm.updateCurrentMessage(it)
            }) {
                Log.i("<Add-Item>", "List=${messages.size} - The item $currentMessage as added")

                vm.addMessage(
                    ChatMessageModel(
                        currentMessage ?: "",
                        true
                    )
                )

                vm.updateCurrentMessage("")
                focusManager.clearFocus()
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(messages) { message ->
                ChatBubble(msg = message)
            }
        }
    }
}


@Preview
@Composable
fun ChatBotScreenPreview() {
    ChatBotScreen(navController = rememberNavController())
}
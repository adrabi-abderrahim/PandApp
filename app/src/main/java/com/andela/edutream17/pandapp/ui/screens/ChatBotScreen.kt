package com.andela.edutream17.pandapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.andela.edutream17.pandapp.database.entities.ChatHistoryEntity
import com.andela.edutream17.pandapp.database.entities.CustomModelEntity
import com.andela.edutream17.pandapp.services.ChatHistoryService
import com.andela.edutream17.pandapp.services.CustomModelService
import com.andela.edutream17.pandapp.services.ModelInterpreterService
import com.andela.edutream17.pandapp.ui.views.chatbot.components.ChatBotBottomBar
import com.andela.edutream17.pandapp.ui.views.chatbot.components.ChatBotTopBar
import com.andela.edutream17.pandapp.ui.views.chatbot.components.ChatBubble
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class ChatBotViewModel: ViewModel() {
    private val maxLength = 255 * 2
    val messages: MutableState<List<ChatHistoryEntity>> = mutableStateOf(listOf())
    var currentMessage: MutableState<String> = mutableStateOf("")
    var model: MutableState<CustomModelEntity?> = mutableStateOf(null)

    fun updateCurrentMessage(text: String) {
        if (text.length < this.maxLength) this.currentMessage.value = text
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ChatBotScreen(
    navController: NavController,
    modelName: String,
    vm: ChatBotViewModel = ChatBotViewModel()
) {

    val context = LocalContext.current
    var model by vm.model
    var messages by vm.messages
    val currentMessage by vm.currentMessage

    val customModelService = CustomModelService.build(context)
    val chatHistoryService = ChatHistoryService.build(context)
    val historyService: ChatHistoryService = ChatHistoryService.build(context)


    runBlocking {
        launch {

            model = customModelService.get(modelName)
            if (model != null) {

                messages = chatHistoryService.getAll()
            }
        }
    }

    if (model != null) {

        val interpreterService = ModelInterpreterService(context, model!!)

        val focusManager = LocalFocusManager.current
        Scaffold(
            backgroundColor = Color(0xFFEAFAE2),
            topBar = {
                ChatBotTopBar(model!!) {
                    navController.popBackStack()
                }
            },
            bottomBar = {
                ChatBotBottomBar(messageValue = currentMessage, onValueChange = {
                    vm.updateCurrentMessage(it)
                }) {
                    if (currentMessage.isNotEmpty()) {
                        val predictedMessage = interpreterService.predict(currentMessage)

                        runBlocking {
                            launch {
                                historyService.addMessage(
                                    modelName,
                                    currentMessage,
                                    true
                                )
                                messages = chatHistoryService.getAll()

                            }
                        }
                        runBlocking {
                            launch {
                                historyService.addMessage(
                                    modelName,
                                    predictedMessage
                                )
                                messages = chatHistoryService.getAll()
                            }
                        }
                        vm.updateCurrentMessage("")
                        focusManager.clearFocus()
                    }
                }
            }
        ) {
            val state = rememberLazyListState()
            val scope = rememberCoroutineScope()

            LazyColumn(modifier = Modifier.padding(it), state = state) {
                items(messages) { message ->
                    ChatBubble(msg = message)
                }
            }

            scope.launch {
                state.animateScrollToItem(messages.size - 1)
            }
        }
    } else {
        navController.popBackStack()
    }

}

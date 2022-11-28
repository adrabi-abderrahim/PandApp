package com.andela.edutream17.pandapp.ui.views.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.andela.edutream17.pandapp.database.entities.ChatHistoryEntity
import com.andela.edutream17.pandapp.database.entities.CustomModelEntity
import com.andela.edutream17.pandapp.services.ChatHistoryService
import com.andela.edutream17.pandapp.services.CustomModelService
import com.andela.edutream17.pandapp.ui.views.home.components.VirtualContactItem
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@Composable
fun LearnView(navController: NavController) {
    val context = LocalContext.current

    var models by remember {
        mutableStateOf(listOf<CustomModelEntity>())
    }

    LaunchedEffect(Unit) {
        val customModelService = CustomModelService.build(context)
        models = customModelService.getAll()
    }

    LazyColumn {
        items(models) {
            var lastMessage: ChatHistoryEntity? by remember {
                mutableStateOf(null)
            }
            runBlocking {
                launch {
                    lastMessage = ChatHistoryService.build(context).getLastMessage()
                }
            }
            VirtualContactItem(it, lastMessage, navController)
        }
    }

}
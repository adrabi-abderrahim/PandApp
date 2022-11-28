package com.andela.edutream17.pandapp.ui.screens

import android.app.DownloadManager
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.andela.edutream17.pandapp.database.entities.CustomModelEntity
import com.andela.edutream17.pandapp.models.DownloadingStatus
import com.andela.edutream17.pandapp.services.ChatHistoryService
import com.andela.edutream17.pandapp.services.CustomModelService
import com.andela.edutream17.pandapp.services.RemoteModelService
import com.andela.edutream17.pandapp.ui.views.virrualpeer.components.VirtualPeerDownloadRow
import com.andela.edutream17.pandapp.ui.views.virrualpeer.components.VirtualPeerDownloadRowState
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@Composable
fun VirtualPeerDownloadScreen(navController: NavController) {

    val context = LocalContext.current

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

        val remoteModelService = RemoteModelService.build(context)
        val customModelService = CustomModelService.build(context)

        var listOfModelsFormFirebase by remember {
            mutableStateOf(listOf<CustomModelEntity>())
        }

        LaunchedEffect(Unit) {
            listOfModelsFormFirebase = remoteModelService
                .getAllModelsMetadata()
                .map { currentModel ->
                    val model = customModelService.get(currentModel.name)
                    currentModel.path = model?.path
                    currentModel
                }
        }

        if (listOfModelsFormFirebase.isEmpty())
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Loading ...", color = Color.Gray, fontSize = 18.sp)
            }
        else
            LazyColumn(modifier = Modifier.padding(it)) {
                items(listOfModelsFormFirebase) { model ->
                    var rowState by remember {
                        mutableStateOf(
                            if (model.path == null)
                                VirtualPeerDownloadRowState.TO_DOWNLOAD
                            else
                                VirtualPeerDownloadRowState.TO_REMOVE
                        )
                    }

                    var progress by remember {
                        mutableStateOf(0f)
                    }

                    VirtualPeerDownloadRow(
                        model,
                        rowState,
                        {
                            rowState = VirtualPeerDownloadRowState.DOWNLOADING
                            download(context, model.name) { ds ->
                                if (ds.progress == 1f) {
                                    rowState = VirtualPeerDownloadRowState.TO_REMOVE
                                } else {
                                    progress = ds.progress
                                }
                            }
                        },
                        {
                            runBlocking {
                                launch {
                                    remoteModelService.removeModel(model.name)
                                    customModelService.delete(model)
                                    ChatHistoryService.build(context).deleteByModel(model.name)
                                    rowState = VirtualPeerDownloadRowState.TO_DOWNLOAD
                                }
                            }
                        },
                        progress = progress
                    )
                }
            }
    }
}


fun download(context: Context, modelName: String, update: (DownloadingStatus) -> Unit) {

    val dl = RemoteModelService.build(context)
    dl.getModel(modelName) {

        update(DownloadingStatus(it.status, it.downloaded / it.total.toFloat()))

        when (it.status) {
            DownloadManager.STATUS_FAILED -> {
                return@getModel false
            }
            DownloadManager.STATUS_SUCCESSFUL -> {
                return@getModel false
            }
            DownloadManager.STATUS_PENDING -> {
                return@getModel true
            }
            DownloadManager.STATUS_PAUSED -> {
                return@getModel true
            }
            DownloadManager.STATUS_RUNNING -> {
                return@getModel true
            }
        }

        false
    }
}


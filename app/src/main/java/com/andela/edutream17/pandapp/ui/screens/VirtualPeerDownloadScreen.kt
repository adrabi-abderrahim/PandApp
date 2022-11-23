package com.andela.edutream17.pandapp.ui.screens

import android.app.DownloadManager
import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.andela.edutream17.pandapp.database.entities.CustomModelEntity
import com.andela.edutream17.pandapp.models.DownloadingStatus
import com.andela.edutream17.pandapp.models.PandAppCustomModel
import com.andela.edutream17.pandapp.services.RemoteModelService
import com.andela.edutream17.pandapp.ui.views.virrualpeer.components.VirtualPeerDownloadRow
import com.andela.edutream17.pandapp.ui.views.virrualpeer.components.VirtualPeerDownloadRowState


val __listOfModelsFormFirebase = listOf<PandAppCustomModel>(
    PandAppCustomModel(
        name = "PandApp-T4",
        label = "Pandox Master",
        description = "The Panda model is the virtual peer that answer every question related to Pandas Python library.",
        inputSize = 7,
        outputSize = 5,
        minAcceptedProbability = 0,
        vocabulary = mapOf(
            "dataframe" to 1,
            "the" to 2,
            "of" to 3,
            "pandas" to 4,
            "index" to 5,
            "labels" to 6,
            "values" to 7,
            "row" to 8,
            "columns" to 9,
            "column" to 10,
            "dtypes" to 11,
            "info" to 12,
            "return" to 13,
            "numpy" to 14,
            "representation" to 15,
            "in" to 16,
            "data" to 17,
            "type" to 18,
            "each" to 19,
            "print" to 20,
            "concise" to 21,
            "summary" to 22,
            "method" to 23,
            "prints" to 24,
            "information" to 25,
            "about" to 26,
            "get" to 27,
            "will" to 28,
            "be" to 29,
            "returned" to 30
        ),
        uuid = listOf(
            "3899e653-d6ad-485e-a4ed-d5869e5f7314",
            "91dfeb74-5f4e-11ed-9b6a-0242ac120002",
            "75712508-6ec6-44b2-b0a6-7e57a0890ece",
            "f2af3803-3a44-49fd-928b-29bf003c2b26",
            "9b49808f-7f66-4e1e-bcdc-9dd867c8b610"
        ),
        responses = mapOf(
            "3899e653-d6ad-485e-a4ed-d5869e5f7314" to "https://pandas.pydata.org/docs/reference/api/pandas.DataFrame.index.html",
            "91dfeb74-5f4e-11ed-9b6a-0242ac120002" to "https://pandas.pydata.org/docs/reference/api/pandas.DataFrame.columns.html",
            "75712508-6ec6-44b2-b0a6-7e57a0890ece" to "https://pandas.pydata.org/docs/reference/api/pandas.DataFrame.dtypes.html",
            "f2af3803-3a44-49fd-928b-29bf003c2b26" to "https://pandas.pydata.org/docs/reference/api/pandas.DataFrame.info.html",
            "9b49808f-7f66-4e1e-bcdc-9dd867c8b610" to "https://pandas.pydata.org/docs/reference/api/pandas.DataFrame.values.html"
        )
    )
)

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
        var listOfModelsFormFirebase by remember {
            mutableStateOf(listOf<CustomModelEntity>())
        }

        LaunchedEffect(Unit) {
            listOfModelsFormFirebase = RemoteModelService.build(context).getAllModelsMetadata()
        }

        LazyColumn(modifier = Modifier.padding(it)) {
            items(listOfModelsFormFirebase) { model ->
                var rowState by remember {
                    mutableStateOf(VirtualPeerDownloadRowState.TO_DOWNLOAD)
                }

                var progress by remember {
                    mutableStateOf(0f)
                }

                VirtualPeerDownloadRow(
                    model, rowState,
                    {
                        rowState = VirtualPeerDownloadRowState.DOWNLOADING
                        download(context, model.name) { ds ->
                            progress = ds.progress
                        }
                    },
                    {
                        rowState = VirtualPeerDownloadRowState.TO_DOWNLOAD
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
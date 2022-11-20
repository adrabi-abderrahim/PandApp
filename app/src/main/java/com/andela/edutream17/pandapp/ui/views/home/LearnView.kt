package com.andela.edutream17.pandapp.ui.views

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.andela.edutream17.pandapp.models.VirtualPeerModel
import com.andela.edutream17.pandapp.ui.views.home.components.VirtualContactItem
import java.time.LocalDateTime

val dummy = listOf<VirtualPeerModel>(
    VirtualPeerModel(
        "Model-1",
        "Panda",
        "This is the last message of Model-1",
        LocalDateTime.now(),
        Icons.Rounded.Face
    ),
    VirtualPeerModel(
        "Model-2",
        "EDA",
        "This is the last message of Model-2",
        LocalDateTime.now(),
        Icons.Rounded.Person
    )
)

@Composable
fun LearnView(navController: NavController) {
    LazyColumn {
        items(dummy) {
            VirtualContactItem(it, navController)
        }
    }

}
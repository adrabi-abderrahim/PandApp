package com.andela.edutream17.pandapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.andela.edutream17.pandapp.ui.views.CompletedView
import com.andela.edutream17.pandapp.ui.views.LearnView
import com.andela.edutream17.pandapp.ui.views.StatisticsView
import com.andela.edutream17.pandapp.ui.views.home.components.PandAppTabBar
import com.andela.edutream17.pandapp.ui.views.home.components.PandAppTopBar
import com.andela.edutream17.pandapp.ui.views.home.components.TabItem


@Composable
fun HomeScreen(navController: NavController) {
    var currentTab by remember {
        mutableStateOf(TabItem.LEARN)
    }

    Scaffold(
        topBar = { PandAppTopBar() },
        floatingActionButton = {
            if (currentTab == TabItem.LEARN) {
                FloatingActionButton(onClick = { navController.navigate("download") }) {
                    Icon(
                        Icons.Rounded.Add,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }
    ) { paddingValues ->
        PandAppTabBar(modifier = Modifier.padding(paddingValues)) {
            currentTab = it
            when (it) {
                TabItem.LEARN -> LearnView(navController)
                TabItem.COMPLETED -> CompletedView(navController)
                TabItem.STATISTICS -> StatisticsView(navController)
            }
        }
    }
}

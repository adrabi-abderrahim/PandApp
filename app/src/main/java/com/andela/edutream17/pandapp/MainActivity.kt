package com.andela.edutream17.pandapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andela.edutream17.pandapp.composable.AppTabBar
import com.andela.edutream17.pandapp.composable.AppTabs
import com.andela.edutream17.pandapp.ui.theme.PandappTheme
import com.andela.edutream17.pandapp.ui.view.CompletedView
import com.andela.edutream17.pandapp.ui.view.LearnView
import com.andela.edutream17.pandapp.ui.view.StatisticsView
import com.andela.edutream17.pandapp.utils.Constant
import com.andela.edutream17.pandapp.utils.Extension.capitalized
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PandappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    MainView()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true)
fun MainView() {
    var showMenu by remember { mutableStateOf(false) }
    var tabSelected by remember { mutableStateOf(Constant.TabItem.LEARN) }
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Panda app") }, elevation = 0.dp, actions = {
            DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                DropdownMenuItem(onClick = {}) {
                    Text(text = "Settings")
                }
            }
            IconButton(onClick = { }) {
                Icon(Icons.Default.Search, contentDescription = null)
            }

            IconButton(onClick = { showMenu = showMenu != true }) {
                Icon(Icons.Default.MoreVert, contentDescription = null)
            }
        })
    }, floatingActionButton = {
        when (tabSelected) {
            Constant.TabItem.LEARN -> {
                FloatingActionButton(onClick = { }, backgroundColor = Color.Blue) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            Constant.TabItem.COMPLETED -> {
                FloatingActionButton(onClick = { }, backgroundColor = Color.Blue) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_done),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            Constant.TabItem.STATISTICS -> {
                FloatingActionButton(onClick = { }, backgroundColor = Color.Blue) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_history),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }, modifier = Modifier.fillMaxSize()
    ) {
        Column(Modifier.fillMaxSize()) {
            TabItemBar(tabSelected, onTabSelected = { tabSelected = it })
            when (tabSelected) {
                Constant.TabItem.LEARN -> LearnView()
                Constant.TabItem.COMPLETED -> CompletedView()
                Constant.TabItem.STATISTICS -> StatisticsView()
            }
        }
    }
}

@Composable
fun TabItemBar(
    tabSelected: Constant.TabItem, onTabSelected: (Constant.TabItem) -> Unit
) {
    AppTabBar { tabBarModifier ->
        AppTabs(modifier = tabBarModifier,
            titles = Constant.TabItem.values().map { it.name.capitalized() },
            tabSelected = tabSelected,
            onTabSelected = { newTab ->
                onTabSelected(Constant.TabItem.values()[newTab.ordinal])
            })
    }
}

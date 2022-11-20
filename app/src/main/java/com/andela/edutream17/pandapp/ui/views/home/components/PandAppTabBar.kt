package com.andela.edutream17.pandapp.ui.views.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
private fun PandAppTabBarRow(
    modifier: Modifier = Modifier,
    children: @Composable (Modifier) -> Unit
) {
    Row(modifier) {
        children(
            modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun PandAppTabBar(modifier: Modifier = Modifier, onTabSelected: @Composable (TabItem) -> Unit) {
    var tabSelected by remember { mutableStateOf(TabItem.LEARN) }

    Column(modifier = modifier) {
        PandAppTabBarRow(modifier = modifier) { tabBarModifier ->
            PandAppTabs(
                modifier = tabBarModifier,
                titles = TabItem.values().map { it.name },
                tabSelected = tabSelected,
                onTabSelected = { newTab -> tabSelected = TabItem.values()[newTab.ordinal] })
        }

        onTabSelected(tabSelected)
    }
}
package com.andela.edutream17.pandapp.ui.views.home.components

import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

enum class TabItem {
    LEARN, COMPLETED, STATISTICS
}

@Composable
fun PandAppTabs(
    modifier: Modifier = Modifier,
    titles: List<String>,
    tabSelected: TabItem,
    onTabSelected: (TabItem) -> Unit
) {
    TabRow(
        selectedTabIndex = tabSelected.ordinal,
        modifier = modifier,
        indicator = { tabIndicator ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(
                    tabIndicator[tabSelected.ordinal]
                )
            )
        },
        divider = { },
    ) {
        titles.forEachIndexed { index, title ->
            val selected = index == tabSelected.ordinal
            Tab(
                text = {Text(title)},
                selected = selected,
                onClick = {onTabSelected(TabItem.values()[index])}
            )
        }
    }
}
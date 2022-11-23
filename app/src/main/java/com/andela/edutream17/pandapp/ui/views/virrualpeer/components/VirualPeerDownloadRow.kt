package com.andela.edutream17.pandapp.ui.views.virrualpeer.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andela.edutream17.pandapp.database.entities.CustomModelEntity
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons

enum class VirtualPeerDownloadRowState {
    TO_DOWNLOAD,
    DOWNLOADING,
    TO_REMOVE
}


@Composable
fun VirtualPeerDownloadRow(
    model: CustomModelEntity,
    state: VirtualPeerDownloadRowState,
    downloadOnClock: () -> Unit,
    removeOnClick: () -> Unit,
    progress: Float = 0f
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier
                .weight(.9f)
                .fillMaxWidth()
        ) {
            Text(model.name, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            when (state) {
                VirtualPeerDownloadRowState.DOWNLOADING ->
                    LinearProgressIndicator(
                        progress = progress, modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                    )
                else -> Divider(modifier = Modifier.padding(vertical = 5.dp))
            }
            Text(model.metadata?.description ?: "", color = Color(0xFF585858))
        }
        Column(modifier = Modifier, verticalArrangement = Arrangement.Center) {
            when (state) {
                VirtualPeerDownloadRowState.TO_DOWNLOAD -> IconButton(
                    modifier = Modifier.padding(top = 6.dp),
                    onClick = downloadOnClock
                ) {
                    FaIcon(faIcon = FaIcons.Download)
                }

                VirtualPeerDownloadRowState.DOWNLOADING ->
                    Row(
                        modifier = Modifier.padding(top = 15.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 5.dp),
                            fontSize = 25.sp,
                            fontWeight = FontWeight.SemiBold,
                            text = "${(progress * 100).toInt()}",
                            color = MaterialTheme.colors.primary,
                        )
                        Text(
                            modifier = Modifier.padding(end = 5.dp),
                            text = "%",
                            color = MaterialTheme.colors.primary,
                        )
                    }

                VirtualPeerDownloadRowState.TO_REMOVE -> IconButton(
                    modifier = Modifier.padding(top = 6.dp),
                    onClick = removeOnClick
                ) {
                    FaIcon(faIcon = FaIcons.Trash, tint = Color(0xFFFA5252))
                }
            }
        }
    }
}

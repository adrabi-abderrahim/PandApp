package com.andela.edutream17.pandapp.ui.views.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.andela.edutream17.pandapp.database.entities.CustomModelEntity
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons


@Composable
fun VirtualContactItem(virtualPeer: CustomModelEntity, navController: NavController) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                navController.navigate("chatbot")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        FaIcon(
            FaIcons.Robot,
            tint = Color.DarkGray,
            size = 48.dp
        )
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = virtualPeer.metadata?.label ?: "",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = virtualPeer.dateOfLastMessage?.toString() ?: "<NEW>")
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Check,
                    modifier = Modifier.size(16.dp),
                    tint = Color.Green,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = virtualPeer.lastMessage ?: "<No message yet>",
                    maxLines = 1,
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.LightGray)
        }
    }
}
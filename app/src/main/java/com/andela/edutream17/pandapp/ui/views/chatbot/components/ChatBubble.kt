package com.andela.edutream17.pandapp.ui.views.chatbot.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andela.edutream17.pandapp.database.entities.ChatHistoryEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun ChatBubble(modifier: Modifier = Modifier, msg: ChatHistoryEntity) {

    val start = if (msg.isUserMessage) 50.dp else 5.dp
    val end = if (msg.isUserMessage) 5.dp else 50.dp
    val color = if (msg.isUserMessage) Color(0xFFF4FF81) else Color.White

    Row(modifier = modifier.fillMaxWidth()) {
        if (msg.isUserMessage) Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .padding(top = 2.dp, bottom = 2.dp, start = start, end = end)
                .clip(RoundedCornerShape(12.dp))
                .background(color = color)
        ) {
            Column(horizontalAlignment = if (msg.isUserMessage) Alignment.End else Alignment.Start) {
                Text(
                    text = msg.message,
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .padding(top = 3.dp, bottom = 1.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = if (msg.messageDate.toLocalDate()
                            .isEqual(LocalDateTime.now().toLocalDate())
                    )
                        msg.messageDate.toLocalTime()
                            .format(DateTimeFormatter.ofPattern("HH:mm"))
                    else
                        msg.messageDate.toLocalDate()
                            .format(DateTimeFormatter.ofPattern("dd MMM yy")),
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .padding(top = 1.dp, bottom = 3.dp),
                    fontSize = 12.sp,
                    color = Color(0xED3F3F3F)
                )

            }
        }
    }
}

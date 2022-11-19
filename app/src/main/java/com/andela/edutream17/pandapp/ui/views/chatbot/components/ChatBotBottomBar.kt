package com.andela.edutream17.pandapp.ui.views.chatbot.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons

@Composable
fun ChatBotBottomBar(modifier: Modifier = Modifier){
    Row(modifier = modifier.padding(start = 10.dp, bottom = 5.dp, end = 10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier
                .weight(1f)
                .padding(top = 5.dp, bottom = 5.dp, end = 5.dp),
            shape = RoundedCornerShape(30.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = MaterialTheme.colors.primary,
                textColor = MaterialTheme.colors.primary),
            leadingIcon = { FaIcon(faIcon = FaIcons.Reply, tint = MaterialTheme.colors.primary) },
            placeholder = {Text("Answer")}
        )
        OutlinedButton(onClick = { /*TODO*/ },
            modifier = Modifier.size(58.dp),
            shape = CircleShape,
            border = BorderStroke(1.dp, MaterialTheme.colors.primary)
        ) {
            FaIcon(faIcon = FaIcons.PaperPlane, tint = MaterialTheme.colors.primary)
        }
    }
}
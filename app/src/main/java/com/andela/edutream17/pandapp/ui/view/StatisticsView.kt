package com.andela.edutream17.pandapp.ui.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andela.edutream17.pandapp.R
import kotlin.random.Random

@Composable
fun StatisticsView() {
    Row(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Box(modifier = Modifier.weight(2f)) {
            Image(
                painter = painterResource(id = R.drawable.ic_done),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(48.dp)
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )

        }
        Column(
            Modifier
                .padding(horizontal = 8.dp)
                .weight(8f)
        ) {
            Text(
                "Panda",
                maxLines = 1,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            val progress = Random.nextFloat()
            LinearProgressIndicator(progress = progress, color = Color.Green)
        }
    }
}
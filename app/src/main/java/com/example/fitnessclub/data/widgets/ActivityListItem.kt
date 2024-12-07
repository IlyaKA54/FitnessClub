package com.example.fitnessclub.data.widgets

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fitnessclub.data.Model.Data.Activity
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ActivityListItem(
    activity: Activity,
    onClick: (Activity) -> Unit
) {
    val currentDateTime = remember { Instant.now().atZone(ZoneId.systemDefault()).toLocalDateTime() }
    val startDateTime = Instant.ofEpochMilli(activity.startAt).atZone(ZoneId.systemDefault()).toLocalDateTime()
    val endDateTime = startDateTime.plusSeconds(activity.time)

    val backgroundColor = if (currentDateTime >= startDateTime) {
        Color(0xFFFFCDD2) // Бледнокрасный (розоватый)
    } else {
        Color(0xFFC8E6C9) // Бледносветлозеленый
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .combinedClickable(
                onClick = { onClick(activity) },
                indication = null, // Отключаем визуальный эффект клика
                interactionSource = remember { MutableInteractionSource() } // Отключаем запись взаимодействий
            ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${startDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))} - ${endDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))}",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = activity.name,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start
            )
        }
    }
}

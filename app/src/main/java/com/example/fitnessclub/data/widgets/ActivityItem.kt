package com.example.fitnessclub.data.widgets

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fitnessclub.data.Model.Data.Activity
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ActivityItem(activity: Activity, trainerName: String) {
    val startTime = Instant.ofEpochMilli(activity.startAt)
        .atZone(ZoneId.systemDefault())
        .toLocalTime()
    val endTime = startTime.plusSeconds(activity.time)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${startTime.format(DateTimeFormatter.ofPattern("HH:mm"))} - ${endTime.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = trainerName,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = activity.name,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.End
        )
    }
}

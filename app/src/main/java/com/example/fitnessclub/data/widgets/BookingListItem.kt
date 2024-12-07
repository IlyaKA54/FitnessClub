package com.example.fitnessclub.data.widgets

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fitnessclub.data.Model.Data.Booking
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookingListItem(
    booking: Booking,
    onDelete: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm")
    val startTime = Instant.ofEpochMilli(booking.startAt).atZone(ZoneId.systemDefault())
    val endTime = startTime.plusSeconds(booking.time)

    val startTimeFormatted = startTime.format(formatter)
    val endTimeFormatted = endTime.format(formatter)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier.padding(8.dp)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = if (booking.activityId != null) "group training" else "individual training",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$startTimeFormatted - $endTimeFormatted",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            if (booking.startAt > System.currentTimeMillis()) {
                IconButton(
                    onClick = { onDelete(booking.id) },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Delete Booking",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}


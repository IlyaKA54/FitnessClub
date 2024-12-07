package com.example.fitnessclub.data.View.MainScreen.ActivitiesLIstScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fitnessclub.data.ViewModel.ActivitiesListScreenViewModel
import com.example.fitnessclub.data.widgets.BookingListItem

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ActivitiesListScreenView(activitiesListScreenViewModel: ActivitiesListScreenViewModel) {
    val bookings by activitiesListScreenViewModel.bookings.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(16.dp)
        ) {
            Text(
                text = "Bookings",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center)
            )
            IconButton(
                onClick = { activitiesListScreenViewModel.navigateBack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(bookings) { booking ->
                BookingListItem(
                    booking = booking,
                    onDelete = { bookingId ->
                        activitiesListScreenViewModel.deleteBooking(bookingId)
                    }
                )
            }
        }
    }
}
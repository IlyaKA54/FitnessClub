package com.example.fitnessclub.data.widgets

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HorizontalCalendar(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {

}

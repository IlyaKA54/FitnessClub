package com.example.fitnessclub.data.widgets

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    initialDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate.toEpochMilli()
    )

    AlertDialog(
        modifier = Modifier.fillMaxWidth(1f).padding(0.dp),
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = {
                val selectedMillis = datePickerState.selectedDateMillis
                if (selectedMillis != null) {
                    val selectedDate =
                        Instant.ofEpochMilli(selectedMillis).atZone(ZoneId.systemDefault())
                            .toLocalDate()
                    onDateSelected(selectedDate)
                }
            }) {
                Text("Выбрать")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Отмена")
            }
        },
        text = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(1f) // Задаём ширину 90% от экрана
                    .padding(0.dp)
            ) {
                DatePicker(state = datePickerState)
            }
        }
    )
}


@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toEpochMilli(): Long {
    return atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

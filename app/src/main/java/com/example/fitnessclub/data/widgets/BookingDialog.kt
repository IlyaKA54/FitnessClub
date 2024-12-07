package com.example.fitnessclub.data.widgets

import android.app.TimePickerDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.util.Calendar
import java.util.Locale

@Composable
fun BookingDialog(
    onDismiss: () -> Unit,
    onBook: (Long) -> Unit
) {
    var selectedDate by remember { mutableStateOf<Calendar?>(null) }
    val context = LocalContext.current

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Выберите дату и время",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Выбор даты
                Button(
                    onClick = {
                        showDatePicker(context) { date ->
                            selectedDate = date
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = selectedDate?.let {
                        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        formatter.format(it.time)
                    } ?: "Выберите дату")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Выбор времени (доступен только после выбора даты)
                Button(
                    onClick = {
                        selectedDate?.let {
                            showTimePicker(context, it) { updatedDate ->
                                selectedDate = updatedDate
                            }
                        }
                    },
                    enabled = selectedDate != null,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = selectedDate?.let {
                        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
                        formatter.format(it.time)
                    } ?: "Выберите время")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = { onDismiss() }) {
                        Text(text = "Отмена")
                    }
                    Button(
                        onClick = {
                            selectedDate?.let {
                                if (it.timeInMillis > System.currentTimeMillis()) {
                                    onBook(it.timeInMillis)
                                } else {
                                    Toast.makeText(context, "Нельзя выбрать прошедшую дату", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        enabled = selectedDate != null
                    ) {
                        Text(text = "Записаться")
                    }
                }
            }
        }
    }


}

fun showDatePicker(context: Context, onDateSelected: (Calendar) -> Unit) {
    val currentDate = Calendar.getInstance()
    android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            onDateSelected(selectedDate)
        },
        currentDate.get(Calendar.YEAR),
        currentDate.get(Calendar.MONTH),
        currentDate.get(Calendar.DAY_OF_MONTH)
    ).show()
}

fun showTimePicker(context: Context, initialDate: Calendar, onTimeSelected: (Calendar) -> Unit) {
    TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            initialDate.set(Calendar.HOUR_OF_DAY, hourOfDay)
            initialDate.set(Calendar.MINUTE, minute)
            onTimeSelected(initialDate)
        },
        initialDate.get(Calendar.HOUR_OF_DAY),
        initialDate.get(Calendar.MINUTE),
        true
    ).show()
}


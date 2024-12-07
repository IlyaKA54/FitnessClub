package com.example.fitnessclub.data.View.MainScreen.Schedule

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnessclub.data.Model.Data.Activity
import com.example.fitnessclub.data.ViewModel.DatePickerViewModel
import com.example.fitnessclub.data.widgets.ActivityListItem
import com.example.fitnessclub.data.widgets.DatePickerDialog
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerScreenView(
    viewModel: DatePickerViewModel,
    onNavigate: (String, Activity) -> Unit
) {
    val selectedDate by viewModel.selectedDate.collectAsState()
    val activities by viewModel.activities.collectAsState()

    var showDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(16.dp)
        ) {
            Text(
                text = selectedDate?.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
                    ?: "Дата не выбрана",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )

            IconButton(
                onClick = { showDatePicker = true },
                modifier = Modifier.align(Alignment.CenterEnd).padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Settings",
                    tint = Color.White
                )
            }
        }

        // Список тренировок
        LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            items(activities) { activity ->
                ActivityListItem(
                    activity = activity,
                    onClick = { str ->
                        onNavigate("show_activity", str)
                    }
                )
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            initialDate = selectedDate ?: LocalDate.now(),
            onDateSelected = { date ->
                viewModel.setSelectedDate(date)
                showDatePicker = false
            },
            onDismissRequest = { showDatePicker = false }
        )
    }
}

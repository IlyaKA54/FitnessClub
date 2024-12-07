package com.example.fitnessclub.data.ViewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessclub.data.Model.ActivityRepository
import com.example.fitnessclub.data.Model.Data.Activity
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class DatePickerViewModel(private val _db: Firebase) : ViewModel() {
    private val activityRepository = ActivityRepository(_db)

    // Состояние для выбранной даты
    @RequiresApi(Build.VERSION_CODES.O)
    private val _selectedDate = MutableStateFlow<LocalDate>(LocalDate.now())
    @RequiresApi(Build.VERSION_CODES.O)
    val selectedDate: StateFlow<LocalDate?> = _selectedDate

    // Состояние для списка тренировок
    private val _activities = MutableStateFlow<List<Activity>>(emptyList())
    val activities: StateFlow<List<Activity>> = _activities

    init {
        loadActivitiesForSelectedDate(_selectedDate.value)
    }

    // Функция для установки выбранной даты
    @RequiresApi(Build.VERSION_CODES.O)
    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
        loadActivitiesForSelectedDate(date) // Загружаем тренировки для этой даты
    }

    // Функция для загрузки тренировок по выбранной дате
    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadActivitiesForSelectedDate(date: LocalDate) {
        viewModelScope.launch {
            val activities = activityRepository.getActivitiesByDate(date)
            _activities.value = activities
        }
    }
}

package com.example.fitnessclub.data.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessclub.data.Model.ActivityRepository
import com.example.fitnessclub.data.Model.BookingRepository
import com.example.fitnessclub.data.Model.Data.Activity
import com.example.fitnessclub.data.Model.TrainerRepository
import com.example.fitnessclub.data.View.MainScreen.MainScreenDataObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ActivityScreenViewModel(
    private val _db:Firebase,
    private val _navData:MainScreenDataObject
) : ViewModel() {

    private val bookingRepository = BookingRepository(_db)

}
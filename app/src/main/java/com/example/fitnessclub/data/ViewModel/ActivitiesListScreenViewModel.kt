package com.example.fitnessclub.data.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnessclub.data.Model.BookingRepository
import com.example.fitnessclub.data.Model.Data.Booking
import com.example.fitnessclub.data.View.MainScreen.MainScreenDataObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ActivitiesListScreenViewModel(
    private val _db:Firebase,
    private val _navData: MainScreenDataObject,
    private val onNavigateToBack:()->Unit):ViewModel() {

        private val bookingRepository = BookingRepository(_db)

    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
    val bookings: StateFlow<List<Booking>> = _bookings

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        loadBookings()
    }

    private fun loadBookings() {
        bookingRepository.getBookingsByUserId(
            _navData.uid,
            onSuccess = { bookingList ->
                _bookings.value = bookingList
            },
            onFailure = { exception ->
                _error.value = "Failed to load bookings: ${exception.message}"
            }
        )
    }

    fun deleteBooking(bookingId: String) {
        bookingRepository.deleteBooking(
            bookingId,
            onSuccess = {
                _bookings.value = _bookings.value.filter { it.id != bookingId }
            },
            onFailure = { exception ->
                _error.value = "Failed to delete booking: ${exception.message}"
            }
        )
    }


    fun navigateBack(){
        onNavigateToBack()
    }
}
package com.example.fitnessclub.data.ViewModel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.fitnessclub.data.Model.BookingRepository
import com.example.fitnessclub.data.Model.Data.Booking
import com.example.fitnessclub.data.Model.Data.Trainer
import com.example.fitnessclub.data.Model.ImageManager
import com.example.fitnessclub.data.View.MainScreen.MainScreenDataObject
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TrainerInfoViewModel(
    private val _db:Firebase,
    private val _navData:MainScreenDataObject,
    private val _trainer: Trainer?,
    private val _onNavigateToBack:()->Unit
):ViewModel() {
    private val bookingRepository = BookingRepository(_db)
    private val imageManager = ImageManager()

    private val _profileImage = MutableStateFlow<Bitmap?>(null)
    val profileImage: StateFlow<Bitmap?> = _profileImage

    val trainer = _trainer

    fun navigateBack() {
        _onNavigateToBack()
    }

    init {
        loadProfileImage()
    }

    private fun loadProfileImage() {
        _trainer?.profileImageId?.let { imageId ->
            _db.firestore.collection("imgs").document(imageId).get()
                .addOnSuccessListener { document ->
                    val base64String = document.getString("imageBase64")
                    _profileImage.value = base64String?.let { imageManager.base64ToBitmap(it) }
                }
                .addOnFailureListener { exception ->
                    Log.e("TrainerInfoViewModel", "Failed to load image: ${exception.message}")
                }
        }
    }

    fun bookTraining(dateTime: Long, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val booking = Booking(
            id = _db.firestore.collection("bookings").document().id,
            coachId = _trainer?.id,
            activityId = null,
            userId = _navData.uid,
            startAt = dateTime,
            time = 7200
        )
        bookingRepository.addBooking(booking, onSuccess, onFailure)
    }

}
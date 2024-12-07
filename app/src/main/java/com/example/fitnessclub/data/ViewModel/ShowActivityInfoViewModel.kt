package com.example.fitnessclub.data.ViewModel

import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnessclub.data.Model.BookingRepository
import com.example.fitnessclub.data.Model.Data.Activity
import com.example.fitnessclub.data.Model.Data.Booking
import com.example.fitnessclub.data.Model.Data.Trainer
import com.example.fitnessclub.data.Model.Data.User
import com.example.fitnessclub.data.Model.ImageManager
import com.example.fitnessclub.data.Model.TrainerRepository
import com.example.fitnessclub.data.Model.UserRepository
import com.example.fitnessclub.data.View.MainScreen.MainScreenDataObject
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.Instant
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
class ShowActivityInfoViewModel(
    private val _db: Firebase,
    private val _navData: MainScreenDataObject,
    private val onNavigateBack: () -> Unit,
    private val activity: Activity?
) : ViewModel() {
    private val userRepository = UserRepository(_db)
    private val trainerRepository = TrainerRepository(_db)
    private val bookingRepository = BookingRepository(_db)
    private val imageManager = ImageManager()

    private val _activityBind = MutableStateFlow<Activity?>(activity)
    val activityBind: StateFlow<Activity?> = _activityBind

    private val _trainer = MutableStateFlow<Trainer?>(null)
    val trainer: StateFlow<Trainer?> = _trainer

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _buttonText = MutableStateFlow<String>("")
    val buttonText: StateFlow<String> = _buttonText

    private val _trainerImage = MutableStateFlow<Bitmap?>(null)
    val trainerImage: StateFlow<Bitmap?> = _trainerImage

    private val _status = MutableStateFlow<Boolean>(true)

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        loadUser()
        loadTrainer(activity?.coachId.toString())
        setStatus()
    }

    private fun loadUser(){
        userRepository.getUserById(_navData.uid, onSuccess = { user ->
            _user.value = user
            if(user != null){
                calculateButtonText()
            }
        }, onFailure = { exception ->
            Log.e("loadUser", "Failed to load user data: ${exception.message}")
        })
    }

    private fun loadTrainer(trainerId: String) {
        trainerRepository.getTrainerById(
            trainerId,
            onSuccess = {trainer ->
                _trainer.value = trainer
                if(trainer != null){
                    trainer.profileImageId?.let { loadTrainerImage(it) }
                }
            },
            onFailure = {
            })
    }

    private fun loadTrainerImage(imageId: String) {
        _db.firestore.collection("imgs").document(imageId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val base64String = document.getString("imageBase64")
                    if(!base64String.isNullOrEmpty()){
                        _trainerImage.value = imageManager.base64ToBitmap(base64String)
                    }
                } else {
                    Log.d("MyLog", "Error117")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("MyLog", "Error121")
            }
    }

    private fun calculateButtonText() {
        val now = Instant.now().toEpochMilli()
        if (now >= activity?.startAt!!) {
            _buttonText.value = "Close"
        }
        else if(!_status.value){
            _buttonText.value = "Already Booked"
        }
        else {
            _buttonText.value = if (_user.value?.membershipId != null) {
                "Free"
            } else {
                "${activity.price}"
            }
        }
    }

    private fun setStatus() {
        val activity = activity ?: return

        bookingRepository.isUserAlreadyBooked(_navData.uid, activity.id, { alreadyBooked ->
            _status.value = alreadyBooked
        }, { exception ->
            // Handle error (optional)
        })
    }

    fun bookActivity() {
        val activity = activity ?: return
        val now = Instant.now().toEpochMilli()
        val timeStatus = now >= activity.startAt

        if (_status.value && !timeStatus) {
            val activity = activity ?: return

            val booking = Booking(
                id = UUID.randomUUID().toString(),
                coachId = activity.coachId,
                activityId = activity.id,
                userId = _navData.uid,
                startAt = activity.startAt,
                time = activity.time
            )
            bookingRepository.addBooking(booking,
                onSuccess = {onNavigateBack()},
                onFailure = {error->
                    Log.d("addBooking", error.message.toString())
                })
        }
    }

    fun navigateBack() {
        onNavigateBack()
    }
}